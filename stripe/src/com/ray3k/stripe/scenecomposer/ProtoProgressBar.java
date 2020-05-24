package com.ray3k.stripe.scenecomposer;

public class ProtoProgressBar extends ProtoActor {
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
}
