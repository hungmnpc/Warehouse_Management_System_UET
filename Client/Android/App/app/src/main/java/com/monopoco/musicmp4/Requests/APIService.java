package com.monopoco.musicmp4.Requests;

public class APIService {
    public static String base_url = "http://192.168.0.104:8222/";
    public static DataService getService(String token){
        return APIRetrofitClient.getClient(base_url, token).create(DataService.class);
    }
}
