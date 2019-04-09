package com.jingtao.jtmanage.manage.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel
public class InsuredMessageConResult implements Serializable {

    @ApiModelProperty(value = "投保人姓名", required = true)
    @JSONField(name = "appName")
    private String appName;

    @ApiModelProperty(value = "投保人证件号", required = true)
    @JSONField(name = "appIdNo")
    private String appIdNo;

    @ApiModelProperty(value = "投保人手机号码", required = true)
    @JSONField(name = "appPhone")
    private String appPhone;

    @ApiModelProperty(value = "被保人姓名", required = true)
    @JSONField(name = "insuredName")
    private String insuredName;

    @ApiModelProperty(value = "被保人证件号", required = true)
    @JSONField(name = "insureIDCard")
    private String insureIDCard;

    @ApiModelProperty(value = "被保人保费", required = true)
    @JSONField(name = "insurePrem")
    private BigDecimal insurePrem;

    @ApiModelProperty(value = "被保人保额", required = true)
    @JSONField(name = "insureAmount")
    private BigDecimal insureAmount;
}
