package com.amkuds.app.features.auth;

import com.amkuds.app.base.BaseView;

public interface LogoutPresenter {

    void getLogour();

    interface View extends BaseView{
        void showLogout();
    }
}
