package com.veggie411.veggie411;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SignInActivity extends Activity {
	
	private ImageButton signInButton;
	private ImageButton signUpButton;
	private String email;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(Constants.ACTIVITY_MAIN, Constants.CREATED);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		signInButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent resultIntent = new Intent();
				resultIntent.putExtra("EMAIL", email);
				setResult(Activity.RESULT_OK, resultIntent);
				
			}
		});
		
		signUpButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	
	
}
