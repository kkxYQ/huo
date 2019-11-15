package com.seven.service;

import com.seven.model.Title;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * <p>
 * 标题表 服务类
 * </p>
 *
 * @author huodian
 * @since 2019-11-07
 */
public interface ITitleService extends IService<Title> {
    /**
     * 工作概述
     * @return
     */
    Object workOverview();

    /**
     * 建设项目概况
     * @return
     */
    Object Construction();

    /**
     * 工作依据及委托
     * @return
     */
    Object WorkBasis();

    /**
     * 工作遵循的规程及标准
     * @return
     */
    Object Procedures();

    /**
     * 工作进程及主要研究内容
     * @return
     */
    Object WorkProcessanAndMainResearchContent();

    /**
     * 工作进程
     * @return
     */
    Object WorkProcessan();

    /**
     * 主要研究内容
     * @return
     */
    Object MainResearchContent();

    /**
     * 厂址空冷观测站
     * @return
     */
    Object SiteAirCoolingObservatory();

    /**
     * 厂址观测建设
     * @return
     */
    Object SiteObservationConstruction();

    /**
     * 观测仪器安装与鉴定
     * @return
     */
    Object InstallationAndQualification();

    /**
     * 观测情况说明
     * @return
     */
    Object Observations();


    /**
     * 系留气艇低空探测
     * @return
     */
    Object TetheredAirshipLowAltitudeDetection();

    /**
     * 观测仪器
     * @return
     */
    Object ObservationInstrument();

    /**
     * 主要技术指标
     * @return
     */
    Object MainSpecifications();


    /**
     * 探空仪
     * @return
     */
    Object Radiosonde();

    /**
     * 仪器检定
     * @return
     */

    Object InstrumentVerification();

    /**
     * 工作进程
     * @return
     */
    Object WorkProcess();

    /**
     * 观测方法和数据质量
     * @return
     */
    Object ObservationMethodsAndDataQuality();

    /**
     * 观测地点
     * @return
     */
    Object ObservationLocation();


    /**
     * 气象资料合理性检验
     * @return
     */
    Object Meteorological();
}
