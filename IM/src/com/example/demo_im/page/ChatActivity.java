package com.example.demo_im.page;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.androidquery.AQuery;
import com.example.demo_im.R;
import com.example.demo_im.control.ClientConServer;
import com.example.demo_im.control.ClientConServer.ReceiveMessageListener;

public class ChatActivity extends Activity implements ReceiveMessageListener{  
    private String leftUserAccount,leftUserNick,leftUserIcon;  
    private LinearLayout chatLayout;
      
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		AQuery aq = new AQuery(this);
		Intent i = getIntent();
		leftUserNick = i.getStringExtra("nickname");
		leftUserAccount = i.getStringExtra("account");
		leftUserIcon = i.getStringExtra("icon");
		chatLayout = (LinearLayout) aq.find(R.id.chat_content_layout).getView();
		ClientConServer.getInstance(ChatActivity.this).setOnRecevieMessageListener(leftUserAccount, this);
		aq.find(R.id.chat_btn_send).clicked(new OnClickListener() {
			public void onClick(View arg0) {
				EditText input = (EditText) findViewById(R.id.chat_input);
				String content = input.getText().toString();
				if(TextUtils.isEmpty(content))return;
				chatLayout.addView(createRightChatItem(content));
				ClientConServer.getInstance(ChatActivity.this).sendMessage(leftUserAccount,content);
				input.setText("");
			}
		});
	}

	@Override
	public void onReceiveMessage(String from, String msg) {
		chatLayout.addView(createLeftChatItem(msg));
	}
	
	protected View createRightChatItem(String msg) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.chat_item_right, null);
		AQuery aq = new AQuery(view);
		aq.find(R.id.chat_item_right_content).text(msg);
		aq.find(R.id.chat_item_time).text(getCurrentTime());
//		aq.find(R.id.chat_item_right_icon).image(leftUserIcon,true,true);
		return view;
	}

	private GregorianCalendar ca = new GregorianCalendar();
	private String getCurrentTime() {
		// TODO Auto-generated method stub
		int h = ca.get(GregorianCalendar.HOUR);
		int m = ca.get(GregorianCalendar.MINUTE);
		int s = ca.get(GregorianCalendar.SECOND);
		int am_pm = ca.get(GregorianCalendar.AM_PM);
		return h + ":" + (m < 10 ? "0" + m : m)+ ":"
				+ (s < 10 ? "0" + s : s) + " "
				+ (am_pm == 0 ? "am" : "pm");
	}

	protected View createLeftChatItem(String msg) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(R.layout.chat_item_left, null);
		AQuery aq = new AQuery(view);
		aq.find(R.id.chat_item_left_content).text(msg);
//		aq.find(R.id.chat_item_left_icon).image(leftUserIcon,true,true);
		aq.find(R.id.chat_item_time).text(getCurrentTime());
		return view;
	}
}  