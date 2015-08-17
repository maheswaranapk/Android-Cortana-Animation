package com.scriptedpapers.androidcortanaanimation;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.scriptedpapers.androidcortanaanimation.helper.AshamedViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.BouncyViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.CoolViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.Greeting2ViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.GreetingViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.ListenViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.NeedMoreViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.OptimisticViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.Remind2ViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.RemindViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.SpeakViewHelper;
import com.scriptedpapers.androidcortanaanimation.helper.ThinkViewHelper;
import com.scriptedpapers.androidcortanaanimation.utils.CortanaType;

/**
 * Created by mahes on 12/8/15.
 */
public class CortanaView  extends View {

    int type;

    CortanaInterface cortanaInterface;

    public CortanaView(Context context) {
        super(context);
        init();
    }
    public CortanaView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CortanaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    void init() {

        type = CortanaType.LISTEN_TYPE;
        cortanaInterface = new ListenViewHelper();

        cortanaInterface.initializePaint();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        int diameter = (getLayoutParams().width < getLayoutParams().height)?getLayoutParams().width:getLayoutParams().height;

        int centerX = (getLayoutParams().width / 2);
        int centerY = (getLayoutParams().height / 2);

        if(cortanaInterface != null)
            cortanaInterface.calculateDimensions(diameter, centerX, centerY);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(cortanaInterface != null)
            cortanaInterface.onDraw(canvas);
    }

    public void startAnimation(){
        if(cortanaInterface != null)
            cortanaInterface.startAnimation(this);
    }

    public void stopAnimation() {
        if(cortanaInterface != null)
            cortanaInterface.stopAnimation();
    }

    public void setType(int type) {

        stopAnimation();

        setRotation(0);

        switch(type) {
            case CortanaType.LISTEN_TYPE:
                cortanaInterface = new ListenViewHelper();
                break;
            case CortanaType.SPEAK_TYPE:
                cortanaInterface = new SpeakViewHelper();
                break;
            case CortanaType.REMIND_TYPE:
                cortanaInterface = new RemindViewHelper();
                break;
            case CortanaType.OPTIMISTIC_TYPE:
                cortanaInterface = new OptimisticViewHelper();
                break;
            case CortanaType.REMIND_2_TYPE:
                cortanaInterface = new Remind2ViewHelper();
                break;
            case CortanaType.COOL_TYPE:
                cortanaInterface = new CoolViewHelper();
                break;
            case CortanaType.THINK_TYPE:
                cortanaInterface = new ThinkViewHelper();
                break;
            case CortanaType.BOUNCY_TYPE:
                cortanaInterface = new BouncyViewHelper();
                break;
            case CortanaType.ASHAMED_TYPE:
                cortanaInterface = new AshamedViewHelper();
                break;
            case CortanaType.NEED_MORE_TYPE:
                cortanaInterface = new NeedMoreViewHelper();
                break;
            case CortanaType.GREETING_TYPE:
                cortanaInterface = new GreetingViewHelper();
                break;
            case CortanaType.GREETING_2_TYPE:
                cortanaInterface = new Greeting2ViewHelper();
        }

        int diameter = (getLayoutParams().width < getLayoutParams().height)?getLayoutParams().width:getLayoutParams().height;

        int centerX = (getLayoutParams().width / 2);
        int centerY = (getLayoutParams().height / 2);

        cortanaInterface.initializePaint();
        cortanaInterface.calculateDimensions(diameter, centerX, centerY);

        invalidate();
    }
}
