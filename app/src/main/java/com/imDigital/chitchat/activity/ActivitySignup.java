package com.imDigital.chitchat.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.imDigital.chitchat.R;
import com.imDigital.chitchat.beans.responsebeans.ResSignupSignInBean;
import com.imDigital.chitchat.constants.AppConstant;
import com.imDigital.chitchat.interfaces.GNetworkEvent;
import com.imDigital.chitchat.model.NetworkService;
import com.imDigital.chitchat.preferences.AppPreferences;
import com.imDigital.chitchat.utils.NetworkStatus;
import com.imDigital.chitchat.beans.requestbeans.ReqSignupSigninBean;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class ActivitySignup extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GNetworkEvent {

    private EditText et_name;
    private EditText et_mobile;
    private EditText et_email;
    private EditText et_password;
    private ProgressDialog pd;

    final String LOGIN_TYPE_NORMAL = "normal";
    final String LOGIN_TYPE_FACEBOOK = "facebook";
    final String LOGIN_TYPE_GOOGLE = "google";

    public String emailid="";
    public String password="";
    public String name="";
    public String social_id="";
    public String image_url="";

    /** Social Login Varriables */
    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 0;
    private ConnectionResult mConnectionResult;
    private boolean mIntentInProgress;
    private boolean mSignInClicked;
    private LinearLayout btn_fblogin;
    private LinearLayout btn_glogin;

    /** End Social Login Varriables */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        /** Social Login Initializers*/
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestId()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        /** End Social Login Initializers*/

        initView();
        initVar();

    }

    private void initVar() {
        fbLoginAPI();
    }

    private void initView() {
        pd = new ProgressDialog(this);

        btn_fblogin = (LinearLayout) findViewById(R.id.btn_fblogin);
        btn_glogin = (LinearLayout) findViewById(R.id.btn_glogin);
        et_name = (EditText) findViewById(R.id.et_name);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_fblogin.setOnClickListener(this);
        btn_glogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_fblogin:
                fbLogin();
                break;
            case R.id.btn_glogin:
                googleLogin();
                break;
        }
    }

    /** Init Facebook Login And Callbacks*/
    public void fbLoginAPI(){
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        pd.dismiss();
                        loginResult.getAccessToken();
                        if (Profile.getCurrentProfile() != null) {
                            name = Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName();
                            Log.e("fbdetail", "" + Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName());
                            Toast.makeText(ActivitySignup.this, "Facebook Name: "+ Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName(), Toast.LENGTH_SHORT).show();
                        }

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                String fbID = "";
                                String lastName = "";
                                String firstName = "";
                                String email = "";
                                String gender = "";
                                String birthday = "";
                                URL imageURL = null;
                                String profilePiocUrl = "";
                                JSONObject mJsonObject = response.getJSONObject();
                                try {
                                    fbID = mJsonObject.getString("id");
                                    lastName = mJsonObject.getString("last_name");
                                    gender = mJsonObject.getString("gender");
                                    firstName = mJsonObject.getString("first_name");
                                    email = mJsonObject.getString("email");
//                                    birthday = object.getString("birthday");
                                    profilePiocUrl = mJsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    imageURL = new URL("https://graph.facebook.com/" + fbID + "/picture?type=large");
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                }
                                Log.e("FAcebookImge ", "" + profilePiocUrl);

                                emailid = email;
                                social_id = fbID;
                                image_url = profilePiocUrl;
                                hitSignUpAPI(emailid, password, name, LOGIN_TYPE_FACEBOOK, social_id, image_url);
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday,picture.type(large)");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                        Log.e("cancel", "" + "Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Log.e("onError", "" + exception);
                        if (exception instanceof FacebookAuthorizationException) {
                            if (AccessToken.getCurrentAccessToken() != null) {
                                LoginManager.getInstance().logOut();
                            }
                        }

                        if (pd != null && pd.isShowing())
                            pd.dismiss();
                        Log.e("onError", "" + exception.getMessage());
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    /** End Init Facebook Login And Callbacks*/

    /** Fb Login Initializer*/
    private void fbLogin() {
        if (NetworkStatus.isNetworkConnected(this)) {
            /*if (pd != null && !pd.isShowing())
                pd.show();*/
            LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends", "email"));
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }
    /** End Fb Login Initializer*/

    /** Google Login Initializer*/
    public void googleLogin() {
        if (NetworkStatus.isNetworkConnected(getApplicationContext())) {
            if (!mGoogleApiClient.isConnected()) {
                mSignInClicked = true;
                mGoogleApiClient.connect();
            } else {
                GooglePlushsignIn();
            }
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }
    /** End Google Login Initializer*/

    /** Google Login action*/
    private void GooglePlushsignIn() {
        /*if (mProgress != null && !mProgress.isShowing()) {
        }*/
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    /** End Google Login action*/

    /** Handling Google Signin Result*/
    private void handleGoogleSignInResult(GoogleSignInResult result) {
        Log.e("Googlesignresult", "" + result.isSuccess());
        Log.e("Googlesignresult", "" + result);
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String name = "";
            String firstName = "";
            String lastName = "";
            String email = "";
            String id = "";
            String GPlusUserimage = "";

            name = acct.getDisplayName();
            firstName = acct.getGivenName();
            lastName = acct.getFamilyName();
            email = acct.getEmail();
            id = acct.getId();
            if (acct.getPhotoUrl() != null)
                GPlusUserimage = acct.getPhotoUrl().toString();

            this.name = name;
            this.emailid = email;
            this.social_id = id;
            this.image_url = GPlusUserimage;
            Toast.makeText(this, "Gmail Name: "+name, Toast.LENGTH_SHORT).show();
            hitSignUpAPI(emailid, password, this.name, LOGIN_TYPE_GOOGLE, social_id, image_url);
        } else {
            Log.e("g+ ", "" + result.getStatus() + "");
            /*if (mProgress != null && mProgress.isShowing())
                mProgress.dismiss();*/
        }
    }
    /** End Handling Google Signin Result*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e("g+", result + "");
            if (result != null)
                handleGoogleSignInResult(result);
            else
                Toast.makeText(this, "No result found!!", Toast.LENGTH_SHORT).show();
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void onSignin(View view) {
        startActivity(new Intent(this, ActivitySignin.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onSignup(View view) {
        if (isValidate()){
            hitSignUpAPI(emailid, password, name, LOGIN_TYPE_NORMAL, social_id, image_url);
        }
        else{
            Toast.makeText(this, "Fill All Entry Carefully!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isValidate(){
        if (et_name.getText().toString().trim().length()>0){
            if (et_email.getText().toString().trim().length()>0){
                if(et_mobile.getText().toString().trim().length()>0){
                    if (et_password.getText().toString().trim().length()>0){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void hitSignUpAPI(String email, String password, String name, String login_type, String social_id, String image_url) {
        if (NetworkStatus.isNetworkConnected(getApplicationContext())) {
            ReqSignupSigninBean reqSignupSigninBean = new ReqSignupSigninBean();
            reqSignupSigninBean.device_type = "android";
            reqSignupSigninBean.device_token = "qwerty";
            reqSignupSigninBean.email = email;
            reqSignupSigninBean.password = password;
            reqSignupSigninBean.login_type = login_type;
            reqSignupSigninBean.social_id = social_id;
            reqSignupSigninBean.image_url = image_url;
            reqSignupSigninBean.name = name;
            NetworkService serviceCall = new NetworkService(AppConstant.API_SIGNUP, AppConstant.METHOD_POST, this);
            serviceCall.call(reqSignupSigninBean);
        } else {
            Toast.makeText(this, "No Internet Connection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetworkCallInitiated(String service) {
        pd.setTitle("Please Wait...");
        pd.show();
        Log.e("onInitiatedSignup",""+service);
    }

    @Override
    public void onNetworkCallCompleted(String service, String response) {
        Log.e("onNetworkcall completed", "" + service);
        try {
            if (service.equalsIgnoreCase(AppConstant.API_SIGNUP)) {
                Log.e("RespSignup===>", "" + response);
                ResSignupSignInBean resSignupSignInBean = ResSignupSignInBean.fromJson(response);

                if (resSignupSignInBean != null) {
                    if (resSignupSignInBean.code == 200) {
                        pd.dismiss();
                        Log.d("Respns_Name", "" + resSignupSignInBean.response.name);

                        AppPreferences.INSTANCE.setLogin(true);
                        AppPreferences.INSTANCE.setAccessToken(resSignupSignInBean.response.token);
                        AppPreferences.INSTANCE.setUserId(Integer.parseInt(resSignupSignInBean.response.id));
                        AppPreferences.INSTANCE.setUserName(resSignupSignInBean.response.name);
                        AppPreferences.INSTANCE.setUserEmail(resSignupSignInBean.response.email);

                        Intent in = new Intent(this, MainActivity.class);
                        in.putExtra("user_data", resSignupSignInBean.response);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();
                    } else if (resSignupSignInBean.code == 400) {
                        pd.dismiss();
                        Toast.makeText(this, "" + resSignupSignInBean.message, Toast.LENGTH_SHORT).show();
                    } else if (resSignupSignInBean.code == 401) {
                        pd.dismiss();
                        Toast.makeText(this, "" + resSignupSignInBean.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    pd.dismiss();
                    Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkCallError(String service, String errorMessage) {
        pd.dismiss();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this, 0).show();
            return;
        }
        if (!mIntentInProgress) {
            mConnectionResult = connectionResult;
            if (mSignInClicked) {
                resolveSignInError();
            } else {

            }
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
}
