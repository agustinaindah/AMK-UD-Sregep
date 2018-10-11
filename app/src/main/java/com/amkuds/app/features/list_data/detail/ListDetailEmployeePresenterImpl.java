package com.amkuds.app.features.list_data.detail;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.model.BaseResponse;
import com.amkuds.app.model.ItemKaryawan;
import com.amkuds.app.utils.ApiService;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.ServiceInterface;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class ListDetailEmployeePresenterImpl implements ListDetailEmployeePresenter {

    private View mView;

    public ListDetailEmployeePresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getSingleList(final int id) {
        AmkUdsApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getSingleList(id);
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
                    String data = Helper.getGsonInstance().toJson(response.body().getData());
                    JsonObject jsonData = Helper.parseToJsonObject(data);
                    JsonObject item = jsonData.get("results").getAsJsonObject();
                    ItemKaryawan itemKaryawan = (new Gson()).fromJson(item, ItemKaryawan.class);
                    mView.success(itemKaryawan);
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
