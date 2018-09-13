package com.amkuds.app.base;

public interface BaseView {

    void showProgress();

    void hideProgress();

    void showMessage(String msg);

    void notConnect(String msg);
}
