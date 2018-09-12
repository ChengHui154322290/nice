package com.nice.good.service;

import java.util.Map;

public interface WXMessageService {

	String pushMassege(Integer ownId, Map<String,Object> dataMap, int type);
}
