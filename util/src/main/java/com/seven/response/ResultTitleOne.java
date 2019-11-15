package com.seven.response;

import com.seven.parent.graph.OneGraph;
import com.seven.parent.histogram.OneHistogram;
import com.seven.parent.linechart.OneLineChart;
import com.seven.parent.table.OneTable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
* @Description: 模板返回实体类
* @Param: 标题，文本内容，图片地址，各种图形数据
* @return: ResultTitle
* @Author: Mr.Y
* @Date: 2019/11/13
*/
@Data
@ApiModel(value="ResultTitle对象", description="返回的对象")
public class ResultTitleOne {

    @ApiModelProperty(value = "标题")
    private String title;
    @ApiModelProperty(value = "文本")
    private String content;
    @ApiModelProperty(value = "图片地址")
    private List<String> imageAddress;
    @ApiModelProperty(value = "返回的表格数据")
    private OneTable oneTable;
    @ApiModelProperty(value = "返回的柱状图数据")
    private OneHistogram oneHistogram;
    @ApiModelProperty(value = "返回的折线图数据")
    private OneLineChart oneLineChart;
    @ApiModelProperty(value = "返回的曲线图数据")
    private OneGraph oneGraph;
    @ApiModelProperty(value = "返回的风玫瑰图数据")
    private com.seven.parent.rosefigure.oneRoseFigure oneRoseFigure;

}
