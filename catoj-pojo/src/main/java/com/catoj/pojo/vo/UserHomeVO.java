package com.catoj.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "用户主页信息")
public class UserHomeVO {
    /**
     * 用户基本信息
     */
    @Schema(description = "用户id")
    private Long id;
    @Schema(description = "用户名")
    private String name;
    @Schema(description = "性别")
    private Integer gender;
    @Schema(description = "用户头像")
    private String avatar;

    /**
     * 用户关注与粉丝信息
     */
    @Schema(description = "关注的人数")
    private Integer followCount;
    @Schema(description = "被关注人数，即粉丝")
    private Integer fanCount;

    /**
     * 是否是我自己 是否已关注(不是我自己需要显示)
     */
    @Schema(description = "是否是我自己")
    private Boolean isMyself;
    @Schema(description = "是否已关注")
    private Boolean isFollowing;

    /**
     * (成就贡献) 获得点赞数 收藏数 声望等级 当前等级溢出量 升到下一级所需经验 是否满级
     */
    @Schema(description = "获得点赞数")
    private Integer likeCount;
    @Schema(description = "获得收藏数")
    private Integer favCount;
    @Schema(description = "当前声望等级")
    private Integer fameLevel;
    @Schema(description = "当前等级经验溢出量")
    private Integer fameExeOffset;
    @Schema(description = "升到下一级所需经验")
    private Integer needExe;

}
