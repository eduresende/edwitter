package com.eduardo;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PostarActivity extends Activity implements OnClickListener, TextWatcher {
	

	private Button buttonPostar;
	private EditText editStatus;
	private TextView textResposta;
	private TextView textContador;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postar);
		
		Log.v("Blah123", "entrei no postar");
		
		buttonPostar = (Button)findViewById(R.id.buttonPostar);
		buttonPostar.setOnClickListener(this);
		
		editStatus = (EditText)findViewById(R.id.editStatus);
		textResposta = (TextView)findViewById(R.id.textResposta);
		
		textContador = (TextView) findViewById(R.id.textContador);
		textContador.setText(Integer.toString(140));
		textContador.setTextColor(Color.GREEN);
		editStatus.addTextChangedListener(this);
					
	}
	
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		if (verificaLimite(status.length())){
			new Postador().execute(status);
		}else{
			mostrarResposta(Color.RED, "A mensagem é muito longa. Max: 140");
		}
		
    }
	
	class Postador extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... status) {
			try {
				Twitter.postar(status[0]);
				return "ok";
			} catch (Exception e) {
				Log.e(Constantes.TAG, e.toString());
				e.printStackTrace();
				return "Failed to post";
			}
		}
		
		@Override
		protected void onPreExecute() {
			mostrarResposta(Color.YELLOW, "Postando, aguarde...");
		}
		
		@Override
		protected void onPostExecute(String result) {
			mostrarResposta(Color.GREEN, "Mensagem enviada com sucesso!");
			limparStatus();
		}
    }

	// metodos TextWatcher
	public void afterTextChanged(Editable s) {
		int count = 140 - s.length();
		exibirNumCaracteres(Color.GREEN, count);
    	if (count < 10)
    		exibirNumCaracteres(Color.YELLOW, count);
    	if (count < 0)
    		exibirNumCaracteres(Color.RED, count);		
	}
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	public void onTextChanged(CharSequence s, int start, int before, int count) {}
	
	public void mostrarResposta(int cor, String resposta){
		textResposta.setTextColor(cor);
		textResposta.setText(resposta);
	}
	
	public void limparStatus(){
		editStatus.setText("");
	}
	
	public void exibirNumCaracteres(int cor, int numCaracteres){
		textContador.setText(Integer.toString(numCaracteres));
    	textContador.setTextColor(cor);
	}

	public boolean verificaLimite(int numCaracteres){
		if (numCaracteres > 140)
			return false;
		
		return true;
	}
}
