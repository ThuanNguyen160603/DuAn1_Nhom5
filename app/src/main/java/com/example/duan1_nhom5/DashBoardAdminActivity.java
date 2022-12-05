package com.example.duan1_nhom5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.duan1_nhom5.adapter.AdapterCategory;
import com.example.duan1_nhom5.databinding.ActivityDashBoardAdminBinding;
import com.example.duan1_nhom5.model.ModelCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DashBoardAdminActivity extends AppCompatActivity {

    //view binding
    private ActivityDashBoardAdminBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //
    private ArrayList<ModelCategory> categoryArrayList;

    //adapter
    private AdapterCategory adapterCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        loadCategories();

        //log out
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUser();
            }
        });

        //chay them button
        binding.addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardAdminActivity.this, CategoryAddActivity.class));
            }
        });
    }

    private void loadCategories() {
        //ánh xạ ArrayList
        categoryArrayList = new ArrayList<>();

        //lấy tên loại món từ firebase
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear array list trước khi add dữ liệu
                categoryArrayList.clear();
                for (DataSnapshot ds : snapshot.getChildren()){
                    //lấy dữ liệu
                    ModelCategory model = ds.getValue(ModelCategory.class);

                    //thêm vào arraylist
                    categoryArrayList.add(model);

                }
                //setup adapter
                adapterCategory = new AdapterCategory(DashBoardAdminActivity.this, categoryArrayList);

                //set adapter lên recyclerview
                binding.categoriesRv.setAdapter(adapterCategory);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            //not logged in, go to main screen
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else
        {
            //logged in, get user info
            String email = firebaseUser.getEmail();
            //set in text view of toolbar
            binding.tvSubTitle.setText(email);
        }
    }
}