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
			mostrarResposta(Color.RED, getString(R.string.respostaMensagemTamanho));
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
				return "fail";
			}
		}
		
		@Override
		protected void onPreExecute() {
			mostrarResposta(Color.YELLOW, getString(R.string.respostaAguardandoPostagem));
			desabilitarBotao();
		}
		
		@Override
		protected void onPostExecute(String result) {
			mostrarResposta(Color.GREEN, getString(R.string.respostaMensagemSucesso));
			limparStatus();
			habilitarBotao();
		}
    }

	// metodos TextWatcher
	public void afterTextChanged(Editable s) {
		int count = 140 - s.length();
		exibirNumCaracteres(count);
	}
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
	public void onTextChanged(CharSequence s, int start, int before, int count) {}
	
	
	public void desabilitarBotao(){
		buttonPostar.setEnabled(false);
	}
	
	public void habilitarBotao(){
		buttonPostar.setEnabled(true);
	}
	
	public void mostrarResposta(int cor, String resposta){
		textResposta.setTextColor(cor);
		textResposta.setText(resposta);
	}
	
	public void limparStatus(){
		editStatus.setText("");
	}
	
	public void exibirNumCaracteres(int count){
		textContador.setText(String.valueOf(count));
		if (count >= 10)
			textContador.setTextColor(Color.GREEN);
		else if (count >= 0)
    		textContador.setTextColor(Color.YELLOW);
		else
    		textContador.setTextColor(Color.RED);	
	}

	public boolean verificaLimite(int numCaracteres){
		if (numCaracteres == 0 || numCaracteres > 140)
			return false;
		
		return true;
	}
}
