package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author huo
 * @since 2019-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_observe_temperature")
@ApiModel(value="ObserveTemperature对象", description="")
public class ObserveTemperature implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作人id")
    private Long userId;

    @ApiModelProperty(value = "高度")
    private Double height;

    @ApiModelProperty(value = "表年月日")
    private Date day;

    @ApiModelProperty(value = "21时气温")
    private Double temperatureA21;

    @ApiModelProperty(value = "22时气温")
    private Double temperatureA22;

    @ApiModelProperty(value = "23时气温")
    private Double temperatureA23;

    @ApiModelProperty(value = "0时气温")
    private Double temperatureA0;

    @ApiModelProperty(value = "1时气温")
    private Double temperatureA1;

    @ApiModelProperty(value = "2时气温")
    private Double temperatureA2;

    @ApiModelProperty(value = "3时气温")
    private Double temperatureA3;

    @ApiModelProperty(value = "4时气温")
    private Double temperatureA4;

    @ApiModelProperty(value = "5时气温")
    private Double temperatureA5;

    @ApiModelProperty(value = "6时气温")
    private Double temperatureA6;

    @ApiModelProperty(value = "7时气温")
    private Double temperatureA7;

    @ApiModelProperty(value = "8时气温")
    private Double temperatureA8;

    @ApiModelProperty(value = "9时气温")
    private Double temperatureA9;

    @ApiModelProperty(value = "10时气温")
    private Double temperatureA10;

    @ApiModelProperty(value = "11时气温")
    private Double temperatureA11;

    @ApiModelProperty(value = "12时气温")
    private Double temperatureA12;

    @ApiModelProperty(value = "13时气温")
    private Double temperatureA13;

    @ApiModelProperty(value = "14时气温")
    private Double temperatureA14;

    @ApiModelProperty(value = "15时气温")
    private Double temperatureA15;

    @ApiModelProperty(value = "16时气温")
    private Double temperatureA16;

    @ApiModelProperty(value = "17时气温")
    private Double temperatureA17;

    @ApiModelProperty(value = "18时气温")
    private Double temperatureA18;

    @ApiModelProperty(value = "19时气温")
    private Double temperatureA19;

    @ApiModelProperty(value = "20时气温")
    private Double temperatureA20;


}
