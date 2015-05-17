package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ShareBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Share;
import com.privatewardrobe.model.Suit;
import com.privatewardrobe.service.Notify;

public class ShareCreateActivity extends BaseActivity {

	public static final String SUIT = "suit";
	public static final int REQUEST_CODE = 102;

	private EditText mShareEdit;
	private ImageView mSuitImg;
	private LinearLayout mPublicLayout;
	private TextView mPublicText;

	private Suit mSuit;
	private int mPublic;

	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_share_create);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("分享搭配");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			ShareBusiness shareBusiness = new ShareBusiness();
			if(mSuit==null){
				Toast.makeText(ShareCreateActivity.this, "请选择搭配", Toast.LENGTH_LONG).show();
				return;
			}
			shareBusiness.addShare(PWApplication.getInstance().getUserId(), mSuit.getId(), mShareEdit.getText().toString(), mPublic, new BusinessListener<Share>(){
				@Override
				public void onSuccess(Share share) {
					if(share!=null){
						Intent data = new Intent();
						data.putExtra(SUIT, share);
						setResult(RESULT_OK, data);
						ShareCreateActivity.this.finish();
						Toast.makeText(ShareCreateActivity.this, "发送成功", Toast.LENGTH_LONG).show();
					}
				}
			});
		}
		super.onActionBarItemSelected(itemId, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == ChooseSuitActivity.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Suit suit = (Suit) data
						.getSerializableExtra(ChooseSuitActivity.SUIT);
				if (suit != null) {
					mSuit = suit;
					notifyDataSetChanged();
				}
			}
		}
	}

	private void findView() {
		mShareEdit = (EditText) findViewById(R.id.activity_create_share_edit);
		mSuitImg = (ImageView) findViewById(R.id.activity_create_share_img);
		mPublicLayout = (LinearLayout) findViewById(R.id.activity_create_share_public_layout);
		mPublicText = (TextView) findViewById(R.id.activity_create_share_public_text);

	}

	private void initView() {
		bindEvent();
	}

	private void bindEvent() {
		mSuitImg.setOnClickListener(mSuitImgClickListener);
		mPublicLayout.setOnClickListener(mPublicLayoutClickListener);
	}

	private void loadData() {
		Intent intent = getIntent();
		if (intent != null) {
			mSuit = (Suit) intent.getSerializableExtra(SUIT);
		}
	}

	private void notifyDataSetChanged() {
		if (mPublic == 0) {
			mPublicText.setText("公开");
		} else {
			mPublicText.setText("私密");
		}
		if (mSuit != null) {
			imageLoader.displayImage("http://" + mSuit.getImg(), mSuitImg,
					Utils.buildNoneDisplayImageOptions());
		}
	}

	private OnClickListener mSuitImgClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ShareCreateActivity.this,
					ChooseSuitActivity.class);
			startActivityForResult(intent, ChooseSuitActivity.REQUEST_CODE);
		}
	};
	private OnClickListener mPublicLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mPublic = (mPublic + 1) % 2;
			notifyDataSetChanged();
		}
	};
}
