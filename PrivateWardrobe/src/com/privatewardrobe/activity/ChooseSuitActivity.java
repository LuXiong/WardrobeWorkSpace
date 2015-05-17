package com.privatewardrobe.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ChooseSuitListAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.SuitBusiness;
import com.privatewardrobe.model.Suit;

public class ChooseSuitActivity extends BaseActivity {

	public final static int REQUEST_CODE = 104;
	public final static String SUIT = "suit";

	private ActionBar mActionBar;
	private EditText mSearchEdit;
	private LinearLayout mCreateLayout;
	private ListView mListView;

	private ArrayList<Suit> mDataList;
	private ArrayList<Suit> mResultList;

	private ChooseSuitListAdapter mAdapter;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_choose_suit);
		mActionBar = getMyActionBar();
		mActionBar.setTitle("—°‘Ò¥Ó≈‰");
		findView();
		initView();
		loadData();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SuitCreateActivity.REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Suit suit = (Suit) data
						.getSerializableExtra(SuitCreateActivity.SUIT);
				if (suit != null) {
					Intent intent = new Intent();
					intent.putExtra(SUIT, suit);
					setResult(RESULT_OK, intent);
					ChooseSuitActivity.this.finish();
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);

	}
	
	@Override
	protected void onDestroy() {
		PWApplication.getInstance().putCache(PWConstant.CACHE_SUIT, mDataList);
		super.onDestroy();
	}

	private void findView() {
		mSearchEdit = (EditText) findViewById(R.id.activity_choose_suit_edit);
		mCreateLayout = (LinearLayout) findViewById(R.id.activity_choose_create_suit_layout);
		mListView = (ListView) findViewById(R.id.activity_choose_suit_data_list);
	}

	private void initView() {
		mDataList = new ArrayList<Suit>();
		mResultList = new ArrayList<Suit>();
		mAdapter = new ChooseSuitListAdapter(mResultList, this);
		mListView.setAdapter(mAdapter);
		bindEvent();

	}

	private void loadData() {
		ArrayList<Suit> suitList = (ArrayList<Suit>) PWApplication
				.getInstance().getCache(PWConstant.CACHE_SUIT);
		if (suitList != null) {
			loadListView(suitList);
		}
		SuitBusiness suitBusiness = new SuitBusiness();
		suitBusiness.querySuitByUserId(PWApplication.getInstance().getUserId(),
				0, new BusinessListener<Suit>() {
					@Override
					public void onSuccess(ArrayList<Suit> list) {
						loadListView(list);
					}
				});
	}

	private void loadListView(ArrayList<Suit> list) {
		mDataList.clear();
		mDataList.addAll(list);
		mResultList.clear();
		mResultList.addAll(list);
		notifyDataSetChanged();
	}

	private void bindEvent() {
		mListView.setOnItemClickListener(mListViewItemClickListener);
		mSearchEdit.addTextChangedListener(mSearchEditTextWatcher);
		mCreateLayout.setOnClickListener(mCreateLayoutClickListener);
	}

	private void notifyDataSetChanged() {
		mAdapter.notifyDataSetChanged();
	}

	private OnClickListener mCreateLayoutClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ChooseSuitActivity.this,
					SuitCreateActivity.class);
			startActivityForResult(intent, SuitCreateActivity.REQUEST_CODE);

		}
	};

	private OnItemClickListener mListViewItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int which,
				long arg3) {
			Intent intent = new Intent();
			intent.putExtra(SUIT, mResultList.get(which));
			setResult(RESULT_OK, intent);
			ChooseSuitActivity.this.finish();
		}
	};

	private TextWatcher mSearchEditTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mResultList.clear();
			for (int i = 0; i < mDataList.size(); i++) {
				Suit suit = mDataList.get(i);
				if (suit.getDescription().contains(
						mSearchEdit.getText().toString())) {
					mResultList.add(suit);
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
