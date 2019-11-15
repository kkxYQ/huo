package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表1 5主要参数的合理变化参考值
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("one_fives")
@ApiModel(value="Fives对象", description="表1 5主要参数的合理变化参考值")
public class Fives implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "气象要素")
    private String meteorologicalElement;

    @ApiModelProperty(value = "合理相关性")
    private String reasonableCorrelation;

    @ApiModelProperty(value = "用户Id")
    private Long userId;

    @ApiModelProperty(value = "用户版本")
    private Long userVersion;


}
