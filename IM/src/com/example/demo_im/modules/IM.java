package com.example.demo_im.modules;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.example.demo_im.R;
import com.example.demo_im.control.ClientConServer;
import com.example.demo_im.page.BasePage;
import com.example.demo_im.page.ContactsPage;
import com.example.demo_im.page.ConversationPage;
import com.example.demo_im.page.LoginPage;
import com.example.demo_im.page.NewsFeedPage;
import com.example.demo_im.page.SettingsPage;
import com.example.demo_im.util.Constant;

public class IM implements IMAction{

	private Activity context;
	private BasePage loginPage,conversationPage,contactsPage,newsFeedPage,settingsPage;
	private AQuery aq;
	
	public static final int LAYOUT_REGISTER = R.layout.page_register;
	public static final int LAYOUT_LOGIN = R.layout.page_login;

	public static final int LAYOUT_CONTACTS = R.layout.page_contacts;
	public static final int LAYOUT_CONVERSATION = R.layout.page_recent_chats;
	public static final int LAYOUT_NEWS_FEED = R.layout.page_newsfeed;
	public static final int LAYOUT_SETTINGS = R.layout.page_settings;

	public Activity getContext() {
		return context;
	}

	public IM(Activity context) {
		super();
		this.context = context;
		aq = new AQuery(context);
		init();
		initPages();
	}

	private LinearLayout imContent;
	@Override
	public void init() {
		// TODO Auto-generated method stub
		imContent = (LinearLayout) aq.find(R.id.im_content).getView();
		aq.find(R.id.tab_conversation).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeContent(LAYOUT_CONVERSATION);
			}
		});
		aq.find(R.id.tab_contacts).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeContent(LAYOUT_CONTACTS);
			}
		});
		aq.find(R.id.tab_newsfeed).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeContent(LAYOUT_NEWS_FEED);
			}
		});
		aq.find(R.id.tab_settings).clicked(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				changeContent(LAYOUT_SETTINGS);
			}
		});
	}
	
	public void initPages(){
		loginPage = new LoginPage(this, LAYOUT_LOGIN);
		conversationPage = new ConversationPage(this, LAYOUT_CONVERSATION);
		contactsPage = new ContactsPage(this, LAYOUT_CONTACTS);
		newsFeedPage = new NewsFeedPage(this, LAYOUT_NEWS_FEED);
		settingsPage = new SettingsPage(this, LAYOUT_SETTINGS);
	}

	@Override
	public void register(String account, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login(String account, String password) {
		// TODO Auto-generated method stub
		new AsyncTask<String, Void, Boolean>() {
			private ClientConServer ccs;
			
			protected void onPreExecute() {
				aq.find(R.id.progressBar_im).visible();
				ccs = ClientConServer.getInstance(context);
			};
			@Override
			protected Boolean doInBackground(String... params) {
				// TODO Auto-generated method stub
				boolean b = ccs.login(params[0],params[1]);
				if(b)Constant.account = params[0];
				return b;
			}
			@Override
			protected void onPostExecute(Boolean r) {
				aq.find(R.id.progressBar_im).invisible();
				//如果登录成功
				if(r){
					Toast.makeText(context, "登陆成功！", Toast.LENGTH_SHORT).show();
					Constant.groups = ccs.getGroups();
					changeContent(LAYOUT_CONVERSATION);
				}else{
					Toast.makeText(context, "登陆失败！", Toast.LENGTH_SHORT).show();
				}
			}
		}.execute(account,password);
	}

	public void changeContent(int layout) {
		// TODO Auto-generated method stub
		imContent.removeAllViews();
		preLayout = currentLayout;
		currentLayout = layout;
		
		aq.find(R.id.im_tabs).visible();
		BasePage page = null;
		switch (layout) {
		case LAYOUT_LOGIN:
			aq.find(R.id.im_tabs).gone();
			page = loginPage;
			break;
		case LAYOUT_CONVERSATION:
			page = conversationPage;
			updateIndicatorIcon(R.id.icon_conversation, R.drawable.tab_icon_conversation_selected);
			break;
		case LAYOUT_CONTACTS:
			page = contactsPage;
			updateIndicatorIcon(R.id.icon_contacts, R.drawable.tab_icon_contact_selected);
			break;
		case LAYOUT_NEWS_FEED:
			page = newsFeedPage;
			updateIndicatorIcon(R.id.icon_newsfeed, R.drawable.tab_icon_plugin_selected);
			break;
		case LAYOUT_SETTINGS:
			page = settingsPage;
			updateIndicatorIcon(R.id.icon_settings, R.drawable.tab_icon_setup_selected);
			break;

		default:
			break;
		}
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(-1, -1);
		imContent.addView(page.getContentView(), param);
		page.loadPage(null);
	}

	protected void updateIndicatorIcon(int imageId, int resid) {
		aq.find(R.id.icon_conversation).image(R.drawable.tab_icon_conversation);
		aq.find(R.id.icon_contacts).image(R.drawable.tab_icon_contact);
		aq.find(R.id.icon_newsfeed).image(R.drawable.tab_icon_plugin);
		aq.find(R.id.icon_settings).image(R.drawable.tab_icon_setup);

		aq.find(imageId).image(resid);
	}
	
	@Override
	public void logout() {
		ClientConServer.getInstance(context).disconnect();
	}

	@Override
	public void sendMessage(String msg, String to) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFriend(String who) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFriend(String who) {
		// TODO Auto-generated method stub
		
	}

	private int currentLayout = -1, preLayout = -1;
	public boolean goBack() {
		// TODO goBack
		if (currentLayout == LAYOUT_REGISTER && preLayout != -1
				&& preLayout != currentLayout) {
			changeContent(preLayout);
			return true;
		}
		return false;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
	}

	public void onCreate() {
		changeContent(LAYOUT_LOGIN);
	}


	
}
