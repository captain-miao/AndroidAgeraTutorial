package com.github.captain_miao.agera.tutorial.helper;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * @author YanLu
 * @since 16/4/27
 */
public class ViewVisibleBindingAdapter {

    @BindingAdapter("isGone")
    public static void setIsGone(View view, boolean hide){
      view.setVisibility(hide ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter("isInvisible")
    public static void setIsInvisible(View view, boolean hide){
      view.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
    }

}
