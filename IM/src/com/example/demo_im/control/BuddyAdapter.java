package com.example.demo_im.control;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.demo_im.R;
import com.example.demo_im.bean.Group;
import com.example.demo_im.bean.User;

public class BuddyAdapter extends BaseExpandableListAdapter {   
    private List<Group> groups;
    LayoutInflater inflater;
	public BuddyAdapter(Context context, List<Group> groups) {
    	inflater = LayoutInflater.from(context);
		this.groups = groups;
	}
	
	public User getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).getUsers().get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(int groupPosition, int childPosition, boolean arg2, View convertView,
			ViewGroup arg4) {
		convertView = inflater.inflate(R.layout.buddy_listview_child_item, null);
		TextView nickTextView=(TextView) convertView.findViewById(R.id.buddy_listview_child_nick);
		nickTextView.setText(getChild(groupPosition, childPosition).getName());
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getUsers().size();
	}

	public Group getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	public int getGroupCount() {
		return groups.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup arg3) {
		convertView = inflater.inflate(R.layout.buddy_listview_group_item, null);
		TextView groupNameTextView=(TextView) convertView.findViewById(R.id.buddy_listview_group_name);
		Group group = getGroup(groupPosition);
		groupNameTextView.setText(group.getName());
		TextView groupNumTextView=(TextView) convertView.findViewById(R.id.buddy_listview_group_num);
		groupNumTextView.setText(group.getUsers().size()+"");
		return convertView;
	}

	public boolean hasStableIds() {
		return true;
	}
	// 子选项是否可以选择  
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
}