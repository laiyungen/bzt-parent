package com.atwzw.amsservice.service.impl;

import com.atwzw.amsservice.client.SysClient;
import com.atwzw.amsservice.entity.AmsAlertMessage;
import com.atwzw.amsservice.entity.vo.AmsAlertMsgQueryVo;
import com.atwzw.amsservice.mapper.AmsAlertMessageMapper;
import com.atwzw.amsservice.service.AmsAlertMessageService;
import com.atwzw.commonutils.RestResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 报警信息表 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Service
public class AmsAlertMessageServiceImpl extends ServiceImpl<AmsAlertMessageMapper, AmsAlertMessage> implements AmsAlertMessageService {

    @Autowired
    private SysClient sysClient;

    @Override
    public QueryWrapper<AmsAlertMessage> createPageAlertMsgConditionQueryWrapper(AmsAlertMsgQueryVo amsAlertMsgQueryVo) {
        // 构建条件：创建 QueryWrapper
        QueryWrapper<AmsAlertMessage> bztAlertMsgQueryWrapper = new QueryWrapper<>();
        // 多条件组合查询
        // 用户 ID
        Long userId = amsAlertMsgQueryVo.getUserId();
        // 设备 IMEI 号
        String deviceImei = amsAlertMsgQueryVo.getDeviceImei();
        // 报警类型
        Integer typeId = amsAlertMsgQueryVo.getTypeId();
        // 是否包含下级用户
        Boolean isContainSub = amsAlertMsgQueryVo.getIsContainSub();
        // 报警开始时间
        String startTimeStr = amsAlertMsgQueryVo.getStartTimeStr();
        // 报警结束时间
        String endTimeStr = amsAlertMsgQueryVo.getEndTimeStr();

        if (!StringUtils.isEmpty(deviceImei)) {
            bztAlertMsgQueryWrapper.like("device_imei", deviceImei);
        }
        if (typeId != null) {
            bztAlertMsgQueryWrapper.eq("type_id", typeId);
        }
        if (!isContainSub && userId != null) {
            bztAlertMsgQueryWrapper.eq("user_id", userId);
        } else {
            // 通过当前用户 ID 获取其所有下级用户 ID 集合(需要远程调用用户权限服务获取所有下级用户 ID 集合)
            RestResult restResult = sysClient.getAllChildrenSysUserListByPid(userId);
            List<Long> userIdList = (List<Long>) restResult.getData().get("userIdList");
            userIdList.add(userId);
            bztAlertMsgQueryWrapper.in("user_id",userIdList);
        }
        if (startTimeStr != null && startTimeStr != ""){
            bztAlertMsgQueryWrapper.ge("msg_time",startTimeStr);
        }
        if (endTimeStr != null && endTimeStr != ""){
            bztAlertMsgQueryWrapper.le("msg_time",endTimeStr);
        }

        // 排序：默认报警时间从大到小
        bztAlertMsgQueryWrapper.orderByDesc("msg_time");
        return bztAlertMsgQueryWrapper;
    }



}
