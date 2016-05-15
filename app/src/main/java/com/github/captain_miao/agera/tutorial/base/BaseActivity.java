package com.github.captain_miao.agera.tutorial.base;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;



public abstract class BaseActivity extends AppCompatActivity {
    private static String TAG = BaseActivity.class.getSimpleName();

    public abstract void init(Bundle savedInstanceState);

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);


        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }



    /**
     * replace fragment
     */
    protected void initFragment(int containerViewId, BaseFragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment, tag).commitAllowingStateLoss();
    }

    /**
     * add fragment
     */
    protected void addFragment(int containerViewId, BaseFragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment).commitAllowingStateLoss();
    }

    protected <F extends BaseFragment> void addFragment(int containerViewId, Class<F> fragmentClazz) {
        F frg = createFragment(fragmentClazz, getIntent().getExtras());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, frg).commitAllowingStateLoss();
    }

    /**
     * create fragment instance
     * @param fragmentClazz
     * @param args
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T createFragment(Class<T> fragmentClazz, Bundle args) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
