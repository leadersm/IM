package com.example.demo_im.page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.example.demo_im.MainActivity;
import com.example.demo_im.R;

public class WelcomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_welcome);
		Animation aa = AnimationUtils.loadAnimation(this, R.anim.anim_welcome);
		aa.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent i = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(i);
				finish();
			}
		});
		findViewById(R.id.image_welcome).setAnimation(aa);
	}
}
