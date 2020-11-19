package com.atwzw.dmsservice.mapper;

import com.atwzw.dmsservice.entity.dto.DmsDeviceDetailsDto;
import com.atwzw.dmsservice.entity.dto.DmsDeviceListDto;
import com.atwzw.dmsservice.entity.vo.DeviceExportVo;
import com.atwzw.dmsservice.entity.vo.DmsDeviceQueryVo;
import com.atwzw.servicebase.bean.DmsDevice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
public interface DmsDeviceMapper extends BaseMapper<DmsDevice> {

    // 根据条件分页查询设备数据
    IPage<DmsDeviceListDto> selectDmsDeviceListByCondition(DmsDeviceQueryVo dmsDeviceQueryVo);

    // 根据用户 ID 获取该用户下的所有设备(用户设备信息 excel 的导出)
    List<DeviceExportVo> selectDeviceListByUserIdList(List<Long> userIdList);

    // 根据设备 IMEI 号获取设备详情信息
    DmsDeviceDetailsDto selectDeviceDetailsByDeviceImei(@Param("deviceImei") String deviceImei);

}
