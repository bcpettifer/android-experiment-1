package com.codesandbox.android.experiment1.experiments;

import android.content.Context;
import android.graphics.drawable.VectorDrawable;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.codesandbox.android.experiment1.R;
import com.codesandbox.android.experiment1.base.ExperimentBase;

/**
 * Simple spiral vector animation.
 */
public class SpiralVectorExperiment extends ExperimentBase {

    private Animation mRotateAnimation;
    ImageView mView;

    @Override
    public void startExperimentFor(Context context, ViewGroup viewGroup) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vector = (VectorDrawable) context.getDrawable(R.drawable.spiral_vector);
            mView = new ImageView(context);
            mView.setImageDrawable(vector);
            mView.setScaleX(2.0f);
            mView.setScaleY(2.0f);

            mRotateAnimation = new RotateAnimation(0, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mRotateAnimation.setDuration(3000);
            mRotateAnimation.setInterpolator(new LinearInterpolator());
            mRotateAnimation.setRepeatCount(Animation.INFINITE);
            mView.setAnimation(mRotateAnimation);

            viewGroup.addView(mView);
        } else {
            Toast.makeText(context, "Sorry, this experiment is not supported on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void killExperimentFor(ViewGroup viewGroup) {
        if (mView != null) {
            viewGroup.removeView(mView);
        }
    }
}
