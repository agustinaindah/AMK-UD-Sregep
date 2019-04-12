package com.amkuds.app.features.list_data;

import com.amkuds.app.base.BaseView;
import com.amkuds.app.model.ItemKaryawan;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ListDataEmployeePresenter {

    void getListEmployee(int page);

    void getSearch(Map<String, String> mapRequest);

    interface View extends BaseView{
        void successListEmployee(List<ItemKaryawan> itemKaryawans, int totalData);
    }
}
