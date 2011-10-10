package com.eduardo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.graphics.Color;
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
	TextView textResposta;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		buttonAtualizar = (Button)findViewById(R.id.buttonAtualizar);
		buttonAtualizar.setOnClickListener(this);
		textResposta = (TextView)findViewById(R.id.textRespostaLeitura);
		listTimeline = (ListView) findViewById(R.id.listPosts);
	}
	
	public void onClick(View v) {
		new Leitor().execute();
    }
	
	class Leitor extends AsyncTask<Void, Void, JSONArray> {
		@Override
		protected JSONArray doInBackground(Void... v) {
			try {
				JSONArray timeline = buscaTimeline();
				return timeline;
			} catch (Exception e) {
				Log.e(Constantes.TAG, e.toString());
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		protected void onPreExecute() {
			desabilitarBotao();
			limpaLista();
			mostrarResposta(Color.YELLOW, getString(R.string.respostaAguardandoLeitura));
		}
		
		@Override
		protected void onPostExecute(JSONArray timeline) {
			ArrayAdapter<String> adapter = preparaAdapter(timeline);
			populaLista(adapter);
			mostrarResposta(Color.GREEN, getString(R.string.respostaLeituraSucesso));
			habilitarBotao();
		}
    }
	
	public JSONArray buscaTimeline(){
		JSONArray result = Twitter.ler();
		return result;
	}
	
	public ArrayAdapter<String> preparaAdapter(JSONArray result){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
	    for(Integer i=0; i< result.length(); i++){
	    	try{
	    		JSONObject obj = result.getJSONObject(i);
	    		String location = obj.getString("geo");
			    String post = obj.getJSONObject("user").getString("name") + ": " + obj.getString("text") + ". Location: " + location;
			    adapter.add(post);
	        }catch(JSONException e){

	        }
	    }
	    return adapter;
	}
	
	public void desabilitarBotao(){
		buttonAtualizar.setEnabled(false);
	}
	
	public void habilitarBotao(){
		buttonAtualizar.setEnabled(true);
	}
	
	public void limpaLista(){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listTimeline.setAdapter(adapter);
		listTimeline.setTextFilterEnabled(true);
	}
	
	
	public void populaLista(ArrayAdapter<String> adapter){
		listTimeline.setAdapter(adapter);
		listTimeline.setTextFilterEnabled(true);
	}
	
	public void mostrarResposta(int cor, String resposta){
		textResposta.setTextColor(cor);
		textResposta.setText(resposta);
	}
	

}
