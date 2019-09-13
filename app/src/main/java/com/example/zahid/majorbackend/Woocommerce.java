package com.example.zahid.majorbackend;

import com.google.gson.Gson;

import java.util.List;
import java.util.Map;

/**
 * Created by HP on 7/30/2018.
 */

public class Woocommerce {
    public static String auth = "";
    public static String useremail = "";
    public Woocommerce() {

    }
    // get data from json directly
    public String GetData(String Json,String endpoint) {
        String data = "";
        Gson gson = new Gson();
        Map<String,Object> mapPost = (Map<String,Object>)gson.fromJson(Json,Map.class);
        data = (String)mapPost.get(endpoint);
        return data;
    }
    // get map object from another map
    public Map<String,Object> GetMapfromMap(Map<String,Object> map,String endpoint) {
        Map<String,Object> data;
        data = (Map<String,Object>) map.get(endpoint);
        return data;
    }
    public Map<String,Object> GetMapfromJson(String Json) {
        Map<String,Object> data;
        Gson gson = new Gson();
        data = (Map<String,Object>) gson.fromJson(Json,Map.class);
        return data;
    }
    public String GetDatafromMap(Map<String,Object> map,String endpoint) {
        String data;
        data = (String) map.get(endpoint);
        return data;
    }
    public double GetIntDatafromMap(Map<String,Object> map,String endpoint) {
        double data;
        data = (double) map.get(endpoint);
        return data;
    }
    public List<Object> GetListfromJson(String Json) {
        List<Object> data;
        Gson gson = new Gson();
        data = (List) gson.fromJson(Json,List.class);
        return data;
    }
    public Map<String,Object> GetMapfromList(List<Object> list,int index) {
        Map<String,Object> data;
        data = (Map<String,Object>) list.get(index);
        return data;
    }
    public List<Object> GetListfromMap(Map<String,Object> map,String endpoint) {
        List<Object> data;
        data = (List<Object>) map.get(endpoint);
        return data;
    }
}
