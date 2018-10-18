package com.amkuds.app.features.auth;

import com.amkuds.app.base.BaseView;

public interface LogoutPresenter {

    void getLogout();

    interface View extends BaseView{
        void showLogout();
    }
}
