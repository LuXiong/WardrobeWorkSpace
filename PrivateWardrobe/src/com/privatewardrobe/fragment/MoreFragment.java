package com.privatewardrobe.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.AboutUsActivity;
import com.privatewardrobe.activity.ClothesCreateActivity;
import com.privatewardrobe.activity.FunctionActivity;
import com.privatewardrobe.activity.LoginActivity;
import com.privatewardrobe.activity.ShareListActivity;
import com.privatewardrobe.activity.UserProfileActivity;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.UserBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.User;

public class MoreFragment extends Fragment {
	private TextView logoutText, nameText;
	private RelativeLayout mShareListLayout, mCollectionsLayout,
			mCheckVersionLayout, mFunctionsLayout, mAboutUsLayout,
			mAccountLayout;
	private ImageView mHeadImg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_main_more, container, false);
		findView(v);
		initView(v);
		loadData();
		return v;
	}

	private void loadData() {
		UserBusiness userBusiness = new UserBusiness();
		userBusiness.queryUserById(PWApplication.getInstance().getUserId(),
				new BusinessListener<User>() {
					public void onSuccess(User user) {
						nameText.setText(user.getName());
						PWApplication
								.getInstance()
								.getImageLoader()
								.displayImage("http://" + user.getAvatar(),
										mHeadImg,
										Utils.buildNoneDisplayImageOptions());
					};
				});

	}

	private void findView(View v) {
		mShareListLayout = (RelativeLayout) v
				.findViewById(R.id.fragment_more_share_layout);
		mCollectionsLayout = (RelativeLayout) v
				.findViewById(R.id.fragment_more_collections_Layout);
		mCheckVersionLayout = (RelativeLayout) v
				.findViewById(R.id.fragment_more_checkversion_layout);
		mFunctionsLayout = (RelativeLayout) v
				.findViewById(R.id.fragment_more_function_intorduce_layout);
		mAboutUsLayout = (RelativeLayout) v
				.findViewById(R.id.fragment_more_aboutus_Layout);
		mAccountLayout = (RelativeLayout) v
				.findViewById(R.id.fragment_more_account_Layout);
		nameText = (TextView) v.findViewById(R.id.fragment_more_nameTxt);
		mHeadImg = (ImageView) v.findViewById(R.id.fragment_more_headImg);
		logoutText = (TextView) v.findViewById(R.id.logout_text);
	}

	private void initView(View v) {
		mShareListLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						ShareListActivity.class);
				startActivity(intent);
			}
		});
		logoutText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				PWApplication.getInstance().Logout();
				startActivity(new Intent(getActivity(), LoginActivity.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK));
				getActivity().finish();
			}
		});
		mAccountLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						UserProfileActivity.class);
				intent.putExtra(UserProfileActivity.EXTRA_INPUT, PWApplication
						.getInstance().getUserId());
				startActivity(intent);
			}
		});
		mCheckVersionLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("提示");
				builder.setMessage("当前已经是最新版本！");
				builder.create().show();
			}
		});
		mFunctionsLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), FunctionActivity.class);
				startActivity(intent);
			}
		});
		mAboutUsLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), AboutUsActivity.class);
				startActivity(intent);
			}
		});
		mCollectionsLayout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setTitle("提示");
				builder.setMessage("该功能还在开发中");
				builder.create().show();
			}
		});
	}
}
