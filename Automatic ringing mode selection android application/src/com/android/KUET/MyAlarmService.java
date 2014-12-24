package com.android.KUET;

import java.util.Timer;

import android.app.Service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.widget.Toast;


/*When attaching a location listener to a location provider 
 * (regardless of the exact kind of provider), 
 * it is important to execute this listener code in the main thread of your activity or service.
 *  If you run this in any background thread (like using an executor) then this will not work. 
 *  The listener code itself has to be on the main thread. You can kick of a task in an executor in the listener,
 *  but the listener itself must run in the main thread of the app.*/



public class MyAlarmService extends Service{
	
    	 double longi1,lati1,currentlongi,currentlati,radd;
	    final double PI = 3.14159265;
	    final double deg2radians = PI/180.0;
	    String str;

	    LocationManager manager;  
	    AudioManager mode;       



@Override

public void onCreate() {

manager= (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
mode= (AudioManager) this  .getSystemService(Context.AUDIO_SERVICE);
Toast.makeText(getBaseContext(), "Service activated",Toast.LENGTH_LONG).show();

}



@Override

public IBinder onBind(Intent intent) {
Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();

return null;

}



@Override

public void onDestroy() {

// TODO Auto-generated method stub

super.onDestroy();
Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();

}



@Override

public void onStart(Intent intent, int startId) {
	
super.onStart(intent, startId);//Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();



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
			        currentlati=location.getLatitude();
				    currentlongi=location.getLongitude();
				    
				  // str = "lat: " + location.getLatitude() + "longi: " + location.getLongitude();
			 	// Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
				    
				}
			};
			
          	manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
				    
				   String TableName = "myTable";
				    SQLiteDatabase myDB  ;
				        
				        try {
				        	   myDB = openOrCreateDatabase("prottoy_db", MODE_PRIVATE, null);
				        	   Cursor c =myDB.rawQuery("SELECT longi ,lati FROM " + TableName, null);
				        	   if (c != null) 
				        	   {
				        		   boolean  match=false;	  
				        		   c.moveToFirst();
				       	           currentlongi=currentlongi * deg2radians;
				       	           currentlati=currentlati* deg2radians;
				        		  do  
				        	     {
				        			  radd=0;
				        			//  Log.d("x", "Do while loop");
				        	       longi1 = c.getDouble(c.getColumnIndex("longi"));
				                   lati1=c.getDouble(c.getColumnIndex("lati"));
				          	       longi1 = longi1 *  deg2radians;
				       	           lati1 = lati1  * deg2radians;
				       	           radd = 2*Math.asin(Math.sqrt(Math.pow(Math.sin((lati1-currentlati)/2),2.0) +  Math.cos(lati1)*Math.cos(currentlati)*Math.pow(Math.sin((longi1-currentlongi)/2),2.0)));
				       	           radd=(radd*6371)*3280.839;
				       	          // Log.d("x=radd", "radd="+radd);
				       	          
				       	        //Log.d("x=rad", "rad="+radd);
				       	           if(radd<=200)
				       	           {
				       	        	  // Log.d("x", "before match=true");
				       	        	match=true;
				       	        //	Log.d("x", "after match=true");
				       	        	break;
				       	           }
				        	     }while(c.moveToNext());
				        	     if(match==true)
				        	     {
				        	    	 //Log.d("x", "before if match=true");
				        	       	mode.setRingerMode(AudioManager.RINGER_MODE_SILENT); 
				        	        Toast.makeText(getBaseContext(), "Silent Mode Activated",
				                            Toast.LENGTH_LONG).show();
				        	     }
				        	     else 
				        	     { 
				        	    	 mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);	
				        	    	 Toast.makeText(getBaseContext(), "RInging Mode Activated",
				                             Toast.LENGTH_LONG).show();
				        	     }
				        		}
				        	   myDB.close(); 
				        }
				        catch(Exception ex)
				        {
				        	mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				        	 //Toast.makeText(getBaseContext(), "Ringing Mode Activated",
				              //          Toast.LENGTH_LONG).show();
				        	//Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();		
				        } 
}



@Override

public boolean onUnbind(Intent intent) {


Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();

return super.onUnbind(intent);

}
}