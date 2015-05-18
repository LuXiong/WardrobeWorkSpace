package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.adapter.ChooseClothesListAdapter;
import com.privatewardrobe.adapter.ImgHorizenGridAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.model.Clothes;

public class ChooseClothesActivity extends BaseActivity {

	public final static String CLOTHES = "clothes";
	public final static String CLOTHES_CHOOSED = "clothesChoosed";
	public final static int REQUEST_CODE = 100;

	private ArrayList<Clothes> mClothesList;
	private ArrayList<Clothes> mChosedList;
	private ArrayList<Clothes> mResultList;

	private ActionBar mActionBar;
	private GridView mClothesGridView;
	private ListView mCLothesListVew;
	private RelativeLayout mCreateLayout;
	private EditText mSearchEdit;

	private ImgHorizenGridAdapter mGridAdapter;
	private ChooseClothesListAdapter mListAdapter;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_choose_clothes);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("Ñ¡ÔñÒÂ·þ");
		mActionBar.addActionItem(0, null, R.drawable.icon_finish,
				ActionItem.SHOWACTION_SHOW);
		findView();
		initView();
		loadData();
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		if (itemId == 0) {
			Intent data = new Intent();
			data.putExtra(CLOTHES_CHOOSED, mChosedList);
			setResult(RESULT_OK, data);
			finish();
		}
		super.onActionBarItemSelected(itemId, item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ClothesCreateActivity.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Clothes clothes = (Clothes) data
						.getSerializableExtra(ClothesCreateActivity.CLOTHES);
				if (clothes != null) {
					mChosedList.add(clothes);
					mClothesList.add(0, clothes);
					mResultList.add(0, clothes);
					notifyDataSetChanged();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}

	private void findView() {
		mClothesGridView = (GridView) findViewById(R.id.activity_choose_clothes_gridView);
		mCLothesListVew = (ListView) findViewById(R.id.activity_choose_clothes_data_list);
		mCreateLayout = (RelativeLayout) findViewById(R.id.activity_choose_create_clothes_layout);
		mSearchEdit = (EditText) findViewById(R.id.activity_choose_clothes_edit);
	}

	private void initView() {
		mClothesList = new ArrayList<Clothes>();
		mChosedList = new ArrayList<Clothes>();
		mResultList = new ArrayList<Clothes>();
		mGridAdapter = new ImgHorizenGridAdapter(this, mChosedList);
		mListAdapter = new ChooseClothesListAdapter(this, mResultList,
				mChosedList);
		mClothesGridView.setAdapter(mGridAdapter);
		mCLothesListVew.setAdapter(mListAdapter);
		mCLothesListVew.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_choose_clothes_empty, null));
		bindEvent();

	}

	private void bindEvent() {
		mClothesGridView.setOnItemClickListener(mGridViewItemClickListener);
		mCLothesListVew.setOnItemClickListener(mListViewItemClickListener);
		mCreateLayout.setOnClickListener(mCreateClickListener);
		mSearchEdit.addTextChangedListener(mSearchTextWatcher);
	}

	private void loadData() {
		Intent intent = getIntent();

		ArrayList<Clothes> choosedClothes = (ArrayList<Clothes>) intent
				.getSerializableExtra(CLOTHES_CHOOSED);
		if (choosedClothes != null) {
			mChosedList.clear();
			mChosedList.addAll(choosedClothes);
		}

		ArrayList<Clothes> clothesList = (ArrayList<Clothes>) PWApplication
				.getInstance().getCache(PWConstant.CACHE_CLOTHES);
		if (clothesList != null) {
			mClothesList.clear();
			mClothesList.addAll(clothesList);
			mResultList.clear();
			mResultList.addAll(clothesList);
		}
		ClothesBusiness clothesBusiness = new ClothesBusiness();
		clothesBusiness.queryClothesByUserId(PWApplication.getInstance().getUserId(), 0, new BusinessListener<Clothes>(){
			@Override
			public void onSuccess(ArrayList<Clothes> list) {
				mClothesList.clear();
				mClothesList.addAll(list);
				mResultList.clear();
				mResultList.addAll(list);
				notifyDataSetChanged();
			}
		});
		notifyDataSetChanged();

	}

	private void notifyDataSetChanged() {
		mGridAdapter.notifyDataSetChanged();
		mListAdapter.notifyDataSetChanged();
	}

	private OnItemClickListener mGridViewItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View v,
				int position, long arg3) {
			mChosedList.remove(position);
			notifyDataSetChanged();
		}
	};
	private OnItemClickListener mListViewItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View v,
				int position, long arg3) {
			mSearchEdit.setText("");
			Clothes clothes = mResultList.get(position);
			if (mChosedList.contains(clothes)) {
				mChosedList.remove(clothes);
			} else {
				mChosedList.add(clothes);
			}
			notifyDataSetChanged();

		}
	};

	private OnClickListener mCreateClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ChooseClothesActivity.this,
					ClothesCreateActivity.class);
			startActivityForResult(intent, ClothesCreateActivity.REQUEST_CODE);
		}

	};
	private TextWatcher mSearchTextWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			mResultList.clear();
			for(int i=0;i<mClothesList.size();i++){
				Clothes clohtes = mClothesList.get(i);
				if(clohtes.getDescription().contains(mSearchEdit.getText().toString())){
					mResultList.add(clohtes);
				}
			}
			notifyDataSetChanged();
			
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
	};
}