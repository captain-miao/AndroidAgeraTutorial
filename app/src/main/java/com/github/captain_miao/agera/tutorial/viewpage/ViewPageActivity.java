package com.github.captain_miao.agera.tutorial.viewpage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.github.captain_miao.agera.tutorial.R;
import com.github.captain_miao.agera.tutorial.base.BasePagerAdapter;
import com.github.captain_miao.agera.tutorial.databinding.ViewPageItemViewBinding;

import java.util.ArrayList;
import java.util.List;


/**
 * new user guide
 */
public class ViewPageActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPageDotView mWelcomeDotView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.view_page);

        final GuideAdapter guideAdapter = new GuideAdapter(ViewPageActivity.this);
        guideAdapter.addAll(getGuideViewModels());
        mViewPager.setAdapter(guideAdapter);
        mViewPager.addOnPageChangeListener(this);

        mWelcomeDotView = (ViewPageDotView) findViewById(R.id.dot_view);
        mWelcomeDotView.setNumOfCircles(guideAdapter.getCount(), getResources().getDimensionPixelSize(R.dimen.height_very_small));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mWelcomeDotView.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        mWelcomeDotView.onPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        mWelcomeDotView.onPageScrollStateChanged(state);
    }


    private class GuideAdapter extends BasePagerAdapter<GuideViewModel> {

        public GuideAdapter(Context c) {
            super(c);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            GuideViewModel model = getItem(position);

            ViewPageItemViewBinding frgGuideBinding
                    = ViewPageItemViewBinding.inflate(mInflater, container, false);
            frgGuideBinding.setViewModel(model);
            container.addView(frgGuideBinding.getRoot());
            return frgGuideBinding.getRoot();

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if(object instanceof View) {
                container.removeView((View) object);
            }
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    @SuppressWarnings("deprecation")
    private List<GuideViewModel> getGuideViewModels() {
        return new ArrayList<GuideViewModel>() {{
            add(new GuideViewModel(getResources().getColor(R.color.guide_bg_color_1), getString(R.string.guide_title_1),
                    getString(R.string.guide_description_1), R.drawable.guide_1));

            add(new GuideViewModel(getResources().getColor(R.color.guide_bg_color_2), getString(R.string.guide_title_2),
                    getString(R.string.guide_description_2), R.drawable.guide_2));

            add(new GuideViewModel(getResources().getColor(R.color.guide_bg_color_3), getString(R.string.guide_title_3),
                    getString(R.string.guide_description_3), R.drawable.guide_3));
        }};
    }
}
