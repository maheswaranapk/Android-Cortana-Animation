package com.scriptedpapers.androidcortanaanimation.helper;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.scriptedpapers.androidcortanaanimation.CortanaInterface;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 13/8/15.
 */
public class OptimisticViewHelper implements CortanaInterface {

    Paint innerCirclePaint;
    Paint outerCirclePaint;

    int INNER_RADIUS = 0;
    int INNER_BORDER_WIDTH = 0;

    int outerRadius = 0;
    int borderWidth = 0;

    int centerX = 0;
    int centerY = 0;

    int OUTER_BORDER_MAX_WIDTH = 0;
    int OUTER_BORDER_MIN_WIDTH = 0;

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
    }

    @Override
    public void calculateDimensions(int diameter, int centerX, int centerY) {


        OUTER_BORDER_MAX_WIDTH = diameter / 4;
        OUTER_BORDER_MIN_WIDTH = (diameter * 10) / 100;

        INNER_RADIUS = diameter / 2;
        INNER_BORDER_WIDTH = OUTER_BORDER_MIN_WIDTH;

        this.centerX = centerX;
        this.centerY = centerY;

        borderWidth = OUTER_BORDER_MAX_WIDTH;
        outerRadius = OUTER_BORDER_MAX_WIDTH;

        innerCirclePaint.setStrokeWidth(INNER_BORDER_WIDTH);
        outerCirclePaint.setStrokeWidth(borderWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {

        canvas.drawCircle(centerX, centerY, INNER_RADIUS - (INNER_BORDER_WIDTH / 2), innerCirclePaint);

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
}
