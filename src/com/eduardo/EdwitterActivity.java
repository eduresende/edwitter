package com.eduardo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	
	

	public void ler(){
		StringBuilder builder = new StringBuilder();
		HttpGet httpGet = new HttpGet("http://api.twitter.com/1/statuses/home_timeline.json");
		try {
			consumer.sign(httpGet);
			org.apache.http.HttpResponse response = mClient.execute(httpGet);
			StatusLine statusLine = ((org.apache.http.HttpResponse) response).getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				//Couldn't obtain the data
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return builder.toString();
		Log.v("Blah123", builder.toString());
	}

}
