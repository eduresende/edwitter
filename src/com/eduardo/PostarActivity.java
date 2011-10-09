package com.eduardo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class PostarActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.postar);
		
		Log.v("Blah123", "entrei no postar");
	}

}
