package com.example.duan1_nhom5;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {
    GoogleSignInClient mGoogleSignInClient;
    SignInButton btnSignInButton;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ánh xạ
        btnSignInButton = findViewById(R.id.btnSignInGoogle);
        btnLogout = findViewById(R.id.btnLogout);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                checkLogin.launch(signInIntent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btnSignInButton.setVisibility(View.VISIBLE);
                        btnLogout.setVisibility(View.GONE);
                        Toast.makeText(LoginActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Toast.makeText(this, "Đã đăng nhập tài khoản " + account.getEmail(), Toast.LENGTH_SHORT).show();
            btnSignInButton.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        }
    }

    ActivityResultLauncher<Intent> checkLogin = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            //đăng nhập thành công
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            String displayName = account.getDisplayName();
                            String email = account.getEmail();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công - " + displayName + " - " + email, Toast.LENGTH_SHORT).show();
                            btnSignInButton.setVisibility(View.GONE);
                            btnLogout.setVisibility(View.VISIBLE);
                            Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(homeIntent);
                            finish();
                        } catch (ApiException e) {
                            //đăng nhập thất bại
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });






}