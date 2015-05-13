package com.privatewardrobe.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.common.FileManager;
import com.privatewardrobe.common.Utils;

public class PhotoFilterActivity extends BaseActivity {
	public final static String URI = "uri";
	
	private RelativeLayout mLayout;
	private ImageView mTempPhotoImg;
	private Button mCancelBtn, mConfirmBtn;
	private String mFromLargeUri;

	private int mWidth;
	private int mHeight;
	private Bitmap mBmp;

	private int mRotateTimes;
	private ActionBar mActionBar;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_photo_filter);
		mActionBar = getMyActionBar();
		mActionBar.addActionItem(0, null,
				R.drawable.activity_photo_process_rotate_img,
				ActionItem.SHOWACTION_SHOW);
		mActionBar.setTitle("´¦ÀíÕÕÆ¬");
		findView();
		initView();
		loadData();

	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			rotateImg();
		}
		super.onActionBarItemSelected(itemId, item);
	}

	private void rotateImg() {
		if (mRotateTimes > 3) {
			mRotateTimes = 0;
		}
		mRotateTimes++;

		Matrix m = new Matrix();
		m.setRotate(90 * mRotateTimes);
		Bitmap b2 = Bitmap.createBitmap(mBmp, 0, 0, mBmp.getWidth(),
				mBmp.getHeight(), m, true);
		mTempPhotoImg.setImageBitmap(b2);
		b2 = null;
	}

	private void findView() {
		mLayout = (RelativeLayout) findViewById(R.id.photo_processing_layout);
		mTempPhotoImg = (ImageView) findViewById(R.id.activity_photo_filter_tempphoto_img);
		mCancelBtn = (Button) findViewById(R.id.activity_photo_filter_cancel_btn);
		mConfirmBtn = (Button) findViewById(R.id.activity_photo_filter_confirm_btn);
	}

	private void initView() {
		mCancelBtn.setOnClickListener(backto_activity);
		mConfirmBtn.setOnClickListener(confirm_processing);
	}



	private void loadData() {
		Intent intent = getIntent();
		mFromLargeUri = intent.getStringExtra(URI);
		imageLoader.displayImage(mFromLargeUri, mTempPhotoImg);
		Uri uri = Uri.parse(mFromLargeUri);
		try {
			FileInputStream fis = new FileInputStream(uri.getPath());
			mBmp = BitmapFactory.decodeStream(fis);
			mWidth = mBmp.getWidth();
			mHeight = mBmp.getHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	OnClickListener backto_activity = new OnClickListener() {

		@Override
		public void onClick(View v) {
			finish();
		}
	};
	OnClickListener confirm_processing = new OnClickListener() {

		@Override
		public void onClick(View v) {
			try {
				Matrix m = new Matrix();
				m.setRotate(90 * mRotateTimes);
				mBmp = Bitmap.createBitmap(mBmp, 0, 0, mBmp.getWidth(),
						mBmp.getHeight(), m, true);

				File file = new File(FileManager.getTempDir() + File.separator
						+ "LARGE_" + Utils.getPhotoFileName());

				Intent intent = new Intent();
				intent.putExtra(URI, saveBitmap(mBmp, file.getPath())
						.toString());
				setResult(RESULT_OK, intent);
				mBmp = null;
				mConfirmBtn.setClickable(false);
				System.gc();
				finish();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	private Bitmap getBitmapFromView(View view) {
		Bitmap bitmap = null;
		try {
			int width = view.getWidth();
			int height = view.getHeight();
			if (width != 0 && height != 0) {
				bitmap = Bitmap.createBitmap(width, height,
						Bitmap.Config.ARGB_8888);
				Canvas canvas = new Canvas(bitmap);
				view.draw(canvas);

			}
		} catch (Exception e) {
			bitmap = null;
			e.getStackTrace();
		}
		return bitmap;
	}

	private Uri saveBitmap(Bitmap bmp, String path) throws IOException {
		File file = new File(path);
		FileOutputStream out = new FileOutputStream(file);
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
		out.flush();
		out.close();
		return Uri.fromFile(file);
	}
}
