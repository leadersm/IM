package com.example.demo_im.page;

import java.util.HashMap;
import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import com.example.demo_im.R;
import com.example.demo_im.bean.Group;
import com.example.demo_im.bean.User;
import com.example.demo_im.control.BuddyAdapter;
import com.example.demo_im.modules.IM;
import com.example.demo_im.util.Constant;

public class ContactsPage extends BasePage {
	ExpandableListView expandablelistview;
	public ContactsPage(IM im, int layoutId) {
		super(im, layoutId);
	}

	private void init() {
		final List<Group> groups = Constant.groups;
        System.out.println("groups.size:"+groups.size());
        expandablelistview= (ExpandableListView) aq.find(R.id.expandableListView1).getExpandableListView();
        ExpandableListAdapter adapter=new BuddyAdapter(context,groups);
        expandablelistview.setAdapter(adapter);
        //分组展开
        expandablelistview.setOnGroupExpandListener(new OnGroupExpandListener(){
			public void onGroupExpand(int groupPosition) {
				
			}
        });
        //分组关闭
        expandablelistview.setOnGroupCollapseListener(new OnGroupCollapseListener(){
			public void onGroupCollapse(int groupPosition) {
				
			}
        });
        //子项单击
        expandablelistview.setOnChildClickListener(new OnChildClickListener(){
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int groupPosition, int childPosition, long arg4) {
				User user = groups.get(groupPosition).getUsers().get(childPosition);
				Intent i = new Intent(context,ChatActivity.class);
				i.putExtra("nickname",user.getName());
				i.putExtra("account",user.getAccount());
				i.putExtra("icon", user.getIcon());
				context.startActivity(i);
				return false;
			}
        });
	}

	@Override
	public void loadPage(HashMap<String, Object> data) {
		init();
	}

	@Override
	public void onDestory() {
		// TODO Auto-generated method stub

	}

}
