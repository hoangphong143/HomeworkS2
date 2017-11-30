package com.example.admins.loginwithfacebook;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    public LoginButton loginButton;
    public ProfilePictureView profilePictureView;
    public TextView tvName;
    CallbackManager callbackManager;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager=CallbackManager.Factory.create();

        setContentView(R.layout.activity_main);
        setupUI();
        profilePictureView.setVisibility(View.INVISIBLE);
        tvName.setVisibility(View.INVISIBLE);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));

        setLogin();


    }

    private void setLogin() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


                loginButton.setVisibility(View.INVISIBLE);
                profilePictureView.setVisibility(View.VISIBLE);
                tvName.setVisibility(View.VISIBLE);
                result();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void result() {
        GraphRequest graphRequest= GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    name =object.getString("name");
                    profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
                    tvName.setText(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle params= new Bundle();
        params.putString("fields","name");
        graphRequest.setParameters(params);
        graphRequest.executeAsync();
    }

    private void setupUI() {
        loginButton=findViewById(R.id.login_button);
        profilePictureView=findViewById(R.id.pp_image);
        tvName=findViewById(R.id.tv_Name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
}
