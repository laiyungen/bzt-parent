package com.atwzw.dmsservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atwzw.dmsservice.entity.dto.DmsDeviceDetailsDto;
import com.atwzw.dmsservice.entity.dto.DmsDeviceListDto;
import com.atwzw.dmsservice.entity.vo.DeviceExportVo;
import com.atwzw.dmsservice.entity.vo.DmsDeviceQueryVo;
import com.atwzw.dmsservice.listener.DmsDeviceExcelListener;
import com.atwzw.dmsservice.mapper.DmsDeviceMapper;
import com.atwzw.dmsservice.service.DmsDeviceService;
import com.atwzw.servicebase.bean.DmsDevice;
import com.atwzw.servicebase.bean.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 设备表 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Service
public class DmsDeviceServiceImpl extends ServiceImpl<DmsDeviceMapper, DmsDevice> implements DmsDeviceService {

    @Override
    public IPage<DmsDeviceListDto> getDmsDeviceListByCodition(Page<DmsDeviceListDto> pageDevice, DmsDeviceQueryVo dmsDeviceQueryVo) {
        return baseMapper.selectDmsDeviceListByCondition(dmsDeviceQueryVo);
    }

    @Override
    public void batchImportDevice(MultipartFile multipartFile, DmsDeviceService dmsDeviceService, SysUser sysUser) {
        try {
            //文件输入流
            InputStream in = multipartFile.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, DmsDevice.class, new DmsDeviceExcelListener(dmsDeviceService, sysUser)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<DeviceExportVo> getDeviceListByUserId(List<Long> userIdList) {
        return baseMapper.selectDeviceListByUserIdList(userIdList);
    }

    @Override
    public DmsDeviceDetailsDto getDeviceDetailsByDeviceImei(String deviceImei) {
        return baseMapper.selectDeviceDetailsByDeviceImei(deviceImei);
    }
}
