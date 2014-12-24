package com.android.KUET;


import android.app.Activity;
import android.app.AlarmManager;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;


import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Calendar;
import android.app.PendingIntent;

import android.content.Intent;



public class Main extends Activity {
	
	
      private PendingIntent pendingIntent;
	  double longi1,lati1,currentlongi,currentlati,radd;
	  String str;
	  
	AnimationDrawable anim;
	ImageView UI;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	  
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        UI =(ImageView) findViewById(R.id.imageView1);
        UI.setBackgroundResource(R.anim.animation);
        anim= (AnimationDrawable)(UI.getBackground());
        
 
        Button b1 = (Button) findViewById(R.id.button1);//this button is for storing user's location
        Button b2=  (Button) findViewById(R.id.button2);//aita akta akamer button :) (info button)
        Button b3=  (Button) findViewById(R.id.button3);//this button is for erasing user's previous location
        Button buttonStart = (Button)findViewById(R.id.button4);
        

        
        b1.setOnClickListener(new OnClickListener() 
        {
        	
			public void onClick(View v)
			{
				startActivity(new Intent(Main.this, Second.class));		
				
			}
		});
        
        b3.setOnClickListener(new OnClickListener()
        {
   	       
			public void onClick(View v) {
			startActivity(new Intent(Main.this,Third.class));
			}
		});
        
        buttonStart.setOnClickListener(new Button.OnClickListener()
        {
        @Override
			public void onClick(View arg0) 
		   {
              Intent myIntent = new Intent(Main.this, MyAlarmService.class);
              pendingIntent = PendingIntent.getService(Main.this, 0, myIntent, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 2);//the time interval between first button press & start of service (directly in second unit) 
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 120*1000, pendingIntent);       
  }});
        
        
         b2.setOnClickListener(new OnClickListener() 
          {
			
			public void onClick(View v) 
			{

				
				 AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

				  alarmManager.cancel(pendingIntent);
				           // Tell the user about what we did.
				           Toast.makeText(Main.this, "Service Stopped", Toast.LENGTH_LONG).show();
				 }});
         
 }
    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (hasFocus);
       //anim.start();
    }
}

