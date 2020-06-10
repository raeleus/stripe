package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.scenes.scene2d.Touchable;

public class ProtoSlider extends ProtoActor {
    public String name;
    public ProtoStyle style;
    public boolean disabled;
    public float value;
    public float minimum;
    public float maximum = 100;
    public float increment = 1;
    public boolean vertical;
    public float animationDuration;
    public Interpol animateInterpolation = Interpol.LINEAR;
    public boolean round = true;
    public Interpol visualInterpolation = Interpol.LINEAR;
    public Touchable touchable = Touchable.enabled;
}
