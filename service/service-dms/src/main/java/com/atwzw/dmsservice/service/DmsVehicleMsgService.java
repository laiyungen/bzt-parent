package com.atwzw.dmsservice.service;

import com.atwzw.dmsservice.entity.DmsVehicleMsg;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 车辆信息表 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
public interface DmsVehicleMsgService extends IService<DmsVehicleMsg> {

    // 新增设备车辆信息
    Long saveDmsHehicleMsg(DmsVehicleMsg dmsVehicleMsg);

    // 根据设备 IMEI 号获取设备安装车辆信息
    DmsVehicleMsg getDmsVehicleMsgByDeviceImei(String deviceImei);

}
