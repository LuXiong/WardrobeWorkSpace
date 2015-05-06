package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.photo.PhotoHelper;
import com.privatewardrobe.photo.PhotoHelper.PhotoProcessListener;

public class PerfectInfoActivity extends BaseActivity {

	private ImageView mHeadImg;
	private TextView mBoyText, mGirlText;
	private EditText mNameText;
	private ActionBar mActionBar;

	private PhotoHelper mPhotoHelper;

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
	}

	private void findView() {
		mHeadImg = (ImageView) findViewById(R.id.activity_perfect_info_avatar_img);

	}

	private void initView() {
		mPhotoHelper = new PhotoHelper(this, new PhotoProcessListener() {

			@Override
			public void onComplete(Uri source, Uri large, Uri thumbnail) {
				imageLoader.displayImage(large.toString(),
						mHeadImg);
			}
		});
		mHeadImg.setOnClickListener(mHeadImgClickListener);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mPhotoHelper.process(requestCode, resultCode, data);
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		// TODO Auto-generated method stub
		super.onActionBarItemSelected(itemId, item);
	}

	OnClickListener mHeadImgClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Utils.buildPhotoHelperListDialog(PerfectInfoActivity.this,
					mPhotoHelper);
		}
	};
}
