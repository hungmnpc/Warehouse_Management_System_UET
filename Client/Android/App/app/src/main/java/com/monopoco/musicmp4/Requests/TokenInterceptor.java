//package com.monopoco.musicmp4.Requests;
//
//import android.content.SharedPreferences;
//
//import androidx.annotation.NonNull;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class TokenInterceptor implements Interceptor {
//    @NonNull
//    @Override
//    public Response intercept(@NonNull Chain chain) throws IOException {
//
//        SharedPreferences sp1= getSharedPreferences("Login", MODE_PRIVATE);
//        Boolean isLogin = Boolean.valueOf(sp1.getString("isLogin", null));
//        Request newRequest=chain.request().newBuilder()
//                .header("Authorization","Bearer "+ yourtokenvalue)
//                .build();
//
//        return chain.proceed(newRequest);
//    }
//}
