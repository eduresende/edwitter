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
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Twitter {
	
	static CommonsHttpOAuthConsumer consumer;
	static HttpClient client = new DefaultHttpClient();
	
	public static CommonsHttpOAuthConsumer getConsumer(){
		consumer = new CommonsHttpOAuthConsumer(Constantes.CONSUMER_KEY, Constantes.CONSUMER_SECRET);
		consumer.setTokenWithSecret(Constantes.TOKEN, Constantes.TOKEN_SECRET);
		return consumer;
	}
	
	
	public static JSONObject postar(String params[]){
		JSONObject jso = null;
		HttpParams parametros = new BasicHttpParams();
		HttpProtocolParams.setUseExpectContinue(parametros, false);
		try {
			HttpPost post = new HttpPost(Constantes.POST_RESOURCE);
			LinkedList<BasicNameValuePair> out = new LinkedList<BasicNameValuePair>();
			out.add(new BasicNameValuePair("status", params[0]));
			out.add(new BasicNameValuePair("lat", params[1]));
			out.add(new BasicNameValuePair("long", params[2]));
			out.add(new BasicNameValuePair("place_id", params[3]));
			out.add(new BasicNameValuePair("display_coordinates", "true"));
			
			Log.v(Constantes.TAG, params[1]);
			Log.v(Constantes.TAG, params[2]);
			
			
			post.setEntity(new UrlEncodedFormEntity(out, HTTP.UTF_8));
			post.setParams(parametros);
			getConsumer().sign(post);
			String response = client.execute(post, new BasicResponseHandler());
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
		return jso;
	}
	
	public static JSONArray ler(){
		HttpGet httpGet = new HttpGet(Constantes.GET_TIMELINE_RESOURCE);
		JSONArray array = null;
		try {
			getConsumer().sign(httpGet);
			String response = client.execute(httpGet, new BasicResponseHandler());
			
			Log.v(Constantes.TAG, response);
			
			array = new JSONArray(response);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		return array;
	}
	
	public static String buscarPlaceId(String latitude, String longitude) throws JSONException{
		String params = "?lat=" + latitude + "&" + "long=" + longitude; 
		HttpGet httpGet = new HttpGet(Constantes.GET_PLACE_ID + params);
		String response = "";
		String id = "";
		try {
			getConsumer().sign(httpGet);
			response = client.execute(httpGet, new BasicResponseHandler());
			JSONObject jso = null;
			jso = new JSONObject(response);
			id = jso.getJSONObject("result").getJSONArray("places").getJSONObject(0).getString("id");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			e.printStackTrace();
		}
		return id;
	}

}
