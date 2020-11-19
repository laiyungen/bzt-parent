package com.atwzw.aclservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atwzw.aclservice.client.DmsClient;
import com.atwzw.aclservice.entity.dto.SysUserListDto;
import com.atwzw.aclservice.entity.vo.SysUserQueryVo;
import com.atwzw.aclservice.entity.vo.UserExportVo;
import com.atwzw.aclservice.listener.SmsUserExcelListener;
import com.atwzw.commonutils.RestResult;
import com.atwzw.commonutils.ResultCodeEnum;
import com.atwzw.servicebase.bean.DmsDevice;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.aclservice.entity.dto.SysUserTreeDto;
import com.atwzw.aclservice.mapper.SysUserMapper;
import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.security.security.DefaultPasswordEncoder;
import com.atwzw.servicebase.exceptionhandler.BztException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户服务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;

    @Autowired
    private DmsClient dmsClient;

    @Override
    public SysUser selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public IPage<SysUserListDto> getSysUserListByCodition(Page<SysUserListDto> page, SysUserQueryVo sysUserQueryVo) {

        // 是否包含下级用户
        Boolean isContainSub = sysUserQueryVo.getIsContainSub();
        List<Long> userIdList = new ArrayList<>();
        Long userId = sysUserQueryVo.getId();
        if (isContainSub != null && isContainSub) {
            // 递归查找所有下级用户 ID 集合
            userIdList = this.getAllChildrenSysUserListByPid(sysUserQueryVo.getId(), userIdList);
        } else {
            userIdList.add(userId);
            userIdList.addAll(baseMapper.selectChildrenUserIdListByPId(userId));
        }
        sysUserQueryVo.setUserIdList(userIdList);
        sysUserQueryVo.setId(null);
        return baseMapper.selectSysUserListByCodition(page, sysUserQueryVo);
    }

    // 根据父级用户 ID 查询出所有下级用户信息集合
    @Override
    public List<Long> getAllChildrenSysUserListByPid(Long userId, List<Long> userIdList) {
        userIdList.add(userId);
        List<Long> sysUserList = baseMapper.selectChildrenUserIdListByPId(userId);
        if (sysUserList.size() > 0) {
            for (Long id : sysUserList) {
                this.getAllChildrenSysUserListByPid(id, userIdList);
            }
        }
        return userIdList;
    }

    @Override
    public Boolean updateResetUserPwd(Long id) {
        String pwd = defaultPasswordEncoder.encode("123456");
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setPassword(pwd);
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public boolean checkOldPwd(long id, String oldPwd) {
        String oldPwdEncode = defaultPasswordEncoder.encode(oldPwd);
        SysUser sysUser = baseMapper.selectById(id);
        if (oldPwdEncode.equals(sysUser.getPassword())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateSysUserPwd(long id, String newPwd) {
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setPassword(defaultPasswordEncoder.encode(newPwd));
        return baseMapper.updateById(sysUser) > 0;
    }

    @Override
    public SysUserTreeDto getSysUserTreeList(long id) {
        SysUser sysUser = baseMapper.selectById(id);
        SysUserTreeDto btd = new SysUserTreeDto();
        BeanUtils.copyProperties(sysUser,btd);
        if (sysUser != null) {
            List<SysUserTreeDto> sysUserTreeDtoList = baseMapper.batchSubUserTree(id);
            for (SysUserTreeDto sysUserTreeDto : sysUserTreeDtoList){
                sysUserTreeDto.setChildren(this.getChildrenSysUser(sysUserTreeDto));
            }
            btd.setChildren(sysUserTreeDtoList);
        }
        return btd;
    }

    private List<SysUserTreeDto> getChildrenSysUser(SysUserTreeDto sysUserTreeDto){
        List<SysUserTreeDto> batchSubUserTree = baseMapper.batchSubUserTree(sysUserTreeDto.getUserId());
        for (SysUserTreeDto btd:batchSubUserTree){
            btd.setChildren(this.getChildrenSysUser(btd));
        }
        return batchSubUserTree;
    }

    @Override
    public List<Long> getLonginUserWithSubUserIdList(SysUser sysUser) {
        List<Long> userIdList = new ArrayList<>();
        // 递归查找所有下级用户 ID 集合
        userIdList = this.getAllChildrenSysUserListByPid(sysUser.getId(), userIdList);
        return userIdList;
    }

    @Override
    public void batchImportUser(MultipartFile multipartFile, SysUserService sysUserService, SysUser paramUser,Integer userType) {

        try {
            //文件输入流
            InputStream in = multipartFile.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SysUser.class,new SmsUserExcelListener(sysUserService, paramUser,userType)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<UserExportVo> getUserByUserIdList(List<Long> userIdList) {
        return baseMapper.selectUserByUserIdList(userIdList);
    }

    @Override
    public void removeUserByUserId(long id) {
        // 首先：判断该用户下是否有下级用户
        List<SysUserTreeDto> sysUserTreeDtos = baseMapper.batchSubUserTree(id);
        List<DmsDevice> dmsDeviceList = null;
        if (sysUserTreeDtos.size() == 0){
            // 远程调用设备模块微服务接口：根据用户 ID 获取旗下的设备
            RestResult restResult = dmsClient.getDeviceByUserId(id);
            if (restResult.getCode() == 20001){
                throw new BztException(ResultCodeEnum.CD285.getCode(),ResultCodeEnum.CD285.getMsg());
            }
            dmsDeviceList = (List<DmsDevice>) restResult.getData().get("deviceList");
            if (dmsDeviceList.size() > 0){
                throw new BztException(ResultCodeEnum.CD506.getCode(),ResultCodeEnum.CD506.getMsg());
            }
        }else {
            throw new BztException(ResultCodeEnum.CD505.getCode(),ResultCodeEnum.CD505.getMsg());
        }
    }

}
