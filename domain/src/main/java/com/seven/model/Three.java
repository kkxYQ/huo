package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 表1 3主要参数的合理范围参考值
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("one_three")
@ApiModel(value="Three对象", description="表1 3主要参数的合理范围参考值")
public class Three implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "气象要素")
    private String meteorologicalElement;

    @ApiModelProperty(value = "合理范围")
    private String reasonableRange;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户版本")
    private Long userVersion;


}
