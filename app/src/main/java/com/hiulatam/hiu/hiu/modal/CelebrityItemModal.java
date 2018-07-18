package com.hiulatam.hiu.hiu.modal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by:  Shiny Solutions
 * Created on:  10/8/17.
 */

public class CelebrityItemModal implements Parcelable {

    private String image;
    private String name;
    private String article;
    private int percentage;
    private String favorite;
    private String emptyResult;
    private String value;

    public CelebrityItemModal() {
    }

    protected CelebrityItemModal(Parcel in) {
        image = in.readString();
        name = in.readString();
        article = in.readString();
        percentage = in.readInt();
        favorite = in.readString();
        emptyResult = in.readString();
        value = in.readString();
    }

    public static final Creator<CelebrityItemModal> CREATOR = new Creator<CelebrityItemModal>() {
        @Override
        public CelebrityItemModal createFromParcel(Parcel in) {
            return new CelebrityItemModal(in);
        }

        @Override
        public CelebrityItemModal[] newArray(int size) {
            return new CelebrityItemModal[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getEmptyResult() {
        return emptyResult;
    }

    public void setEmptyResult(String emptyResult) {
        this.emptyResult = emptyResult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(name);
        parcel.writeString(article);
        parcel.writeInt(percentage);
        parcel.writeString(favorite);
        parcel.writeString(emptyResult);
        parcel.writeString(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
