package com.privatewardrobe.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.privatewardrobe.PWApplication;
import com.privatewardrobe.PWConstant;
import com.privatewardrobe.R;
import com.privatewardrobe.activity.ClothesDetailActivity;
import com.privatewardrobe.activity.MainActivity;
import com.privatewardrobe.adapter.ClothesListAdapter;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.ClothesBusiness;
import com.privatewardrobe.control.LoadMoreListView;
import com.privatewardrobe.control.LoadMoreListView.OnLoadMoreListener;
import com.privatewardrobe.control.RefreshInterface;
import com.privatewardrobe.model.Clothes;

public class ClothesFragment extends Fragment implements RefreshInterface {

	private ArrayList<Clothes> mDataList;
	private ArrayList<Clothes> mResultList;

	private EditText mSearchEdit;
	private GridView mGridView;

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
		queryClothesByPage();

	}

	@Override
	public void onDestroyView() {
		PWApplication.getInstance().putCache(PWConstant.CACHE_CLOTHES,
				mDataList);
		super.onDestroyView();
	}

	private void queryClothesByPage() {
		ClothesBusiness clothesBusiness = new ClothesBusiness();
		clothesBusiness.queryClothesByUserId(PWApplication.getInstance()
				.getUserId(), 0, new BusinessListener<Clothes>() {
			@Override
			public void onSuccess(ArrayList<Clothes> list) {
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

	private void findView(View v) {
		mSearchEdit = (EditText) v
				.findViewById(R.id.fragment_clothes_search_edit);
		mGridView = (GridView) v
				.findViewById(R.id.fragment_clothes_gridView);

	}

	private void initView(View v) {
		mDataList = new ArrayList<Clothes>();
		mResultList = new ArrayList<Clothes>();
		mClothesAdapter = new ClothesListAdapter(getActivity(), mResultList);
		mGridView.setAdapter(mClothesAdapter);
		mSearchEdit.addTextChangedListener(searchEditWatcher);
		// if(mListView.getChildCount()>=1){
		// mListView.getChildAt(0).get;
		// }
		mGridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("xionglu", "onTouch");
				if (mGridView.getFirstVisiblePosition() == 0
						|| event.getAction() == MotionEvent.ACTION_UP) {
					Log.i("xionglu", "onTouch Deliver");
					MainActivity activity = (MainActivity) getActivity();
					activity.mActionBar.onTouch(event);
				}

				return false;
			}
		});
		// mListView.setOnOverScrolledTopListener(new
		// OnOverScrolledTopListener() {
		//
		// @Override
		// public void onOverScrolled(MotionEvent ev, int startY) {
		// MainActivity activity = (MainActivity) getActivity();
		// activity.mActionBar.onTouch(ev, startY);
		// }
		// });
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),
						ClothesDetailActivity.class);
				startActivity(intent);
			}
		});
		//mListView.setEmptyView(LayoutInflater.from(getActivity()).inflate(R.layout.view_fragment_clothes_empty, null));
	}

	private void notifyDatasetChanged() {
		mClothesAdapter.notifyDataSetChanged();
	}

	private TextWatcher searchEditWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			mResultList.clear();
			for (int i = 0; i < mDataList.size(); i++) {
				Clothes clohtes = mDataList.get(i);
				if (clohtes.getDescription().contains(
						mSearchEdit.getText().toString())) {
					mResultList.add(clohtes);
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

	@Override
	public void onRefresh() {
		queryClothesByPage();

	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub
		
	}
}
