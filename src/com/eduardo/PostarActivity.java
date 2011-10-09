package com.eduardo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class PostarActivity extends Activity implements OnClickListener {
	

	private Button buttonPostar;
	private EditText editStatus;
	private TextView textResposta;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postar);
		
		Log.v("Blah123", "entrei no postar");
		
		buttonPostar = (Button)findViewById(R.id.buttonPostar);
		buttonPostar.setOnClickListener(this);
		
		editStatus = (EditText)findViewById(R.id.editStatus);
		textResposta = (TextView)findViewById(R.id.textResposta);
					
	}
	
	public void onClick(View v) {
		String status = editStatus.getText().toString();
		new Postador().execute(status);
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
			textResposta.setText("Postando, aguarde...");
		}
		
		@Override
		protected void onPostExecute(String result) {
			textResposta.setText("Mensagem enviada com sucesso!");
		}
		    	
    }

}
