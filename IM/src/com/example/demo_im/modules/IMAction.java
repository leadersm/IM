package com.example.demo_im.modules;

public interface IMAction {

	public void init();
	public void register(String account,String password);
	public void login(String account,String password);
	public void logout();
	public void sendMessage(String msg,String to);
	public void deleteFriend(String who);
	public void addFriend(String who);
	public void onDestroy();
}
