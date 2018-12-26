package com.shenl.utils.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.shenl.utils.R;


public class SpinnerView extends RelativeLayout implements OnClickListener {

	private EditText mEtInput;
	private ImageView mIvArrow;

	private PopupWindow mWindow;
	private ListAdapter mAdapter;
	private OnItemClickListener mListener;
	private TextWatcher textwatcher;
	private ListView mContentView;
	private int scheight;

	public SpinnerView(Context context) {
		this(context, null);
	}

	public SpinnerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// xml和class 绑定
		View view = View.inflate(context, R.layout.spinner, this);
		mEtInput = (EditText) view.findViewById(R.id.et_input);
		mIvArrow = (ImageView) view.findViewById(R.id.iv_arrow);
		mIvArrow.setOnClickListener(this);
		mEtInput.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		clickArrow();
	}

	public void setAdapter(ListAdapter adapter) {
		this.mAdapter = adapter;
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mListener = listener;
	}

	public void addTextChangedListener(TextWatcher textwatcher) {
		this.textwatcher = textwatcher;
	}

	private void clickArrow() {
		// 点击箭头，需要弹出显示list数据的层
		if (mAdapter == null) {
			throw new RuntimeException("请调用setAapter()去设置数据");
		}

		if (mWindow == null) {
			// contentView：显示的view
			// width height:popup宽和高

			mContentView = new ListView(getContext());

			// 设置数据
			mContentView.setAdapter(mAdapter);// ---》adapter---》List《数据》

			mContentView.setBackgroundColor(Color.parseColor("#ffffff"));
			int width = mEtInput.getWidth();


			int height = width/3;

			mWindow = new PopupWindow(mContentView, width, height);
			// 设置获取焦点
			mWindow.setFocusable(true);

			// 设置边缘点击收起
			mWindow.setOutsideTouchable(true);
			mWindow.setBackgroundDrawable(new ColorDrawable());
		}

		// 设置item的点击事件
		mContentView.setOnItemClickListener(mListener);
		mWindow.showAsDropDown(mEtInput);
		if (textwatcher != null) {
			mEtInput.addTextChangedListener(textwatcher);
		}

	}

	public void setText(String data) {
		mEtInput.setText(data);
	}

	public String getText() {
		return mEtInput.getText().toString().trim();
	}

	public void setSelection(int length) {
		mEtInput.setSelection(length);
	}

	public void dismiss() {
		if (mWindow != null) {
			mWindow.dismiss();
		}
	}
}
