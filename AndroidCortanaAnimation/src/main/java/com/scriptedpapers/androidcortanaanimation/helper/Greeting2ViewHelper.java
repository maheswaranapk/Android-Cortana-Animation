package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 16/8/15.
 */
public class Greeting2ViewHelper implements CortanaInterface, Animator.AnimatorListener {

    private static final int ANIM_DURATION = 1000;

    Paint mInnerCirclePaint;
    Paint mOuterCirclePaint;

    float mBorderWidth = 0;
    float mOuterBorderWidth = 0;

    float MAX_WIDTH = 0;

    int diameter;

    RectF mInnerRect, mOuterRect;

    View mAnimatingView;

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

    final int SWEEP_START_ANGLE = 0;
    final int SWEEP_END_ANGLE = 360;

    int INNER_MAX_STROKE_WIDTH;

    int angle = 0;

    int percent = 0;

    ObjectAnimator outerCircleRotateAnimator;

    @Override
    public void initializePaint() {
        mInnerCirclePaint = new Paint();
        mOuterCirclePaint = new Paint();

        mInnerCirclePaint.setStyle(Paint.Style.STROKE);
        mOuterCirclePaint.setStyle(Paint.Style.STROKE);

        mInnerCirclePaint.setStrokeCap(Paint.Cap.BUTT);
        mOuterCirclePaint.setStrokeCap(Paint.Cap.BUTT);

        mInnerCirclePaint.setColor(CortanaType.OUTER_CIRCLE_COLOR);
        mOuterCirclePaint.setColor(CortanaType.INNER_CIRCLE_COLOR);

        mInnerCirclePaint.setAntiAlias(true);
        mOuterCirclePaint.setAntiAlias(true);
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {

        this.diameter = diameter;

        mBorderWidth = diameter *5 / 100;

        percent = 1;

         MAX_WIDTH = (float) ((percent * diameter/2) - (1.5 * mBorderWidth));

        mOuterBorderWidth = MAX_WIDTH;

        mInnerCirclePaint.setStrokeWidth(mBorderWidth);
        mOuterCirclePaint.setStrokeWidth(mOuterBorderWidth);

        mInnerRect = new RectF();
        mOuterRect = new RectF();

        innerLeft = (3 * mBorderWidth / 2) + (mOuterBorderWidth/2);
        innerTop = (3 * mBorderWidth / 2)  + (mOuterBorderWidth/2);
        innerRight = diameter - (3 * mBorderWidth / 2)  - (mOuterBorderWidth/2);
        innerBottom = diameter - (3 * mBorderWidth / 2)  - (mOuterBorderWidth/2);

        outerLeft = mBorderWidth / 2;
        outerTop = mBorderWidth / 2;
        outerRight = diameter - (mBorderWidth / 2);
        outerBottom = diameter - (mBorderWidth / 2);

        mInnerRect.set(innerLeft, innerTop, innerRight, innerBottom);
        mOuterRect.set(outerLeft, outerTop, outerRight, outerBottom);
    }

    @Override
    public void onDraw(Canvas canvas) {

            canvas.drawArc(mOuterRect, 270, angle, false, mInnerCirclePaint);
            canvas.drawArc(mInnerRect, 270, angle, false, mOuterCirclePaint);

    }

    @Override
    public void startAnimation(View view) {

        if (view == null)
            return;

        intializeAnimation();

        mAnimatingView = view;

        if (mAnimator == null) {

            outerCircleRotateAnimator = ObjectAnimator.ofInt(this, "outerRotate",
                    SWEEP_START_ANGLE, SWEEP_END_ANGLE);
            outerCircleRotateAnimator.setDuration(ANIM_DURATION);

            ObjectAnimator innerCircleRotateAnimator = ObjectAnimator.ofFloat(this, "innerRotate",
                    1, 0.25F);
            innerCircleRotateAnimator.setDuration(ANIM_DURATION);


            mAnimator = new AnimatorSet();
            mAnimator.play(outerCircleRotateAnimator).with(innerCircleRotateAnimator)
                    /*.with(outerCircleRotateReverseAnimator).with(innerCircleRotateReverseAnimator)*/;
            mAnimator.addListener(this);
        }

        mAnimator.start();
    }

    void setInnerRotate(float percent) {


        mOuterBorderWidth = (float) ((percent * diameter/2) - (1.5 * mBorderWidth));

        mOuterCirclePaint.setStrokeWidth(mOuterBorderWidth);

        innerLeft = (3 * mBorderWidth / 2) + (mOuterBorderWidth/2);
        innerTop = (3 * mBorderWidth / 2)  + (mOuterBorderWidth/2);
        innerRight = diameter - (3 * mBorderWidth / 2)  - (mOuterBorderWidth/2);
        innerBottom = diameter - (3 * mBorderWidth / 2)  - (mOuterBorderWidth/2);

        outerLeft = mBorderWidth / 2;
        outerTop = mBorderWidth / 2;
        outerRight = diameter - (mBorderWidth / 2);
        outerBottom = diameter - (mBorderWidth / 2);

        mInnerRect.set(innerLeft, innerTop, innerRight, innerBottom);
        mOuterRect.set(outerLeft, outerTop, outerRight, outerBottom);
    }

    public void setOuterRotate(int angle) {

        this.angle = angle;

        int percentage = angle / SWEEP_END_ANGLE * 100;
        int reversePercentage = (100 - percentage);



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

        MAX_WIDTH = (float) ((percent * diameter/2) - (1.5 * mBorderWidth));





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

        mInnerRect.set(innerLeft, innerTop, innerRight, innerBottom);
        mOuterRect.set(outerLeft, outerTop, outerRight, outerBottom);

        if(mAnimatingView != null)
            mAnimatingView.invalidate();
    }
}
