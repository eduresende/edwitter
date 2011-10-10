package com.eduardo;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

public class PostarActivity extends Activity implements OnClickListener, TextWatcher, LocationListener {
	

	private Button buttonPostar;
	private EditText editStatus;
	private TextView textResposta;
	private TextView textContador;
	
	//location
	private static final long LOCATION_MIN_TIME = 3600000; // One hour
	private static final float LOCATION_MIN_DISTANCE = 1000; // One kilometer
	LocationManager locationManager;
	Location location;
	String provider;
	
	
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
		
		String texto = editStatus.getText().toString();
		
		double latlong[] = buscarPosicaoGeografica();
		String latitude = String.valueOf(latlong[0]);
		String longitude = String.valueOf(latlong[0]);
		
		String params[] = {texto, latitude, longitude};

		//String status = editStatus.getText().toString();
		if (verificaLimite(texto.length())){
			new Postador().execute(params);
		}else{
			mostrarResposta(Color.RED, getString(R.string.respostaMensagemTamanho));
		}
		
    }
	
	class Postador extends AsyncTask<String, Integer, String> {
		@Override
		protected String doInBackground(String... params) {
			try {
				Twitter.postar(params);
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

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		this.location = location;

		
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		if (this.provider.equals(provider))
			locationManager.removeUpdates(this);

		
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		if (this.provider.equals(provider))
			locationManager.requestLocationUpdates(this.provider, LOCATION_MIN_TIME,
			LOCATION_MIN_DISTANCE, this);

		
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	public double[] buscarPosicaoGeografica(){
		provider = LocationManager.GPS_PROVIDER;
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		double latlong[] = {0,0};
		
		if (locationManager != null) {
			location = locationManager.getLastKnownLocation(provider); //
			locationManager.requestLocationUpdates(provider, LOCATION_MIN_TIME, LOCATION_MIN_DISTANCE, this);
		}
		if (location != null){
			latlong[0] = location.getLatitude();
			latlong[1] = location.getLongitude();
		}
		return latlong;
	}
	
}
