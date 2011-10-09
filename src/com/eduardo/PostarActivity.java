package com.eduardo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class PostarActivity extends Activity {
	
	
	private String CONSUMER_KEY =           "v3ux2TP1Wppio2eO8kAg";
	private String CONSUMER_SECRET =        "8W5Gnr1GMLhBCSmkt08FkIyqMQk5i8WX2bcywCbOMQ";
	private String CALLBACK_URL =           "http://www.eduardo.com";

	private String TOKEN =           "387261509-Yk9Br5sw0bUdLNucEEfxE0Kp4Y2YcJ81PIXhJSPE";
	private String TOKEN_SECRET =           "iJprawuBSbobJaQGHt5igOa1sMsLIw5Y5E1p3SRvs";
	
	private CommonsHttpOAuthConsumer consumer;
	HttpClient mClient = new DefaultHttpClient();
	JSONObject jso = null;
	
	private Button buttonPostar;
	private EditText editStatus;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postar);
		
		Log.v("Blah123", "entrei no postar");
		
		buttonPostar = (Button)findViewById(R.id.buttonPostar);
		editStatus = (EditText)findViewById(R.id.editStatus);
		
		
		buttonPostar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.v("Blah123", "cliquei no botao postar");
				String status = editStatus.getText().toString();
				Log.v("Blah123", "vou postar" + status);
				postar(status);
			}
		});
		
		
	}
	
	public void postar(String texto){
		
		
		
		
		consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);
		consumer.setTokenWithSecret(TOKEN, TOKEN_SECRET);
		
		HttpParams parametros = new BasicHttpParams();
		HttpProtocolParams.setUseExpectContinue(parametros, false);
		
		try {
			HttpPost post = new HttpPost("http://twitter.com/statuses/update.json");
			LinkedList<BasicNameValuePair> out = new LinkedList<BasicNameValuePair>();
			out.add(new BasicNameValuePair("status", texto));
			post.setEntity(new UrlEncodedFormEntity(out, HTTP.UTF_8));
			post.setParams(parametros);
			// sign the request to authenticate
			consumer.sign(post);
			String response = mClient.execute(post, new BasicResponseHandler());
			jso = new JSONObject(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} finally {

		}
		//return jso;
	}

}
