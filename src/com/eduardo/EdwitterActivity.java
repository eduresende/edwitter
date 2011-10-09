package com.eduardo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class EdwitterActivity extends Activity {

	private Button buttonPost;
	private Button buttonRead;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		buttonPost = (Button)findViewById(R.id.ButtonPost);
		buttonPost.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				chamarPostarActivity();
			}
		});
		
		buttonRead = (Button)findViewById(R.id.ButtonRead);
		buttonRead.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				chamarTimelineActivity();
			}
		});
	}
	
	public void chamarPostarActivity(){
		Intent i = new Intent(EdwitterActivity.this, PostarActivity.class);
		EdwitterActivity.this.startActivity(i);
	}
	
	public void chamarTimelineActivity(){
		Intent i = new Intent(EdwitterActivity.this, TimelineActivity.class);
		EdwitterActivity.this.startActivity(i);
	}

}
