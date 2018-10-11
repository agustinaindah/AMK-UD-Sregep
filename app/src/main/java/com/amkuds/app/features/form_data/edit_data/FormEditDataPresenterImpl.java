package com.amkuds.app.features.form_data.edit_data;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.model.BaseResponse;
import com.amkuds.app.utils.ApiService;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.ServiceInterface;
import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class FormEditDataPresenterImpl implements FormEditDataPresenter {

    private View mView;

    public FormEditDataPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void postEditEmployee(final JsonObject jsonData) {
        boolean cancel = mView.validate();
        if (!cancel){
            AmkUdsApp.getInstance().service(new ServiceInterface() {
                @Override
                public Call<BaseResponse> callBackResponse(ApiService apiService) {
                    return apiService.postEditEmployee(jsonData);
                }

                @Override
                public void showProgress() {
                    mView.showProgress();
                }

                @Override
                public void hideProgress() {
                    mView.hideProgress();
                }

                @Override
                public void responseSuccess(Response<BaseResponse> response) {
                    try {
                        mView.success();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void responseFailed(Response<BaseResponse> response) {
                    try {
                        JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                        mView.showMessage(jsonRes.get("msg").getAsString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable t) {
                    mView.notConnect(t.getLocalizedMessage());
                }
            });
        }

    }
}
