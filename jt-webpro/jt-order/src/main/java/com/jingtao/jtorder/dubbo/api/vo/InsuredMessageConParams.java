package com.jingtao.jtorder.dubbo.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class InsuredMessageConParams implements Serializable {

    @ApiModelProperty(value = "保单号", required = true)
    @JSONField(name = "policyNo")
    private String policyNo;


    @ApiModelProperty(value = "投保人Id", required = true)
    @JSONField(name = "insuredId")
    private String insuredId;

}
