package com.codesandbox.android.experiment1.experiments;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Shape object to be used with the MystifyExperiment.
 * Adapted from https://github.com/hevs-isi/gdx2d/blob/master/gdx2d-desktop/src/hevs/gdx2d/demos/simple/mistify/BounceShape.java
 */
public class MystifyShape {
    private static final Random RANDOM = new Random();
    private static final int COLOUR_CHANGE_SPEED = 1;
    private static final double X_SPEED_MULTIPLIER = 6, Y_SPEED_MULTIPLIER = 6;
    private static final double X_SPEED_BASE = 1, Y_SPEED_BASE = 1;
    private double[] x = new double[4];
    private double[] y = new double[4];
    private double[] xSpeed = new double[4];
    private double[] ySpeed = new double[4];
    private int c;
    private int targetC;

    public MystifyShape(int width, int height) {
        // Initialise the x/y and xSpeeds/ySpeeds
        for (int loop = 0; loop < 4; loop++) {
            x[loop] = RANDOM.nextInt(width);
            y[loop] = RANDOM.nextInt(height);

            xSpeed[loop] = RANDOM.nextDouble() * X_SPEED_MULTIPLIER;
            ySpeed[loop] = RANDOM.nextDouble() * Y_SPEED_MULTIPLIER;
        }

        // Pick a random colour for the current colour and target colour
        c = pickColour();
        targetC = pickColour();
    }

    void drawShape(Paint paint, Canvas canvas) {
        paint.setColor(c);

        for (int pointLoop = 0; pointLoop < 4; pointLoop++) {
            canvas.drawLine(
                    (float) x[pointLoop],
                    (float) y[pointLoop],
                    (float) x[(pointLoop + 1) % 4],
                    (float) y[(pointLoop + 1) % 4],
                    paint);
        }
    }

    void moveShape(int theScreenWidth, int theScreenHeight) {
        for (int loop = 0; loop < 4; loop++) {
            // Move the particles
            x[loop] += xSpeed[loop];
            y[loop] += ySpeed[loop];

            // Bounce the particles when they hit the edge of the window
            if (x[loop] > theScreenWidth) {
                x[loop] = theScreenWidth;
                xSpeed[loop] = -X_SPEED_BASE + RANDOM.nextFloat()
                        * (X_SPEED_MULTIPLIER * -1);
            }
            if (x[loop] < 0.0f) {
                x[loop] = 0.0f;
                xSpeed[loop] = X_SPEED_BASE + RANDOM.nextFloat() * X_SPEED_MULTIPLIER;
            }

            if (y[loop] > theScreenHeight) {
                y[loop] = theScreenHeight;
                ySpeed[loop] = -Y_SPEED_BASE + RANDOM.nextFloat()
                        * (Y_SPEED_MULTIPLIER * -1);
            }
            if (y[loop] < 0.0f) {
                y[loop] = 0.0f;
                ySpeed[loop] = Y_SPEED_BASE + RANDOM.nextFloat() * Y_SPEED_MULTIPLIER;
            }

        }
    }

    private int pickColour() {
        return Color.rgb(RANDOM.nextInt(255), RANDOM.nextInt(255), RANDOM.nextInt(255));
    }

    public void shiftShapeColour(int frameCount) {
        // Change the target colour every 500 frames
        if (frameCount % 500 == 0)
            targetC = pickColour();

        int red = Color.red(c);
        int green = Color.green(c);
        int blue = Color.blue(c);

        // Shift red component close to red target
        if (red < Color.red(targetC))
            red += COLOUR_CHANGE_SPEED;
        if (red > Color.red(targetC))
            red -= COLOUR_CHANGE_SPEED;

        // Shift green component closer to green target
        if (green < Color.green(targetC))
            green += COLOUR_CHANGE_SPEED;
        if (green > Color.green(targetC))
            green -= COLOUR_CHANGE_SPEED;

        // Shift blue component closer to blue target
        if (blue < Color.blue(targetC))
            blue += COLOUR_CHANGE_SPEED;
        if (blue > Color.blue(targetC))
            blue -= COLOUR_CHANGE_SPEED;

        c = Color.rgb(red, green, blue);
    }
}
