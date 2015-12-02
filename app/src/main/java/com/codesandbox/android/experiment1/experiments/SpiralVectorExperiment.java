package com.codesandbox.android.experiment1.experiments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.codesandbox.android.experiment1.R;

/**
 * Simple spiral vector animation.
 */
public class SpiralVectorExperiment extends Fragment {

    public static String TAG = "SPIRAL_FRAGMENT";
    private Animation mRotateAnimation;
    ImageView mView;
    FrameLayout mLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        startExperimentFor(getActivity(), container);
        return mLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mView.clearAnimation();
    }

    public void startExperimentFor(Context context, ViewGroup viewGroup) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            VectorDrawable vector = (VectorDrawable) context.getDrawable(R.drawable.spiral_vector);
            mView = new ImageView(context);
            mView.setImageDrawable(vector);

            mRotateAnimation = new RotateAnimation(0, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            mRotateAnimation.setDuration(3000);
            mRotateAnimation.setInterpolator(new LinearInterpolator());
            mRotateAnimation.setRepeatCount(Animation.INFINITE);
            mView.setAnimation(mRotateAnimation);

            int h = viewGroup.getHeight();
            int w = viewGroup.getWidth();

            int spiralEdgeLength = (int) Math.ceil(Math.hypot(h, w));

            mLayout = new FrameLayout(context);
            mLayout.setClipToPadding(false);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(spiralEdgeLength, spiralEdgeLength);
            mLayout.setLayoutParams(layoutParams);
            mLayout.setX((w - spiralEdgeLength) / 2);
            mLayout.setY((h - spiralEdgeLength) / 2);

            mLayout.addView(mView);

        } else {
            Toast.makeText(context, "Sorry, this experiment is not supported on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    public String getFriendlyName() {
        return "Spiral Vector";
    }
}
