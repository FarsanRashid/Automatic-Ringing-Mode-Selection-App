package com.android.KUET;

import android.app.Activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class Second extends Activity {
	
	AnimationDrawable anim2;
	ImageView iv2;

	
	 EditText text;
	 String input;
	 SQLiteDatabase myDB;
     String TableName = "myTable";
     
     Button b3;
	 double longitude;
	 double latitude;
	 String str;
	  
	  
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
		
	//	 iv2 =(ImageView) findViewById(R.id.imageView2);
	  //   iv2.setBackgroundResource(R.anim.animation2);
	    // anim2= (AnimationDrawable)(iv2.getBackground());
	
		
		b3= (Button) findViewById(R.id.button4);// this is submit button
		b3.setEnabled(false);
		text = (EditText) findViewById(R.id.editText1);
		
		LocationManager manager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        
		LocationListener listener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
						
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				Toast.makeText(getApplicationContext(),"GPS is Enabled" ,Toast.LENGTH_SHORT).show();
				
			}
			
						@Override
			public void onProviderDisabled(String provider) {
				Toast.makeText(getApplicationContext(),"GPS is Disabled" ,Toast.LENGTH_SHORT).show();
				
			}
			
						
			@Override
			public void onLocationChanged(Location location) {
			
				longitude=200;
				latitude=200;
		        latitude=location.getLatitude();
			    longitude=location.getLongitude();
		if(latitude<200&&longitude<200)
		{
	    		b3.setEnabled(true);
			    b3.setOnClickListener(new OnClickListener() {	
				
				public void onClick(View v) {
					  
					
					input= text.getText().toString().trim();
					
			        try {
			        	   myDB = openOrCreateDatabase("prottoy_db", MODE_PRIVATE, null);
			        	   
			        	   myDB.execSQL("CREATE TABLE IF NOT EXISTS "
			        	     + TableName + " (location VARCHAR, longi REAL,lati REAL);");
			
			        	   myDB.execSQL("INSERT INTO "
			  	        	    + TableName +" Values ('"+input+"','"+longitude+"','"+latitude+"');");
			        	     
			        	   Toast.makeText(getApplicationContext(), "Successfully inserted",Toast.LENGTH_SHORT).show();
			        	   
			        	   startActivity(new Intent(Second.this,Main.class));
		
			            }
			        
			        catch(Exception ex){
			        	Toast.makeText(getApplicationContext(), ex.toString(),Toast.LENGTH_LONG).show();
			                           }
			        	    myDB.close();
				};
			});
		}
			 //str = "lat: " + location.getLatitude() + "longi: " + location.getLongitude();
			 //Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
			}
		};
		
		
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
}
	 @Override
	    public void onWindowFocusChanged (boolean hasFocus) {
	       super.onWindowFocusChanged(hasFocus);
	       if (hasFocus)
	       {

	    	//   anim2.start();
	    	}
	       }
}
