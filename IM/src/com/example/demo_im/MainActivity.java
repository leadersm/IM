package com.example.demo_im;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;

import com.example.demo_im.modules.IM;

public class MainActivity extends Activity{

	IM im;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		im = new IM(this);
		im.onCreate();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		im.onDestroy();
	}

	private static final int DIALOG_EXIT = 0;
	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (im.goBack()) {
				return false;
			}else{
				showDialog(DIALOG_EXIT);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
    protected Dialog onCreateDialog(int id) {
    	// TODO Auto-generated method stub
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("Are you sure to exit?").setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				im.logout();
				finish();
			}
		}).setNegativeButton(getString(R.string.no), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
    	return builder.create();
    }
}
