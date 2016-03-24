package com.linxinzhe.android.codebaseapp.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.linxinzhe.android.codebaseapp.AppConfig;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author linxinzhe on 2016/3/9.
 */
public class RetrofitUtil {

    private static Retrofit mRetrofit;

    public static final String DATE_FORMAT = "YYYY-MM-DDTHH:MM:SSZ";

    private static Gson customGsonInstance;

    static {
        customGsonInstance = new GsonBuilder()
//                .setDateFormat(DATE_FORMAT)
                .serializeNulls()
                .registerTypeAdapter(new TypeToken<List>() {
                }.getType(), new ResultsDeserializer())
                .create();
    }

    public static Gson getGson() {
        return customGsonInstance;
    }

    public static Retrofit getInstance() {
        if (mRetrofit == null) {
            synchronized (RetrofitUtil.class) {
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(AppConfig.BASE_URL)
                        .client(OkHttpUtil.getInstance())
                        .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .build();
            }
        }
        return mRetrofit;
    }



    static class ResultsDeserializer<T> implements JsonDeserializer<List<T>> {
        @Override
        public List<T> deserialize(JsonElement je, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            //根据自己的json格式改写

            JsonElement error = je.getAsJsonObject().get("error");
            if (error != null) {
                throw new JsonParseException(error.getAsString());
            }

//            替换子对象json的(\")为(")
            JsonElement results = je.getAsJsonObject().get("result");
            String raw = results.toString();
            String processed = raw.replace("\\\"", "\"");
//            消除子对象json头尾的(")
            if (processed.startsWith("\"")) {
                processed = processed.substring(1, processed.length() - 1);
            }

            return new Gson().fromJson(processed, typeOfT);
        }
    }
}
