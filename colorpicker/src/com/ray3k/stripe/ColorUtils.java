/*
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
 */
// Re-licensed by the author (Tommy Ettinger) as MIT; it was Apache 2.0 and still is in TextraTypist, where it was from.

package com.ray3k.stripe;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.NumberUtils;

/**
 * A few static methods for commonly-used color handling tasks.
 * This has methods to convert from HSLA colors to RGBA and back again, for hue-changing effects mainly.
 * It also has {@link #lerpColors(int, int, float)} to blend RGBA colors, and {@link #multiplyAlpha(int, float)} to
 * alter only the alpha channel on an RGBA or HSLA int color.
 */
public class ColorUtils {
    /**
     * Converts the four HSLA components, each in the 0.0 to 1.0 range, to an int in RGBA8888 format.
     * I brought this over from colorful-gdx's FloatColors class. I (Tommy Ettinger) can't recall where I got the
     * original HSL(A) code from, but there's a strong chance it was written by cypherdare/cyphercove for their color
     * space comparison.
     * <br>
     * The {@code h} parameter for hue can be lower than 0.0 or higher than 1.0 because the hue "wraps around;" only the
     * fractional part of h is used. The other parameters must be between 0.0 and 1.0 (inclusive) to make sense.
     *
     * @param h hue, usually from 0.0 to 1.0, but only the fractional part is used
     * @param s saturation, from 0.0 to 1.0
     * @param l lightness, from 0.0 to 1.0
     * @param a alpha, from 0.0 to 1.0
     * @return an RGBA8888-format int
     */
    public static int hsl2rgb(final float h, final float s, final float l, final float a) {
        float hue = h - MathUtils.floor(h);
        float x = Math.min(Math.max(Math.abs(hue * 6f - 3f) - 1f, 0f), 1f);
        float y = hue + (2f / 3f);
        float z = hue + (1f / 3f);
        y -= (int) y;
        z -= (int) z;
        y = Math.min(Math.max(Math.abs(y * 6f - 3f) - 1f, 0f), 1f);
        z = Math.min(Math.max(Math.abs(z * 6f - 3f) - 1f, 0f), 1f);
        float v = (l + s * Math.min(l, 1f - l));
        float d = 2f * (1f - l / (v + 1e-10f));
        return Color.rgba8888(v * MathUtils.lerp(1f, x, d), v * MathUtils.lerp(1f, y, d), v * MathUtils.lerp(1f, z, d), a);
    }

    /**
     * Converts the four RGBA components, each in the 0.0 to 1.0 range, to an int in HSLA format (hue,
     * saturation, lightness, alpha). This format is exactly like RGBA8888 but treats what would normally be red as hue,
     * green as saturation, and blue as lightness; alpha is the same.
     *
     * @param r red, from 0.0 to 1.0
     * @param g green, from 0.0 to 1.0
     * @param b blue, from 0.0 to 1.0
     * @param a alpha, from 0.0 to 1.0
     * @return an "HSLA-format" int
     */
    public static int rgb2hsl(final float r, final float g, final float b, final float a) {
        float x, y, z, w;
        if (g < b) {
            x = b;
            y = g;
            z = -1f;
            w = 2f / 3f;
        } else {
            x = g;
            y = b;
            z = 0f;
            w = -1f / 3f;
        }
        if (r < x) {
            z = w;
            w = r;
        } else {
            w = x;
            x = r;
        }
        float d = x - Math.min(w, y);
        float l = x * (1f - 0.5f * d / (x + 1e-10f));
        return Color.rgba8888(Math.abs(z + (w - y) / (6f * d + 1e-10f)), (x - l) / (Math.min(l, 1f - l) + 1e-10f), l, a);
    }

    /**
     * Converts the four HSBA/HSVA components, each in the 0.0 to 1.0 range, to an int in RGBA8888 format.
     * I brought this over from colorful-gdx's FloatColors class. I (Tommy Ettinger) can't recall where I got the
     * original HSL(A) code from, but there's a strong chance it was written by cypherdare/cyphercove for their color
     * space comparison.
     * <br>
     * HSV and HSB are synonyms; it makes a little more sense to call the third channel brightness.
     * The {@code h} parameter for hue can be lower than 0.0 or higher than 1.0 because the hue "wraps around;" only the
     * fractional part of h is used. The other parameters must be between 0.0 and 1.0 (inclusive) to make sense.
     *
     * @param h hue, from 0.0 to 1.0
     * @param s saturation, from 0.0 to 1.0
     * @param b brightness, from 0.0 to 1.0
     * @param a alpha, from 0.0 to 1.0
     * @return an RGBA8888-format int
     */
    public static int hsb2rgb(final float h, final float s, final float b, final float a) {
        float hue = h - MathUtils.floor(h);
        float x = Math.min(Math.max(Math.abs(hue * 6f - 3f) - 1f, 0f), 1f);
        float y = hue + (2f / 3f);
        float z = hue + (1f / 3f);
        y -= (int) y;
        z -= (int) z;
        y = Math.min(Math.max(Math.abs(y * 6f - 3f) - 1f, 0f), 1f);
        z = Math.min(Math.max(Math.abs(z * 6f - 3f) - 1f, 0f), 1f);
        return Color.rgba8888(b * MathUtils.lerp(1f, x, s), b * MathUtils.lerp(1f, y, s), b * MathUtils.lerp(1f, z, s), a);
    }

    /**
     * Converts the four RGBA components, each in the 0.0 to 1.0 range, to an int in HSBA/HSVA format (hue,
     * saturation, brightness/value, alpha). This format is exactly like RGBA8888 but treats what would normally be red
     * as hue, green as saturation, and blue as brightness/value; alpha is the same. HSV and HSB are synonyms; it makes
     * a little more sense to call the third channel brightness.
     *
     * @param r red, from 0.0 to 1.0
     * @param g green, from 0.0 to 1.0
     * @param b blue, from 0.0 to 1.0
     * @param a alpha, from 0.0 to 1.0
     * @return an "HSBA/HSVA-format" int
     */
    public static int rgb2hsb(final float r, final float g, final float b, final float a) {
        float v = Math.max(Math.max(r, g), b);
        float n = Math.min(Math.min(r, g), b);
        float c = v - n;
        float h;
        if (c == 0) h = 0f;
        else if (v == r) h = ((g - b) / c) / 6f;
        else if (v == g) h = ((b - r) / c + 2f) / 6f;
        else h = ((r - g) / c + 4f) / 6f;
        return Color.rgba8888(h, v == 0 ? 0f : c / v, v, a);
    }

    /**
     * Given a packed int color and a channel value from 0 to 3, gets the value of that channel as a float from 0.0f
     * to 1.0f . Channel 0 refers to R in RGBA8888 and H in {@link #rgb2hsl(float, float, float, float) HSLA} ints,
     * channel 1 refers to G or S, 2 refers to B or L, and 3 always refers to A.
     *
     * @param color   a packed int color in any 32-bit, 4-channel format
     * @param channel which channel to access, as an index from 0 to 3 inclusive
     * @return the non-packed float value of the requested channel, from 0.0f to 1.0f inclusive
     */
    public static float channel(int color, int channel) {
        return (color >>> 24 - ((channel & 3) << 3) & 255) / 255f;
    }

    /**
     * Given a packed int color and a channel value from 0 to 3, gets the value of that channel as an int from 0 to
     * 255 . Channel 0 refers to R in RGBA8888 and H in {@link #rgb2hsl(float, float, float, float) HSLA} ints,
     * channel 1 refers to G or S, 2 refers to B or L, and 3 always refers to A.
     *
     * @param color   a packed int color in any 32-bit, 4-channel format
     * @param channel which channel to access, as an index from 0 to 3 inclusive
     * @return the int value of the requested channel, from 0 to 255 inclusive
     */
    public static int channelInt(int color, int channel) {
        return (color >>> 24 - ((channel & 3) << 3) & 255);
    }

    /**
     * Interpolates from the RGBA8888 int color start towards end by change. Both start and end should be RGBA8888
     * ints, and change can be between 0f (keep start) and 1f (only use end). This is a good way to reduce allocations
     * of temporary Colors.
     *
     * @param s      the starting color as a packed int
     * @param e      the end/target color as a packed int
     * @param change how much to go from start toward end, as a float between 0 and 1; higher means closer to end
     * @return an RGBA8888 int that represents a color between start and end
     */
    public static int lerpColors(final int s, final int e, final float change) {
        final int
                sA = (s & 0xFE), sB = (s >>> 8) & 0xFF, sG = (s >>> 16) & 0xFF, sR = (s >>> 24) & 0xFF,
                eA = (e & 0xFE), eB = (e >>> 8) & 0xFF, eG = (e >>> 16) & 0xFF, eR = (e >>> 24) & 0xFF;
        return   ((((int) (sR + change * (eR - sR)) & 0xFF) << 24)
                | (((int) (sG + change * (eG - sG)) & 0xFF) << 16)
                | (((int) (sB + change * (eB - sB)) & 0xFF) << 8)
                | (((int) (sA + change * (eA - sA)) & 0xFE)     ));
    }

    /**
     * Given several colors, this gets an even mix of all colors in equal measure.
     * If {@code colors} is null or has no items, this returns 256.
     * This is mostly useful in conjunction with {@link IntArray}, using its {@code items}
     * for colors, typically 0 for offset, and its {@code size} for size.
     * @param colors an array of RGBA8888 int colors; all should use the same color space
     * @param offset the index of the first item in {@code colors} to use
     * @param size how many items from {@code colors} to use
     * @return an even mix of all colors given, as an RGBA8888 int color
     */
    public static int mix(int[] colors, int offset, int size) {
        int end = offset + size;
        if(colors == null || colors.length < end || offset < 0 || size <= 0)
            return 256; // transparent super-dark-blue, used to indicate "not found"
        int result = 256;
        while(colors[offset] == 256)
        {
            offset++;
        }
        if(offset < end)
            result = colors[offset];
        for (int i = offset + 1, o = end, denom = 2; i < o; i++, denom++) {
            if(colors[i] != 256)
                result = lerpColors(result, colors[i], 1f / denom);
            else --denom;
        }
        return result;
    }

    /**
     * Mixes any number of colors with arbitrary weights per-color. Takes an array of varargs of alternating ints
     * representing colors and weights, as with {@code color, weight, color, weight...}.
     * If {@code colors} is null or has no items, this returns 0 (fully-transparent black). Each color
     * should be an RGBA8888 int, and each weight should be greater than 0.
     * @param colors an array or varargs that should contain alternating {@code color, weight, color, weight...} ints
     * @return the mixed color, as an RGBA8888 int
     */
    public static int unevenMix(int... colors) {
        if(colors == null || colors.length == 0) return 0;
        if(colors.length <= 2) return colors[0];
        return unevenMix(colors, 0, colors.length);
    }

    /**
     * Mixes any number of colors with arbitrary weights per-color. Takes an array of alternating ints representing
     * colors and weights, as with {@code color, weight, color, weight...}, starting at {@code offset} in the array and
     * continuing for {@code size} indices in the array. The {@code size} should be an even number 2 or greater,
     * otherwise it will be reduced by 1. The weights can be any non-negative int values; this method handles
     * normalizing them internally. Each color should an RGBA8888 int, and each weight should be greater than 0.
     * If {@code colors} is null or has no items, or if size &lt;= 0, this returns 0 (fully-transparent black).
     *
     * @param colors starting at {@code offset}, this should contain alternating {@code color, weight, color, weight...} ints
     * @param offset where to start reading from in {@code colors}
     * @param size how many indices to read from {@code colors}; must be an even number
     * @return the mixed color, as an RGBA8888 int
     */
    public static int unevenMix(int[] colors, int offset, int size) {
        size &= -2;
        final int end = offset + size;
        if(colors == null || colors.length < end || offset < 0 || size <= 0)
            return 256; // placeholder color
        while(colors[offset] == 256)
        {
            if((offset += 2) >= end) return 256;
        }
        int result = colors[offset];
        float current = colors[offset + 1], total = current;
        for (int i = offset+3; i < end; i += 2) {
            if(colors[i-1] != 256)
                total += colors[i];
        }
        total = 1f / total;
        current *= total;
        for (int i = offset+3; i < end; i += 2) {
            int mixColor = colors[i-1];
            if(mixColor == 256)
                continue;
            float weight = colors[i] * total;
            result = lerpColors(result, mixColor, weight / (current += weight));
        }
        return result;
    }

    /**
     * Interpolates from the int color start towards white by change. While change should be between 0f (return
     * start as-is) and 1f (return white), start should be an RGBA8888 color.
     * This is a good way to reduce allocations of temporary Colors, and is a little more efficient and clear than
     * using {@link #lerpColors(int, int, float)} to lerp towards
     * white. Unlike {@link #lerpColors(int, int, float)}, this keeps the alpha of start as-is.
     * @see #darken(int, float) the counterpart method that darkens an int color
     * @param start the starting color as an RGBA8888 int
     * @param change how much to go from start toward white, as a float between 0 and 1; higher means closer to white
     * @return an RGBA8888 int that represents a color between start and white
     */
    public static int lighten(final int start, final float change) {
        final int r = start >>> 24, g = start >>> 16 & 0xFF, b = start >>> 8 & 0xFF,
                a = start & 0x000000FE;
        return  ((int) (r + (0xFF - r) * change) & 0xFF) << 24 |
                ((int) (g + (0xFF - g) * change) & 0xFF) << 16 |
                ((int) (b + (0xFF - b) * change) & 0xFF) << 8 |
                a;
    }

    /**
     * Interpolates from the int color start towards black by change. While change should be between 0f (return
     * start as-is) and 1f (return black), start should be an RGBA8888 color.
     * This is a good way to reduce allocations of temporary Colors, and is a little more efficient and clear than
     * using {@link #lerpColors(int, int, float)} to lerp towards
     * black. Unlike {@link #lerpColors(int, int, float)}, this keeps the alpha of start as-is.
     * @see #lighten(int, float) the counterpart method that lightens an int color
     * @param start the starting color as an RGBA8888 int
     * @param change how much to go from start toward black, as a float between 0 and 1; higher means closer to black
     * @return an RGBA8888 int that represents a color between start and black
     */
    public static int darken(final int start, final float change) {
        final int r = start >>> 24, g = start >>> 16 & 0xFF, b = start >>> 8 & 0xFF,
                a = start & 0x000000FE;
        final float ch = 1f - change;
        return  ((int) (r * ch) & 0xFF) << 24 |
                ((int) (g * ch) & 0xFF) << 16 |
                ((int) (b * ch) & 0xFF) << 8 |
                a;
    }

    /**
     * Brings the chromatic components of {@code start} closer to grayscale by {@code change} (desaturating them). While
     * change should be between 0f (return start as-is) and 1f (return fully gray), start should be an RGBA8888 int
     * color. This leaves alpha alone.
     * <br>
     * <a href="http://www.graficaobscura.com/matrix/index.html">The algorithm used is from here</a>.
     * @see #enrich(int, float) the counterpart method that makes an int color more saturated
     * @param start the starting color as an RGBA8888 int
     * @param change how much to change start to a desaturated color, as a float between 0 and 1; higher means a less saturated result
     * @return an RGBA8888 int that represents a color between start and a desaturated color
     */
    public static int dullen(final int start, final float change) {
        final float rc = 0.32627f, gc = 0.3678f, bc = 0.30593001f;
        final int r = start >>> 24, g = start >>> 16 & 0xFF, b = start >>> 8 & 0xFF,
                a = start & 0x000000FE;
        final float ch = 1f - change, rw = change * rc, gw = change * gc, bw = change * bc;
        return  (int) Math.min(Math.max(r * (rw+ch) + g * rw + b * rw, 0), 255) << 24 |
                (int) Math.min(Math.max(r * gw + g * (gw+ch) + b * gw, 0), 255) << 16 |
                (int) Math.min(Math.max(r * bw + g * bw + b * (bw+ch), 0), 255) << 8  |
                a;
    }

    /**
     * Pushes the chromatic components of {@code start} away from grayscale by change (saturating them). While change
     * should be between 0f (return start as-is) and 1f (return maximally saturated), start should be an RGBA8888 int
     * color.
     * <br>
     * <a href="http://www.graficaobscura.com/matrix/index.html">The algorithm used is from here</a>.
     * @see #dullen(int, float) the counterpart method that makes an int color less saturated
     * @param start the starting color as an RGBA8888 int
     * @param change how much to change start to a saturated color, as a float between 0 and 1; higher means a more saturated result
     * @return an RGBA8888 int that represents a color between start and a saturated color
     */
    public static int enrich(final int start, final float change) {
        final float rc = -0.32627f, gc = -0.3678f, bc = -0.30593001f;
        final int r = start >>> 24, g = start >>> 16 & 0xFF, b = start >>> 8 & 0xFF,
                a = start & 0x000000FE;
        final float ch = 1f + change, rw = change * rc, gw = change * gc, bw = change * bc;
        return  (int) Math.min(Math.max(r * (rw+ch) + g * rw + b * rw, 0), 255) << 24 |
                (int) Math.min(Math.max(r * gw + g * (gw+ch) + b * gw, 0), 255) << 16 |
                (int) Math.min(Math.max(r * bw + g * bw + b * (bw+ch), 0), 255) << 8  |
                a;
    }

    /**
     * Gets an "offset color" for the original {@code color} where high red, green, or blue channels become low values
     * in that same channel, and vice versa, then blends the original with that offset, using more of the offset if
     * {@code power} is higher (closer to 1.0f). It is usually fine for {@code power} to be 0.5f . This can look...
     * pretty strange for some input colors, and you may want {@link #offsetLightness(int, float)} instead.
     * @param color the original color as an RGBA8888 int
     * @param power between 0.0f and 1.0f, this is how heavily the offset color should factor in to the result
     * @return a mix between {@code color} and its offset, with higher {@code power} using more of the offset
     */
    public static int offset(final int color, float power) {
        return lerpColors(color, color ^ 0x80808000, power);
    }

    /**
     * Gets an "offset color" for the original {@code color}, lightening it if it is perceptually dark (under 40% luma
     * by a simplistic measurement) or darkening it if it is perceptually light. This essentially uses the lightness to
     * determine whether to call {@link #lighten(int, float) lighten(color, power)} or
     * {@link #darken(int, float) darken(color, power)}. It is usually fine for {@code power} to be 0.5f . This leaves
     * hue alone, and doesn't change saturation much. The lightness measurement is effectively
     * {@code red * 3/8 + green * 1/2 + blue * 1/8}.
     * @param color the original color as an RGBA8888 int
     * @param power between 0.0f and 1.0f, this is how much this should either lighten or darken the result
     * @return a variant on {@code color}, either lighter or darker depending on its original lightness
     */
    public static int offsetLightness(final int color, float power) {
        int light = (color >>> 24) * 3 + (color >>> 14 & 0x3FC) + (color >>> 8 & 0xFF); // ranges from 0 to 2020
        if(light < 808) // under 40% luma
            return lighten(color, power);
        return darken(color, power);
    }

    /**
     * Given an RGBA8888 or HSLA color as an int, this multiplies the alpha of that color by multiplier and returns
     * another int color of the same format passed in. This clamps the alpha if it would go below 0 or above 255, and
     * leaves the RGB or HSL channels alone.
     *
     * @param color      an RGBA8888 or HSLA color
     * @param multiplier a multiplier to apply to color's alpha
     * @return another color of the same format as the one given, with alpha multiplied
     */
    public static int multiplyAlpha(int color, float multiplier) {
        return (color & 0xFFFFFF00) | Math.min(Math.max((int) ((color & 0xFF) * multiplier), 0), 255);
    }

    /**
     * Given a packed ABGR float color, this multiplies the alpha of that color by multiplier and returns
     * another float color of the same format passed in. This clamps the alpha if it would go below 0 or above 255, and
     * leaves the RGB channels alone.
     *
     * @param color      an RGBA8888 or HSLA color
     * @param multiplier a multiplier to apply to color's alpha
     * @return another color of the same format as the one given, with alpha multiplied
     */
    public static float multiplyAlpha(float color, float multiplier) {
        int bits = NumberUtils.floatToIntBits(color);
        return NumberUtils.intBitsToFloat((bits & 0x00FFFFFF) | Math.min(Math.max((int) ((bits >>> 25) * multiplier), 0), 127) << 25);
    }

    /**
     * Given any purely-non-null 2D int array representing RGBA or HSLA colors, this multiplies the alpha channel of
     * each color by multiplier, modifying the given array, and returns the changed array for chaining. This uses
     * {@link #multiplyAlpha(int, float)} internally, so its documentation applies.
     *
     * @param colors     a 2D int array of RGBA or HSLA colors, none of which can include null arrays
     * @param multiplier a multiplier to apply to each color's alpha
     * @return colors, after having each item's alpha multiplied
     */
    public static int[][] multiplyAllAlpha(int[][] colors, float multiplier) {
        for (int x = 0; x < colors.length; x++) {
            for (int y = 0; y < colors[x].length; y++) {
                colors[x][y] = multiplyAlpha(colors[x][y], multiplier);
            }
        }
        return colors;
    }

    /**
     * This simply looks up {@code key} in {@link Colors}, returning 256 (fully transparent,
     * extremely dark blue) if no Color exists by that exact name (case-sensitive), or returning the RGBA8888 value
     * of the color otherwise. All color names are {@code ALL_CAPS} in libGDX's Colors collection by default.
     * @param key a color name, typically in {@code ALL_CAPS}
     * @return the RGBA8888 int color matching the name, or {@code 256} if the name was not found
     */
    public static int lookupInColors(final String key) {
        final Color c = Colors.get(key);
        return c == null ? 256 : Color.rgba8888(c);
    }

}
