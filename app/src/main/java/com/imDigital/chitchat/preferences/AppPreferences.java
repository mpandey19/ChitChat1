package com.imDigital.chitchat.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


/**
 * @author Gaurava enum for SharedPreference of application because it will
 *         use through out the app.
 */
public enum AppPreferences {
    INSTANCE;

    private static final String SHARED_PREFERENCE_NAME = "AppPreference";
    private SharedPreferences mPreferences;
    private Editor mEditor;
    private String deviceTocken;

    /**
     * private constructor for singleton class
     *
     * @param context
     */
    public void initAppPreferences(Context context) {
        mPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public boolean getUserLoginFirstTime() {
        return mPreferences.getBoolean(SharedPreferencesKeys.firstTime.toString(), true);
    }

    public void setUserLoginFirstTime(boolean firstlogin) {
        mEditor.putBoolean(SharedPreferencesKeys.firstTime.toString(), firstlogin);
        mEditor.commit();

    }

    public boolean isLogin() {
        return mPreferences.getBoolean(SharedPreferencesKeys.isLogin.toString(), false);
    }

    public void setLogin(boolean isLogin) {
        mEditor.putBoolean(SharedPreferencesKeys.isLogin.toString(), isLogin);
        mEditor.commit();
    }

    public int getUserId() {
        return mPreferences.getInt(SharedPreferencesKeys.userId.toString(), 00);
    }

    public void setUserId(int value) {
        mEditor.putInt(SharedPreferencesKeys.userId.toString(), value);
        mEditor.commit();
    }

    public String getFirstName() {
        return mPreferences.getString(SharedPreferencesKeys.firstName.toString(), "");
    }

    public void setFirstName(String name) {
        mEditor.putString(SharedPreferencesKeys.firstName.toString(), name);
        mEditor.commit();
    }

    public String getLastName() {
        return mPreferences.getString(SharedPreferencesKeys.lastName.toString(), "");
    }

    public void setLastName(String name) {
        mEditor.putString(SharedPreferencesKeys.lastName.toString(), name);
        mEditor.commit();
    }

    public String getUserName() {
        return mPreferences.getString(SharedPreferencesKeys.userName.toString(), "");
    }

    public void setUserName(String name) {
        mEditor.putString(SharedPreferencesKeys.userName.toString(), name);
        mEditor.commit();
    }

    public String getUserDob() {
        return mPreferences.getString(SharedPreferencesKeys.dob.toString(), "");
    }

    public void setUserDob(String dob) {
        mEditor.putString(SharedPreferencesKeys.dob.toString(), dob);
        mEditor.commit();
    }

    public String getUserMobileNo() {
        return mPreferences.getString(SharedPreferencesKeys.mobile.toString(), null);
    }

    public void setUserMobileNo(String mobileNo) {
        mEditor.putString(SharedPreferencesKeys.mobile.toString(), mobileNo);
        mEditor.commit();
    }

    public String getUserEmail() {
        return mPreferences.getString(SharedPreferencesKeys.email.toString(), "");
    }

    public void setUserEmail(String email) {
        mEditor.putString(SharedPreferencesKeys.email.toString(), email);
        mEditor.commit();
    }

    public String getUserImage() {
        return mPreferences.getString(SharedPreferencesKeys.profile.toString(), null);
    }

    public void setUserImage(String instName) {
        mEditor.putString(SharedPreferencesKeys.profile.toString(), instName);
        mEditor.commit();
    }

    public String getCity() {
        return mPreferences.getString(SharedPreferencesKeys.instCity.toString(), "");
    }

    public void setCity(String instName) {
        mEditor.putString(SharedPreferencesKeys.instCity.toString(), instName);
        mEditor.commit();
    }

    public String getState() {
        return mPreferences.getString(SharedPreferencesKeys.instState.toString(), "");
    }

    public void setState(String instName) {
        mEditor.putString(SharedPreferencesKeys.instState.toString(), instName);
        mEditor.commit();
    }

    public String getCountry() {
        return mPreferences.getString(SharedPreferencesKeys.country.toString(), "");
    }

    public void setCountry(String instName) {
        mEditor.putString(SharedPreferencesKeys.country.toString(), instName);
        mEditor.commit();
    }

    public String getZip() {
        return mPreferences.getString(SharedPreferencesKeys.instZip.toString(), "");
    }

    public void setZip(String instName) {
        mEditor.putString(SharedPreferencesKeys.instZip.toString(), instName);
        mEditor.commit();
    }

    public String getCountryCode() {
        return mPreferences.getString(SharedPreferencesKeys.currentCountry.toString(), "");
    }

    public void setCountryCode(String instName) {
        mEditor.putString(SharedPreferencesKeys.currentCountry.toString(), instName);
        mEditor.commit();
    }

    public void setAddress1(String address) {
        mEditor.putString(SharedPreferencesKeys.address1.toString(), address);
        mEditor.commit();
    }

    public String getAddress1() {
        return mPreferences.getString(SharedPreferencesKeys.address1.toString(), "");
    }

    public void setAddress2(String address) {
        mEditor.putString(SharedPreferencesKeys.address2.toString(), address);
        mEditor.commit();
    }

    public String getAddress2() {
        return mPreferences.getString(SharedPreferencesKeys.address2.toString(), "");
    }

    public void setAddress3(String address) {
        mEditor.putString(SharedPreferencesKeys.address3.toString(), address);
        mEditor.commit();
    }

    public String getAddress3() {
        return mPreferences.getString(SharedPreferencesKeys.address3.toString(), "");
    }

    public String getDeviceID() {
        return mPreferences.getString(SharedPreferencesKeys.userTypeId.toString(), "");
    }

    public void setDeviceID(String value) {
        mEditor.putString(SharedPreferencesKeys.userTypeId.toString(), value);
        mEditor.commit();
    }

    public String getAccessTokengFB() {
        return mPreferences.getString(SharedPreferencesKeys.fb_token.toString(), "");
    }

    public void setAccessTokenFB(String deviceToken) {
        mEditor.putString(SharedPreferencesKeys.fb_token.toString(), deviceToken);
        mEditor.commit();
    }

    public String getFacebookID() {
        return mPreferences.getString(SharedPreferencesKeys.fb_id.toString(), "");
    }

    public void setFacebookID(String deviceToken) {
        mEditor.putString(SharedPreferencesKeys.fb_id.toString(), deviceToken);
        mEditor.commit();
    }

    public boolean getFirstLogin() {
        return mPreferences.getBoolean(SharedPreferencesKeys.first.toString(), false);
    }

    public void setFirstlogin(boolean firstlogin) {
        mEditor.putBoolean(SharedPreferencesKeys.first.toString(), firstlogin);
        mEditor.commit();
    }

    public void setAccessToken(String accessToken) {
        mEditor.putString(SharedPreferencesKeys.accessToken.toString(), accessToken);
        mEditor.commit();
    }

    public String getAccessToken() {
        return mPreferences.getString(SharedPreferencesKeys.accessToken.toString(), "");
    }
    public void setDeviceTocken(String deviceTocken) {
        this.deviceTocken = deviceTocken;
        mEditor.putString(SharedPreferencesKeys.deviceToken.toString(), deviceTocken);
        mEditor.commit();
    }

    public String getDeviceTocken() {
        return mPreferences.getString(SharedPreferencesKeys.deviceToken.toString(), "");
    }

    public void setCurrentLatitude(String latitude) {
        mEditor.putString(SharedPreferencesKeys.latitude.toString(), latitude);
        mEditor.commit();
    }

    public String getCurrentLatitude() {
        return mPreferences.getString(SharedPreferencesKeys.latitude.toString(), "0.0");
    }

    public void setCurrentLongititude(String longitude) {
        mEditor.putString(SharedPreferencesKeys.longitude.toString(), longitude);
        mEditor.commit();
    }

    public String getCurrentLongititude() {
        return mPreferences.getString(SharedPreferencesKeys.longitude.toString(), "0.0");
    }

    /**
     * Used to clear all the values stored in preferences
     *
     * @return void
     */

    public void clearPreferences() {
        mEditor.clear();
        mEditor.commit();
    }

    public void setPlanPurchaseStatus(String planPurchaseStatus)
    {
        mEditor.putString(SharedPreferencesKeys.purchase_status.toString(), planPurchaseStatus);
        mEditor.commit();
    }

    public String getPlanPurchaseStatus()
    {
        return mPreferences.getString(SharedPreferencesKeys.purchase_status.toString(), "0.0");
    }

    /**
     * Enum for shared preferences keys to store various values
     *
     * @author Madstech
     */
    public enum SharedPreferencesKeys {
        first,
        firstTime,
        userId,
        userName,
        dob,
        isLogin,
        email,
        mobile,
        deviceToken,
        userTypeId,
        lastName,
        fb_token,
        firstName,
        address1,
        address2,
        address3,
        accessToken, lastInserted_trans, plan_id, plan_id_grrom, lastInserted_groom,purchase_status,
        latitude,
        longitude,
        fb_id,
        instCity,
        instState,
        country,
        currentCountry,
        profile,
        instZip,
    }
}
