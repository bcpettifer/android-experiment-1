package com.codesandbox.android.experiment1.experiments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.codesandbox.android.experiment1.base.Experiment;


/**
 * "Insanity: doing the same thing over and over again and expecting different results." - Einstein
 * Created by jaminja on 01/11/2015.
 */
public class CirclingMadnessExperiment extends Experiment {
    private static final int CIRCLES_COUNT = 13;
    private static final int[] COLOURS = {
            Color.BLACK,
            Color.WHITE
    };
    private static final int BACKGROUND_COLOUR = Color.DKGRAY;
    private static final float OFFSET_MULTIPLIER = 1.1f;
    private static final boolean LOOPY_MODE_ENABLED = false;
    private Paint mPaint;
    private Animation mRotateAnimation;
    private RelativeLayout mLayout;

    @Override
    public String getFriendlyName() {
        return "Circling Madness";
    }

    @Override
    public void startExperimentFor(Context context, ViewGroup viewGroup) {

        viewGroup.setBackgroundColor(BACKGROUND_COLOUR);

        mLayout = new RelativeLayout(context);
        mLayout.setClipToPadding(false);

        ImageView circles = createCircles(CIRCLES_COUNT, Math.min(viewGroup.getWidth(), viewGroup.getHeight()), context, viewGroup);
        mLayout.addView(circles);

        mRotateAnimation = new RotateAnimation(0, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1500);
        mRotateAnimation.setInterpolator(new LinearInterpolator());
        mRotateAnimation.setRepeatCount(Animation.INFINITE);
        mLayout.setAnimation(mRotateAnimation);

        viewGroup.addView(mLayout);
    }

    @Override
    public void killExperimentFor(ViewGroup viewGroup) {
        if (mRotateAnimation != null) {
            mRotateAnimation.cancel();
        }
        if (mLayout != null) {
            mLayout.removeAllViews();
            viewGroup.removeView(mLayout);
        }
    }

    private ImageView createCircles(int circlesCount, int diameter, Context context, View view) {
        ImageView imageView = new ImageView(context);
        final Bitmap circle = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(circle);

        int d = diameter;
        int centreX = diameter / 2;
        int centreY = diameter / 2;

        for (int i = 0; i < circlesCount; i++) {
            float xOffset = (i == 0) ? d/2 : (d/2) * OFFSET_MULTIPLIER;
            float yOffset = (i == 0)? d/2 : (d/2) * OFFSET_MULTIPLIER;
            int x = Math.max(0, Math.round(centreX - xOffset));
            int y = Math.max(0, Math.round(centreY - yOffset));
            if (LOOPY_MODE_ENABLED) {
                x = 0;
                y = 0;
            }
            drawCircleToCanvas(canvas, COLOURS[i % COLOURS.length], x, y, d);
            d -= d / circlesCount;
        }
        imageView.setImageBitmap(circle);
        imageView.setX((view.getWidth() / 2) - centreX);
        imageView.setY((view.getHeight()/2) - centreY);
        return imageView;
    }

    private void drawCircleToCanvas(Canvas canvas, int colour, int x, int y, int diameter) {
        Paint paint = getPaint();
        final Rect rect = new Rect(x, y, x+diameter, y+diameter);
        final RectF rectF = new RectF(rect);
        paint.setColor(colour);
        canvas.drawOval(rectF, paint);
    }

    private Paint getPaint() {
        if (mPaint != null) {
            return mPaint;
        }
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        return mPaint;
    }
}
