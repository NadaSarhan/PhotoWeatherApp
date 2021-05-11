package com.nada.photoweatherapp.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nada.photoweatherapp.R;

public class AlbumActivity extends AppCompatActivity {

    private TextView text;
    private ImageView pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        // assign variables
        text = (TextView) findViewById(R.id.text);
        pic = (ImageView) findViewById(R.id.album_pic);

//        text.setText();

    }

}
