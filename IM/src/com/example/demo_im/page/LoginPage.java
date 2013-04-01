package com.example.demo_im.page;

import java.util.HashMap;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.demo_im.R;
import com.example.demo_im.modules.IM;

public class LoginPage extends BasePage {

	public LoginPage(IM im, int layoutId) {
		super(im, layoutId);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		aq.find(R.id.login_account).text("test");
		aq.find(R.id.login_password).text("123");
		find(R.id.login_login).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String account = aq.find(R.id.login_account).getText().toString();
				String password = aq.find(R.id.login_password).getText().toString();
				if (account.equals("") || password.equals("")) {
					Toast.makeText(context, "账号或密码不能为空！", Toast.LENGTH_SHORT).show();
				} else {
					im.login(account, password);
				}
			}
		});
	}

	@Override
	public void loadPage(HashMap<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub

	}

}
