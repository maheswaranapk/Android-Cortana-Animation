package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 16/8/15.
 */
public class AshamedViewHelper implements CortanaInterface, Animator.AnimatorListener {

    private static final int ANIM_DURATION = 1000;

    Paint mInnerCirclePaint;
    Paint mOuterCirclePaint;

    int mBorderWidth = 0;

    RectF mInnerRect, mOuterRect;

    View mAnimatingView;
    float mSizeInner, mSizeOuter;
    boolean mIsHalfDone;
    boolean mIsInitHalfDone;

    float innerLeft;
    float innerTop;
    float innerRight;
    float innerBottom;

    float outerLeft;
    float outerTop;
    float outerRight;
    float outerBottom;

    float innerMiddle;
    float outerMiddle;

    AnimatorSet mAnimator;

    float lastBaseValue;
    float lastBaseValue2;

    float innerRotate1;
    float innerRotate2;

    float outerRotate1;
    float outerRotate2;

    ObjectAnimator outerCircleRotateAnimator;
    ObjectAnimator innerCircleRotateAnimator;
    ObjectAnimator outerCircleRotateReverseAnimator;
    ObjectAnimator innerCircleRotateReverseAnimator;

    @Override
    public void initializePaint() {
        mInnerCirclePaint = new Paint();
        mOuterCirclePaint = new Paint();

        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);

        mOuterCirclePaint.setStrokeCap(Paint.Cap.SQUARE);

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
        mSizeInner = (diameter - ((3 * mBorderWidth) / 2));

        innerLeft = (3 * mBorderWidth) / 2;
        innerTop = (3 * mBorderWidth) / 2;
        innerRight = (diameter - ((3 * mBorderWidth) / 2));
        innerBottom = (diameter - ((3 * mBorderWidth) / 2));

        outerLeft = mBorderWidth / 2;
        outerTop = mBorderWidth / 2;
        outerRight = diameter - (mBorderWidth / 2);
        outerBottom = diameter - (mBorderWidth / 2);

        innerMiddle = (innerBottom - innerTop) / 2;
        outerMiddle = (outerBottom - outerTop) / 2;

        innerRotate1 = innerMiddle - (2 * mBorderWidth);
        innerRotate2 = innerMiddle - mBorderWidth;

        outerRotate1 = outerMiddle - (3 * mBorderWidth);
        outerRotate2 = outerMiddle - (2 * mBorderWidth);

        mInnerRect.set(innerLeft, innerTop, innerRight, innerBottom);
        mOuterRect.set(outerLeft, outerTop, outerRight, outerBottom);
    }

    @Override
    public void onDraw(Canvas canvas) {

        if (mIsHalfDone) {
            canvas.drawArc(mOuterRect, 180, 180, false, mOuterCirclePaint);
            canvas.drawOval(mInnerRect, mInnerCirclePaint);
            canvas.drawArc(mOuterRect, 0, 180, false, mOuterCirclePaint);
        } else {
            canvas.drawArc(mOuterRect, 0, 180, false, mOuterCirclePaint);
            canvas.drawOval(mInnerRect, mInnerCirclePaint);
            canvas.drawArc(mOuterRect, 180, 180, false, mOuterCirclePaint);
        }
    }

    @Override
    public void startAnimation(View view) {

        if (view == null)
            return;

        intializeAnimation();

        mAnimatingView = view;

        if (mAnimator == null) {

            outerCircleRotateAnimator = ObjectAnimator.ofFloat(this, "outerRotate",
                    0, outerMiddle, 0, outerRotate1);
            outerCircleRotateAnimator.setDuration(ANIM_DURATION);

            innerCircleRotateAnimator = ObjectAnimator.ofFloat(this, "innerRotate",
                    0, innerRotate1);
            innerCircleRotateAnimator.setDuration(ANIM_DURATION);

            outerCircleRotateReverseAnimator = ObjectAnimator.ofFloat(this, "outerRotate",
                    outerRotate1, outerRotate2, 0);
            outerCircleRotateReverseAnimator.setStartDelay(ANIM_DURATION);
            outerCircleRotateReverseAnimator.setInterpolator(new AccelerateInterpolator());
            outerCircleRotateReverseAnimator.setDuration(ANIM_DURATION);

            innerCircleRotateReverseAnimator = ObjectAnimator.ofFloat(this, "innerRotate",
                    innerRotate1, innerRotate2, 0);
            innerCircleRotateReverseAnimator.setStartDelay(ANIM_DURATION);
            innerCircleRotateReverseAnimator.setInterpolator(new AccelerateInterpolator());
            innerCircleRotateReverseAnimator.setDuration(ANIM_DURATION);

            mAnimator = new AnimatorSet();
            mAnimator.play(outerCircleRotateAnimator).with(innerCircleRotateAnimator)
                    .with(outerCircleRotateReverseAnimator).with(innerCircleRotateReverseAnimator);
            mAnimator.addListener(this);
        }

        mAnimator.start();
    }

    public void setOuterRotate(float baseValue) {

        if (lastBaseValue != baseValue && mIsInitHalfDone) {

            if (lastBaseValue < baseValue) {
                mIsHalfDone = false;
            }
        }

        if (!mIsInitHalfDone && lastBaseValue != -1) {

            if (lastBaseValue > baseValue) {
                mIsHalfDone = true;
                mIsInitHalfDone = true;
            }
        }

        mOuterRect.set(outerLeft, outerTop + baseValue,
                outerRight, outerRight - baseValue);
        mAnimatingView.invalidate();

        lastBaseValue = baseValue;
        lastBaseValue2 = baseValue;
    }

    public void setInnerRotate(float baseValue) {

        mInnerRect.set(innerLeft, innerTop + baseValue,
                innerRight, innerBottom - baseValue);

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

        intializeAnimation();

        mAnimator.setStartDelay(200);
        mAnimator.start();
    }

    @Override
    public void onAnimationCancel(Animator animation) {
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
    }

    void intializeAnimation() {

        mIsHalfDone = false;
        lastBaseValue = -1;

        mIsInitHalfDone = false;

        mInnerRect.set(innerLeft, innerTop, innerRight, innerBottom);
        mOuterRect.set(outerLeft, outerTop, outerRight, outerBottom);

        if(mAnimatingView != null)
            mAnimatingView.invalidate();
    }
}
