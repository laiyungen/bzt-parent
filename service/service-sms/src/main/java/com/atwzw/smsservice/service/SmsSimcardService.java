package com.atwzw.smsservice.service;

import com.alibaba.excel.metadata.BaseRowModel;
import com.atwzw.smsservice.entity.SmsSimcard;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * SIM 卡信息表 服务类
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
public interface SmsSimcardService extends IService<SmsSimcard> {

    // 根据条件分页查询 SIM 卡信息列表
    QueryWrapper<SmsSimcard> createPageSimcardConditionQueryWrapper(SmsSimcard smsSimcard);

    // 批量导入 SIM 卡信息
    void batchImportSimCard(MultipartFile multipartFile, SmsSimcardService smsSimcardService);

    // 批量导出 SIM 卡信息
    void batchExportSimCard(HttpServletResponse response, String sheetName, List<? extends BaseRowModel> list);

}
