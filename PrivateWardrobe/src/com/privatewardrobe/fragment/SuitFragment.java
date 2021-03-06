package com.privatewardrobe.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.ClothesDetailActivity;
import com.privatewardrobe.activity.MainActivity;
import com.privatewardrobe.activity.SuitDetailActivity;
import com.privatewardrobe.adapter.SuitListAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.SuitBusiness;
import com.privatewardrobe.control.RefreshInterface;
import com.privatewardrobe.model.Suit;

public class SuitFragment extends Fragment implements RefreshInterface {

	private ArrayList<Suit> mDataList;
	private ArrayList<Suit> mResultList;
	private EditText mSearchEdit;
	private GridView mListView;

	private SuitListAdapter mSuitAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater
				.inflate(R.layout.fragment_main_suit, container, false);
		findView(v);
		initView(v);
		loadData();
		return v;
	}

	private void loadData() {
		ArrayList<Suit> clothess = (ArrayList<Suit>) PWApplication
				.getInstance().getCache(PWConstant.CACHE_SUIT);
		if (clothess != null) {
			mDataList.clear();
			mDataList.addAll(clothess);
			mResultList.clear();
			mResultList.addAll(clothess);
			notifyDatasetChanged();
		}
		queryClothesByPage();
	}

	@Override
	public void onDestroyView() {
		PWApplication.getInstance().putCache(PWConstant.CACHE_SUIT, mDataList);
		super.onDestroyView();
	}

	private void queryClothesByPage() {
		SuitBusiness suitBusiness = new SuitBusiness();
		suitBusiness.querySuitByUserId(PWApplication.getInstance().getUserId(),
				0, new BusinessListener<Suit>() {
					@Override
					public void onSuccess(ArrayList<Suit> list) {
						MainActivity activity = (MainActivity) getActivity();
						activity.completeRefresh();
						mDataList.clear();
						mResultList.clear();

						mDataList.addAll(list);
						mResultList.addAll(list);
						notifyDatasetChanged();
					}
				});
	}

	private void notifyDatasetChanged() {
		mSuitAdapter.notifyDataSetChanged();

	}

	private void findView(View v) {
		mSearchEdit = (EditText) v.findViewById(R.id.fragment_suit_search_edit);
		mListView = (GridView) v.findViewById(R.id.fragment_suit_gridView);
	}

	private void initView(View v) {
		mDataList = new ArrayList<Suit>();
		mResultList = new ArrayList<Suit>();
		mSuitAdapter = new SuitListAdapter(getActivity(), mResultList);
		mListView.setAdapter(mSuitAdapter);
		mSearchEdit.addTextChangedListener(searchEditWatcher);
		mListView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("xionglu", "onTouch");
				if (mListView.getFirstVisiblePosition() == 0
						|| event.getAction() == MotionEvent.ACTION_UP) {
					Log.i("xionglu", "onTouch Deliver");
					MainActivity activity = (MainActivity) getActivity();
					activity.mActionBar.onTouch(event);
				}

				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						SuitDetailActivity.class);
				intent.putExtra(SuitDetailActivity.EXTRA_INPUT, mResultList.get(arg2));
				startActivity(intent);
			}
		});
		// mListView.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.view_fragment_suit_empty,
		// null));
	}

	private TextWatcher searchEditWatcher = new TextWatcher() {

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
			notifyDatasetChanged();

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

	@Override
	public void onRefresh() {
		queryClothesByPage();

	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub

	}
}
