package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 13/8/15.
 */
public class CoolViewHelper implements CortanaInterface {

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    int outerRadius;
    int borderWidth = 0;
    int outerBorderWidth = 0;

    int centerX = 0;
    int centerY = 0;

    int OUTER_BORDER_WIDTH = 0;

    int OUTER_CIRCLE_MAX_RADIUS = 0;
    int OUTER_CIRCLE_MIN_RADIUS = 0;

    int INNER_BORDER_WIDTH;

    int INNER_RADIUS_MAX = 0;
    int INNER_RADIUS_MIN = 0;

    ObjectAnimator innerCircleBorderAnimator;
    ObjectAnimator outerCircleBorderAnimator;

    int innerRadius;

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

        this.centerX = centerX;
        this.centerY = centerY;



        INNER_RADIUS_MIN = diameter * 25 / 100;
        INNER_RADIUS_MAX= diameter * 40 / 100;

        INNER_BORDER_WIDTH = diameter * 5 / 100;

        OUTER_BORDER_WIDTH = (diameter * 50 / 100) - INNER_RADIUS_MIN;

        OUTER_CIRCLE_MAX_RADIUS = diameter / 2;
        OUTER_CIRCLE_MIN_RADIUS = INNER_RADIUS_MAX;

        outerRadius = OUTER_CIRCLE_MIN_RADIUS;
        innerRadius = INNER_RADIUS_MIN;
        outerBorderWidth = OUTER_BORDER_WIDTH;

        innerCirclePaint.setStrokeWidth(INNER_BORDER_WIDTH);
        outerCirclePaint.setStrokeWidth(outerBorderWidth);

    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, outerRadius - (outerBorderWidth / 2), outerCirclePaint);

        canvas.drawCircle(centerX, centerY, innerRadius - (INNER_BORDER_WIDTH / 2), innerCirclePaint);
    }

    @Override
    public void startAnimation(final View view) {

        if(view == null)
            return;

        animatingView = view;

        innerCircleBorderAnimator = ObjectAnimator.ofInt(this, "innerCircleRadius", INNER_RADIUS_MIN, INNER_RADIUS_MAX);
        innerCircleBorderAnimator.setStartDelay(0);
        innerCircleBorderAnimator.setDuration(500);
        innerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerCircleBorderAnimator.setInterpolator(new AccelerateInterpolator());
        innerCircleBorderAnimator.start();

        outerCircleBorderAnimator = ObjectAnimator.ofInt(this, "outerCircleBorder", OUTER_CIRCLE_MAX_RADIUS, OUTER_CIRCLE_MIN_RADIUS);
        outerCircleBorderAnimator.setStartDelay(0);
        outerCircleBorderAnimator.setDuration(500);
        outerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerCircleBorderAnimator.setInterpolator(new DecelerateInterpolator());
        outerCircleBorderAnimator.start();
    }

    @Override
    public void stopAnimation() {

    }

    void setOuterCircleBorder(int radius) {
        outerRadius = radius;

        animatingView.invalidate();
    }

    void setInnerCircleRadius(int radius) {

        innerRadius = radius;
        animatingView.invalidate();
    }
}