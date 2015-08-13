package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 13/8/15.
 */
public class Remind2ViewHelper implements CortanaInterface {

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    int INNER_MIN_RADIUS = 0;
    int INNER_MAX_RADIUS = 0;
    int INNER_BORDER_WIDTH = 0;

    int outerRadius = 0;
    int borderWidth = 0;

    int innerRadius;
    int innerBorderWidth;

    int centerX = 0;
    int centerY = 0;

    int OUTER_BORDER_MAX_WIDTH = 0;
    int OUTER_BORDER_MIN_WIDTH = 0;

    ObjectAnimator outerCircleBorderAnimator;
    ObjectAnimator innerCircleRadiusAnimator;

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
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {


        OUTER_BORDER_MAX_WIDTH = diameter / 4;
        OUTER_BORDER_MIN_WIDTH = (diameter * 10) / 100;

        INNER_MIN_RADIUS = diameter / 4 + OUTER_BORDER_MIN_WIDTH;
        INNER_MAX_RADIUS = diameter / 2;

        INNER_BORDER_WIDTH = OUTER_BORDER_MIN_WIDTH;

        this.centerX = centerX;
        this.centerY = centerY;

        borderWidth = OUTER_BORDER_MAX_WIDTH;
        outerRadius = OUTER_BORDER_MAX_WIDTH;

        outerCirclePaint.setStrokeWidth(borderWidth);

        innerRadius = INNER_MAX_RADIUS;
        innerBorderWidth = INNER_BORDER_WIDTH;
        innerCirclePaint.setStrokeWidth(innerBorderWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, innerRadius - (INNER_BORDER_WIDTH / 2), innerCirclePaint);

        canvas.drawCircle(centerX, centerY, outerRadius - (borderWidth / 2), outerCirclePaint);
    }

    @Override
    public void startAnimation(final View view) {

        stopAnimation();

        if(view == null)
            return;

        animatingView = view;

        outerCircleBorderAnimator = ObjectAnimator.ofInt(this, "borderWidth", OUTER_BORDER_MAX_WIDTH, OUTER_BORDER_MIN_WIDTH, OUTER_BORDER_MAX_WIDTH);
        outerCircleBorderAnimator.setStartDelay(0);
        outerCircleBorderAnimator.setDuration(500);
        outerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerCircleBorderAnimator.setInterpolator(new AccelerateInterpolator());
        outerCircleBorderAnimator.start();

        innerCircleRadiusAnimator = ObjectAnimator.ofInt(this, "innerRadius", INNER_MIN_RADIUS, INNER_MAX_RADIUS, INNER_MIN_RADIUS);
        innerCircleRadiusAnimator.setStartDelay(0);
        innerCircleRadiusAnimator.setDuration(500);
        innerCircleRadiusAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerCircleRadiusAnimator.setInterpolator(new AccelerateInterpolator());
        innerCircleRadiusAnimator.start();
    }

    @Override
    public void stopAnimation() {

        if(outerCircleBorderAnimator != null) {
            outerCircleBorderAnimator.cancel();
            outerCircleBorderAnimator = null;
        }
    }

    void setBorderWidth(int borderWidth){

        this.borderWidth = borderWidth;
        outerCirclePaint.setStrokeWidth(borderWidth);

        outerRadius = (int) (OUTER_BORDER_MAX_WIDTH + (OUTER_BORDER_MAX_WIDTH - borderWidth));
        animatingView.invalidate();

    }

    void setInnerRadius(int radius){

        innerRadius = radius;
        animatingView.invalidate();

    }
}
