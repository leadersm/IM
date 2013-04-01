package com.example.demo_im.control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterGroup;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.demo_im.bean.Group;
import com.example.demo_im.bean.User;
import com.example.demo_im.util.Constant;

public class ClientConServer implements MessageListener{
	private static final String TAG = "ClientConServer";
	private Context context;
	private XMPPConnection connection;
	private ChatManager cm ;
	private Roster roster ;

	private static ClientConServer instance;
	
	private ClientConServer(Context context) {
		this.context = context;
	}
	
	public static ClientConServer getInstance(Context context){
		if(instance==null){
			instance = new ClientConServer(context);
		}
		return instance;
	}

	private String account = "";
	public boolean login(String account, String password) {

		ConnectionConfiguration config = new ConnectionConfiguration(
				Constant.SERVER, Constant.PORT);

		config.setReconnectionAllowed(true);
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
		/** 是否启用安全验证 */
		config.setSASLAuthenticationEnabled(false);
		// if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		// {
		// config.setTruststoreType("AndroidCAStore");
		// config.setTruststorePassword(null);
		// config.setTruststorePath(null);
		// } else {
		// config.setTruststoreType("BKS");
		// String path = System.getProperty("javax.net.ssl.trustStore");
		// if (path == null)
		// path = System.getProperty("java.home") + File.separator + "etc"
		// + File.separator + "security" + File.separator
		// + "cacerts.bks";
		// config.setTruststorePath(path);
		// }
		/** 是否启用调试 */
		// config.setDebuggerEnabled(true);
		/** 创建connection链接 */
		connection = new XMPPConnection(config);
		cm = connection.getChatManager();  
		try {
			/** 建立连接 */
			connection.connect();
			/** 登录 */
			connection.login(account, password);
			/** 开启读写线程，并加入到管理类中 */
			// ClientSendThread cst=new ClientSendThread(connection);
			// cst.start();
			// ManageClientThread.addClientSendThread(a, cst);

			// 在登陆以后应该建立一个监听消息的监听器，用来监听收到的消息：
			this.account = account;
			ChatManager chatManager = connection.getChatManager();
			// 监听被动接收消息
			chatManager.addChatListener(new ChatManagerListener() {

				public void chatCreated(Chat chat, boolean arg1) {
					chat.addMessageListener(ClientConServer.this);
				}});
			roster = connection.getRoster();
			//添加花名册监听器，监听好友状态的改变。   
            roster.addRosterListener(new RosterListener() {  

                @Override  
                public void entriesAdded(Collection<String> addresses) {  
                    System.out.println("entriesAdded");  
                }  

                @Override  
                public void entriesUpdated(Collection<String> addresses) {  
                    System.out.println("entriesUpdated");  
                }  

                @Override  
                public void entriesDeleted(Collection<String> addresses) {  
                    System.out.println("entriesDeleted");  
                }  

                @Override  
                public void presenceChanged(Presence presence) {  
                    System.out.println("presenceChanged - >" + presence.getStatus());  
                }  
                  
            }); 
			return true;
		} catch (XMPPException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void handleMessage(Message msg) {
		android.os.Message m = handler.obtainMessage();
		m.obj = msg;
		m.sendToTarget();
	}
	
	public List<Group> getGroups() {
		Collection<RosterGroup> entriesGroup = roster.getGroups();
		List<Group> groups = new ArrayList<Group>();
		for (RosterGroup rgroup : entriesGroup) {
			Collection<RosterEntry> entries = rgroup.getEntries();
			Log.i(TAG, rgroup.getName());
			Group group = new Group();
			group.setName(rgroup.getName());
			List<User> users = new ArrayList<User>();
			for (RosterEntry entry : entries) {
				Presence presence = roster.getPresence(entry.getUser());
				Log.i(TAG, "presence: "+presence.toString());
				Log.i(TAG, "user: "+entry.getUser());
				Log.i(TAG, "name: " + entry.getName());
				Log.i(TAG, "tyep: "+entry.getType());
				Log.i(TAG, "status: "+entry.getStatus());
				Log.i(TAG, "groups: "+entry.getGroups());
				User user = new User(entry.getName());
				user.setAccount(entry.getUser());
				user.setIcon("");
				users.add(user);
			}
			group.setUsers(users);
			groups.add(group);
		}
		return groups;
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message m) {
			Message msg = new Message();
			msg = (Message) m.obj;
			// 把从服务器获得的消息通过广播发送
			Intent intent = new Intent("org.yhn.mes");
			String[] message = new String[] { msg.getFrom(), msg.getBody() };
			
//			Log.e(TAG, ": "+msg.getFrom());
//			Log.e(TAG, ": "+msg.getBody());
//			Log.e(TAG, ": "+msg.getLanguage());
//			Log.e(TAG, ": "+msg.getPacketID());
//			Log.e(TAG, ": "+msg.getSubject());
//			Log.e(TAG, ": "+msg.getThread());
//			Log.e(TAG, ": "+msg.getTo());
//			Log.e(TAG, ": "+msg.getXmlns());
//			Log.e(TAG, ": "+msg.getType());
//			Log.e(TAG, ": "+msg.getSubjects());
//			Log.e(TAG, ": "+msg.getSubjectLanguages());
//			Log.e(TAG, ": "+msg.getPropertyNames());
//			Log.e(TAG, ": "+msg.getExtensions());
//			Log.e(TAG, ": "+msg.getError());
			String key = msg.getFrom().substring(0,msg.getFrom().indexOf('/'));
			if(listeners.containsKey(key)){
				listeners.get(key).onReceiveMessage(msg.getFrom(), msg.getBody());
			}
			Log.i(TAG,"新消息：[" + message[0]+"]对你说:"+ message[1]);
			intent.putExtra("message", message);
			context.sendBroadcast(intent);
		};
	};
	
	public interface ReceiveMessageListener{
		public void onReceiveMessage(String from,String msg);
	}
	
	private LinkedHashMap<String, ReceiveMessageListener> listeners = new LinkedHashMap<String, ClientConServer.ReceiveMessageListener>();
	public void setOnRecevieMessageListener(String account,ReceiveMessageListener lsn){
		listeners.put(account, lsn);
	}

	public void sendMessage(String account,String content) {
		try {  
           
			Chat chat = cm.createChat(account, this);  
            Message m=new Message();  
            m.setBody(content);  
            chat.sendMessage(m);
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	
	
	public void createAccount(String account,String password) {
        AccountManager accountManager = connection.getAccountManager();
        for (String attr : accountManager.getAccountAttributes()) {
            Log.d("AccountAttribute: {0}", attr);
        }
        Log.d("AccountInstructions: {0}", accountManager.getAccountInstructions());
        
        Log.d("supportsAccountCreation: {0}",""+ accountManager.supportsAccountCreation());
        try {
            accountManager.createAccount(account, password);
            /** 修改密码 */
            accountManager.changePassword("abc");
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void processMessage(Chat chat, Message msg) {
		handleMessage(msg);
	}

	public void disconnect() {
		connection.disconnect();
	}
	
	
	
	
}
