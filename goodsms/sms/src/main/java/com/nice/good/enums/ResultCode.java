package com.nice.good.enums;

/**
 * 响应码枚举，参考HTTP状态码的语义
 */
public enum ResultCode {
	/**
	 * 系统错误
	 */
	SUCCESS("200", "成功"),//成功
	FAIL("400", "失败"),//失败
	USERNAME_NOT_EXIST("401", "用户名不存在!"),
	PASSWORD_IS_WORONG("402", "密码错误!"),
	PERMISSION_NOT_HAS("409", "用户权限不足,登录失败!"),
	INTERNAL_SERVER_ERROR("500", "服务器内部错误"),
	IMPORT_FAIL("501", "导入失败!"),


	/**
	 * 业务错误
	 */
	PASS_IS_WORD("1044","密码不合法,由英文与数字组合的8-16位"),
	ORDER_IS_OWN("1043","该主播还有未完成的订单,不能删除"),
	CODE_ALREADY_EXISTS("1042", "该场地下的直播间编码已存在"),
	OFF_THE_STOCK("1041", "已完成的订单状态不能再做修改"),
	CAN_NOT_REVISE("1040", "订单已归还不能做修改"),
	DETAIL_STATUS_RETURN("1039", "已归还状态下的明细才可删除"),
	NO_STORAGE_LOCATION("1038", "该库位已经不存在"),
	CAN_NOT_RETURN("1037", "只有已收货状态下才能归还"),
	PENDING_ID_STATE("1036", "只有待收货状态下才能借领"),
	IS_NOT_OPERATING("1035", "您不是运营,无权操作"),
	DETAILS_ID_NULL("1034", "明细不能为空"),
	HAS_BEEN_RETURNED("1033", "借领单已归还才可删除"),
	CAN_BE_DELETED("1032", "预约单已完成或取消才可删除"),
	ORDER_IS_CANNOT("1031", "开播前三小时状态为已预约才可取消"),
	SUCCESS_AND_DEFEATED("1030", "直播间预约成功.库存不足借领失败"),
	CHOOSE_IS_NULL("1029", "领用量不能为空"),
	HAS_BENN_SELECTED("1028", "该时间段已被预约,请重新选择"),
	CHOOSE_REASONABLE_TIME("1027", "请选择合理的时间"),
	DAYS_IN_ADVANCE("1026", "预约时间最多提前3天"),
	OWN_IS_NULL("1025", "所属人不能为空"),
	ROOMCODE_IS_NULL("1024", "直播间编码不能为空"),
	APPOINTMENT_IS_NULL("1023", "预约时间不能为空"),
	STOCK_IS_NULL("1022", "库存不足"),
	OBJECT_IS_NULL("1000", "请选择要操作的选项!"),
	ID_IS_NULL("1001", "您还没有登录,请先登录!"),
	USERID_IS_NULL("1006", "您还没有登录,请先登录!"),
	SYSUSERNAME_IS_REPEAT("1010", "用户名重复"),
	PLACENUMBER_IS_REPEAT("1011", "场地编号重复"),
	ROLEIDORROLENAME_IS_REPEAT("1012", "角色编号重复"),
	KEYID_IS_NULL("1013", "主键id不可为空"),
	TRADEID_IS_BEYOND_SETTING("1014", "流水订单号生成异常"),
	PURCHASECODE_IS_BEYOND_SETTING("1015", "采购单号生成异常"),
	RECEIVECODE_IS_ERROR("1016", "收货单编号生成异常"),
	SENDCODE_IS_ERROR("1017", "收货单编号生成异常"),
	ROLEID_IS_NULL("1018", "请先选择角色，再进行删除操作。"),
	PLACENUMBER_IS_NULL("1019", "请先选择场地，再进行删除操作。"),
	PICTURE_IS_NULL("1020", "图片为空"),
	UPLOAD_IS_FAIL("1021", "图片上传失败"),
    EXHIBITION_IS_REPEAT("1022", "场地名称不能重复!"),
    EXHIBITION_IS_NULL("1023", "场地名称不能为空!"),
	/**
	 * 文件上传
	 */
	FILE_NOTNULL("2001", "文件不能为空!"),
	FILE_NOTEXCEL("2002", "文件必须是excel格式!"),
	FILE_NOTEMPITY("2003", "文件内容不能为空!"),

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
	PROVIDERCODE_NOT_REPEAT("4011", "供应商编码不可重复!"),
	CARRIERCODE_NOT_REPEAT("4012", "承运商编码不可重复!"),
	GOODERCODE_NOT_REPEAT("4013", "货主编码不允许重复!"),

	/**
	 * 操作不合法
	 */
	OPERATION_ILLEGAL("5000", "操作失败"),

	/**
	 * 登录过期或者还未登录
	 */
	NOLOGIN("5001", "您还未登录,请先登录"),


	/**
	 * 场地不能为空
	 */
	PLACE_IS_NULL("5002", "请先选择场地!"),
	/**
	 * 图片大小超过4M
	 */
	PIC_OVER_MAXSIZE("6003","上传失败，图片大小超过3M！"),;


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
