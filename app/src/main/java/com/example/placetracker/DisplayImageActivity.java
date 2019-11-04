package com.example.placetracker;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



import com.example.placetracker.domain.PlaceSelfie;
import com.example.placetracker.utility.CurrentJob;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;


import androidx.coordinatorlayout.widget.CoordinatorLayout;

public class DisplayImageActivity extends Activity {

	ImageView selfie;
	TextView lang;
	TextView longi;
	TextView date;
	ImageView selfie2;
	TextView lang2;
	TextView long2;
	TextView date2;
	private FloatingActionButton btnSelect;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		Intent intent = getIntent();
		PlaceSelfie placeSelfie = (PlaceSelfie) intent.getExtras().get("selfie");

		btnSelect = findViewById(R.id.btnmap);
		btnSelect.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DisplayImageActivity.this, GoogleMapActivity.class);
				startActivity(intent);
			}
		});
		selfie = findViewById(R.id.selfie1);
		lang = findViewById(R.id.lang);
		lang.setTextColor(Color.BLACK);
		longi = findViewById(R.id.longi);
		longi.setTextColor(Color.BLACK);
		date = findViewById(R.id.dateTime);
		date.setTextColor(Color.BLACK);
		selfie2 = findViewById(R.id.selfie2);
		lang2 = findViewById(R.id.lang2);
		lang2.setTextColor(Color.BLACK);
		long2 = findViewById(R.id.long2);
		long2.setTextColor(Color.BLACK);
		date2 = findViewById(R.id.dateTime2);
		date2.setTextColor(Color.BLACK);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
		String strDate = "";
		try {
			if (placeSelfie.getFirstSelfieDate() != null) {
				strDate = formatter.format(placeSelfie.getFirstSelfieDate());
			}

			longi.setText("Longitude : " + placeSelfie.getLongitude1());
			lang.setText("Latitude : " + placeSelfie.getLatitude1());
			date.setText("Date Time : " + strDate);

			byte[] outImage = placeSelfie.getFirstSelfie();
			ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
			Bitmap theImage = BitmapFactory.decodeStream(imageStream);
			selfie.setImageBitmap(theImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		strDate = "";
        try {
			long2.setText("Longitude : " + placeSelfie.getLongitude2());
			lang2.setText("Latitude : " + placeSelfie.getLatitude2());
			if (placeSelfie.getLastSelfieDate() != null) {
				strDate= formatter.format(placeSelfie.getLastSelfieDate());
			}
			date2.setText("Date Time : " +strDate);
            byte[] outImage2 = placeSelfie.getLastSelfie();
            ByteArrayInputStream imageStream2 = new ByteArrayInputStream(outImage2);
            Bitmap theImage2 = BitmapFactory.decodeStream(imageStream2);
            selfie2.setImageBitmap(theImage2);

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}