package com.example.duan1_nhom5;

import android.widget.Filter;

import com.example.duan1_nhom5.adapter.AdapterCategory;
import com.example.duan1_nhom5.model.ModelCategory;

import java.util.ArrayList;

public class FilterCategory extends Filter {
    //đưa arraylist đã tạo ở mục loại món
    ArrayList<ModelCategory> filterList;
    //đưa adapter vào
    AdapterCategory adapterCategory;

    //constructor
    public FilterCategory(ArrayList<ModelCategory> filterList, AdapterCategory adapterCategory) {
        this.filterList = filterList;
        this.adapterCategory = adapterCategory;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //validate ko được để trống dữ liệu
        if (constraint != null && constraint.length() > 0){
            //validate chữ hoa và chữ thường
            constraint.toString().toUpperCase();
            ArrayList<ModelCategory> filteredModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++){

            }
        }
        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

    }
}
