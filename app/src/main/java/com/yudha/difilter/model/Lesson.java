package com.yudha.difilter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class Lesson implements Parcelable {

    @DocumentId
    public String id;
    public String link;
    public String judul;

    protected Lesson(Parcel in) {
        id = in.readString();
        link = in.readString();
        judul = in.readString();
    }

    public Lesson(){}

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(link);
        parcel.writeString(judul);
    }
}
