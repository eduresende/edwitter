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

public class Twitter {
	
	static String CONSUMER_KEY =           "v3ux2TP1Wppio2eO8kAg";
	static String CONSUMER_SECRET =        "8W5Gnr1GMLhBCSmkt08FkIyqMQk5i8WX2bcywCbOMQ";
	static String CALLBACK_URL =           "http://www.eduardo.com";

	static String TOKEN =           "387261509-Yk9Br5sw0bUdLNucEEfxE0Kp4Y2YcJ81PIXhJSPE";
	static String TOKEN_SECRET =           "iJprawuBSbobJaQGHt5igOa1sMsLIw5Y5E1p3SRvs";
	
	static CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);;
	static HttpClient mClient = new DefaultHttpClient();
	
	public static void postar(String texto){
		
		JSONObject jso = null;
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
