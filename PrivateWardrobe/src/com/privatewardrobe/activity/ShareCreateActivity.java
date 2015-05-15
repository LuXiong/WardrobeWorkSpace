package com.privatewardrobe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.common.Utils;
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
		mActionBar.setTitle("·ÖÏí´îÅä");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {

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
			mPublicText.setText("¹«¿ª");
		} else {
			mPublicText.setText("Ë½ÃÜ");
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
