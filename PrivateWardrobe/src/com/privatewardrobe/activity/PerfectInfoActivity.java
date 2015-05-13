package com.privatewardrobe.activity;

import java.io.File;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.UploadHelper;
import com.privatewardrobe.UploadHelper.UpCompletionListener;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.PassBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.User;
import com.privatewardrobe.photo.PhotoHelper;
import com.privatewardrobe.photo.PhotoHelper.PhotoProcessListener;

public class PerfectInfoActivity extends BaseActivity {

	public final static String PHONE_NUM = "phoneNum";
	public final static String PASSWORD = "password";
	public final static String CODE = "code";

	private ImageView mHeadImg;
	private TextView mBoyText, mGirlText;
	private EditText mNameText;
	private ActionBar mActionBar;

	private PhotoHelper mPhotoHelper;

	private String mPhone;
	private String mPassword;
	private String mCode;
	private String mAvatar;
	private int mGender = 0;
	private String mName;

	private Uri mSource;
	// private int

	private AlertDialog mLoadingDialog;
	private boolean hasImg = false;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_perfect_info);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("ÕÍ…∆◊ ¡œ");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();
	}

	private void loadData() {
		Intent intent = getIntent();
		mPhone = intent.getStringExtra(PHONE_NUM);
		mPassword = intent.getStringExtra(PASSWORD);
		mCode = intent.getStringExtra(CODE);

	}

	private void findView() {
		mHeadImg = (ImageView) findViewById(R.id.activity_perfect_info_avatar_img);
		mNameText = (EditText) findViewById(R.id.activity_perfect_info_name_edit);
		mBoyText = (TextView) findViewById(R.id.activity_perfect_info_gender_boy_text);
		mGirlText = (TextView) findViewById(R.id.activity_perfect_info_gender_girl_text);
	}

	private void initView() {
		mLoadingDialog = Utils.buildLoadingDialog(this);
		mPhotoHelper = new PhotoHelper(this, new PhotoProcessListener() {

			@Override
			public void onComplete(Uri source, final Uri large, Uri thumbnail) {
				mSource = thumbnail;
				hasImg = true;
				imageLoader.displayImage(thumbnail.toString(), mHeadImg);
			}
		});
		mHeadImg.setOnClickListener(mHeadImgClickListener);
		mBoyText.setOnClickListener(mBoyTextClickListener);
		mGirlText.setOnClickListener(mGrilTextClickListener);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mPhotoHelper.process(requestCode, resultCode, data);
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		super.onActionBarItemSelected(itemId, item);
		if (itemId == 0) {
			mLoadingDialog.show();
			mName = mNameText.getText().toString();
			if (TextUtils.isEmpty(mName)) {
				Toast.makeText(PerfectInfoActivity.this, "«ÎÃÓ–¥Í«≥∆",
						Toast.LENGTH_LONG).show();
				return;
			}
			if (hasImg) {
				
				UploadHelper uploadHelper = new UploadHelper();
				uploadHelper.upload(new File(mSource.getPath()),
						new UpCompletionListener() {

							@Override
							public void onSuccess(String img) {
								mAvatar = img;
								PassBusiness passBusiness = new PassBusiness();
								passBusiness.regist(mName, mGender, mPassword,
										mPhone, mAvatar, PWApplication
												.getInstance().getDeviceId(),
										mCode, new BusinessListener<User>() {
											@Override
											public void onFinish() {
												mLoadingDialog.dismiss();
											}

											@Override
											public void onSuccess(User user) {
												Intent intent = new Intent(
														PerfectInfoActivity.this,
														MainActivity.class);
												intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
														| Intent.FLAG_ACTIVITY_NEW_TASK);
												startActivity(intent);
												PerfectInfoActivity.this
														.finish();
											}
										});

							}

						});

			} else {
				PassBusiness passBusiness = new PassBusiness();
				passBusiness.regist(mName, mGender, mPassword, mPhone, "de",
						PWApplication.getInstance().getDeviceId(), mCode,
						new BusinessListener<User>() {
							@Override
							public void onFinish() {
								mLoadingDialog.dismiss();
							}

							@Override
							public void onSuccess(User user) {
								Intent intent = new Intent(
										PerfectInfoActivity.this,
										MainActivity.class);
								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
										| Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								PerfectInfoActivity.this.finish();
							}
						});
			}

		}

	}

	private OnClickListener mHeadImgClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Utils.buildPhotoHelperListDialog(PerfectInfoActivity.this,
					mPhotoHelper);
		}
	};
	private OnClickListener mBoyTextClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(PerfectInfoActivity.this, "ƒ–", Toast.LENGTH_LONG)
					.show();
			mGender = 1;
		}
	};
	private OnClickListener mGrilTextClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Toast.makeText(PerfectInfoActivity.this, "≈Æ", Toast.LENGTH_LONG)
					.show();
			mGender = 0;
		}
	};
}
