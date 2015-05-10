package com.privatewardrobe.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.adapter.ClothesListAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.control.LoadMoreListView;
import com.privatewardrobe.control.LoadMoreListView.OnLoadMoreListener;
import com.privatewardrobe.model.Clothes;

public class ClothesFragment extends Fragment {

	private ArrayList<Clothes> mDataList;
	private ArrayList<Clothes> mResultList;

	private EditText mSearchEdit;
	private LoadMoreListView mListView;

	private int mPage = 1;

	private ClothesListAdapter mClothesAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_main_clothes, container,
				false);
		findView(v);
		initView(v);
		loadData();
		return v;
	}

	private void loadData() {
		ArrayList<Clothes> clothess = (ArrayList<Clothes>) PWApplication
				.getInstance().getCache(PWConstant.CACHE_CLOTHES);
		if (clothess != null) {
			mDataList.clear();
			mDataList.addAll(clothess);
			mResultList.clear();
			mResultList.addAll(clothess);
			notifyDatasetChanged();
		}
		queryClothesByPage(1);

	}

	private void queryClothesByPage(final int page) {
		ClothesBusiness clothesBusiness = new ClothesBusiness();
		clothesBusiness.queryClothesByUserId(PWApplication.getInstance()
				.getUserId(), page, new BusinessListener<Clothes>() {
			@Override
			public void onSuccess(ArrayList<Clothes> list) {
				if (page == 1) {
					mDataList.clear();
					mResultList.clear();
				}
				if (list.size() > 0) {
					mDataList.addAll(list);
					mResultList.addAll(list);
					mPage = page+1;
					notifyDatasetChanged();
					mListView.onLoadMoreComplete();
				} else {
					Toast.makeText(getActivity(), "已经是最后一页", Toast.LENGTH_LONG)
							.show();
				}

			}
		});
	}

	private void findView(View v) {
		mSearchEdit = (EditText) v
				.findViewById(R.id.fragment_clothes_search_edit);
		mListView = (LoadMoreListView) v
				.findViewById(R.id.fragment_clothes_listview);

	}

	private void initView(View v) {
		mDataList = new ArrayList<Clothes>();
		mResultList = new ArrayList<Clothes>();
		mClothesAdapter = new ClothesListAdapter(getActivity(), mResultList);
		mListView.setAdapter(mClothesAdapter);
		mSearchEdit.addTextChangedListener(searchEditWatcher);
		mListView.setOnLoadMoreListener(new OnLoadMoreListener() {
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void notifyDatasetChanged() {
		mClothesAdapter.notifyDataSetChanged();
	}

	private TextWatcher searchEditWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

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

	private void popInputMethod() {
		(new Handler()).postDelayed(new Runnable() {
			public void run() {
				InputMethodManager imm = (InputMethodManager) (getActivity())
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
				mSearchEdit.setFocusable(true);
				mSearchEdit.requestFocus();
			}
		}, 500);
	}
}
