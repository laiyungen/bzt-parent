package com.atwzw.dmsservice.service;

import com.atwzw.dmsservice.entity.dto.DmsDeviceDetailsDto;
import com.atwzw.dmsservice.entity.dto.DmsDeviceListDto;
import com.atwzw.dmsservice.entity.vo.DeviceExportVo;
import com.atwzw.dmsservice.entity.vo.DmsDeviceQueryVo;
import com.atwzw.servicebase.bean.DmsDevice;
import com.atwzw.servicebase.bean.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 设备表 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
public interface DmsDeviceService extends IService<DmsDevice> {

    // 根据条件分页查询设备数据
    IPage<DmsDeviceListDto> getDmsDeviceListByCodition(Page<DmsDeviceListDto> pageDevice, DmsDeviceQueryVo dmsDeviceQueryVo);

    // 批量导入设备
    void batchImportDevice(MultipartFile multipartFile, DmsDeviceService dmsDeviceService, SysUser sysUser);

    // 根据用户 ID 获取该用户下的所有设备(用户设备信息 excel 的导出)
    List<DeviceExportVo> getDeviceListByUserId(List<Long> userIdList);

    // 根据设备 IMEI 号获取设备详情信息
    DmsDeviceDetailsDto getDeviceDetailsByDeviceImei(String deviceImei);
}
