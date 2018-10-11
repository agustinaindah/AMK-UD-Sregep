package com.amkuds.app.features.list_data.detail;

import com.amkuds.app.base.BaseView;
import com.amkuds.app.model.ItemKaryawan;

public interface ListDetailEmployeePresenter {

    void getSingleList(int id);

    interface View extends BaseView{

        void success(ItemKaryawan itemKaryawan);
    }
}
