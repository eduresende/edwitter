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
	
	static CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(Constantes.CONSUMER_KEY, Constantes.CONSUMER_SECRET);
	static HttpClient mClient = new DefaultHttpClient();
	
	public static JSONObject postar(String texto){
		
		JSONObject jso = null;
		consumer.setTokenWithSecret(Constantes.TOKEN, Constantes.TOKEN_SECRET);
		
		HttpParams parametros = new BasicHttpParams();
		HttpProtocolParams.setUseExpectContinue(parametros, false);
		
		try {
			HttpPost post = new HttpPost(Constantes.POST_RESOURCE);
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
		return jso;
	}
	
	public static JSONArray ler(){
		
		//StringBuilder builder = new StringBuilder();
		HttpGet httpGet = new HttpGet("http://api.twitter.com/1/statuses/home_timeline.json");
		//org.apache.http.HttpResponse response;
		consumer.setTokenWithSecret(Constantes.TOKEN, Constantes.TOKEN_SECRET);
		
		JSONArray array = null;
		
		try {
			consumer.sign(httpGet);
			String response = mClient.execute(httpGet, new BasicResponseHandler());
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
		
			//StatusLine statusLine = ((org.apache.http.HttpResponse) response).getStatusLine();
			//int statusCode = statusLine.getStatusCode();
			
			
			//if (statusCode == 200) {
				
				
				
				
				//jso = new JSONObject(response);
				
//				HttpEntity entity = response.getEntity();
//				InputStream content = entity.getContent();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//				String line;
//				while ((line = reader.readLine()) != null) {
//					builder.append(line);
//					//Log.v(Constantes.TAG, "         linha            " + line);
//				}
//				
//				try {
//					Log.v(Constantes.TAG, "to aqui");
//					jso = new JSONObject();
//					
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			} else {
//				//Couldn't obtain the data
//			}
//			
//		} catch (OAuthMessageSignerException e) {
//			e.printStackTrace();
//		} catch (OAuthExpectationFailedException e) {
//			e.printStackTrace();
//		} catch (OAuthCommunicationException e) {
//			e.printStackTrace();
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		
//		
		
		
		//Log.v(Constantes.TAG, statusCode);
		
//		try {
//			consumer.sign(httpGet);
//			org.apache.http.HttpResponse response = mClient.execute(httpGet);
//			StatusLine statusLine = ((org.apache.http.HttpResponse) response).getStatusLine();
//			int statusCode = statusLine.getStatusCode();
//			if (statusCode == 200) {
//				HttpEntity entity = response.getEntity();
//				InputStream content = entity.getContent();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
//				String line;
//				while ((line = reader.readLine()) != null) {
//					builder.append(line);
//				}
//			} else {
//				//Couldn't obtain the data
//			}
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		catch (OAuthMessageSignerException e) {
//			e.printStackTrace();
//		} catch (OAuthExpectationFailedException e) {
//			e.printStackTrace();
//		} catch (OAuthCommunicationException e) {
//			e.printStackTrace();
//		}
//		//return builder.toString();
		//Log.v(Constantes.TAG, builder.toString());
	}

}
