package com.catoj.server.service.Impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.catoj.common.constants.StatusConstant;
import com.catoj.common.utils.BeanUtils;
import com.catoj.common.utils.CollUtils;
import com.catoj.common.utils.ThreadLocalUtil;
import com.catoj.pojo.dto.PageDTO;
import com.catoj.pojo.entity.*;
import com.catoj.pojo.query.ProblemQuery;
import com.catoj.pojo.vo.ProblemDetailVO;
import com.catoj.pojo.vo.ProblemIoVO;
import com.catoj.pojo.vo.ProblemPageVO;
import com.catoj.pojo.vo.ProblemTagsVO;
import com.catoj.server.mapper.*;
import com.catoj.server.service.ICodeProblemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目表 服务实现类
 * </p>
 *
 * @author rance
 * @since 2026-06-19
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CodeProblemServiceImpl extends ServiceImpl<CodeProblemMapper, CodeProblem> implements ICodeProblemService {

    private final CodeProblemMapper problemMapper;
    private final ProblemTagRelationMapper tagRelationMapper;
    private final ProblemTagMapper problemTagMapper;
    private final ProblemIoMapper problemIoMapper;
    private final ProblemIoImgMapper problemIoImgMapper;

    @Override
    public PageDTO<ProblemPageVO> pageQueryProblem(ProblemQuery query) {
        // 1. 构建分页（默认按创建时间降序）
        Page<CodeProblem> page = query.toMpPageDefaultSortByCreateTimeDesc();

        // 2. 构建查询条件
        LambdaQueryWrapper<CodeProblem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CodeProblem::getCodeStatus, 1);

        if (StrUtil.isNotBlank(query.getTitle())) {
            wrapper.like(CodeProblem::getTitle, query.getTitle());
        }
        if (query.getDifficulty() != null) {
            wrapper.eq(CodeProblem::getDifficulty, query.getDifficulty());
        }

        // 3. 执行查询
        Page<CodeProblem> result = problemMapper.selectPage(page, wrapper);

        if (result.getRecords().isEmpty()) {
            return PageDTO.empty(result);
        }

        // 4. 获取题目ID列表
        List<Long> problemIds = result.getRecords().stream()
                .map(CodeProblem::getId)
                .collect(Collectors.toList());

        // 5. 批量查询标签（根据 isShowTag 决定是否查询）
        Map<Long, List<String>> tagMap;
        if (Boolean.TRUE.equals(query.getIsShowTag())) {
            tagMap = getTagMap(problemIds);
        } else {
            tagMap = new HashMap<>();
        }

        // 6. 转换 VO
        List<ProblemPageVO> voList = result.getRecords().stream()
                .map(p -> {
                    ProblemPageVO vo = new ProblemPageVO();
                    vo.setId(p.getId());
                    vo.setProblemNo(p.getProblemNo());
                    vo.setTitle(p.getTitle());
                    vo.setDifficulty(p.getDifficulty().getCode());
                    vo.setPassRate(p.getPassRate());
                    vo.setTagList(tagMap.getOrDefault(p.getId(), Collections.emptyList()));
                    vo.setIsMyFav(false);
                    vo.setIsAc(false);
                    return vo;
                })
                .collect(Collectors.toList());

        return PageDTO.of(result, voList);
    }

    @Override
    public ProblemDetailVO getProblemDetail(Long id) {
        // 1. 查询题目
        CodeProblem problem = problemMapper.selectById(id);
        if (problem == null) {
            throw new RuntimeException("题目不存在");
        }

        // 2. 查询 IO
        List<ProblemIo> ioList = problemIoMapper.selectList(
                new LambdaQueryWrapper<ProblemIo>()
                        .eq(ProblemIo::getQuestionId, id)
                        .eq(ProblemIo::getDeleted, false)
                        .orderByAsc(ProblemIo::getSort)
        );

        // 3. 查询 IO 图片
        List<Long> ioIds = ioList.stream().map(ProblemIo::getId).collect(Collectors.toList());
        Map<Long, List<String>> imgMap;
        if (!ioIds.isEmpty()) {
            List<ProblemIoImg> imgs = problemIoImgMapper.selectList(
                    new LambdaQueryWrapper<ProblemIoImg>()
                            .in(ProblemIoImg::getIoId, ioIds)
                            .eq(ProblemIoImg::getDeleted, false)
                            .orderByAsc(ProblemIoImg::getSort)
            );
            imgMap = imgs.stream()
                    .collect(Collectors.groupingBy(
                            ProblemIoImg::getIoId,
                            Collectors.mapping(ProblemIoImg::getUrl, Collectors.toList())
                    ));
        } else {
            imgMap = Collections.emptyMap();
        }

        // 4. 用 BeanUtils 复制题目
        ProblemDetailVO vo = BeanUtils.copyBean(problem, ProblemDetailVO.class);
        if (BeanUtils.isNotEmpty(vo) && vo!=null) {
            // 手动设置 difficulty 的 code
            vo.setDifficulty(problem.getDifficulty().getCode());

            // 5. IO 转 VO（用 BeanUtils 批量复制）
            List<ProblemIoVO> ioVOList = ioList.stream().map(io -> {
                ProblemIoVO ioVO = BeanUtils.copyBean(io, ProblemIoVO.class);
                if (ioVO != null) {
                    ioVO.setUrl(imgMap.getOrDefault(io.getId(), Collections.emptyList()));
                }
                return ioVO;
            }).collect(Collectors.toList());
            vo.setProblemIoVOList(ioVOList);

            //TODO 用户状态（待实现）
            vo.setIsAc(false);
            vo.setIsMyFav(false);
            vo.setIsMyLike(false);
           //TODO 点赞数 评论数
        }


        return vo;
    }

    @Override
    public List<ProblemTagsVO> getProblemTags(Long problemId) {
        // 1. 查询关联关系
        List<ProblemTagRelation> relations = tagRelationMapper.selectList(
                new LambdaQueryWrapper<ProblemTagRelation>()
                        .eq(ProblemTagRelation::getProblemId, problemId)
        );

        if (CollUtils.isEmpty(relations)) {
            return CollUtils.emptyList();
        }

        // 2. 获取标签ID列表
        List<Long> tagIds = relations.stream()
                .map(ProblemTagRelation::getTagId)
                .collect(Collectors.toList());

        // 3. 查询标签
        List<ProblemTag> tags = problemTagMapper.selectList(
                new LambdaQueryWrapper<ProblemTag>()
                        .in(ProblemTag::getId, tagIds)
                        .eq(ProblemTag::getDeleted, false)
        );

        // 4. 转 VO
        return tags.stream().map(t -> {
            ProblemTagsVO vo = new ProblemTagsVO();
            vo.setId(t.getId());
            vo.setTagName(t.getTagName());
            return vo;
        }).collect(Collectors.toList());
    }

    private Map<Long, List<String>> getTagMap(List<Long> problemIds) {
        if (problemIds.isEmpty()) {
            return Collections.emptyMap();
        }

        // 1. 查询关联关系
        LambdaQueryWrapper<ProblemTagRelation> relationWrapper = new LambdaQueryWrapper<>();
        relationWrapper.in(ProblemTagRelation::getProblemId, problemIds);
        List<ProblemTagRelation> relations = tagRelationMapper.selectList(relationWrapper);

        if (relations.isEmpty()) {
            return Collections.emptyMap();
        }

        // 2. 获取标签ID列表
        List<Long> tagIds = relations.stream()
                .map(ProblemTagRelation::getTagId)
                .distinct()
                .collect(Collectors.toList());

        // 3. 查询标签名称
        LambdaQueryWrapper<ProblemTag> tagWrapper = new LambdaQueryWrapper<>();
        tagWrapper.in(ProblemTag::getId, tagIds)
                .eq(ProblemTag::getDeleted, StatusConstant.Deleted.NOT);
        List<ProblemTag> tags = problemTagMapper.selectList(tagWrapper);

        Map<Long, String> tagNameMap = tags.stream()
                .collect(Collectors.toMap(ProblemTag::getId, ProblemTag::getTagName));

        // 4. 组装
        Map<Long, List<String>> result = new HashMap<>();
        for (ProblemTagRelation relation : relations) {
            String tagName = tagNameMap.get(relation.getTagId());
            if (tagName != null) {
                result.computeIfAbsent(relation.getProblemId(), k -> new ArrayList<>())
                        .add(tagName);
            }
        }
        return result;
    }
}