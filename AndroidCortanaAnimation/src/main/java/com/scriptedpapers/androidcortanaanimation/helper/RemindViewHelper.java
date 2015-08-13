package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 12/8/15.
 */
public class RemindViewHelper implements CortanaInterface {

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    int outerRadius;
    int innerRadiusWoBorder;
    int borderWidth = 0;
    int outerBorderWidth = 0;

    int centerX = 0;
    int centerY = 0;

    int OUTER_BORDER_WIDTH = 0;

    int OUTER_CIRCLE_MAX_RADIUS = 0;
    int OUTER_CIRCLE_MIN_RADIUS = 0;

    int INNER_BORDER_MAX_WIDTH = 0;
    int INNER_BORDER_MIN_WIDTH = 0;

    ObjectAnimator innerCircleBorderAnimator;
    ObjectAnimator outerCircleBorderAnimator;

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

        INNER_BORDER_MAX_WIDTH = diameter / 4;
        INNER_BORDER_MIN_WIDTH = (diameter * 10) / 100;

        OUTER_CIRCLE_MAX_RADIUS = (diameter/2);
        OUTER_CIRCLE_MIN_RADIUS = OUTER_CIRCLE_MAX_RADIUS - INNER_BORDER_MIN_WIDTH;

        OUTER_BORDER_WIDTH = diameter * 25 / 100;

        this.centerX = centerX;
        this.centerY = centerY;

        borderWidth = INNER_BORDER_MAX_WIDTH;
        innerCirclePaint.setStrokeWidth(borderWidth);

        outerBorderWidth = OUTER_BORDER_WIDTH;
        outerCirclePaint.setStrokeWidth(OUTER_BORDER_WIDTH);

        innerRadiusWoBorder = INNER_BORDER_MAX_WIDTH;
        outerRadius = OUTER_CIRCLE_MAX_RADIUS;
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

        innerCircleBorderAnimator = ObjectAnimator.ofInt(this, "innerCircleBorder", INNER_BORDER_MAX_WIDTH, INNER_BORDER_MIN_WIDTH, INNER_BORDER_MAX_WIDTH);
        innerCircleBorderAnimator.setStartDelay(0);
        innerCircleBorderAnimator.setDuration(500);
        innerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerCircleBorderAnimator.setInterpolator(new LinearInterpolator());
        innerCircleBorderAnimator.start();

        outerCircleBorderAnimator = ObjectAnimator.ofInt(this, "outerCircleBorder", OUTER_CIRCLE_MIN_RADIUS, OUTER_CIRCLE_MAX_RADIUS, OUTER_CIRCLE_MIN_RADIUS);
        outerCircleBorderAnimator.setStartDelay(0);
        outerCircleBorderAnimator.setDuration(500);
        outerCircleBorderAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerCircleBorderAnimator.setInterpolator(new LinearInterpolator());
        outerCircleBorderAnimator.start();
    }

    @Override
    public void stopAnimation() {

    }

    void setOuterCircleBorder(int radius) {
        outerRadius = radius;
        animatingView.invalidate();
    }

    void setInnerCircleBorder(int radius) {

        borderWidth = radius;
        innerCirclePaint.setStrokeWidth(borderWidth);

        float percentage = ((((float) (INNER_BORDER_MAX_WIDTH - borderWidth) / (float) (INNER_BORDER_MAX_WIDTH - INNER_BORDER_MIN_WIDTH))) * 100);
        int relatedBorderWidth = (int) (INNER_BORDER_MIN_WIDTH * percentage / 100);

        innerRadiusWoBorder = (int) (INNER_BORDER_MAX_WIDTH + (INNER_BORDER_MAX_WIDTH - borderWidth) + relatedBorderWidth);
        animatingView.invalidate();
    }
}
