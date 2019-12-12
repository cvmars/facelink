package com.youxiake.util;

import android.app.Activity;
import android.content.Context;

import com.youxiake.R;

public class AnimationHelper {



	/***
	 * zoom 动画 页面消失
	 *
	 * @param context
	 */
	public static void activityZoomAnimation(Context context) {
		((Activity) context).overridePendingTransition(R.anim.start_page_enter,
				R.anim.start_page_exit);
	}

}
