package com.amkuds.app.features.form_data.edit_data;

import com.amkuds.app.base.BaseView;
import com.amkuds.app.model.ItemKaryawan;
import com.google.gson.JsonObject;

import java.util.Map;

public interface FormEditDataPresenter {

    void postEditEmployee(JsonObject jsonData);

    interface View extends BaseView {

        boolean validate();

        void success();
    }
}
