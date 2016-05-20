package com.github.captain_miao.agera.tutorial.listener;

import com.google.android.agera.BaseObservable;

/**
 * @author YanLu
 * @since 16/4/24
 */
public class SimpleObservable extends BaseObservable{
    public void update() {
        dispatchUpdate();
    }
}
