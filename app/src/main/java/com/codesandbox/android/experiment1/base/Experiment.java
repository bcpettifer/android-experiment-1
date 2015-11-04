package com.codesandbox.android.experiment1.base;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by jaminja on 01/11/2015.
 */
public abstract class Experiment {

    public abstract void startExperimentFor(Context context, ViewGroup viewGroup);
    public abstract void killExperimentFor(ViewGroup viewGroup);

    public String getFriendlyName() {
        return "Generic Experiment";
    }
}
