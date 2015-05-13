package com.privatewardrobe.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.UploadHelper;
import com.privatewardrobe.UploadHelper.UpCompletionListener;
import com.privatewardrobe.adapter.ImgHorizenGridAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.business.SuitBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.model.Suit;
import com.privatewardrobe.photo.PhotoHelper;
import com.privatewardrobe.photo.PhotoHelper.PhotoProcessListener;

public class SuitCreateActivity extends BaseActivity {
	public static int REQUEST_CODE = 102;
	public static String SUIT = "suit";

	public final static String IMG = "img";

	private ActionBar mActionBar;
	private ImageView mSuitImg;
	private EditText mDescriptionEdit;
	private TextView mWeatherText, mOccasionText;
	private LinearLayout mSeasonLayout, mOccasionLayout;
	private Button mLikeBtn;
	private GridView mClothesGridView;
	private AlertDialog mLoadingDialog;

	private String mSeason, mOccasion, mImg, mDescription, mClothes;
	private int mLike;

	private ImgHorizenGridAdapter mGridAdapter;
	private ArrayList<Clothes> mChoosedClothes;

	private UploadHelper mUploadHelper;
	private PhotoHelper mPhotoHelper;
	private Uri mSource;
	private boolean hasImg = false;

	// private
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_create_suit);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("添加搭配");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();

	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			mLoadingDialog.show();
			if (hasImg) {
				mUploadHelper = new UploadHelper();
				mUploadHelper.upload(new File(mSource.getPath()),
						new UpCompletionListener() {

							@Override
							public void onSuccess(String img) {
								SuitBusiness suitBusiness = new SuitBusiness();
								suitBusiness.addSuit(PWApplication
										.getInstance().getUserId(), img, null,
										SuitBusiness.CheckWeatherW(mSeason),
										SuitBusiness.CheckOccasionW(mOccasion),
										mDescriptionEdit.getText().toString(),
										mLike, new BusinessListener<Suit>() {
											@Override
											public void onFinish() {
												mLoadingDialog.dismiss();
											}

											@Override
											public void onSuccess(Suit suit) {
												Intent data = new Intent();
												data.putExtra(SUIT, suit);
												setResult(RESULT_OK, data);
												SuitCreateActivity.this
														.finish();
												Toast.makeText(
														SuitCreateActivity.this,
														"创建成功",
														Toast.LENGTH_LONG)
														.show();
											}
										});
							}
						});
			} else {

				SuitBusiness suitBusiness = new SuitBusiness();
				suitBusiness.addSuit(PWApplication.getInstance().getUserId(),
						null, null, SuitBusiness.CheckWeatherW(mSeason),
						SuitBusiness.CheckOccasionW(mOccasion),
						mDescriptionEdit.getText().toString(), mLike,
						new BusinessListener<Suit>() {
							@Override
							public void onFinish() {
								mLoadingDialog.dismiss();
							}

							@Override
							public void onSuccess(Suit suit) {
								Intent data = new Intent();
								data.putExtra(SUIT, suit);
								setResult(RESULT_OK, data);
								SuitCreateActivity.this.finish();
								Toast.makeText(SuitCreateActivity.this, "创建成功",
										Toast.LENGTH_LONG).show();
							}
						});

			}
		}
		super.onActionBarItemSelected(itemId, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mPhotoHelper.process(requestCode, resultCode, data);
		if (requestCode == ChooseClothesActivity.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {

				ArrayList<Clothes> choosedClothes = (ArrayList<Clothes>) data
						.getSerializableExtra(ChooseClothesActivity.CLOTHES_CHOOSED);
				if (choosedClothes != null) {
					mChoosedClothes.clear();
					mChoosedClothes.addAll(choosedClothes);
				}
				notifyDatasetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void notifyDatasetChanged() {
		if (TextUtils.isEmpty(mSeason)) {
			mWeatherText.setText("夏装");
		} else {
			mWeatherText.setText(mSeason);
		}
		if (TextUtils.isEmpty(mOccasion)) {
			mOccasionText.setText("上学");
		} else {
			mOccasionText.setText(mOccasion);
		}
		imageLoader.displayImage(mImg, mSuitImg);
		mLikeBtn.setText("喜欢" + mLike);
		mGridAdapter.notifyDataSetChanged();
	}

	private void findView() {
		mSuitImg = (ImageView) findViewById(R.id.activity_create_suit_img);
		mDescriptionEdit = (EditText) findViewById(R.id.activity_create_suit_description_edit);
		mWeatherText = (TextView) findViewById(R.id.activity_create_suit_weather_text);
		mOccasionText = (TextView) findViewById(R.id.activity_create_suit_occasion_text);
		mSeasonLayout = (LinearLayout) findViewById(R.id.activity_create_suit_weather_layout);
		mOccasionLayout = (LinearLayout) findViewById(R.id.activity_create_suit_occasion_layout);
		mLikeBtn = (Button) findViewById(R.id.activity_create_suit_like_btn);
		mClothesGridView = (GridView) findViewById(R.id.activity_create_suit_clothes_gridView);

	}

	private void initView() {
		mClothesGridView.setEmptyView(LayoutInflater.from(this).inflate(
				R.layout.view_suit_creat_empty, null));
		mChoosedClothes = new ArrayList<Clothes>();
		mGridAdapter = new ImgHorizenGridAdapter(this, mChoosedClothes);
		mClothesGridView.setAdapter(mGridAdapter);
		mPhotoHelper = new PhotoHelper(this, new PhotoProcessListener() {

			@Override
			public void onComplete(Uri source, Uri large, Uri thumbnail) {
				mSource = large;
				hasImg = true;
				imageLoader.displayImage(thumbnail.toString(), mSuitImg);
			}
		});
		bindEvent();
		mLoadingDialog = Utils.buildLoadingDialog(SuitCreateActivity.this);
	}

	private void bindEvent() {
		mSuitImg.setOnClickListener(mImgClickListener);
		mSeasonLayout.setOnClickListener(mSeasonLayoutClickListener);
		mOccasionLayout.setOnClickListener(mOccasionLayoutClickListener);
		mLikeBtn.setOnClickListener(mLikeBtnClickListener);
		mClothesGridView.setOnItemClickListener(mGridViewClickListener);
	}

	private OnClickListener mImgClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Utils.buildPhotoHelperListDialog(SuitCreateActivity.this,
					mPhotoHelper);
		}

	};

	private OnClickListener mSeasonLayoutClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SuitCreateActivity.this);
			final ArrayList<String> choices = SuitBusiness
					.getWeatherStringList();
			String[] seasonChoices = choices
					.toArray(new String[choices.size()]);
			builder.setTitle("选择季节");
			builder.setItems(seasonChoices,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mSeason = choices.get(which);
							notifyDatasetChanged();
						}
					});
			builder.create().show();
		}

	};
	private OnClickListener mOccasionLayoutClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					SuitCreateActivity.this);
			final ArrayList<String> choices = SuitBusiness
					.getOccasionStringList();
			String[] occisionChoices = choices.toArray(new String[choices
					.size()]);
			builder.setTitle("选择场合");
			builder.setItems(occisionChoices,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mOccasion = choices.get(which);
							notifyDatasetChanged();
						}
					});
			builder.create().show();
		}

	};
	private OnClickListener mLikeBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mLike = (mLike + 1) % 2;
			notifyDatasetChanged();
		}
	};

	private OnItemClickListener mGridViewClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			chooseClothes();
		}
	};

	private void loadData() {
		mImg = getIntent().getStringExtra(IMG);
		mSource = Uri.parse(mImg);
		notifyDatasetChanged();
		chooseClothes();
	}

	private void chooseClothes() {
		Intent intent = new Intent(SuitCreateActivity.this,
				ChooseClothesActivity.class);
		intent.putExtra(ChooseClothesActivity.CLOTHES_CHOOSED, mChoosedClothes);
		startActivityForResult(intent, ChooseClothesActivity.REQUEST_CODE);
	}
}
