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
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 新建项目-项目概况表
 * </p>
 *
 * @author huo
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_newproject")
@ApiModel(value="Newproject对象", description="新建项目-项目概况表")
public class Newproject implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "所属类目(1直接空冷，2间接空冷，3水冷)")
    private Integer type;

    @ApiModelProperty(value = "版本")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "承担单位")
    private String bearerUnit;

    @ApiModelProperty(value = "报告主编")
    private String reportEditor;

    @ApiModelProperty(value = "报告编写")
    private String reportWriting;

    @ApiModelProperty(value = "现场低空探测")
    private String lowAltitudeObservation;

    @ApiModelProperty(value = "报告审核")
    private String reportAudit;

    @ApiModelProperty(value = "报告批准")
    private String reportApproval;

    @ApiModelProperty(value = "委托单位")
    private String entrustedUnit;

    @ApiModelProperty(value = "单位名称")
    private String unitName;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "电话")
    private String talephone;

    @ApiModelProperty(value = "邮政编码")
    private String postalCode;

    @ApiModelProperty(value = "观测站温度名称")
    private String guanWenname;

    @ApiModelProperty(value = "观测站风速名称")
    private String guanFengname;

    @ApiModelProperty(value = "参证站温度名称")
    private String canWenname;

    @ApiModelProperty(value = "参证站风速名称")
    private String canFengname;


}
