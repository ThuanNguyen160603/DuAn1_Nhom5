package com.example.duan1_nhom5.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom5.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QlMenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ql_menu, container, false);
        // Ánh xạ
        RecyclerView recyclerView1 = view.findViewById(R.id.recyclerView1);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        // lấy data từ firebase về


        // load lên recycleView

        return view;
    }
}