package com.codesandbox.android.experiment1.experiments;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.codesandbox.android.experiment1.base.ExperimentBaseFragment;

/**
 * A clone of the classic mystify screensaver.
 */
public class MystifyExperiment extends ExperimentBaseFragment {

    final static int INTERVAL = 20; // 50 FPS
    final int SHAPE_COUNT = 3;
    MystifyShape[] mShapes;
    int mFrame = 0;
    Paint mPaint;
    Runnable mRunnable;
    Handler mHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        mPaint = getPaint();
        mPaint.setStrokeWidth(5.0f);
        mShapes = new MystifyShape[SHAPE_COUNT];

        for (int i = 0; i < SHAPE_COUNT; i++) {
            mShapes[i] = new MystifyShape(container.getWidth(), container.getHeight());
        }

        if (container.getBackground() instanceof  ColorDrawable) {
            container.setBackgroundColor(
                darkenColour(((ColorDrawable)container.getBackground()).getColor(), 0.5f));
        } else {
            container.setBackgroundColor(darkenColour(Color.CYAN, 0.15f));
        }

        final ImageView imageView = new ImageView(getActivity());
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                onTick(imageView, container.getWidth(), container.getHeight());
                mHandler.postDelayed(this, INTERVAL);
            }
        };

        mHandler.postDelayed(mRunnable, INTERVAL);

        return imageView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacks(mRunnable);
    }

    private void onTick(ImageView imageView, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);

        // Draw and move the shapes
        for (MystifyShape shape : mShapes) {
            shape.drawShape(mPaint, canvas);
            shape.moveShape(width, height);
            shape.shiftShapeColour(mFrame++);
        }

        imageView.setImageBitmap(bitmap);
    }

    @Override
    public String getFriendlyName() {
        return "Mystify";
    }

    private int darkenColour(int colour, float factor) {
        if (factor < 0f || factor > 1f) {
            throw new IllegalArgumentException("factor must be between 0 and 1");
        }
        float[] hsv = new float[3];
        Color.colorToHSV(colour, hsv);
        hsv[2] *= factor; // value component
        return Color.HSVToColor(hsv);
    }
}
