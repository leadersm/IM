package com.example.demo_im.page;

import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.androidquery.AQuery;
import com.example.demo_im.modules.IM;

public abstract class BasePage {

	public static final String TAG = "BasePage" ;
	public View contentView;
	public IM im;
	public Activity context;
	public AQuery aq;

	public BasePage(IM im,int layoutId) {
		this.im = im;
		context = im.getContext();
		onCreate(layoutId);
	}
	
	public View getContentView() {
		return contentView;
	}

	public void onCreate(int layoutId) {
		contentView = LayoutInflater.from(context).inflate(layoutId, null);
		aq = new AQuery(contentView);
	}
	public abstract void loadPage(HashMap<String,Object> data);
	public abstract void onDestory();
	
	public View find(int id){
		return contentView.findViewById(id);
	}
	
	public String getString(int id){
		return context.getString(id);
	}
	
	public LayoutInflater getLayoutInflater(){
		return context.getLayoutInflater() ;
	}
	
	public void showDialog(int id){
		try {
			if(contentView!=null&&contentView.getWindowToken()!=null){
				context.showDialog(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
