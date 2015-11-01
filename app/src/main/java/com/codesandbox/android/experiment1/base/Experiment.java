package com.codesandbox.android.experiment1.base;

import android.app.Activity;

/**
 * Created by jaminja on 01/11/2015.
 */
public abstract class Experiment {

    public abstract void startExperimentFor(Activity activity);

    public String getFriendlyName() {
        return "Generic Experiment";
    }
}
