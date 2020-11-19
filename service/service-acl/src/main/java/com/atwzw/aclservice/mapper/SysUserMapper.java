package com.atwzw.aclservice.mapper;

import com.atwzw.aclservice.entity.dto.SysUserListDto;
import com.atwzw.aclservice.entity.vo.UserExportVo;
import com.atwzw.servicebase.bean.SysUser;
import com.atwzw.aclservice.entity.dto.SysUserTreeDto;
import com.atwzw.aclservice.entity.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import feign.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户表 Mapper 接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    // 根据条件分页查询用户信息集合
    IPage<SysUserListDto> selectSysUserListByCodition(Page<?> page, @Param("sysUserQueryVo") SysUserQueryVo sysUserQueryVo);

    // 根据父级 id 获取对应的用户 id 集合
    List<Long> selectChildrenUserIdListByPId(Long pid);

    // 根据父级用户 ID 获取直属下级用户集合
    List<SysUserTreeDto> batchSubUserTree(Long pid);

    // 根据用户 ID 集合获取用户信息
    List<UserExportVo> selectUserByUserIdList(List<Long> userIdList);

}
