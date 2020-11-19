package com.atwzw.dmsservice.mapper;

import com.atwzw.dmsservice.entity.DmsVehicleMsg;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
public interface DmsVehicleMsgMapper extends BaseMapper<DmsVehicleMsg> {

    // 新增一条设备车辆信息
    Long insertDmsVehicleMsg(DmsVehicleMsg dmsVehicleMsg);

    // 根据设备 IMEI 号获取设备安装车辆信息
    DmsVehicleMsg selectVehicleMsgByDeviceImei(String deviceImei);

}
