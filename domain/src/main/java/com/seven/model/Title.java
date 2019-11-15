package com.seven.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 标题表
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_title")
@ApiModel(value="Title对象", description="标题表")
public class Title implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "一级标题id")
    private Long oneid;

    @ApiModelProperty(value = "二级标题id")
    private Long twoid;

    @ApiModelProperty(value = "三级标题id")
    private Long threeid;

    @ApiModelProperty(value = "标题名称")
    private String titleContent;

    @ApiModelProperty(value = "文本内容")
    private String content;

    @ApiModelProperty(value = "图片地址")
    private String imageAddress;


}
