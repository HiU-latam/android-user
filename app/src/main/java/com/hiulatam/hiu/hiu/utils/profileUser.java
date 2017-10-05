package com.hiulatam.hiu.hiu.utils;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.facebook.AccessToken;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.hiulatam.hiu.hiu.MyApplication;
import com.steelkiwi.instagramhelper.model.InstagramUser;
import com.steelkiwi.instagramhelper.utils.SharedPrefUtils;

/**
 * Created by ltaleron on 10/3/17.
 */

public class profileUser implements Parcelable {

    public Profile profilefacebok;
    public InstagramUser profileInstagram;



    public profileUser() {
    }


    public static boolean isLoggedInFAacebook(Context contex) {

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    public static boolean isLoggedInInstagram(Context context) {
        String token = SharedPrefUtils.getToken(context);
        MyApplication.profile.profileInstagram=SharedPrefUtils.getInstagramUser(context);

        return token != null;
    }

    /**
     * Logout From Facebook
     */
    public static void callFacebookLogout(Context context) {

        LoginManager.getInstance().logOut();
    }
    public static void callInstagramLogout(Context context) {

       SharedPrefUtils.clearPrefs(context);

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.profilefacebok, flags);

    }

    protected profileUser(Parcel in) {
        this.profilefacebok = in.readParcelable(Profile.class.getClassLoader());
        this.profileInstagram = in.readParcelable(InstagramUser.class.getClassLoader());
    }

    public static final Creator<profileUser> CREATOR = new Creator<profileUser>() {
        @Override
        public profileUser createFromParcel(Parcel source) {
            return new profileUser(source);
        }

        @Override
        public profileUser[] newArray(int size) {
            return new profileUser[size];
        }
    };
}