package com.github.captain_miao.agera.tutorial.observable;


import android.view.View;

import com.google.android.agera.BaseObservable;

/**
 * @author YanLu
 * @since 16/5/15
 */
public abstract class OnClickObservable extends BaseObservable {
    public abstract void onClick(View view);
}
