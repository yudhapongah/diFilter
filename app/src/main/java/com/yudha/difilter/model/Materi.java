package com.yudha.difilter.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;

public class Materi implements Parcelable {

    @DocumentId
    public String id;
    public String judul;
    public String deskripsi;
    public String gambar;

    public Materi(String id, String title, String desc, String img) {
        this.id = id;
        this.judul = title;
        this.deskripsi = desc;
        this.gambar = img;
    }

    public Materi(){}

    protected Materi(Parcel in) {
        id = in.readString();
        judul = in.readString();
        deskripsi = in.readString();
        gambar = in.readString();
    }

    public static final Creator<Materi> CREATOR = new Creator<Materi>() {
        @Override
        public Materi createFromParcel(Parcel in) {
            return new Materi(in);
        }

        @Override
        public Materi[] newArray(int size) {
            return new Materi[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(judul);
        parcel.writeString(deskripsi);
        parcel.writeString(gambar);
    }
}
