package com.privatewardrobe.control;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.privatewardrobe.R;
import com.privatewardrobe.business.CommentOnSendListener;

public class CommentFragment extends Fragment {
	private EditText editTxt;
	private ImageButton sendButton;
	private CommentOnSendListener mSendlistener;
	private OnClickListener sendClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mSendlistener != null) {
				mSendlistener.OnSendClick(editTxt);
				if (!editTxt.getText().toString().equals("")) {
					hiddenSystemInputMethod();
				}
			}
		}
	};

	

	public void setCommentClickListener(CommentOnSendListener listener) {
		this.mSendlistener = listener;
	}

	public void hiddenSystemInputMethod() {
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(editTxt.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_comment_layout, container,
				false);
		initView(v);
		bindEvent();
		return v;
	}

	private void bindEvent() {
		sendButton.setOnClickListener(sendClickListener);
	}

	private void initView(View v) {
		editTxt = (EditText) v.findViewById(R.id.input_txt);
		sendButton = (ImageButton) v.findViewById(R.id.send_btn);
	}

	public EditText getEditText() {
		return editTxt;
	}

	public ImageButton getButton() {
		return sendButton;
	}

	public void setEditTextHint(String hintStr) {
		editTxt.setHint(hintStr);
	}

}
