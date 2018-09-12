package com.nice.miniprogram.service;

import java.util.Map;

public interface WXMessageService {

	String pushMassege(Integer ownId, Map<String, Object> dataMap, int type);
}
