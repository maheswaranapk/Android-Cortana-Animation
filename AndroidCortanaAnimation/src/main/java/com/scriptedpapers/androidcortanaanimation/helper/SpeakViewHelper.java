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
 * Created by mahes on 12/8/15.
 */
public class SpeakViewHelper implements CortanaInterface{

    int INNER_CIRCLE_RADIUS = 0;
    int OUTER_CIRCLE_100_RADIUS = 0;
    int OUTER_CIRCLE_90_RADIUS = 0;
    int OUTER_CIRCLE_80_RADIUS = 0;

    int outerRadius;

    int centerX = 0;
    int centerY = 0;

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    ObjectAnimator outerRadiusAnimator;

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

        INNER_CIRCLE_RADIUS = (diameter / 4);

        OUTER_CIRCLE_100_RADIUS = (diameter / 2);

        OUTER_CIRCLE_90_RADIUS = OUTER_CIRCLE_100_RADIUS * 90 / 100;
        OUTER_CIRCLE_80_RADIUS = OUTER_CIRCLE_100_RADIUS * 80 / 100;

        this.centerX = centerX;
        this.centerY = centerY;

        outerRadius = OUTER_CIRCLE_100_RADIUS;
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, outerRadius, outerCirclePaint);
        canvas.drawCircle(centerX, centerY, INNER_CIRCLE_RADIUS, innerCirclePaint);
    }

    @Override
    public void startAnimation(final View view) {

        stopAnimation();

        if(view == null)
            return;

        animatingView = view;

        outerRadiusAnimator = ObjectAnimator.ofInt(this, "outerRadius", OUTER_CIRCLE_100_RADIUS, OUTER_CIRCLE_90_RADIUS,
                OUTER_CIRCLE_80_RADIUS, OUTER_CIRCLE_90_RADIUS,
                OUTER_CIRCLE_80_RADIUS, OUTER_CIRCLE_90_RADIUS,
                OUTER_CIRCLE_80_RADIUS, OUTER_CIRCLE_100_RADIUS,
                OUTER_CIRCLE_80_RADIUS, OUTER_CIRCLE_90_RADIUS,
                OUTER_CIRCLE_80_RADIUS, OUTER_CIRCLE_90_RADIUS,
                OUTER_CIRCLE_100_RADIUS
        );

        outerRadiusAnimator.setStartDelay(0);
        outerRadiusAnimator.setDuration(1500);
        outerRadiusAnimator.setRepeatCount(ValueAnimator.INFINITE);
        outerRadiusAnimator.setInterpolator(new LinearInterpolator());
        outerRadiusAnimator.start();
    }

    @Override
    public void stopAnimation() {

        if(outerRadiusAnimator != null) {
            outerRadiusAnimator.cancel();
            outerRadiusAnimator = null;
        }
    }

    void setOuterRadius(int radius){
        outerRadius = radius;
        animatingView.invalidate();
    }
}
