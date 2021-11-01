package com.cookandroid.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {

    private View loginButton;
    private String adminEmail = "pmy0237@kakao.com1";
    private DatabaseReference mDatabase;
    private int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this,
                        new Function2<OAuthToken, Throwable, Unit>() {
                    @Override
                    public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {

                        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
                            @Override
                            public Unit invoke(User user, Throwable throwable) {

                                HashMap result = new HashMap<>();
                                result.put("name",
                                        user.getKakaoAccount().getProfile().getNickname());
                                result.put("email",
                                        user.getKakaoAccount().getEmail());

                                mDatabase.child("users").child(Integer.toString(i++))
                                        .setValue(result);

                                if(user.getKakaoAccount().getEmail().equals(adminEmail)){
                                    Intent intent = new Intent(getApplicationContext(),
                                            AdminReportListActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Intent intent = new Intent(getApplicationContext(),
                                            HomeActivity.class);
                                    intent.putExtra("email",
                                            user.getKakaoAccount().getEmail());
                                    startActivity(intent);
                                }

                                return null;

                            }
                        });

                        return null;
                    }
                });
            }
        });
    }
}