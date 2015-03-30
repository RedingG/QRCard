package com.reding.qrcard;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends Activity {

	private final int SPLASH_TIME = 2000;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Log.d("No pointer", "19");
		
		Context c = SplashActivity.this;
		SharedPreferences ini = c.getSharedPreferences("INI", MODE_PRIVATE);
		Boolean b = ini.getBoolean("ISFIRST", true);
		
		Log.d("No pointer", "25");
		
		try{
		
		if(b){
			Log.d("No pointer", "28");
			new Handler().postDelayed(new Runnable() {

				public void run() {
				// TODO Auto-generated method stub
					Log.d("No pointer", "33");
				Intent mainIntent = new Intent(SplashActivity.this,WelcomeActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
				Log.d("No pointer", "37");
				}
			}, SPLASH_TIME);
		}
		else{
			new Handler().postDelayed(new Runnable() {
				
				public void run() {
				// TODO Auto-generated method stub
					Log.d("No pointer", "46");
				Intent mainIntent = new Intent(SplashActivity.this,MainActivity.class);
				SplashActivity.this.startActivity(mainIntent);
				SplashActivity.this.finish();
				Log.d("No pointer", "50");
				}
			}, SPLASH_TIME);
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
