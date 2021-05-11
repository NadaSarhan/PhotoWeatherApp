package com.nada.photoweatherapp.database;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public interface AlbumDAO {
    @Insert
    Comparable insertPic(Bitmap pic);

    @Insert
    Comparable insertText(Album str);

    @Query("select * from album")
    Single<List<Album>> getAlbum();
}
