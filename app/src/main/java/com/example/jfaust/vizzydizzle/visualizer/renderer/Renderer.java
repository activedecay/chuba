/**
 * Copyright 2011, Felix Palmer
 * <p/>
 * Licensed under the MIT license:
 * http://creativecommons.org/licenses/MIT/
 */
package com.example.jfaust.vizzydizzle.visualizer.renderer;

import android.graphics.Canvas;
import android.graphics.Rect;

abstract public class Renderer {
    // Have these as members, so we don't have to re-create them each time
    protected float[] mPoints;
    protected float[] mFFTPoints;

    public Renderer() {
    }

    // As the display of raw/FFT audio will usually look different, subclasses
    // will typically only implement one of the below methods

    /**
     * Implement this method to render the audio data onto the canvas
     *
     * @param canvas - Canvas to draw on
     * @param rect   - Rect to render into
     * @param bytes  - Audio to render
     */
    abstract public void onRenderAudio(Canvas canvas, Rect rect, byte[] bytes);

    /**
     * Implement this method to render the FFT audio data onto the canvas
     *
     * @param canvas - Canvas to draw on
     * @param bytes  - Data to render
     * @param rect   - Rect to render into
     */
    abstract public void onRenderFft(Canvas canvas, byte[] bytes, Rect rect);

    // These methods should actually be called for rendering

    /**
     * Render the audio data onto the canvas
     *
     * @param canvas - Canvas to draw on
     * @param rect   - Rect to render into
     * @param bytes  - audio bytes to render
     */
    final public void renderAudio(Canvas canvas, Rect rect, byte[] bytes) {
        if (mPoints == null || mPoints.length < bytes.length * 4) {
            mPoints = new float[bytes.length * 4];
        }

        onRenderAudio(canvas, rect, bytes);
    }

    /**
     * Render the FFT data onto the canvas
     *
     * @param canvas - Canvas to draw on
     * @param bytes  - Data to render
     * @param rect   - Rect to render into
     */
    final public void renderFft(Canvas canvas, byte[] bytes, Rect rect) {
        if (mFFTPoints == null || mFFTPoints.length < bytes.length * 4) {
            mFFTPoints = new float[bytes.length * 4];
        }

        onRenderFft(canvas, bytes, rect);
    }
}
