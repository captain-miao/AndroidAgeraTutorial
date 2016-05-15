package com.github.captain_miao.agera.tutorial.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class ActivityNavigation {

	public static ActivityNavigation from(final Context context) {
		return new ActivityNavigation(context);
	}


    private ActivityNavigation(final Context context) {
   		mContext = context;
   		mIntent = new Intent(Intent.ACTION_VIEW);
   	}

	public ActivityNavigation withExtras(final Bundle extras) {
		if(extras == null) {
            return this;
        }

		mIntent.putExtras(extras);
		return this;
	}

	public ActivityNavigation withFlags(final int flags) {
		mIntent.addFlags(flags);
		return this;
	}

    public boolean toUri(final String uri) {

        if (TextUtils.isEmpty(uri))
            return false;

        return toUri(Uri.parse(uri));
    }


    public boolean toUri(final Uri uri) {
        final Intent intent = mIntent.setData(uri);
        Log.d(TAG, uri.toString());
        try {
            mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



	private final Context mContext;
	private final Intent mIntent;

	private static final String TAG = "ActivityNavigation";
}
