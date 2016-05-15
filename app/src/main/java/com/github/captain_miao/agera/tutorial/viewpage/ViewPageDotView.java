package com.github.captain_miao.agera.tutorial.viewpage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 */
public class ViewPageDotView extends View {
    public static int DEFAULT_RADIUS = 8;
    public static int DEFAULT_STROKE_COLOR = Color.parseColor("#999999");
    public static int DEFAULT_FILL_COLOR = Color.parseColor("#ffffff");

    private int mNumOfCircles = 0;
    private int mScreenWidth;
    private float mRadius = DEFAULT_RADIUS;
    private float mDistance;
    private float[] mXPositions;
    private float mYPositions;

    private float mLeftOrRightSpace;
    private float mAllDotsDomainLength;
    private float mOnceMovedTotalDistance;

    private Paint mPaintStroke;
    private int mStrokeColor = DEFAULT_STROKE_COLOR;
    private Paint mPaintFill;
    private int mFillColor = DEFAULT_FILL_COLOR;

    private float mScrollRatio; // current drag ratio

    private int mCurrentIndex;
    private int mState;

    public ViewPageDotView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ViewPageDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initLengthRelatedVariables();
    }

    public void setNumOfCircles(int numOfCircles){
        setNumOfCircles(numOfCircles, DEFAULT_RADIUS);
    }

    public void setNumOfCircles(int numOfCircles, float radius) {
        if (numOfCircles <= 1) {
            this.setVisibility(View.INVISIBLE);
            return;
        }
        this.mRadius = radius;
        initLengthRelatedVariables();
        mNumOfCircles = numOfCircles;
        mAllDotsDomainLength = 2 * mRadius * mNumOfCircles + mDistance * (mNumOfCircles - 1);
        mLeftOrRightSpace = (mScreenWidth - mAllDotsDomainLength) / 2.0f;
        mXPositions = new float[mNumOfCircles];
        for (int i = 0; i < mNumOfCircles; i++) {
            mXPositions[i] = mLeftOrRightSpace + mRadius * (i * 2 + 1) + mDistance * i;
        }
    }


    /**
     * 设置画笔相关变量
     */
    private void initPaint() {
        mPaintStroke = new Paint();
        mPaintStroke.setColor(mStrokeColor);
        mPaintStroke.setAntiAlias(true);
        mPaintFill = new Paint();
        mPaintFill.setColor(mFillColor);
        mPaintFill.setAntiAlias(true);
    }

    /**
     * 设置距离相关变量
     */
    private void initLengthRelatedVariables() {
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mDistance = mRadius * 2;
        mYPositions = mRadius;
        mOnceMovedTotalDistance = mRadius * 2 + mDistance;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawStrokeCircle(canvas);
        drawFillCircle(canvas);
    }

    /**
     * 绘制静态圆点
     *
     * @param canvas
     */
    private void drawStrokeCircle(Canvas canvas) {
        if (mNumOfCircles <= 0) {
            return;
        }
        for (int i = 0; i < mNumOfCircles; i++) {
            canvas.drawCircle(mXPositions[i], mYPositions, mRadius, mPaintStroke);
        }
    }

    /**
     * 绘制动态圆点
     *
     * @param canvas
     */
    private void drawFillCircle(Canvas canvas) {
        if (mNumOfCircles <= 0) {
            return;
        }
        float offset = mLeftOrRightSpace + mRadius + mCurrentIndex * mOnceMovedTotalDistance + mScrollRatio * mOnceMovedTotalDistance;
        if (offset < mLeftOrRightSpace + mRadius) {
            offset = mLeftOrRightSpace + mRadius;
        }
        if (offset > (mLeftOrRightSpace + mRadius) + (mNumOfCircles - 1) * mOnceMovedTotalDistance) {
            offset = mLeftOrRightSpace + mRadius + (mNumOfCircles - 1) * mOnceMovedTotalDistance;
        }
        canvas.drawCircle(offset, mYPositions, mRadius, mPaintFill);
    }

    public void onPageScrolled(int canSeePageIndex, float v, int i2) {
        mScrollRatio = v;
        mCurrentIndex = canSeePageIndex;
        invalidate();
    }

    public void onPageSelected(int currentIndex) {
        if (mState == ViewPager.SCROLL_STATE_IDLE) {
            mCurrentIndex = currentIndex;
            invalidate();
        }
    }

    public void onPageScrollStateChanged(int i) {
        mState = i;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        ViewPageDotView.SavedState savedState = (ViewPageDotView.SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mCurrentIndex = savedState.currentPage;
        this.requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        ViewPageDotView.SavedState savedState = new ViewPageDotView.SavedState(superState);
        savedState.currentPage = this.mCurrentIndex;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPage;
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public ViewPageDotView.SavedState createFromParcel(Parcel in) {
                return new ViewPageDotView.SavedState(in);
            }

            public ViewPageDotView.SavedState[] newArray(int size) {
                return new ViewPageDotView.SavedState[size];
            }
        };

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.currentPage = in.readInt();
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(this.currentPage);
        }
    }
}
