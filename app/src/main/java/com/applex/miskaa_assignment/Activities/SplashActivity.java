package com.applex.miskaa_assignment.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.applex.miskaa_assignment.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splash_image = findViewById(R.id.splash_image);

        Display display = getWindowManager().getDefaultDisplay();
        int displayWidth = display.getWidth();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), R.drawable.assignment, options);

        int width = options.outWidth;
        if (width > displayWidth) {
            options.inSampleSize = Math.round((float) width / (float) displayWidth);
        }
        options.inJustDecodeBounds = false;

        Bitmap scaledBitmap =  BitmapFactory.decodeResource(getResources(), R.drawable.assignment, options);
        splash_image.setImageBitmap(scaledBitmap);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            finish();
        }, 1000);
    }
}