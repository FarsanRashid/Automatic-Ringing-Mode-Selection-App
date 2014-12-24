package com.android.KUET;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class about_us extends Activity {
	 
	AnimationDrawable white_anim;
	ImageView white_image;
	 protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.about_us);
			
			 
		     
	        white_image =(ImageView) findViewById(R.id.white);
	        white_image.setBackgroundResource(R.anim.white_anim);
	        white_anim= (AnimationDrawable)(white_image.getBackground());
			}
	 @Override
	    public void onWindowFocusChanged (boolean hasFocus) {
	        super.onWindowFocusChanged(hasFocus);
	        if (hasFocus)
	        {
	        	white_anim.start();
	        }
	       
	     }
	    
}
