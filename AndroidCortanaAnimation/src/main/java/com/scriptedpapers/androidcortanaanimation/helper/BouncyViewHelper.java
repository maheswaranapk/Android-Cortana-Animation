package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by akhil on 14/8/15.
 */
public class BouncyViewHelper implements CortanaInterface, Animator.AnimatorListener {
    private static final int ANIM_DURATION = 1200;

    Paint mInnerCirclePaint;
    Paint mOuterCirclePaint;

    RectF mInnerRect, mOuterRect;

    View mAnimatingView;
    private ObjectAnimator mAnimator;
    private float mBorderWidth,mOuterSize, mInnerSize, mDiameter, mRotation = 0,
            mSweepAngle = 91, mOuterRectTop, mOuterRectBottom, mInnerCircleRadius,
            mJumpHeight, mInnerRectTop, mInnerRectBottom;

    @Override
    public void initializePaint() {
        mInnerCirclePaint = new Paint();
        mOuterCirclePaint = new Paint();

        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);

        mInnerCirclePaint.setColor(CortanaType.THINK_INNER_CIRCLE_COLOR);
        mOuterCirclePaint.setColor(CortanaType.THINK_OUTER_CIRCLE_COLOR);

        mInnerCirclePaint.setAntiAlias(true);
        mOuterCirclePaint.setAntiAlias(true);
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {
        mOuterSize = diameter;

        mBorderWidth = mOuterSize / 10;
        mJumpHeight = mOuterSize / 3;

        mInnerCirclePaint.setStrokeWidth(mBorderWidth);
        mOuterCirclePaint.setStrokeWidth(mBorderWidth);

        mDiameter = mOuterSize - 5 * mBorderWidth;
        mInnerCircleRadius = mDiameter/2;

        mOuterSize -= (3 * mBorderWidth) / 2;
        mInnerSize = mOuterSize - mBorderWidth;

        mInnerRectTop = (5 * mBorderWidth) / 2;
        mInnerRectBottom = mInnerSize;
        mOuterRectTop = (3 * mBorderWidth) / 2;
        mOuterRectBottom = mOuterSize;

        mInnerRect = new RectF(mOuterRectTop, mOuterRectTop, mOuterRectBottom, mOuterRectBottom);
        mOuterRect = new RectF(mInnerRectTop, mInnerRectTop , mInnerRectBottom, mInnerRectBottom);

    }

    @Override
    public void onDraw(Canvas canvas) {
        float startAngle = mRotation;
        canvas.drawCircle(mInnerRect.centerX(), mInnerRect.centerY(),
                mDiameter / 2, mInnerCirclePaint);
        for (int i = 0 ; i < 4 ; i++) {
            canvas.drawArc(mOuterRect, startAngle, mSweepAngle, false, mOuterCirclePaint);
            startAngle += 90;
        }
    }

    @Override
    public void startAnimation(View view) {
        if(view == null)
            return;

        mAnimatingView = view;
        if (mAnimator == null) {
            PropertyValuesHolder jumpValueHolder = PropertyValuesHolder.ofFloat("jumpHeight",
                    0, -mJumpHeight, -mJumpHeight, -mJumpHeight, 0);
            PropertyValuesHolder rectSizeHolder = PropertyValuesHolder.ofFloat("rectSize",
                    0, mBorderWidth/2, mBorderWidth/2, 0);
            PropertyValuesHolder diameterHolder = PropertyValuesHolder.ofFloat("diameter",
                    mDiameter, mDiameter - mBorderWidth, mDiameter - mBorderWidth, mDiameter);
            PropertyValuesHolder sweepAngleHolder = PropertyValuesHolder.ofFloat("sweepAngle",
                    91, 50, 50, 91);
            PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("rotation",
                    300);
            mAnimator = ObjectAnimator.ofPropertyValuesHolder(this,
                    rectSizeHolder, sweepAngleHolder, diameterHolder, rotationHolder, jumpValueHolder);
            mAnimator.setDuration(ANIM_DURATION);
            mAnimator.setInterpolator(new OvershootInterpolator());
            mAnimator.addListener(this);
        }
        mAnimator.setStartDelay(200);
        mAnimator.start();
    }

    public void setRectSize(float changeValue) {
        mOuterRect.set(mOuterRectTop - changeValue, mOuterRectTop - changeValue + mJumpHeight,
                mOuterRectBottom + changeValue, mOuterRectBottom + changeValue + mJumpHeight);
        mInnerRect.set(mInnerRectTop, mInnerRectTop + mJumpHeight,
                mInnerRectBottom, mInnerRectBottom + mJumpHeight);

    }

    public void setSweepAngle(float sweepAngle) {
        this.mSweepAngle = sweepAngle;
    }

    public void setDiameter(float diameter) {
        this.mDiameter = diameter;
    }

    public void setRotation(float rotation) {
        this.mRotation = rotation % 360;
        mAnimatingView.invalidate();
    }

    public void setJumpHeight(float jumpHeight) {
        this.mJumpHeight = jumpHeight;
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
