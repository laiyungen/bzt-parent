package com.atwzw.aclservice.controller;


import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.atwzw.aclservice.entity.dto.SysUserListDto;
import com.atwzw.aclservice.entity.dto.SysUserTreeDto;
import com.atwzw.aclservice.entity.vo.SysUserQueryVo;
import com.atwzw.aclservice.entity.vo.UserExportVo;
import com.atwzw.aclservice.service.SysRoleService;
import com.atwzw.aclservice.service.SysUserService;
import com.atwzw.commonutils.RestResult;
import com.atwzw.commonutils.utils.EasyExcelUtils;
import com.atwzw.commonutils.utils.FileUploadUtils;
import com.atwzw.commonutils.utils.MD5Utils;
import com.atwzw.servicebase.bean.SysUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(description = "用户处理器")
@RestController
@RequestMapping("/admin/acl/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "获取管理用户分页列表")
    @PostMapping("{page}/{limit}")
    public RestResult index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam(name = "sysUserQueryVo", value = "查询用户条件对象") @RequestBody(required = false) SysUserQueryVo sysUserQueryVo) {
        // 创建 page 对象
        Page<SysUserListDto> pageUser = new Page<>(page, limit);

        // 执行查询
        IPage<SysUserListDto> sysUserListByCodition = sysUserService.getSysUserListByCodition(pageUser, sysUserQueryVo);

        // 从分页对象 page 中获取数据总数和用户数据集合
        long total = sysUserListByCodition.getTotal();
        List<SysUserListDto> sysUserList = sysUserListByCodition.getRecords();

        return RestResult.success().data("total", total).data("items", sysUserList);
    }

    @ApiOperation(value = "上传用户头像")
    @PostMapping("uploadAvatar")
    public RestResult uploadAvatar(MultipartFile file) {
        // 区分文件组
        String fileGroupStr = "head portrait";
        //获取上传文件  MultipartFile 返回上传到oss的路径
        String headPortraitUrl = FileUploadUtils.fileUpload(file, fileGroupStr);
        return RestResult.success().data("headPortraitUrl", headPortraitUrl);
    }

    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public RestResult save(@ApiParam(name = "sysUser", value = "新增用户信息对象", required = true) @RequestBody SysUser sysUser) {
        sysUser.setPassword(MD5Utils.encrypt(sysUser.getPassword()));
        return sysUserService.save(sysUser) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "修改管理用户信息")
    @PutMapping("update")
    public RestResult updateById(@ApiParam(name = "sysUser", value = "修改用户信息对象", required = true) @RequestBody SysUser sysUser) {
        return sysUserService.updateById(sysUser) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "获取管理用户信息")
    @GetMapping("get/{id}")
    public RestResult getUserById(@ApiParam(name = "id", value = "查询用户 ID", required = true) @PathVariable long id) {
        SysUser sysUser = sysUserService.getById(id);
        return RestResult.success().data("item", sysUser);
    }

    @ApiOperation(value = "逻辑删除管理用户")
    @DeleteMapping("remove/{id}")
    public RestResult remove(@ApiParam(name = "id", value = "用户ID", required = true) @PathVariable long id) {
        // 判断删除用户是否存在两种情况：1.是否存在下级用户；2.是否存在设备。
        sysUserService.removeUserByUserId(id);
        return sysUserService.removeById(id) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "根据 id 列表批量删除管理用户")
    @DeleteMapping("batchRemove")
    public RestResult batchRemove(@ApiParam(name = "idList", value = "批量逻辑删除的用户 ID 集合", required = true) @RequestBody List<Long> idList) {
        for (Long userId : idList) {
            sysUserService.removeUserByUserId(userId);
        }
        return sysUserService.removeByIds(idList) ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "根据用户 ID 获取角色数据")
    @GetMapping("/toAssign/{userId}")
    public RestResult toAssign(@ApiParam(name = "usuerId", value = "查询用户 ID", required = true) @PathVariable long userId) {
        Map<String, Object> roleMap = sysRoleService.findRoleByUserId(userId);
        return RestResult.success().data(roleMap);
    }

    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public RestResult doAssign(@ApiParam(name = "userId", value = "分配角色的用户 ID", required = true) @RequestParam long userId,
                               @ApiParam(name = "roleId", value = "将分配的角色 ID 数组", required = true) @RequestParam Long[] roleId) {
        sysRoleService.saveUserRoleRealtionShip(userId, roleId);
        return RestResult.success();
    }

    @ApiOperation(value = "根据当前登录用户获取用户树")
    @GetMapping("getSysUserTree/{id}")
    public RestResult getSysUserTree(@ApiParam(name = "id", value = "查询用户 ID", required = true) @PathVariable long id) {
        // 获取所有下级用户的用户树
        SysUserTreeDto sysUserTreeDto = sysUserService.getSysUserTreeList(id);
        return RestResult.success().data("sysUserTreeList", sysUserTreeDto);
    }

    @ApiOperation(value = "修改用户密码")
    @PostMapping("changeSysUserPsw/{id}")
    public RestResult changeSysUserPassword(@ApiParam(name = "id", value = "修改密码用户 ID", required = true) @PathVariable long id,
                                            @ApiParam(name = "oldPwd", value = "旧密码", required = true) String oldPwd,
                                            @ApiParam(name = "newPwd", value = "新密码", required = true) String newPwd) {
        // 验证修改用户密码对应的旧密码是否正确
        if (sysUserService.checkOldPwd(id, oldPwd)) {
            boolean updateStatus = sysUserService.updateSysUserPwd(id, newPwd);
            return updateStatus ? RestResult.success() : RestResult.error();
        }
        return RestResult.error();
    }

    @ApiOperation(value = "重置用户密码")
    @GetMapping("resetSysUserPsw/{id}")
    public RestResult resetSysUserPassword(@ApiParam(name = "id", value = "重置用户 ID", required = true) @PathVariable long id) {
        // 默认密码为：123456 对其进行加密后再做修改
        Boolean updateStatus = sysUserService.updateResetUserPwd(id);
        return updateStatus ? RestResult.success() : RestResult.error();
    }

    @ApiOperation(value = "批量转移用户")
    @PostMapping("batchMoveSysUser")
    public RestResult batchMoveSysUser(@ApiParam(name = "toUserId", value = "转移对象用户 ID", required = true) long toUserId,
                                       @ApiParam(name = "userIdFile", value = "将转移的用户 ID 数组", required = true) Long[] userIdFile) {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SysUser loginSysUser = sysUserService.selectByUsername(username);
        // 获取当前登录用户的所有子集用户 ID 集合
        List<Long> userIdList = sysUserService.getLonginUserWithSubUserIdList(loginSysUser);
        // 遍历所有需要转移的用户 ID 集合
        List<SysUser> sysUserList = new ArrayList<>();
        if (userIdList.contains(toUserId)) {
            // 获取转移用户的信息
            SysUser toSysUserObj = sysUserService.getById(toUserId);
            for (Long userId : userIdFile) {
                if (userIdList.contains(userId)) {
                    SysUser sysUser = new SysUser();
                    sysUser.setId(userId);
                    sysUser.setPid(toUserId);
                    sysUser.setParentName(toSysUserObj.getUsername());
                    sysUserList.add(sysUser);
                } else {
                    return RestResult.error();
                }
            }
            // 批量用户转移
            return sysUserService.updateBatchById(sysUserList) ? RestResult.success() : RestResult.error();
        }
        return RestResult.error();
    }

    @ApiOperation(value = "转移用户")
    @PostMapping("moveSysUser")
    public RestResult moveSysUser(@ApiParam(name = "toUserId", value = "转移对象用户 ID", required = true) long toUserId,
                                  @ApiParam(name = "userId", value = "将转移的用户 ID", required = true) long userId) {
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        SysUser loginSysUser = sysUserService.selectByUsername(username);
        // 获取当前登录用户的所有子集用户 ID 集合
        List<Long> userIdList = sysUserService.getLonginUserWithSubUserIdList(loginSysUser);
        // 判断当前转移与被转移用户是否为当前登录用户的子用户
        if (userIdList.contains(toUserId) && userIdList.contains(userId)) {
            // 获取转移用户的信息
            SysUser toSysUserObj = sysUserService.getById(toUserId);
            SysUser sysUser = new SysUser();
            sysUser.setId(userId);
            sysUser.setPid(toUserId);
            sysUser.setParentName(toSysUserObj.getUsername());
            return sysUserService.updateById(sysUser) ? RestResult.success() : RestResult.error();
        }
        return RestResult.error();
    }

    @ApiOperation(value = "获取用户详情信息")
    @GetMapping("getSysUserDetail/{id}")
    public RestResult getSysUserDetail(@ApiParam(name = "id", value = "查询用户 ID", required = true) @PathVariable long id) {
        SysUser sysUser = sysUserService.getById(id);
        return RestResult.success().data("sysUser", sysUser);
    }

    @ApiOperation(value = "根据父级用户 ID 查询出所有下级用户信息集合")
    @GetMapping("getAllChildUser/{userId}")
    public RestResult getAllChildrenSysUserListByPid(@ApiParam(name = "userId", value = "父级用户 ID", required = true) @PathVariable long userId) {
        List<Long> userIdList = sysUserService.getAllChildrenSysUserListByPid(userId, new ArrayList<>());
        return RestResult.success().data("userIdList", userIdList);
    }

    @ApiOperation(value = "批量导入用户")
    @PostMapping("batchImportUser")
    public RestResult batchImportUser(MultipartFile multipartFile,
                                      @ApiParam(name = "user", value = "批量导入用户共用信息对象", required = true) @RequestBody SysUser user) {
        // 判断转移对象是否为当前用户的子用户
        SysUser parentUser = sysUserService.getById(user.getId());
        Integer userType = user.getUserType();
        // 执行批量导入用户信息数据
        sysUserService.batchImportUser(multipartFile, sysUserService, parentUser,userType);
        return RestResult.success();
    }

    @ApiOperation(value = "批量导出用户信息")
    @PostMapping("batchExportUser")
    public void batchExportUser(HttpServletResponse response,
                                @ApiParam(name = "userId", value = "用户 ID", required = true) @PathVariable long userId,
                                @ApiParam(name = "isContainSub", value = "是否包含下级", required = true) boolean isContainSub) {
        try {
            List<Long> userIdList = null;
            if (isContainSub) {
                userIdList = sysUserService.getAllChildrenSysUserListByPid(userId, new ArrayList<>());
            } else {
                userIdList.add(userId);
            }
            List<UserExportVo> userExportVoList = sysUserService.getUserByUserIdList(userIdList);

            // 测试数据
//            List<UserExportVo> userExportVoList = new ArrayList<>();
//            UserExportVo userExportVo = new UserExportVo();
//            userExportVo.setUsername("88888888");
//            userExportVoList.add(userExportVo);

            Map<String, List<? extends BaseRowModel>> map = new HashMap<>();
            map.put("userExportVoList", userExportVoList);
            EasyExcelUtils.createExcelStreamMutilByEaysExcel(response, map, ExcelTypeEnum.XLSX);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

}

