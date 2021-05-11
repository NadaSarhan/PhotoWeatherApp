package com.nada.photoweatherapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "album")
public class Album {

    @PrimaryKey(autoGenerate = true)
    private int picId;
    private String text;
//    private Bitmap pic;

//    public Album(int picId, Bitmap pic) {
//        this.picId = picId;
//        this.pic = pic;
//    }


    public Album(int picId, String text) {
        this.picId = picId;
        this.text = text;
    }

    public int getPicId() {
        return picId;
    }
    public void setPicId(int picId) {
        this.picId = picId;
    }

//    public Bitmap getPic() {
//        return pic;
//    }
//    public void setPic(Bitmap pic) {
//        this.pic = pic;
//    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
