package com.hdweiss.codemap.view.fragments;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.hdweiss.codemap.util.CodeMapPoint;
import com.hdweiss.codemap.util.SpanUtils;
import com.hdweiss.codemap.view.codemap.CodeMapView;
import com.hdweiss.codemap.view.fragments.FunctionLinkSpan.FunctionLinkSpanConverter;

public class CodeMapFunction extends CodeMapItem {

	private TextView sourceView;
	private CodeMapView codeMapView;
	
	public CodeMapFunction(Context context, AttributeSet attrs) {
		this(context, new CodeMapPoint(0, 0), "", new SpannableString(""), null);
	}
	
	public CodeMapFunction(Context context, CodeMapPoint point, String name,
			SpannableString content, CodeMapView codeMapView) {
		super(context, null, name);
		
		this.codeMapView = codeMapView;
		
		sourceView = new TextView(getContext());
		setContentView(sourceView);
		
		init(name, content);
		setPosition(point);
	}
	
	private void init(String name, SpannableString content) {
		sourceView.setText(content);
		
		Spannable span = SpanUtils.replaceAll(content,
				URLSpan.class, new FunctionLinkSpanConverter(this));
		
		sourceView.setText(span);
		sourceView.setLinksClickable(true);
		sourceView.setMovementMethod(LinkMovementMethod.getInstance());
	}

	public void addChildFragment(String url) {
		this.codeMapView.openFragmentFromUrl(url, this);
	}
}
