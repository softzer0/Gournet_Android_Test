package com.gournet.test;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

class Location implements Serializable {
    double lat, lng;
}

class Marker {
    Integer id;
    String shortname;
    String name;
    @SerializedName("type_display")
    String typeDisplay;
    Location location;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}

class User implements Serializable {
    int id;
    Location location;
    String username;
    String first_name;
    String last_name;
    String birthdate;
    String language;
    String currency;
    String tz;
    int gender;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getFullName() {
        return first_name+" "+last_name;
    }
}

class TokenObj implements Serializable {
    String token;
    long exp;
}

class Token implements Serializable {
    TokenObj access, refresh;
    User user;
}

class UserPass {
    private final String username, password;

    UserPass(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

interface accountService {
    @GET("api/users/?info=1&format=json")
    Call<User> getData();
}

interface loginService {
    @POST("api/token/?format=json")
    Observable<Token> doLogin(@Body UserPass body);
}

interface homeService {
    @GET("api/home/")
    Observable<ResponseBody> getHome();
}

class Client {
    private final static OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
    private final static Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl("https://gournet.co/") //mikisoft-64231.portmap.io:64231 //192.168.1.231:8000
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create());

    private static OkHttpClient client = clientBuilder.build();
    static Retrofit service = builder.client(client).build();

    static Retrofit generateWToken(final String token) {
        client = clientBuilder.addInterceptor(
            new Interceptor() {
                @Override
                public Response intercept(@NonNull Chain chain) throws IOException {
                    okhttp3.Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.addHeader("Authorization", "Bearer "+token);
                    return chain.proceed(ongoing.build());
                }
            })
            .build();
        service = builder
            .client(client)
            .build();
        return service;
    }
}
