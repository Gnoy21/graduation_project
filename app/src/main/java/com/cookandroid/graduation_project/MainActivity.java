package com.cookandroid.graduation_project;
import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private View loginButton, logoutButton, profileImage;
    private TextView nickName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.login);
        logoutButton = findViewById(R.id.logout);
        nickName = findViewById(R.id.nickname);
        profileImage = (ImageView)findViewById(R.id.profile);
        profileImage.setBackgroundResource(R.drawable.custom_button_gray);

        nickName.setText("로그인이 필요합니다.");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                    UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                            if(oAuthToken != null){
                                Log.i(TAG, "로그인 성공");
                                loginSucceed();
                            }
                            if(throwable != null){
                                Log.e(TAG, "로그인 실패");
                            }
                            updateKakaoLoginUi();
                            return null;
                        }
                    });
                }
                else{
                    Log.e(TAG, "카카오톡 설치가 필요합니다.");
                    /*UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
                            if(oAuthToken != null){
                                //TBD
                            }
                            if(throwable != null){
                                Log.e(TAG, "사용자 정보 요청 실패");
                            }
                            updateKakaoLoginUi();
                            return null;
                        }
                    });*/
                }
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable throwable) {
                        updateKakaoLoginUi();
                        return null;
                    }
                });
            }
        });
    }


    private void updateKakaoLoginUi(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                if(user != null) {
                    nickName.setText(user.getKakaoAccount().getProfile().getNickname());
                    Glide.with(getApplicationContext()).load(user.getKakaoAccount().getProfile().getProfileImageUrl()).into((ImageView) profileImage);
                    loginButton.setVisibility(View.GONE);
                    logoutButton.setVisibility(View.VISIBLE);
                } else {
                    loginButton.setVisibility(View.VISIBLE);
                    logoutButton.setVisibility(View.GONE);
                }
                return null;
            }
        });
    }

    private void loginSucceed(){
        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable throwable) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("email",user.getKakaoAccount().getEmail());
                intent.putExtra("nickname",user.getKakaoAccount().getProfile().getNickname());
                intent.putExtra("profileImageURL",user.getKakaoAccount().getProfile().getProfileImageUrl());
                startActivity(intent);
                return null;
            }
        });
    }
}