package com.github.captain_miao.agera.tutorial.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;


public abstract class BaseFragment extends Fragment {
    public static final String TAG = "BaseFragment";



    /**
     * 在这里更改Actionbar
     * @param menu
     * @param inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * create fragment instance
     * @param fragmentClazz
     * @param args
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(Class<T> fragmentClazz, Bundle args) {
        T fragment = null;
        try {
            fragment = fragmentClazz.newInstance();
            fragment.setArguments(args);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fragment;
    }


}
