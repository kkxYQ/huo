package com.seven.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.EAN;

/**
 * <p>
 * 目录表
 * </p>
 *
 * @author huo
 * @since 2019-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_category")
@ApiModel(value="Category对象", description="目录表")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，编号")
    private Long id;

    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "一级节点")
    private Integer s1;

    @ApiModelProperty(value = "二级节点")
    private Integer s2;

    @ApiModelProperty(value = "三级节点")
    private Integer s3;

    @ApiModelProperty(value = "返回的子目录")
    private List<Category> children=new ArrayList<>();


}
