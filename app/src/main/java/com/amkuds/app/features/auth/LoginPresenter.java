package com.amkuds.app.features.auth;

import com.amkuds.app.base.BaseView;
import com.google.gson.JsonObject;

public interface LoginPresenter {

    void login(JsonObject jsonRequest);

    interface View extends BaseView {
        void successLogin(JsonObject user);
    }
}
