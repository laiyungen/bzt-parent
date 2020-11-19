package com.atwzw.smsservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.util.CollectionUtils;
import com.atwzw.commonutils.ResultCodeEnum;
import com.atwzw.servicebase.exceptionhandler.BztException;
import com.atwzw.smsservice.entity.SmsSimcard;
import com.atwzw.smsservice.listener.SmsSimCardExcelListener;
import com.atwzw.smsservice.mapper.SmsSimcardMapper;
import com.atwzw.smsservice.service.SmsSimcardService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 * SIM 卡信息表 服务实现类
 * </p>
 *
 * @author lyg
 * @since 2020-09-19
 */
@Service
public class SmsSimcardServiceImpl extends ServiceImpl<SmsSimcardMapper, SmsSimcard> implements SmsSimcardService {

    @Override
    public QueryWrapper<SmsSimcard> createPageSimcardConditionQueryWrapper(SmsSimcard smsSimcard) {
        // 构建条件：创建 QueryWrapper
        QueryWrapper<SmsSimcard> bztSimcardQueryWrapper = new QueryWrapper<>();
        // 多条件组合查询
        // SIM 卡的 ICCID 号
        String iccid = smsSimcard.getIccid();
        // SIM卡的MSISDN号
        String msisdn = smsSimcard.getMsisdn();
        if (!StringUtils.isEmpty(iccid)) {
            bztSimcardQueryWrapper.like("iccid", iccid);
        }
        if (!StringUtils.isEmpty(msisdn)) {
            bztSimcardQueryWrapper.like("msisdn", msisdn);
        }
        // 排序：默认用户创建时间从大到小
        bztSimcardQueryWrapper.orderByDesc("gmt_create");
        return bztSimcardQueryWrapper;
    }

    @Override
    public void batchImportSimCard(MultipartFile multipartFile,SmsSimcardService smsSimcardService) {
        try {
            //文件输入流
            InputStream in = multipartFile.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SmsSimcard.class,new SmsSimCardExcelListener(smsSimcardService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 导出 SIM 卡信息数据到 excel
     *
     * @param response
     * @param sheetName 表格sheet名
     * @param list 导出数据
     *
     */
    @Override
    public void batchExportSimCard(HttpServletResponse response, String sheetName, List<? extends BaseRowModel> list){
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        try(ServletOutputStream out = response.getOutputStream()){
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(sheetName, "UTF-8")
                    + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".xlsx");

            ExcelWriter writer = EasyExcelFactory.getWriter(out);
            Sheet sheet = new Sheet(1, 0, list.get(0).getClass());
            sheet.setSheetName(sheetName);
            sheet.setAutoWidth(Boolean.TRUE);

            writer.write(list, sheet);
            writer.finish();
            out.flush();
        }catch (IOException e){
            throw new BztException(ResultCodeEnum.CD504.getCode(),ResultCodeEnum.CD504.getMsg());
        }
    }

}
