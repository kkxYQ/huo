package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表1 1主要工作进程表
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("one_one")
@ApiModel(value="One对象", description="表1 1主要工作进程表")
public class One implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "时段")
    private String time;

    @ApiModelProperty(value = "主要工作内容")
    private String contnt;

    @ApiModelProperty(value = "完成投标工作")
    @TableField("Completion")
    private String Completion;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户版本")
    private Long userVersion;


}
