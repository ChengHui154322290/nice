package com.nice.good.core;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public  class DtoModel  {

    private Map<String ,JSONObject> map = new HashMap();

    public Map<String, JSONObject> getMap() {
        return map;
    }

    public void setMap(Map<String, JSONObject> map) {
        this.map = map;
    }
}
