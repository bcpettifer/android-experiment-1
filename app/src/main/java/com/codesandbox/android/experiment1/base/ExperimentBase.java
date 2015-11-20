package com.codesandbox.android.experiment1.base;

import android.content.Context;
import android.graphics.Paint;
import android.view.ViewGroup;

/**
 * Building block for creating new and fun experiments.
 */
public abstract class ExperimentBase {

    private Paint mPaint;

    public abstract void startExperimentFor(Context context, ViewGroup viewGroup);
    public abstract void killExperimentFor(ViewGroup viewGroup);

    public String getFriendlyName() {
        return "Generic Experiment";
    }

    protected Paint getPaint() {
        if (mPaint != null) {
            return mPaint;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        return mPaint;
    }
}
