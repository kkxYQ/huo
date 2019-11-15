package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_code")
@ApiModel(value="Code对象", description="")
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ids")
    @TableId(value = "ids", type = IdType.AUTO)
    private Long ids;

    @ApiModelProperty(value = "电话号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "请求次数")
    private Integer amount;

    @ApiModelProperty(value = "请求ip地址")
    private String ip;

    @ApiModelProperty(value = "添加时间")
    private Date createTime;


}
