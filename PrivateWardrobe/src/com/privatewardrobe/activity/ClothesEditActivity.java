package com.privatewardrobe.activity;

import java.io.File;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.ClothesTypeHelper;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.R;
import com.privatewardrobe.UploadHelper;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.UploadHelper.UpCompletionListener;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.Clothes;
import com.privatewardrobe.photo.PhotoHelper;
import com.privatewardrobe.photo.PhotoHelper.PhotoProcessListener;

public class ClothesEditActivity extends BaseActivity {
	public static int REQUEST_CODE = 107;
	public static String CLOTHES = "clothes";

	private ActionBar mActionBar;
	private ImageView mClothesImg;
	private EditText mDescriptionEdit;
	private TextView mColorText, mCategoryText;
	private RelativeLayout mColorLayout, mCategoryLayout;
	private PhotoHelper mPhotoHelper;
	private ClothesTypeHelper mClothesTypeHelper = ClothesTypeHelper
			.getInstance();
	private UploadHelper mUploadHelper;
	// private int mColor = 2, mCategory = 0, mExponent;
	private boolean hasImg = false;
	// // private String mImg;
	private Uri mSource;
	// // private int
	private Clothes mClothes;

	private AlertDialog mLoadingDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_edit_clothes);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("添加衣服");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();
	}

	private void loadData() {
		Clothes clothes = (Clothes) getIntent().getSerializableExtra(CLOTHES);
		if (clothes != null) {
			mClothes = clothes;
		}
		notifyDataSetChanged();
	}

	private void notifyDataSetChanged() {
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("冷色系");
		colors.add("中色系");
		colors.add("暖色系");
		mColorText.setText(colors.get(mClothes.getColor() - 1));
		mDescriptionEdit.setText(mClothes.getDescription());
		if (mClothes.getCategory() == 0) {
			mCategoryText.setText("默认分类");
		} else {
			mCategoryText.setText(mClothesTypeHelper.getDetailName(mClothes
					.getCategory()));
		}
		if (mClothes.getImg() != null) {
			imageLoader.displayImage("http://" + mClothes.getImg(),
					mClothesImg, Utils.buildNoneDisplayImageOptions());
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mPhotoHelper.process(requestCode, resultCode, data);
		super.onActivityResult(requestCode, resultCode, data);

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
								ClothesBusiness clothesBusiness = new ClothesBusiness();
								clothesBusiness.updateClothes(mClothes.getId(),
										mClothes.getColor(),
										mClothes.getCategory(), img,
										mDescriptionEdit.getText().toString(),
										mClothes.getLike(),
										new BusinessListener<Clothes>() {
											@Override
											public void onFailure(String reason) {
												Toast.makeText(
														ClothesEditActivity.this,
														reason,
														Toast.LENGTH_LONG)
														.show();
											}

											@Override
											public void onFinish() {
												mLoadingDialog.dismiss();
											}

											@Override
											public void onSuccess(
													Clothes clothes) {
												mLoadingDialog.dismiss();
												Intent data = new Intent();
												data.putExtra(CLOTHES, clothes);
												setResult(RESULT_OK, data);
												ClothesEditActivity.this
														.finish();
												Toast.makeText(
														ClothesEditActivity.this,
														"修改成功",
														Toast.LENGTH_LONG)
														.show();
											}

										});
							}
						});
			} else {
				ClothesBusiness clothesBusiness = new ClothesBusiness();
				clothesBusiness.updateClothes(mClothes.getId(), mClothes
						.getColor(), mClothes.getCategory(), mClothes.getImg(),
						mDescriptionEdit.getText().toString(), mClothes
								.getLike(), new BusinessListener<Clothes>() {
							@Override
							public void onFailure(String reason) {
								Toast.makeText(ClothesEditActivity.this,
										reason, Toast.LENGTH_LONG).show();
							}

							@Override
							public void onFinish() {
								mLoadingDialog.dismiss();
							}

							@Override
							public void onSuccess(Clothes clothes) {
								mLoadingDialog.dismiss();
								Intent data = new Intent();
								data.putExtra(CLOTHES, clothes);
								setResult(RESULT_OK, data);
								ClothesEditActivity.this.finish();
								Toast.makeText(ClothesEditActivity.this,
										"修改成功", Toast.LENGTH_LONG).show();
							}

						});
			}

		}
		super.onActionBarItemSelected(itemId, item);
	}

	private void findView() {
		mClothesImg = (ImageView) findViewById(R.id.activity_create_clothes_img);
		mDescriptionEdit = (EditText) findViewById(R.id.activity_create_clothes_description_edit);
		mColorLayout = (RelativeLayout) findViewById(R.id.activity_create_clothes_color_layout);
		mCategoryLayout = (RelativeLayout) findViewById(R.id.activity_create_category_layout);
		mColorText = (TextView) findViewById(R.id.activity_create_clothes_color_text);
		mCategoryText = (TextView) findViewById(R.id.activity_create_clothes_category_text);
	}

	private void initView() {
		bindEvents();
		mLoadingDialog = Utils.buildLoadingDialog(ClothesEditActivity.this);
		mPhotoHelper = new PhotoHelper(ClothesEditActivity.this,
				new PhotoProcessListener() {

					@Override
					public void onComplete(Uri source, Uri large, Uri thumbnail) {
						mSource = large;
						hasImg = true;
						imageLoader.displayImage(thumbnail.toString(),
								mClothesImg);
					}
				});
	}

	private void bindEvents() {
		mClothesImg.setOnClickListener(mClothesImgClickListener);
		mColorLayout.setOnClickListener(mColorLayoutClickListener);
		mCategoryLayout.setOnClickListener(mCategoryLayoutClickListener);
	}

	private OnClickListener mClothesImgClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Utils.buildPhotoHelperListDialog(ClothesEditActivity.this,
					mPhotoHelper);
		}
	};

	private OnClickListener mColorLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(
					ClothesEditActivity.this);
			String[] colorChoices = { "冷色系", "中色系", "暖色系" };
			builder.setTitle("选择色系");
			builder.setItems(colorChoices,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							mClothes.setColor(which + 1);
							notifyDataSetChanged();
						}
					});
			builder.create().show();
		}
	};

	private OnClickListener mCategoryLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder builder1 = new AlertDialog.Builder(
					ClothesEditActivity.this);
			final ArrayList<String> genders = mClothesTypeHelper.getGender();
			String[] genderChoices = (String[]) genders
					.toArray(new String[genders.size()]);
			builder1.setTitle("选择性别");
			builder1.setItems(genderChoices,
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							final int gender = mClothesTypeHelper
									.getGenderCode(genders.get(which));
							final ArrayList<String> types = mClothesTypeHelper
									.getTypes(gender);
							String[] typeChoices = (String[]) types
									.toArray(new String[types.size()]);
							AlertDialog.Builder builder2 = new AlertDialog.Builder(
									ClothesEditActivity.this);
							builder2.setTitle("选择种类");
							builder2.setItems(typeChoices,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											final ArrayList<String> detailTypes = mClothesTypeHelper
													.getDetailNames(mClothesTypeHelper
															.getTypeCode(
																	gender,
																	types.get(which)));
											String[] detailChoices = (String[]) detailTypes
													.toArray(new String[detailTypes
															.size()]);
											AlertDialog.Builder builder3 = new AlertDialog.Builder(
													ClothesEditActivity.this);
											builder3.setTitle("选择种类");
											builder3.setItems(
													detailChoices,
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(
																DialogInterface dialog,
																int which) {
															mClothes.setCategory(mClothesTypeHelper
																	.getDetailCode(detailTypes
																			.get(which)));
															notifyDataSetChanged();
														}
													});
											builder3.create().show();
										}
									});
							builder2.create().show();
						}
					});
			builder1.create().show();
		}
	};

}
