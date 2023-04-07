package com.muchenlou.phototovideo.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author muchenlou
 * @since 2023-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("video_details")
public class VideoDetailsDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * uuid
     */
    private String uuid;

    /**
     * user_id
     */
    private Integer userId;

    /**
     * 视频地址
     */
    private String videoUrl;

    /**
     * 视频名称
     */
    private String videoName;

    /**
     * 生成时间
     */
    private Date createTime;

    /**
     * 视频生成时间
     */
    private Date videoCreateTime;

    /**
     * 状态：0:未完成，1:已完成 2:失败
     */
    private Integer status;

    /**
     * 失败原因（程序员看）
     */
    private String msg;

    /**
     * 失败原因（用户看）
     */
    private String userMsg;

    /**
     * 用户提交的图片地址
     */
    private String imageUrl;

    /**
     * 模型id
     */
    private Integer modelsId;

}
