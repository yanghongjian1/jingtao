package com.jingtao.jtcommon.constants;


public enum ResultCode {

    //region 通用状态码
    SUCCESS(100,"成功"),//成功
    FAIL(400,"失败"),//失败
    VALID_FAIL(300,"验证失败"),//验证失败
    UNAUTHORIZED(401,"未认证"),//未认证（签名错误）
    NOT_FOUND(404,"接口不存在"),//接口不存在
    INTERNAL_SERVER_ERROR(500,"系统原因"),//服务器内部错误
    PARAMETER_ERROR(600,"参数错误"),//服务器内部错误
    REPEATED_REQUESTS(601,"重复提交"),
    PAY_FAIL(701,"支付失败"),
    UNDERWRITING_FAIL(702, "核保失败"),
    PAY_ING(703, "支付中"),
    UNDERWRITING_ING(704, "核保中");
    /**
     * 返回码
     */
    public int code;

    /**
     * 返回结果描述
     */
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
