package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 16/8/15.
 */
public class GreetingViewHelper implements CortanaInterface {

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    int outerRadius;
    int innerRadiusWoBorder;
    int borderWidth = 0;
    int outerBorderWidth = 0;

    int centerX = 0;
    int centerY = 0;

    int OUTER_BORDER_WIDTH = 0;

    int OUTER_BORDER_MAX_WIDTH = 0;
    int OUTER_BORDER_MID_WIDTH = 0;
    int OUTER_BORDER_MIN_WIDTH = 0;

    int OUTER_CIRCLE_MAX_RADIUS = 0;
    int OUTER_CIRCLE_MID_RADIUS = 0;
    int OUTER_CIRCLE_MIN_RADIUS = 0;

    int INNER_BORDER_MAX_WIDTH = 0;
    int INNER_BORDER_MID_WIDTH = 0;
    int INNER_BORDER_MIN_WIDTH = 0;

    int INNER_CIRCLE_MAX_RADIUS = 0;
    int INNER_CIRCLE_MID_RADIUS = 0;
    int INNER_CIRCLE_MIN_RADIUS = 0;

    ObjectAnimator innerCircleBorderAnimator;
    ObjectAnimator innerCircleRadiusAnimator;
    ObjectAnimator outerCircleBorderAnimator;
    ObjectAnimator outerCircleRadiusAnimator;

    View animatingView;

    @Override
    public void initializePaint() {

        innerCirclePaint = new Paint();
        outerCirclePaint = new Paint();

        innerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setStyle(Paint.Style.STROKE);

        innerCirclePaint.setColor(CortanaType.INNER_CIRCLE_COLOR);
        outerCirclePaint.setColor(CortanaType.OUTER_CIRCLE_COLOR);

        innerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setAntiAlias(true);

        innerCirclePaint.setStrokeWidth(borderWidth);
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {

        INNER_BORDER_MAX_WIDTH = diameter * 5 / 100;
        INNER_BORDER_MID_WIDTH = (diameter / 2) * 50 / 100;
        INNER_BORDER_MIN_WIDTH = (diameter / 2) * 10 / 100;

        INNER_CIRCLE_MAX_RADIUS = diameter / 2;
        INNER_CIRCLE_MID_RADIUS = (diameter / 2) * 50 / 100;
        INNER_CIRCLE_MIN_RADIUS = (diameter / 2) * 10 / 100;

        OUTER_CIRCLE_MAX_RADIUS = (diameter/2);
        OUTER_CIRCLE_MID_RADIUS = (diameter/2);
        OUTER_CIRCLE_MIN_RADIUS = (diameter/2) * 10 / 100;

        OUTER_BORDER_MAX_WIDTH = diameter * 20 / 100;
        OUTER_BORDER_MID_WIDTH = (diameter/2);
        OUTER_BORDER_MIN_WIDTH = (diameter/2) * 10 / 100;

        OUTER_BORDER_WIDTH = diameter * 25 / 100;

        this.centerX = centerX;
        this.centerY = centerY;

        borderWidth = INNER_BORDER_MIN_WIDTH;
        innerCirclePaint.setStrokeWidth(borderWidth);

        outerBorderWidth = OUTER_BORDER_MIN_WIDTH;
        outerCirclePaint.setStrokeWidth(OUTER_BORDER_WIDTH);

        innerRadiusWoBorder = INNER_BORDER_MAX_WIDTH;
        outerRadius = OUTER_CIRCLE_MIN_RADIUS;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, outerRadius - (outerBorderWidth / 2), outerCirclePaint);

        canvas.drawCircle(centerX, centerY, innerRadiusWoBorder - (borderWidth / 2), innerCirclePaint);
    }

    @Override
    public void startAnimation(final View view) {

        if(view == null)
            return;

        animatingView = view;

        innerCircleBorderAnimator = ObjectAnimator.ofInt(this, "innerCircleBorder", INNER_BORDER_MIN_WIDTH, INNER_BORDER_MID_WIDTH, INNER_BORDER_MAX_WIDTH);
        innerCircleBorderAnimator.setStartDelay(0);
        innerCircleBorderAnimator.setDuration(1000);
        innerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerCircleBorderAnimator.setInterpolator(new LinearInterpolator());
        innerCircleBorderAnimator.start();

        innerCircleRadiusAnimator = ObjectAnimator.ofInt(this, "innerCircleRadius", INNER_CIRCLE_MIN_RADIUS,INNER_CIRCLE_MID_RADIUS, INNER_CIRCLE_MAX_RADIUS);
        innerCircleRadiusAnimator.setStartDelay(0);
        innerCircleRadiusAnimator.setDuration(1000);
        innerCircleRadiusAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerCircleRadiusAnimator.setInterpolator(new LinearInterpolator());
        innerCircleRadiusAnimator.start();

        outerCircleBorderAnimator = ObjectAnimator.ofInt(this, "outerCircleBorder", OUTER_BORDER_MIN_WIDTH,OUTER_BORDER_MID_WIDTH, OUTER_BORDER_MAX_WIDTH);
        outerCircleBorderAnimator.setStartDelay(0);
        outerCircleBorderAnimator.setDuration(1000);
        outerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerCircleBorderAnimator.setInterpolator(new LinearInterpolator());
        outerCircleBorderAnimator.start();

        outerCircleRadiusAnimator = ObjectAnimator.ofInt(this, "outerCircleRadius", OUTER_CIRCLE_MIN_RADIUS,OUTER_CIRCLE_MID_RADIUS, OUTER_CIRCLE_MAX_RADIUS);
        outerCircleRadiusAnimator.setStartDelay(0);
        outerCircleRadiusAnimator.setDuration(1000);
        outerCircleRadiusAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerCircleRadiusAnimator.setInterpolator(new LinearInterpolator());
        outerCircleRadiusAnimator.start();
    }

    @Override
    public void stopAnimation() {

        if(innerCircleBorderAnimator != null) {
            innerCircleBorderAnimator.cancel();
            innerCircleBorderAnimator = null;
        }

        if(innerCircleRadiusAnimator != null) {
            innerCircleRadiusAnimator.cancel();
            innerCircleRadiusAnimator = null;
        }

        if(outerCircleBorderAnimator != null) {
            outerCircleBorderAnimator.cancel();
            outerCircleBorderAnimator = null;
        }

        if(outerCircleRadiusAnimator != null) {
            outerCircleRadiusAnimator.cancel();
            outerCircleRadiusAnimator = null;
        }

    }

    void setOuterCircleBorder(int border) {

        outerBorderWidth = border;
        outerCirclePaint.setStrokeWidth(outerBorderWidth);
        animatingView.invalidate();
    }

    void setOuterCircleRadius(int radius) {

        outerRadius = radius;
        animatingView.invalidate();
    }

    void setInnerCircleBorder(int border) {

        borderWidth = border;
        innerCirclePaint.setStrokeWidth(borderWidth);
        animatingView.invalidate();
    }

    void setInnerCircleRadius(int radius) {

        innerRadiusWoBorder = radius;
        animatingView.invalidate();
    }
}
