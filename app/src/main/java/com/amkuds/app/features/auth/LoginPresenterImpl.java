package com.amkuds.app.features.auth;

import android.content.SharedPreferences;

import com.amkuds.app.AmkUdsApp;
import com.amkuds.app.model.BaseResponse;
import com.amkuds.app.utils.ApiService;
import com.amkuds.app.utils.Consts;
import com.amkuds.app.utils.Helper;
import com.amkuds.app.utils.ServiceInterface;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Response;

public class LoginPresenterImpl implements LoginPresenter {

    private View mView;

    public LoginPresenterImpl(View mView) {
        this.mView = mView;
    }

    @Override
    public void login(final JsonObject jsonRequest) {
        AmkUdsApp.getInstance().service(new ServiceInterface() {
            @Override
            public Call<BaseResponse> callBackResponse(ApiService apiService) {
                return apiService.postLogin(jsonRequest);
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

                    saveDataToPref(jsonData);

                    mView.successLogin(jsonData);

                }catch (Exception e){
                    e.printStackTrace();
                    String message = Helper.getGsonInstance().toJson(response.body().getMessage());
                    mView.showMessage(message);
                }
            }

            @Override
            public void responseFailed(Response<BaseResponse> response) {
                try {
                    JsonObject jsonRes = Helper.parseToJsonObject(response.errorBody().string());
                    mView.showMessage(jsonRes.get("message").getAsString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failed(Throwable t) {
                mView.showMessage(t.getLocalizedMessage());
            }
        });
    }

    private void saveDataToPref(JsonObject jsonData) {
        String token = jsonData.get("token").getAsString();
        JsonObject user = jsonData.get("user").getAsJsonObject();
        int iduser = user.get("id").getAsInt();
        String email = user.get("email").getAsString();
        String firstName = user.get("first_name").getAsString();
        String lastName = user.get("last_name").getAsString();
        String role = user.get("role").getAsString();

        SharedPreferences.Editor editor = AmkUdsApp
                .getInstance()
                .Prefs()
                .edit();

        editor.putString(Consts.TOKEN, token);
        editor.putInt(Consts.ID, iduser);
        editor.putString(Consts.EMAIL, email);
        editor.putString(Consts.FIRSTNAME, firstName);
        editor.putString(Consts.LASTNAME, lastName);
        editor.putString(Consts.ROLE, role);
        editor.commit();

        /* "id": 2,
            "email": "owneruds@owner.com",
            "permissions": [],
            "last_login": null,
            "first_name": "Owner",
            "last_name": "UDS",
            "deleted_at": null,
            "created_at": "2018-09-17 11:43:07",
            "updated_at": "2018-09-17 11:43:07",
            "role": "owner"*/
    }
}
