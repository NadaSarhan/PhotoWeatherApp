package com.nada.photoweatherapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nada.photoweatherapp.R;
import com.nada.photoweatherapp.model.Main;
import com.nada.photoweatherapp.model.Weather;
import com.nada.photoweatherapp.api.WeatherAPI;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // initialize variables
    private Button btn_camera, btn_history;
//    private ShareButton sbtn_fb;
//    private LoginButton sbtnloginfb;
    private ImageView pic;

    private String country;
    private String url = "api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}";
    private String apiKey = "a953073afffd4fbeae3ad4671047eb8d";

    FusedLocationProviderClient fusedLocationProviderClient;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // assign variables
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_history = (Button) findViewById(R.id.btn_history);
//        sbtnloginfb = (LoginButton) findViewById(R.id.sbtnloginfb);
//        sbtn_fb = (ShareButton) findViewById(R.id.sbtn_fb);
        pic = (ImageView) findViewById(R.id.iv_pic);

        // for facebook
        // Initialize facebook SDK.
//        FacebookSdk.sdkInitialize(MainActivity.this.getApplicationContext());
        // Create a callbackManager to handle the login responses.
//        callbackManager = CallbackManager.Factory.create();

        // initialized fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // request for a camera runtime permission
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the location of the user
                getLocation();

            }
        });

        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAlbum();
            }
        });

    }

    private void getLocation() {

        // check permission for location
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // when permission generated
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    // initialized location
                    Location location = task.getResult();
                    if (location != null) {
                        try {
                            // initialized GeoCoder
                            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                            // initialized address list
                            List<Address> addressList = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1
                            );
                            // get the latitude
                            double latitude;
                            latitude = addressList.get(0).getLatitude();
                            //get the longitude
                            double longitude;
                            longitude = addressList.get(0).getLongitude();
                            // get the country name
                            country = addressList.get(0).getCountryName();

                            System.out.println("------ country ------ " + country);

                            getWeather();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

        } else {
            // when permission denied
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }

    public void getWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<Weather> weatherCall = weatherAPI.getWeather(country, apiKey);
        weatherCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                if(response.code() == 404) {
                    System.out.println("------ response.code() == 404 ------ ");
                } else if(!(response.isSuccessful())) {
                    System.out.println("------ response.isSuccessful() ------ ");
                }

                Weather weatherOBJ = response.body();
                Main main = weatherOBJ.getMain();
                Double temp = main.getTemp();
                Integer temperature = (int) (temp-273.15);
                String strTEMP = String.valueOf(temperature);

                System.out.println("------ temp ------ " + strTEMP);

                // open camera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            pic.setImageBitmap(bitmap);

//            sbtn_fb.setVisibility(View.VISIBLE);
//            sbtnloginfb.setVisibility(View.VISIBLE);
//
//            // Call callbackManager.onActivityResult to pass login result to the LoginManager via callbackManager.
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//            // Define Content want to share
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) pic.getDrawable();
//            Bitmap bit = bitmapDrawable.getBitmap();
//            SharePhoto sharePhoto = new SharePhoto.Builder()
//                    .setBitmap(bit)
//                    .build();
//            SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
//                    .addPhoto(sharePhoto)
//                    .build();
//            // Set the content want to share.
//            sbtn_fb.setShareContent(sharePhotoContent);
        }
    }

    public void goToAlbum() {
        Intent intent = new Intent(this, AlbumActivity.class);
        startActivity(intent);
    }

}
