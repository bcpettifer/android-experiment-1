package com.codesandbox.android.experiment1.experiments;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.codesandbox.android.experiment1.base.Experiment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaminja on 01/11/2015.
 */
public class CirclingMadnessExperiment extends Experiment {
    private static final int CIRCLES_COUNT = 13;
    private static final int[] COLOURS = {
            Color.BLACK,
            Color.WHITE
    };
    private static int BACKGROUND_COLOUR = Color.DKGRAY;
    private static float OFFSET_MULTIPLIER = 1.1f;

    @Override
    public String getFriendlyName() {
        return "Circling Madness";
    }

    @Override
    public void startExperimentFor(Activity activity) {

        final ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        parent.setBackgroundColor(BACKGROUND_COLOUR);

        RelativeLayout layout = new RelativeLayout(activity);
        layout.setClipToPadding(false);

        List<ImageView> circles = createCircles(CIRCLES_COUNT, Math.min(parent.getWidth(), parent.getHeight()), activity, parent);

        for (int i = 0; i < circles.size(); i++) {
            layout.addView(circles.get(i));
        }

        Animation animation;
        animation = new RotateAnimation(0, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        layout.setAnimation(animation);

        parent.addView(layout);
    }

    private List<ImageView> createCircles(int circlesCount, int diameter, Activity activity, ViewGroup parent) {
        ArrayList<ImageView> circles = new ArrayList<>(circlesCount);
        int d = diameter;

        int centreX = parent.getWidth() / 2;
        int centreY = parent.getHeight() / 2;

        for (int i = 0; i < circlesCount; i++) {
            ImageView imageView = new ImageView(activity);
            Bitmap circle = createCircleBitmap(COLOURS[i % COLOURS.length], d);
            imageView.setImageBitmap(circle);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setLayoutParams(new ActionBar.LayoutParams(d, d));
            float xOffset = (i == 0) ? d/2 : (d/2) * OFFSET_MULTIPLIER;
            float yOffset = (i == 0)? d/2 : (d/2) * OFFSET_MULTIPLIER;
            imageView.setX(centreX - xOffset);
            imageView.setY(centreY - yOffset);
            circles.add(imageView);

            d -= d / circlesCount;
        }
        return circles;
    }

    private Bitmap createCircleBitmap(int colour, int diameter) {
        final Bitmap circle = Bitmap.createBitmap(diameter, diameter, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(circle);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, diameter, diameter);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setColor(colour);
        canvas.drawOval(rectF, paint);

        return circle;
    }
}
