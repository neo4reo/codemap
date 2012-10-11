package com.hdweiss.codemap.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hdweiss.codemap.R;
import com.hdweiss.codemap.util.CodeMapPoint;
import com.hdweiss.codemap.view.fragments.FunctionLinkSpan.URLSpanConverter;

public class CodeMapFunction extends LinearLayout {

	private TextView titleView;
	private TextView sourceView;
	
	public CodeMapFunction(Context context) {
		this(context, new CodeMapPoint(0, 0));
	}
	
	public CodeMapFunction(Context context, CodeMapPoint point) {
		super(context);
		setOrientation(LinearLayout.VERTICAL);

		inflate(getContext(), R.layout.map_fragment, this);
		titleView = (TextView) findViewById(R.id.title);
		sourceView = (TextView) findViewById(R.id.source);
		
		init();
		setPosition(point);
	}
	
	private void init() {
		titleView.setText("main()");
		
		sourceView.setLinksClickable(true);
		
		SpannableString spannableString = new SpannableString(Html.fromHtml(
				"void main() { <br>\n int i = <a href=\"function:func\">func</a>;<br>"
						+ "i++; <br>}"));

		spannableString.setSpan(new ForegroundColorSpan(Color.RED), 5, 9, 0);
		
		sourceView.setText(spannableString);

		
		Spannable span = FunctionLinkSpan.replaceAll(
				(Spanned) sourceView.getText(), URLSpan.class,
				new URLSpanConverter(getContext()));
		
		sourceView.setText(span);
		
		sourceView.setMovementMethod(LinkMovementMethod.getInstance());
	}


	public void setPosition(CodeMapPoint point) {
		setX(point.x);
		setY(point.y);
	}

	public void setPositionCenter(CodeMapPoint point) {
		float startX = point.x - (getWidth() / 2);
		float startY = point.y - (getHeight() / 2);
		setX(startX);
		setY(startY);
	}

	public boolean contains(CodeMapPoint point) {
//		Log.d("CodeMap", "point : [" + getX() + " < " + point.x + " < "
//				+ (getX() + getWidth()) + "] [" + getY() + " < " + point.y
//				+ " < " + (getY() + getHeight()) + "]");
		if (point.x >= getX() && point.x <= getX() + getWidth()
				&& point.y >= getY() && point.y <= getY() + getHeight()) {
			Log.d("CodeMap", "match!");
			return true;
		}
		else
			return false;
	}
}
