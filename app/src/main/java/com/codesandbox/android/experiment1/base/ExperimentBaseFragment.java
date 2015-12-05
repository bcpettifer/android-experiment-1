package com.codesandbox.android.experiment1.base;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Paint;
import android.view.ViewGroup;

/**
 * Building block for creating new and fun experiments.
 */
public abstract class ExperimentBaseFragment extends Fragment {

    public static String TAG = "EXPERIMENT_FRAGMENT";

    private Paint mPaint;

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
