package com.amkuds.app.features.form_data;

import com.amkuds.app.base.BaseView;
import com.google.gson.JsonObject;

public interface FormInputDataPresenter {

    void postInputEmployee(JsonObject jsonData);

    interface View extends BaseView{
        void success();
    }
}
