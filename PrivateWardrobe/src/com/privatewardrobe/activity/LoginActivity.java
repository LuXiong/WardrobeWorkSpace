package com.privatewardrobe.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.privatewardrobe.ActionBar;
import com.privatewardrobe.ActionBar.ActionItem;
import com.privatewardrobe.BaseActivity;
import com.privatewardrobe.R;
import com.privatewardrobe.business.BusinessListener;
import com.privatewardrobe.business.PassBusiness;
import com.privatewardrobe.common.Utils;
import com.privatewardrobe.model.User;

public class LoginActivity extends BaseActivity {

	private EditText mPhoneEdit, mPasswordEdit, mCodeEdit;
	private Button mGetCodeBtn, mRegistOrLoginBtn;
	private TextView mRegistOrLoginText, mForgetText;
	private LinearLayout mCodeLayout;

	private PageState mPageState = PageState.STATE_LOGIN;
	private ActionBar mActionBar;


	private enum PageState {
		// page of the login state
		STATE_LOGIN,
		// page of the regist state
		STATE_REGIST
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		mActionBar = getMyActionBar();
		mActionBar.setLeftDrawable(null);
		setContentView(R.layout.activity_login);
		findView();
		initView();
	}

	@Override
	protected void onActionBarItemSelected(int itemId, ActionItem item) {
		super.onActionBarItemSelected(itemId, item);
	}

	private void findView() {
		mPhoneEdit = (EditText) findViewById(R.id.activity_login_username_edit);
		mPasswordEdit = (EditText) findViewById(R.id.activity_login_password_edit);
		mCodeEdit = (EditText) findViewById(R.id.activity_login_code_edit);
		mGetCodeBtn = (Button) findViewById(R.id.activity_login_getcode_btn);
		mRegistOrLoginBtn = (Button) findViewById(R.id.activity_login_btn);
		mRegistOrLoginText = (TextView) findViewById(R.id.activity_login_regist_text);
		mForgetText = (TextView) findViewById(R.id.activity_login_forget_text);
		mCodeLayout = (LinearLayout) findViewById(R.id.activity_login_code_layout);
	}

	private void initView() {
		bindEvents();
		notifyPage();
		
	}

	private void bindEvents() {
		mGetCodeBtn.setOnClickListener(mGetCodeBtnClickListener);
		mForgetText.setOnClickListener(mForgetTextClickListener);
	}

	private void notifyPage() {
		if (mPageState == PageState.STATE_LOGIN) {
			mCodeLayout.setVisibility(View.GONE);
			mRegistOrLoginBtn.setOnClickListener(mLoginBtnClickListener);
			mRegistOrLoginText.setOnClickListener(mRegistTextClickListener);
			mRegistOrLoginBtn.setText("登陆");
			mRegistOrLoginText.setText("注册");
		}
		if (mPageState == PageState.STATE_REGIST) {
			mCodeLayout.setVisibility(View.VISIBLE);
			mRegistOrLoginBtn.setOnClickListener(mRegisterBtnClickListenr);
			mRegistOrLoginText.setOnClickListener(mLoginTextClickListener);
			mRegistOrLoginBtn.setText("注册");
			mRegistOrLoginText.setText("登陆");
		}
	}

	private OnClickListener mRegistTextClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mPageState = PageState.STATE_REGIST;
			notifyPage();

		}
	};

	private OnClickListener mLoginTextClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			mPageState = PageState.STATE_LOGIN;
			notifyPage();
		}
	};

	private OnClickListener mForgetTextClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};

	private OnClickListener mLoginBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			PassBusiness passBusiness = new PassBusiness();
			passBusiness.login(mPhoneEdit.getText().toString(), mPasswordEdit
					.getText().toString(), new BusinessListener<User>() {

				@Override
				public void onSuccess(User user) {
					if (user != null) {
						Intent intent = new Intent(LoginActivity.this,
								MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
								| Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						LoginActivity.this.finish();
					}

				}

				public void onFailure(String reason) {
					Log.i("xionglu", "Login Failed:" + reason);
				};

			});
		}
	};

	private OnClickListener mRegisterBtnClickListenr = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (isPhoneNum(mPhoneEdit.getText().toString())
					&& isRightPassword(mPasswordEdit.getText().toString())
					&& mCodeEdit.getText().toString().equals("1234")) {
				Intent intent = new Intent(LoginActivity.this,
						PerfectInfoActivity.class);
				intent.putExtra(PerfectInfoActivity.PHONE_NUM, mPhoneEdit
						.getText().toString());
				intent.putExtra(PerfectInfoActivity.PASSWORD, mPasswordEdit
						.getText().toString());
				intent.putExtra(PerfectInfoActivity.CODE, mCodeEdit.getText()
						.toString());
				startActivity(intent);
			}

		}
	};

	private OnClickListener mGetCodeBtnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (mPageState == PageState.STATE_REGIST) {
				TextUpdateTask textUpdateTask = new TextUpdateTask();
				textUpdateTask.execute();
			}
		}
	};

	private class TextUpdateTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			for (int i = 59; i >= 0; i--) {
				try {
					Thread.sleep(1000);
					publishProgress(i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return 0;
		}

		@Override
		protected void onPreExecute() {
			mGetCodeBtn.setEnabled(false);
		};

		@Override
		protected void onPostExecute(Integer result) {
			mGetCodeBtn.setEnabled(true);
			mGetCodeBtn.setText("获取验证码");
		};

		@Override
		protected void onProgressUpdate(Integer[] values) {
			mGetCodeBtn.setText("(" + values[0] + ")" + "重新发送");
		};
	};

	/**
	 * 使用正则表达式判断手机号码是否符合规范
	 * 
	 * @param phone
	 * @return
	 */
	private boolean isPhoneNum(String phone) {
		Pattern pMobile = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); // 中国移动号码
		Pattern pCM = Pattern
				.compile("^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$"); // 中国联通号码
		Pattern pCU = Pattern.compile("^1(3[0-2]|5[256]|8[56])\\d{8}$"); // 中国电信号码
		Matcher mMobile = pMobile.matcher(phone);
		Matcher mCM = pCM.matcher(phone);
		Matcher mCU = pCU.matcher(phone);

		if (mMobile.matches() || mCM.matches() || mCU.matches()) {
			return true;
		} else {
			System.out.println("手机号码为非法格式，请重新输入");
			return false;
		}
	}

	/**
	 * 密码格式检查
	 * 
	 * @param password
	 * @return
	 */
	private boolean isRightPassword(String password) {
		if ((password.length() <= 16) && (password.length() >= 6)) {
			return true;
		} else {
			System.out.println("password的位数请位于6-16之间");
			return false;
		}
	}
}
