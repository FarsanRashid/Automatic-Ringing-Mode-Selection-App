package com.android.KUET;
import java.util.Timer;
import java.util.TimerTask;
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
/*
 * new Timer().scheduleAtFixedRate(task, after, interval);
 * task being the method to be executed
after the time to initial execution
(interval the time for repeating the execution)*/
/*
 * When an application component starts and the application does not have any other components running, the Android system starts a new Linux process for the application with a single thread of execution. By default, all components of the same application run in the same process and thread (called the "main" thread). If an application component starts and there already exists a process for that application (because another component from the application exists), then the component is started within that process and uses the same thread of execution. However, you can arrange for different components in your application to run in separate processes, and you can create additional threads for any process
 * Threads
When an application is launched, the system creates a thread of execution for the application, called "main." This thread is very important because it is in charge of dispatching events to the appropriate user interface widgets, including drawing events. It is also the thread in which your application interacts with components from the Android UI toolkit (components from the android.widget and android.view packages). As such, the main thread is also sometimes called the UI thread.

The system does not create a separate thread for each instance of a component. All components that run in the same process are instantiated in the UI thread, and system calls to each component are dispatched from that thread. Consequently, methods that respond to system callbacks (such as onKeyDown() to report user actions or a lifecycle callback method) always run in the UI thread of the process.

For instance, when the user touches a button on the screen, your app's UI thread dispatches the touch event to the widget, which in turn sets its pressed state and posts an invalidate request to the event queue. The UI thread dequeues the request and notifies the widget that it should redraw itself.

When your app performs intensive work in response to user interaction, this single thread model can yield poor performance unless you implement your application properly. Specifically, if everything is happening in the UI thread, performing long operations such as network access or database queries will block the whole UI. When the thread is blocked, no events can be dispatched, including drawing events. From the user's perspective, the application appears to hang. Even worse, if the UI thread is blocked for more than a few seconds (about 5 seconds currently) the user is presented with the infamous "application not responding" (ANR) dialog. The user might then decide to quit your application and uninstall it if they are unhappy.

Additionally, the Andoid UI toolkit is not thread-safe. So, you must not manipulate your UI from a worker thread—you must do all manipulation to your user interface from the UI thread. Thus, there are simply two rules to Android's single thread model:

Do not block the UI thread
Do not access the Android UI toolkit from outside the UI thread*/


public class  timer extends Service
{
	 
	    double longi1,lati1,currentlongi,currentlati,radd;
	    final double PI = 3.14159265;
	    final double deg2radians = PI/180.0;
	    String str;
	    LocationManager manager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);  
	    AudioManager mode = (AudioManager) this  .getSystemService(Context.AUDIO_SERVICE);      
        private Timer myTimer;
        
        @Override
        public void onCreate() {
           //code to execute when the service is first created
        }
    
	/** Called when the activity is first created. */
        public void onStart(Bundle savedInstanceState) {
		//Log.d("wa", "Do while loop");
		super.onCreate();
		//setContentView(R.layout.main);

		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				TimerMethod();
			}

		}, 0, 5000);// 0 mane ai function call er kotokkhon por se ai command execute korbe ... 100000 means 100000 milisecond por por check korbe
	}

	private void TimerMethod()
	{
			Runnable Timer_Tick = new Runnable() {
		public void run()
		{
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
				    
				   str = "lat: " + location.getLatitude() + "longi: " + location.getLongitude();
					 Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
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
				        			  Log.d("x", "Do while loop");
				        	       longi1 = c.getDouble(c.getColumnIndex("longi"));
				                   lati1=c.getDouble(c.getColumnIndex("lati"));
				          	       longi1 = longi1 *  deg2radians;
				       	           lati1 = lati1  * deg2radians;
				       	           radd = 2*Math.asin(Math.sqrt(Math.pow(Math.sin((lati1-currentlati)/2),2.0) +  Math.cos(lati1)*Math.cos(currentlati)*Math.pow(Math.sin((longi1-currentlongi)/2),2.0)));
				       	           radd=(radd*6371)*3280.839;
				       	           Log.d("x=radd", "radd="+radd);
				       	           //int rad=(int) radd;
				       	        Log.d("x=rad", "rad="+radd);
				       	           if(radd<=200)
				       	           {
				       	        	   Log.d("x", "before match=true");
				       	        	match=true;
				       	        	Log.d("x", "after match=true");
				       	        	break;
				       	           }
				        	     }while(c.moveToNext());
				        	     if(match==true)
				        	     {
				        	    	 Log.d("x", "before if match=true");
				        	       	mode.setRingerMode(AudioManager.RINGER_MODE_SILENT); 
				        	     }
				        	     else 
				        	     { 
				        	    	 mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);	
				        	     }
				        		}
				        	   myDB.close(); 
				        }
				        catch(Exception ex)
				        {
				        	//mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				        	Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();		
				        } 
			/*Toast.makeText(getBaseContext(), "Silent Mode Activated",
					 Toast.LENGTH_LONG).show();*/
		}
	};
		/*This method is called directly by the timer
		and runs in the same thread as the timer.
		We call the method that will work with the DB
		through the runOnUiThread method.*/
		//this.runOnUiThread(Timer_Tick);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	/*private Runnable Timer_Tick = new Runnable() {
		public void run()
		{
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
				    
				   str = "lat: " + location.getLatitude() + "longi: " + location.getLongitude();
					 Toast.makeText(getApplicationContext(),str, Toast.LENGTH_SHORT).show();
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
				        			  Log.d("x", "Do while loop");
				        	       longi1 = c.getDouble(c.getColumnIndex("longi"));
				                   lati1=c.getDouble(c.getColumnIndex("lati"));
				          	       longi1 = longi1 *  deg2radians;
				       	           lati1 = lati1  * deg2radians;
				       	           radd = 2*Math.asin(Math.sqrt(Math.pow(Math.sin((lati1-currentlati)/2),2.0) +  Math.cos(lati1)*Math.cos(currentlati)*Math.pow(Math.sin((longi1-currentlongi)/2),2.0)));
				       	           radd=(radd*6371)*3280.839;
				       	           Log.d("x=radd", "radd="+radd);
				       	           //int rad=(int) radd;
				       	        Log.d("x=rad", "rad="+radd);
				       	           if(radd<=200)
				       	           {
				       	        	   Log.d("x", "before match=true");
				       	        	match=true;
				       	        	Log.d("x", "after match=true");
				       	        	break;
				       	           }
				        	     }while(c.moveToNext());
				        	     if(match==true)
				        	     {
				        	    	 Log.d("x", "before if match=true");
				        	       	mode.setRingerMode(AudioManager.RINGER_MODE_SILENT); 
				        	     }
				        	     else 
				        	     { 
				        	    	 mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);	
				        	     }
				        		}
				        	   myDB.close(); 
				        }
				        catch(Exception ex)
				        {
				        	mode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
				        	Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();		
				        } 
			/*Toast.makeText(getBaseContext(), "Silent Mode Activated",
					 Toast.LENGTH_LONG).show();
		}
	};*/
}