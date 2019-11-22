package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * @since 2019-11-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_observe_wind")
@ApiModel(value="ObserveWind对象", description="")
public class ObserveWind implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作人")
    private Long userId;

    @ApiModelProperty(value = "时间年月日(表的时间)")
    private Date day;

    @ApiModelProperty(value = "高度(1.5米，10米，30米)")
    private Double height;

    @ApiModelProperty(value = "21时风方向(东南西北)")
    private String directionA21;

    @ApiModelProperty(value = "21时风速")
    private Double speedA21;

    @ApiModelProperty(value = "22时风方向")
    private String directionA22;

    @ApiModelProperty(value = "22时风速")
    private Double speedA22;

    @ApiModelProperty(value = "23时风方向")
    private String directionA23;

    @ApiModelProperty(value = "23时风速")
    private Double speedA23;

    @ApiModelProperty(value = "0时风方向")
    private String directionA0;

    @ApiModelProperty(value = "0时风速")
    private Double speedA0;

    @ApiModelProperty(value = "1时风方向")
    private String directionA1;

    @ApiModelProperty(value = "1时风速")
    private Double speedA1;

    @ApiModelProperty(value = "2时风方向")
    private String directionA2;

    @ApiModelProperty(value = "2时风速")
    private Double speedA2;

    @ApiModelProperty(value = "3时风方向")
    private String directionA3;

    @ApiModelProperty(value = "3时风速")
    private Double speedA3;

    @ApiModelProperty(value = "4时风方向")
    private String directionA4;

    @ApiModelProperty(value = "4时风速")
    private Double speedA4;

    @ApiModelProperty(value = "5时风方向")
    private String directionA5;

    @ApiModelProperty(value = "5时风速")
    private Double speedA5;

    @ApiModelProperty(value = "6时风方向")
    private String directionA6;

    @ApiModelProperty(value = "6时风速")
    private Double speedA6;

    @ApiModelProperty(value = "7时风方向")
    private String directionA7;

    @ApiModelProperty(value = "7时风速")
    private Double speedA7;

    @ApiModelProperty(value = "8时风方向")
    private String directionA8;

    @ApiModelProperty(value = "8时风速")
    private Double speedA8;

    @ApiModelProperty(value = "9时风方向")
    private String directionA9;

    @ApiModelProperty(value = "9时风速")
    private Double speedA9;

    @ApiModelProperty(value = "10时风方向")
    private String directionA10;

    @ApiModelProperty(value = "10时风速")
    private Double speedA10;

    @ApiModelProperty(value = "11时风方向")
    private String directionA11;

    @ApiModelProperty(value = "11时风速")
    private Double speedA11;

    @ApiModelProperty(value = "12时风方向")
    private String directionA12;

    @ApiModelProperty(value = "12时风速")
    private Double speedA12;

    @ApiModelProperty(value = "13时风方向")
    private String directionA13;

    @ApiModelProperty(value = "13时风速")
    private Double speedA13;

    @ApiModelProperty(value = "14时风方向")
    private String directionA14;

    @ApiModelProperty(value = "14时风速")
    private Double speedA14;

    @ApiModelProperty(value = "15时风方向")
    private String directionA15;

    @ApiModelProperty(value = "15时风速")
    private Double speedA15;

    @ApiModelProperty(value = "16时风方向")
    private String directionA16;

    @ApiModelProperty(value = "16时风速")
    private Double speedA16;

    @ApiModelProperty(value = "17时风方向")
    private String directionA17;

    @ApiModelProperty(value = "17时风速")
    private Double speedA17;

    @ApiModelProperty(value = "18时风方向")
    private String directionA18;

    @ApiModelProperty(value = "18时风速")
    private Double speedA18;

    @ApiModelProperty(value = "19时风方向")
    private String directionA19;

    @ApiModelProperty(value = "19时风速")
    private Double speedA19;

    @ApiModelProperty(value = "20时风方向")
    private String directionA20;

    @ApiModelProperty(value = "20时风速")
    private Double speedA20;


}
