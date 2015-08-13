package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 12/8/15.
 */
public class ListenViewHelper implements CortanaInterface {

    int INNER_CIRCLE_MIN_RADIUS = 0;
    int INNER_CIRCLE_MAX_RADIUS = 0;

    int OUTER_CIRCLE_MIN_RADIUS = 0;
    int OUTER_CIRCLE_MAX_RADIUS = 0;

    int innerRadius;
    int outerRadius;

    int centerX = 0;
    int centerY = 0;

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    ObjectAnimator outerAnimator;
    ObjectAnimator innerAnimator;

    View animatingView;

    @Override
    public void initializePaint() {

        innerCirclePaint = new Paint();
        outerCirclePaint = new Paint();

        innerCirclePaint.setStyle(Paint.Style.FILL);
        outerCirclePaint.setStyle(Paint.Style.FILL);

        innerCirclePaint.setColor(CortanaType.INNER_CIRCLE_COLOR);
        outerCirclePaint.setColor(CortanaType.OUTER_CIRCLE_COLOR);

        outerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setAntiAlias(true);
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {

        INNER_CIRCLE_MAX_RADIUS = (diameter / 4);
        INNER_CIRCLE_MIN_RADIUS = INNER_CIRCLE_MAX_RADIUS * 90 / 100;

        OUTER_CIRCLE_MAX_RADIUS = INNER_CIRCLE_MAX_RADIUS * 2;
        OUTER_CIRCLE_MIN_RADIUS = INNER_CIRCLE_MIN_RADIUS * 2;

        this.centerX = centerX;
        this.centerY = centerY;

        outerRadius = OUTER_CIRCLE_MAX_RADIUS;
        innerRadius = INNER_CIRCLE_MIN_RADIUS;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, outerRadius, outerCirclePaint);
        canvas.drawCircle(centerX, centerY, innerRadius, innerCirclePaint);
    }

    @Override
    public void startAnimation(final View view) {

        stopAnimation();

        if(view == null)
            return;

        animatingView = view;

        outerAnimator = ObjectAnimator.ofInt(this, "outerCircleRadius", OUTER_CIRCLE_MAX_RADIUS, OUTER_CIRCLE_MIN_RADIUS, OUTER_CIRCLE_MAX_RADIUS);
        outerAnimator.setStartDelay(0);
        outerAnimator.setDuration(1000);
        outerAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerAnimator.setInterpolator(new LinearInterpolator());
        outerAnimator.start();

        innerAnimator = ObjectAnimator.ofInt(this, "innerCircleRadius", INNER_CIRCLE_MIN_RADIUS, INNER_CIRCLE_MAX_RADIUS, INNER_CIRCLE_MIN_RADIUS);
        innerAnimator.setStartDelay(0);
        innerAnimator.setDuration(1000);
        innerAnimator.setRepeatCount(ValueAnimator.INFINITE);
        innerAnimator.setInterpolator(new LinearInterpolator());
        innerAnimator.start();
    }

    @Override
    public void stopAnimation() {

        if (outerAnimator != null) {
            outerAnimator.cancel();
            outerAnimator = null;
        }

        if (innerAnimator != null) {
            innerAnimator.cancel();
            innerAnimator = null;
        }
    }

    void setInnerCircleRadius(int radius) {
        innerRadius = radius;
        animatingView.invalidate();
    }

    void setOuterCircleRadius(int radius) {
        outerRadius = radius;
        animatingView.invalidate();
    }
}
