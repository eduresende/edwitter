package com.eduardo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TimelineActivity extends Activity implements OnClickListener {
	
	Button buttonAtualizar;
	ListView listTimeline;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		
		
		//buttonAtualizar = (Button)findViewById(R.id.buttonAtualizar);
		//buttonAtualizar.setOnClickListener(this);
		
		//listTimeline = (ListView)findViewById(R.id.listView1);
		
		  super.onCreate(savedInstanceState);
		  
		  setContentView(R.layout.timeline);  
		  
		  listTimeline = (ListView) findViewById(R.id.listPosts);
		  
		  
		  JSONArray result = Twitter.ler();
//
//			
//			
			 ArrayAdapter<String> servers = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
//
//
			    for(Integer i=0; i< result.length(); i++){
			        try{

			        	JSONObject obj = result.getJSONObject(i);
			        	String post = obj.getJSONObject("user").getString("name") + ": " + obj.getString("text");
			        	

			            servers.add(post);
			        }catch(JSONException e){

			        }
			    }
			
			listTimeline.setAdapter(servers);
			listTimeline.setTextFilterEnabled(true);
		  
		  
		  
		  
		  
		  //setListAdapter(new ArrayAdapter<String>(this,
		  //          android.R.layout.simple_list_item_1, COUNTRIES));
		  //getListView().setTextFilterEnabled(true);
	}
	
	public void onClick(View v) {
		//new Leitor().execute();
		JSONArray result = Twitter.ler();
		//textoTimeline.setText(result);
		//Twitter.ler();
		
		
		 ArrayAdapter<String> servers = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);

		    //Loop though my JSONArray
		    for(Integer i=0; i< result.length(); i++){
		        try{
		            //Get My JSONObject and grab the String Value that I want.
		            String obj = result.getJSONObject(i).getString("NAME");

		            //Add the string to the list
		            servers.add(obj);
		        }catch(JSONException e){

		        }
		    }
		
		//setListAdapter(servers);
		
		//Display the listView
	    //ListView lv = getListView();
	    //lv.setTextFilterEnabled(true);
		
		
		//Log.v(Constantes.TAG, result.toString());
		
    }
	
	class Leitor extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... v) {
			try {
				Twitter.ler();
				return null;
			} catch (Exception e) {
				Log.e(Constantes.TAG, e.toString());
				e.printStackTrace();
				return null;
			}
		}
    }

}
