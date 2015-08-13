package com.scriptedpapers.androidcortanaanimation;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mahes on 12/8/15.
 */
public interface CortanaInterface {

    void initializePaint();
    void calculateDimensions(int diameter, int centerX, int centerY);
    void onDraw(Canvas canvas);
    void startAnimation(final View view);
    void stopAnimation();

}
