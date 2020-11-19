package com.atwzw.aclservice.service;

import com.atwzw.aclservice.entity.dto.SysUserListDto;
import com.atwzw.aclservice.entity.vo.UserExportVo;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.aclservice.entity.dto.SysUserTreeDto;
import com.atwzw.aclservice.entity.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户服务类
 */
public interface SysUserService extends IService<SysUser> {

    // 从数据库中取出用户信息
    SysUser selectByUsername(String username);

    // 根据条件获取用户列表
    IPage<SysUserListDto> getSysUserListByCodition(Page<SysUserListDto> page, SysUserQueryVo sysUserQueryVo);

    // 重置密码
    Boolean updateResetUserPwd(Long id);

    // 验证修改密码用户旧密码是否正确
    boolean checkOldPwd(long id, String oldPwd);

    // 修改密码
    boolean updateSysUserPwd(long id, String newPwd);

    // 根据用户 ID 获取子集用户(用户树)
    SysUserTreeDto getSysUserTreeList(long id);

    // 根据当前登录用户获取其所有的子集用户 ID 集合
    List<Long> getLonginUserWithSubUserIdList(SysUser sysUser);

    // 根据父级用户 ID 查询出所有下级用户信息集合
    List<Long> getAllChildrenSysUserListByPid(Long userId, List<Long> userIdList);

    // 批量导入用户信息
    void batchImportUser(MultipartFile multipartFile, SysUserService sysUserService, SysUser paramUser,Integer userType);

    // 根据用户 ID 集合获取用户信息
    List<UserExportVo> getUserByUserIdList(List<Long> userIdList);

    // 删除用户(只删除无下级用户与属下无设备的用户)
    void removeUserByUserId(long id);
}
