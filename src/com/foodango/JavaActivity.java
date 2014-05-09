package com.foodango;

import com.foodango.R;

import android.app.Activity;
import android.os.Bundle;

public class JavaActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
