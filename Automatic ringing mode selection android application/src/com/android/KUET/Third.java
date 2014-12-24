package com.android.KUET;


import java.util.ArrayList;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import android.view.View;

import android.widget.ListView;

import android.widget.Toast;

public class Third extends ListActivity {
	

    String Word;
    
    SQLiteDatabase myDB;
    String TableName = "myTable";
    ArrayList<String> results = new ArrayList<String>();
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);
        
        try {
        	   myDB = openOrCreateDatabase("prottoy_db", MODE_PRIVATE, null);
        	   
        	   Cursor c = myDB.rawQuery("SELECT location FROM " + TableName   , null);
        	  
        	   
        	   if (c != null) {
        		   
        		   c.moveToFirst();
        		   
        	     do {
        	    	 
        	      Word = c.getString(c.getColumnIndex("location"));
       
        	     results.add("" + Word );
        	       
        	    }while(c.moveToNext());
        	   }
        	   this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,results));
        }
        
        catch(Exception ex){
        	Toast.makeText(getBaseContext(), "No Previous Location is saved",
                    Toast.LENGTH_LONG).show();
        	//Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        	
        }
        	    myDB.close();
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = (String) getListAdapter().getItem(position);
		Toast.makeText(this, item + " is deleted", Toast.LENGTH_LONG).show();
		//Log.d("x",""+position);
		SQLiteDatabase db;
		db = openOrCreateDatabase("prottoy_db", MODE_PRIVATE, null);
		//Log.d("x", "before delete");
		db.execSQL("delete from myTable where location = '"+item+"';");
		//Log.d("x", "after delete");
		startActivity(new Intent(Third.this,Main.class));	
		db.close();
	}
}

