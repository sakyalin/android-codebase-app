package com.linxinzhe.android.codebaseapp.network;

import com.linxinzhe.android.codebaseapp.model.BizModel;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author linxinzhe on 3/24/16.
 */
public interface NetworkService {

    @POST("/")
    Observable<List<BizModel>> queryBizModel(@Body Map<String, String> paramMap);
}
