package com.amkuds.app.features.list_data;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.model.BaseResponse;
import com.amkuds.app.model.ItemKaryawan;
import com.amkuds.app.utils.ApiService;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.ServiceInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

public class ListDataEmployeePresenterImpl implements ListDataEmployeePresenter {

    private View mView;

    public ListDataEmployeePresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void getListEmployee(final int page) {
        AmkUdsApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getListEmployee(page);
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
                    JsonArray jsonRes = jsonData.get("results").getAsJsonArray();
                    ArrayList<ItemKaryawan> itemKaryawans = new ArrayList<ItemKaryawan>();
                    for (JsonElement element : jsonRes) {
                        ItemKaryawan itemKaryawan =
                                Helper.getGsonInstance().fromJson(element, ItemKaryawan.class);
                        itemKaryawans.add(itemKaryawan);
                    }
                    mView.successListEmployee(itemKaryawans, page);

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

    @Override
    public void getSearch(final Map<String, String> mapRequest) {
        AmkUdsApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.getSearch(mapRequest);
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
                    String totalData = "0";
                    JsonArray jsonRes = jsonData.get("results").getAsJsonArray();
                    ArrayList<ItemKaryawan> itemKaryawans = new ArrayList<ItemKaryawan>();
                    for (JsonElement element : jsonRes){
                        ItemKaryawan itemKaryawan =
                                Helper.getGsonInstance().fromJson(element, ItemKaryawan.class);
                        itemKaryawans.add(itemKaryawan);
                    }
                    mView.successListEmployee(itemKaryawans,Integer.valueOf(totalData));
                }catch (Exception e){
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
