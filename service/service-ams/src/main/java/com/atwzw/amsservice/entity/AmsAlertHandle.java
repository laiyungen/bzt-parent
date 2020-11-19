package com.atwzw.amsservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 报警处理表
 * </p>
 *
 * @author lyg
 * @since 2020-10-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AmsAlertHandle对象", description="报警处理表")
public class AmsAlertHandle implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键 ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "报警信息 ID")
    private Long alertId;

    @ApiModelProperty(value = "处理人 ID")
    private Long handleId;

    @ApiModelProperty(value = "处理人姓名")
    private String handleName;

    @ApiModelProperty(value = "处理记录")
    private String handleRecord;

    @ApiModelProperty(value = "逻辑删除:0-未删除,1-已删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    private Date gmtModified;


}
