package com.nice.miniprogram.enums;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
    /**
     * 系统错误
     */
    SUCCESS("200", "成功"),//成功
    FAIL("400", "失败"),//失败
    UNAUTHORIZED("401", "未认证（签名错误）"),//未认证（签名错误）
    NOT_FOUND("404", "接口不存在"),//接口不存在
    INTERNAL_SERVER_ERROR("500", "服务器内部错误"),
    PERMISSION_NOT_HAS("409", "没有操作权限"),

    /**
     * 业务错误
     */
    OBJECT_IS_NULL("1000", "请选择要操作的选项!"),

    ID_IS_NULL("1001", "您还没有登录,请先登录!"),
    CODE_IS_NULL("1002", "编号不能为空"),
    NAME_IS_NULL("1003", "名称不能为空"),
    SEQ_IS_NULL("1004", "排序不能为空"),
    LEVEL_IS_NULL("1005", "级别不能为空"),
    USERID_IS_NULL("1006", "您还没有登录,请先登录!"),
    RESULT_IS_NULL("1007", "查询结果为空"),
    TYPE_IS_NULL("1008", "查询结果为空"),
    SQL_ERROR("1009", "数据库异常"),
    SYSUSERNAME_IS_REPEAT("1010","用户名重复"),
    PLACENUMBER_IS_REPEAT("1011","场地编号重复"),
    ROLEIDORROLENAME_IS_REPEAT("1012", "角色编号重复"),
    KEYID_IS_NULL("1013", "主键id不可为空"),
    TRADEID_IS_BEYOND_SETTING("1014", "流水订单号生成异常"),
    PURCHASECODE_IS_BEYOND_SETTING("1015", "采购单号生成异常"),
    RECEIVECODE_IS_ERROR("1016", "收货单编号生成异常"),
    SENDCODE_IS_ERROR("1017", "收货单编号生成异常"),
    ROLEID_IS_NULL("1018","请先选择角色，再进行删除操作。"),
    PLACENUMBER_IS_NULL("1019", "请先选择场地，再进行删除操作。"),
    PICTURE_IS_NULL("1020","图片为空"),
    UPLOAD_IS_FAIL("1021","图片上传失败"),
    /**
     * 文件上传
     */
    FILE_NOTNULL("2001", "文件不能为空!"),
    FILE_NOTEXCEL("2002", "文件必须是excel格式!"),
    FILE_NOTEMPITY("2003", "文件内容不能为空!"),
    FILE_UPLOADERROR("2004", "导入失败!"),

    GOOD_ID_IS_NULL("3001", "货品id不能为空"),
    GOOD_NAME_IS_NULL("3002", "货品名称不能为空"),
    GOODED_ID_IS_NULL("3001", "货主id不能为空"),
    GOODED_NAME_IS_NULL("3002", "货主名称不能为空"),
    PROVIDERID_IS_NULL("3003", "供应商id不能为空"),
    PROVIDERNAME_IS_NULL("3004", "供应商名称不能为空"),
    PURCHASECODE_IS_NULL("3005", "采购单号不能为空"),
    PURCHASETYPE_IS_NULL("3006", "采购类型不能为空"),
    PLACEID_IS_NULL("3007", "场地id不能为空"),
    PLACENAME_IS_NULL("3008", "场地名称不能为空"),
    COMMODITYCODE_ID_NULL("3009", "商品编码不能为空"),
    PURCHASENUMBER_IS_NULL("3010", "采购数量不能为空"),
    ORDERSTATUS_IS_NULL("3011", "单据状态不能为空"),
    ORDERSTATUS_IS_ERRER("3012", "当前单据状态不能进行该操作"),

    /**
     * 字段非法
     */
    GOODALIASCODE_NOT_REPEAT("4001", "货品别名编码不能重复!"),
    GOODALIASTYPE_NOT_REPEAT("4002", "货品别名类型不可重复!"),
    STOREAREACODE_NOT_REPEAT("4003", "库区编号不可重复!"),
    STOREAREANAME_NOT_REPEAT("4004", "库区名称不可重复!"),
    ORGANIZATIONCODE_NOT_REPEAT("4005", "组织机构编码不允许重复!"),
    ORGANIZATIONNAME_NOT_REPEAT("4006", "组织机构名称不允许重复!"),
    GOODCODE_NOT_REPEAT("4007", "货品编码不允许重复!"),
    PRODUCTCODE_NOT_REPEAT("4008", "商品编码不允许重复!"),
    STORESEATNAME_NOT_REPEAT("4009", "库位名称不允许重复!"),
    STORESEATCODE_NOT_REPEAT("4010", "库位编号不允许重复!"),
    PROVIDERCODE_NOT_REPEAT("4011","供应商编码不可重复!"),
    CARRIERCODE_NOT_REPEAT("4012","承运商编码不可重复!"),
    GOODERCODE_NOT_REPEAT("4013","货主编码不允许重复!"),

    /**
     * 操作不合法
     */
     OPERATION_ILLEGAL("5000","操作失败"),

    /**
     * 登录过期或者还未登录
     */
    NOLOGIN("5001","您还未登录,请先登录"),

    /**
     * 查询无结果
     */
    NO_RESULT("6002","查询无结果！"),
    /**
     * 图片大小超过4M
     */
    PIC_OVER_MAXSIZE("6003","上传失败，图片大小超过4M！"),
    /**
     * 图片格式不正确
     */
    PIC_TYPE_ERROR("6004","上传失败，图片格式不正确！"),
    /**
     * 请选择合理的时间
     */
    CHOSE_TIME_ERROR("6005","请选择合理的时间！"),
    /**
     * 预约时间最多提前3天
     */
    BESPEAK_TIME_ERROR("6006","预约时间最多提前3天！"),
    /**
     * 预约时间最多跨一天
     */
    BESPEAK_TIME_ERROR_NEW("6008","预约结束时间最多大于开始时间一天"),
    /**
     * 主播账号未审核
     */
    NOT_ACCESS("6007","账号未审核"),
    /**
     * 原始密码错误
     */
    OLDPASSWORD_ERROR("6001","原始密码错误！");


    //服务器内部错误

    private final String code;   //状态码
    private final String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过状态码获取ENUM的名字
     *
     * @param code
     * @return
     */
    public static ResultCode getEnumByStatusCode(String code) {
        for (ResultCode p : ResultCode.values()) {
            if (p.code().equalsIgnoreCase(code)) {
                return p;
            }
        }

        return null;
    }
}
