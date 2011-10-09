package com.eduardo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class PostarActivity extends Activity {
	

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
				String status = editStatus.getText().toString();
				Log.v("Blah123", "vou postar " + status);
				Twitter.postar(status);
				Log.v("Blah123", "postou!");
			}
		});
		
		
	}

}
