package com.cnbs.recyclerviewdemo.view;


import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/4/1.
 */

public class AutoScrollFactorScroller extends Scroller {

    private double factor = 1;

    public AutoScrollFactorScroller(Context context) {
        super(context);
    }

    public AutoScrollFactorScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * factor));
    }
}