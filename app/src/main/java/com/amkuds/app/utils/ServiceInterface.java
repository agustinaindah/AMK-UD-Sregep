package com.amkuds.app.utils;

import retrofit2.Call;
import retrofit2.Response;
import com.amkuds.app.model.BaseResponse;

public interface ServiceInterface {

    Call<BaseResponse> callBackResponse(ApiService apiService);

    void showProgress();

    void hideProgress();

    void responseSuccess(Response<BaseResponse> response);

    void responseFailed(Response<BaseResponse> response);

    void failed(Throwable t);

}
