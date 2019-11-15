package com.seven.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.seven.model.Title;
import com.seven.service.ITitleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标题表 前端控制器
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@RestController
@RequestMapping("/title")
@Api(value="目录一",description = "查询目录一模板信息")
public class TitleController {

    @Autowired
    private ITitleService titleService;

    @ApiOperation("工作概况1.0.0")
    @GetMapping("/work")
    public Object workOverview(){
        return titleService.workOverview();
    }
    @ApiOperation("建设项目概况1.1.0")
    @GetMapping("/con")
    public Object Construction(){
        return titleService.Construction(); }
    @ApiOperation("工作依据及委托1.1.1")
    @GetMapping("/wokeBase")
    public Object WorkBasis(){
        return titleService.WorkBasis();
    }
    @ApiOperation("工作遵循的规程及标准1.1.2")
    @GetMapping("/pro")
    public Object Procedures(){
        return titleService.Procedures();
    }
    @ApiOperation("工作进程及主要研究内容1.2.0")
    @GetMapping("/wokrPro")
    public Object WorkProcessanAndMainResearchContent(){
        return titleService.WorkProcessanAndMainResearchContent();
    }
    @ApiOperation("工作进程1.2.1")
    @GetMapping("/wokeProce")
    public Object WorkProcessan(){
        return titleService.WorkProcessan();
    }
    @ApiOperation("主要研究内容1.2.2")
    @GetMapping("/main")
    public Object MainResearchContent(){
        return titleService.MainResearchContent();
    }
    @ApiOperation("厂址空冷观测站建设及对比观测1.3.0")
    @GetMapping("/siteAir")
    public Object SiteAirCoolingObservatory(){
        return titleService.SiteAirCoolingObservatory();
    }
    @ApiOperation("厂址观测站建设1.3.1")
    @GetMapping("/siteObsetvation")
    public Object SiteObservationConstruction(){
        return titleService.SiteObservationConstruction();
    }
    @ApiOperation("观测仪器安装与鉴定1.3.2")
    @GetMapping("/inst")
    public Object InstallationAndQualification(){
        return titleService.InstallationAndQualification();
    }
    @ApiOperation("观测情况说明1.3.3")
    @GetMapping("/observations")
    public Object Observations(){
        return titleService.Observations();
    }
    @ApiOperation("系留气艇低空探测1.4.0")
    @GetMapping("/tethered")
    public Object TetheredAirshipLowAltitudeDetection(){
        return titleService.TetheredAirshipLowAltitudeDetection();
    }
    @ApiOperation("观测仪器1.4.1")
    @GetMapping("/observation")
    public Object ObservationInstrument(){ return titleService.ObservationInstrument(); }
    @ApiOperation("主要技术指标1.4.2")
    @GetMapping("/mainSpec")
    public Object MainSpecifications(){
        return titleService.MainSpecifications();
    }
    @ApiOperation("探空仪1.4.3")
    @GetMapping("/radi")
    public Object Radiosonde(){
        return titleService.Radiosonde();
    }
    @ApiOperation("仪器的检定1.4.4")
    @GetMapping("/instruMent")
    public Object InstrumentVerification() {
        return titleService.InstrumentVerification();
    }
    @ApiOperation("工作进程1.4.5")
    @GetMapping("/workProcess")
    public Object WorkProcess() {
        return titleService.WorkProcess();
    }
    @ApiOperation("观测方法和数据质量1.4.6")
    @GetMapping("/observationM")
    public Object ObservationMethodsAndDataQuality(){
        return titleService.ObservationMethodsAndDataQuality();
    }
    @ApiOperation("观测地点1.4.7")
    @GetMapping("/observationL")
    public Object ObservationLocation() {
        return titleService.ObservationLocation();
    }
    @ApiOperation("气象资料合理性检验1.4.8")
    @GetMapping("/mete")
    public Object Meteorological() {
        return titleService.Meteorological();
    }

}
