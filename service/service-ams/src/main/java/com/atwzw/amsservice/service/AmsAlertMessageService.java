package com.atwzw.amsservice.service;

import com.atwzw.amsservice.entity.AmsAlertMessage;
import com.atwzw.amsservice.entity.vo.AmsAlertMsgQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 报警信息表 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
public interface AmsAlertMessageService extends IService<AmsAlertMessage> {

    // 根据条件分页查询报警信息列表的构建 QueryWrapper 对象
    QueryWrapper<AmsAlertMessage> createPageAlertMsgConditionQueryWrapper(AmsAlertMsgQueryVo amsAlertMsgQueryVo);
}
