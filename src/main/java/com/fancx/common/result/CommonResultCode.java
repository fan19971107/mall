package com.fancx.common.result;

public enum CommonResultCode implements ResultCode {
    SUCCESS(200, "操作成功"),
    IMAGE_UPLOAD_FAILED(701, "图片上传失败"),


    SQLIntegrityConstraintViolationException(702,"操作失败，错误代码702");
    private int code;
    private String message;


    CommonResultCode(int code, String message) {//有属性的枚举需要有构造函数
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
