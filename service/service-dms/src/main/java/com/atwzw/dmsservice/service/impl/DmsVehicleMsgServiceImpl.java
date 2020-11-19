package com.atwzw.dmsservice.service.impl;

import com.atwzw.dmsservice.entity.DmsVehicleMsg;
import com.atwzw.dmsservice.mapper.DmsVehicleMsgMapper;
import com.atwzw.dmsservice.service.DmsVehicleMsgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 车辆信息表 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-11-05
 */
@Service
public class DmsVehicleMsgServiceImpl extends ServiceImpl<DmsVehicleMsgMapper, DmsVehicleMsg> implements DmsVehicleMsgService {

    @Override
    public Long saveDmsHehicleMsg(DmsVehicleMsg dmsVehicleMsg) {
        return baseMapper.insertDmsVehicleMsg(dmsVehicleMsg);
    }

    @Override
    public DmsVehicleMsg getDmsVehicleMsgByDeviceImei(String deviceImei) {
        return baseMapper.selectVehicleMsgByDeviceImei(deviceImei);
    }
}
