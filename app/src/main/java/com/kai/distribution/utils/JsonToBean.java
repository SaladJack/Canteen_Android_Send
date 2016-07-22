package com.kai.distribution.utils;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kai.distribution.entity.Distributed;
import com.kai.distribution.entity.Distributing;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tusm on 16/7/18.
 */
public class JsonToBean {



    public static List<Distributed> getDistributeds(String json)  {

        JSONObject jsonObj = null;
        List<Distributed> distributeds = new ArrayList<>();
        //转化为json形式的数据
        jsonObj = JSON.parseObject(json);
        JSONArray res = jsonObj.getJSONArray("array");
        distributeds = JSON.parseArray(res.toJSONString(), Distributed.class);

        return distributeds;
    }



    public static List<Distributing> getDistributings(String json)  {

        JSONArray jsonArray = null;
        List<Distributing> distributings = new ArrayList<>();
        //转化为json形式的数据
        jsonArray = JSON.parseArray(json);
        distributings = JSON.parseArray(jsonArray.toJSONString(), Distributing.class);
        return distributings;
    }
}
