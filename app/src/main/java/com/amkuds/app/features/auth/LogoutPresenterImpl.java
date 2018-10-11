package com.amkuds.app.features.auth;

import android.content.SharedPreferences;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.model.BaseResponse;
import com.amkuds.app.utils.ApiService;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.ServiceInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class LogoutPresenterImpl implements LogoutPresenter {

    private View mView;
    private SharedPreferences mPref;

    public LogoutPresenterImpl(View mView) {
        this.mView = mView;
        mPref = AmkUdsApp.getInstance().Prefs();
    }

    @Override
    public void getLogour() {
        AmkUdsApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getLogout();
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
                mView.showLogout();
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
