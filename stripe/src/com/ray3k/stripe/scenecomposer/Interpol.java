/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2021 Raymond Buckley
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.math.Interpolation;

public enum Interpol {
    LINEAR(Interpolation.linear, "Linear", "linear"), SMOOTH(Interpolation.smooth, "Smooth", "smooth"), SMOOTH2(
            Interpolation.smooth2, "Smooth 2", "smooth2"),
    SMOOTHER(Interpolation.smoother, "Smoother", "smoother"), FADE(Interpolation.fade, "Fade", "fade"), POW2(
            Interpolation.pow2, "Pow 2", "pow2"),
    POW2IN(Interpolation.pow2In, "Pow 2 In", "pow2In"), SLOW_FAST(Interpolation.slowFast, "Slow Fast",
            "slowFast"), POW2OUT(Interpolation.pow2Out, "Pow 2 Out", "pow2Out"),
    FAST_SLOW(Interpolation.fastSlow, "Fast Slow", "fastSlow"), POW2IN_INVERSE(Interpolation.pow2In,
            "Pow 2 In Inverse", "pow2In"),
    POW2OUT_INVERSE(Interpolation.pow2OutInverse, "Pow 2 Out Inverse", "pow2OutInverse"), POW3(Interpolation.pow3,
            "Pow 3", "pow3"), POW3IN(Interpolation.pow3In, "Pow 3 In", "pow3In"),
    POW3OUT(Interpolation.pow3Out, "Pow 3 Out", "pow3Out"), POW3IN_INVERSE(Interpolation.pow3InInverse,
            "Pow 3 In Inverse", "pow3InInverse"),
    POW3OUT_INVERSE(Interpolation.pow3OutInverse, "Pow 3 Out Inverse", "pow3OutInverse"), POW4(Interpolation.pow4,
            "Pow 4", "pow4"), POW4IN(Interpolation.pow4In, "Pow 4 In", "pow4In"),
    POW4OUT(Interpolation.pow4Out, "Pow 4 Out", "pow4Out"), POW5(Interpolation.pow5, "Pow 5", "pow5"), POW5IN(
            Interpolation.pow5In, "Pow 5 In", "pow5In"),
    POW5OUT(Interpolation.pow5Out, "Pow 5 Out", "pow5Out"), SINE(Interpolation.sine, "Sine", "sine"), SINE_IN(
            Interpolation.sineIn, "Sine In", "sineIn"),
    SINE_OUT(Interpolation.sineOut, "Sine Out", "sineOut"), EXP10(Interpolation.exp10, "Exp 10", "exp10"), EXP10_IN(
            Interpolation.exp10In, "Exp 10 In", "exp10In"),
    EXP10_OUT(Interpolation.exp10Out, "Exp 10 Out", "exp10Out"), EXP5(Interpolation.exp5, "Exp 5", "exp5"), EXP5IN(
            Interpolation.exp5In, "Exp 5 In", "exp5In"),
    EXP5OUT(Interpolation.exp5Out, "Exp 5 Out", "exp5Out"), CIRCLE(Interpolation.circle, "Circle",
            "circle"), CIRCLE_IN(Interpolation.circleIn, "Circle In", "circleIn"),
    CIRCLE_OUT(Interpolation.circleOut, "Circle Out", "circleOut"), ELASTIC(Interpolation.elastic, "Elastic",
            "elastic"), ELASTIC_IN(Interpolation.elasticIn, "Elastic In", "elasticIn"),
    ELASTIC_OUT(Interpolation.elasticOut, "Elastic Out", "elasticOut"), SWING(Interpolation.swing, "Swing",
            "swing"), SWING_IN(Interpolation.swingIn, "Swing In", "swingIn"),
    SWING_OUT(Interpolation.swingOut, "Swing Out", "swingOut"), BOUNCE(Interpolation.bounce, "Bounce",
            "bounce"), BOUNCE_IN(Interpolation.bounceIn, "Bounce In", "bounceIn"),
    BOUNCE_OUT(Interpolation.bounceOut, " Bounce Out", "bounceOut");
    
    public Interpolation interpolation;
    public String text;
    public String code;
    
    Interpol(Interpolation interpolation, String text, String code) {
        this.interpolation = interpolation;
        this.text = text;
        this.code = code;
    }

    @Override
    public String toString() {
        return text;
    }
}
