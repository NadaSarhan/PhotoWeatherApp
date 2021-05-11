package com.nada.photoweatherapp.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nada.photoweatherapp.R;
import com.nada.photoweatherapp.database.AppDatabase;

public class AlbumActivity extends AppCompatActivity {

    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        // assign variables
        pic = (ImageView) findViewById(R.id.album_pic);

        final AppDatabase postsDatabase = AppDatabase.getInstance(this);

//        postsDatabase.albumDAO().insertText(new Album(1, "texte;lkjlk"))
//                .subscribeOn(Schedulers.computation())
//                .subscribe(new CompletableObserver() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                });


    }

}
