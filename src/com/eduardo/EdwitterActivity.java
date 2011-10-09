package com.eduardo;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EdwitterActivity extends Activity {

	private Button buttonPost;
	private Button buttonRead;
	
	private String CONSUMER_KEY =           "v3ux2TP1Wppio2eO8kAg";
	private String CONSUMER_SECRET =        "8W5Gnr1GMLhBCSmkt08FkIyqMQk5i8WX2bcywCbOMQ";
	//private String CALLBACK_URL =           "http://www.eduardo.com";
	
	private String TOKEN =           "387261509-Yk9Br5sw0bUdLNucEEfxE0Kp4Y2YcJ81PIXhJSPE";
	private String TOKEN_SECRET =           "iJprawuBSbobJaQGHt5igOa1sMsLIw5Y5E1p3SRvs";

	private CommonsHttpOAuthConsumer consumer;
	

	
	HttpClient mClient = new DefaultHttpClient();
	
	JSONObject jso = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		System.setProperty("http.keepAlive", "false");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);
		
		buttonPost = (Button)findViewById(R.id.ButtonPost);
		buttonRead = (Button)findViewById(R.id.ButtonRead);
		
		
		buttonPost.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				chamarPostarActivity();
			}
		});
		
		
		buttonRead.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//Log.v("Blah123", "lendo");
				//ler();
				//JSONArray result = Twitter.ler();
				chamarTimelineActivity();
			}
		});
	}
	
	public void chamarPostarActivity(){
		Intent i = new Intent(EdwitterActivity.this, PostarActivity.class);
		EdwitterActivity.this.startActivity(i);
	}
	
	public void chamarTimelineActivity(){
		Intent i = new Intent(EdwitterActivity.this, TimelineActivity.class);
		EdwitterActivity.this.startActivity(i);
	}

}
