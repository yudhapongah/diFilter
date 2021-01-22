package com.yudha.difilter.model;

import com.google.firebase.firestore.DocumentId;

public class Soal {
    @DocumentId
    public String id;
    public String soal;
    public String jawaban;
    public String jawabanA;
    public String jawabanB;
    public String jawabanC;
    public String jawabanD;
    public boolean status;

    public Soal(){
        status =false;
    }
}
