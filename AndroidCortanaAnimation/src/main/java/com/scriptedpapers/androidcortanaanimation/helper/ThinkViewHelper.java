package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by akhil on 14/8/15.
 */
public class ThinkViewHelper implements CortanaInterface, Animator.AnimatorListener {
    private static final int ANIM_DURATION = 400;

    Paint mInnerCirclePaint;
    Paint mOuterCirclePaint;

    int mBorderWidth = 0;

    RectF mInnerRect, mOuterRect;

    View mAnimatingView;
    float mSizeInner, mSizeOuter;
    boolean mIsHalfDone;

    AnimatorSet mAnimator;

    @Override
    public void initializePaint() {
        mInnerCirclePaint = new Paint();
        mOuterCirclePaint = new Paint();

        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);

        mOuterCirclePaint.setStrokeCap(Paint.Cap.ROUND);

        mInnerCirclePaint.setColor(CortanaType.OUTER_CIRCLE_COLOR);
        mOuterCirclePaint.setColor(CortanaType.INNER_CIRCLE_COLOR);

        mInnerCirclePaint.setAntiAlias(true);
        mOuterCirclePaint.setAntiAlias(true);
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {

        mBorderWidth = diameter / 10;

        mInnerCirclePaint.setStrokeWidth(mBorderWidth);
        mOuterCirclePaint.setStrokeWidth(mBorderWidth);

        mInnerRect = new RectF();
        mOuterRect = new RectF();

        mSizeOuter = diameter;
        mSizeOuter -= mBorderWidth / 2;
        mSizeInner = mSizeOuter - mBorderWidth;

        mInnerRect.set((3 * mBorderWidth)/2, (3 * mBorderWidth)/2, mSizeInner, mSizeInner);
        mOuterRect.set(mBorderWidth /2, mBorderWidth /2, mSizeOuter, mSizeOuter);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (mIsHalfDone) {
            canvas.drawArc(mOuterRect, 270, 180, false, mOuterCirclePaint);
            canvas.drawOval(mInnerRect, mInnerCirclePaint);
            canvas.drawArc(mOuterRect, 90, 180, false, mOuterCirclePaint);
        } else {
            canvas.drawArc(mOuterRect, 90, 180, false, mOuterCirclePaint);
            canvas.drawOval(mInnerRect, mInnerCirclePaint);
            canvas.drawArc(mOuterRect, 270, 180, false, mOuterCirclePaint);
        }
    }

    @Override
    public void startAnimation(View view) {
        if(view == null)
            return;

        mAnimatingView = view;
        if (mAnimator == null) {
            ObjectAnimator firstCircleAnim = ObjectAnimator.ofFloat(this, "rectSizeFirst",
                    0, (mSizeOuter / 2) - (mBorderWidth * 0.4f), 0);
            firstCircleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
            firstCircleAnim.setDuration(ANIM_DURATION);

            ObjectAnimator secondCircleStart = ObjectAnimator.ofFloat(this, "rectSizeSecond",
                    0, (mSizeInner / 2) - (mBorderWidth));
            secondCircleStart.setInterpolator(new AccelerateInterpolator());
            secondCircleStart.setDuration(ANIM_DURATION);

            ObjectAnimator secondCircleEnd = ObjectAnimator.ofFloat(this, "rectSizeSecond",
                    (mSizeInner / 2) - (mBorderWidth), 0);
            secondCircleEnd.setInterpolator(new DecelerateInterpolator());
            secondCircleEnd.setDuration(ANIM_DURATION);

            mAnimator = new AnimatorSet();
            mAnimator.play(firstCircleAnim).with(secondCircleEnd).after(secondCircleStart);
            mAnimator.addListener(this);
        }
        mAnimator.setStartDelay(200);
        mAnimator.start();
    }

    public void setRectSizeFirst(float baseValue) {

        if (baseValue == (mSizeOuter / 2)-(mBorderWidth * 0.4)) {
            mIsHalfDone = true;
        } else if (baseValue == 0) {
            mIsHalfDone = false;
        }

        mOuterRect.set(mBorderWidth /2 + baseValue, mBorderWidth / 2,
                mSizeOuter - baseValue, mSizeOuter);
    }

    public void setRectSizeSecond(float baseValue) {
        mInnerRect.set((3 * mBorderWidth) / 2 + baseValue, (3 * mBorderWidth) / 2,
                mSizeInner - baseValue, mSizeInner);
        mAnimatingView.invalidate();
    }

    @Override
    public void stopAnimation() {
        mAnimator.end();
    }

    @Override
    public void onAnimationStart(Animator animation) {
        
    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mAnimator.setStartDelay(200);
        mAnimator.start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
