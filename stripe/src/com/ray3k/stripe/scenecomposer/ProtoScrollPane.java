package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.scenes.scene2d.Touchable;

public class ProtoScrollPane extends ProtoActor implements SimSingleChild {
    public String name;
    public ProtoStyle style;
    public boolean fadeScrollBars = true;
    public ProtoActor child;
    public boolean clamp;
    public boolean flickScroll = true;
    public float flingTime = 1f;
    public boolean forceScrollX;
    public boolean forceScrollY;
    public boolean overScrollX = true;
    public boolean overScrollY = true;
    public float overScrollDistance = 50;
    public float overScrollSpeedMin = 30;
    public float overScrollSpeedMax = 200;
    public boolean scrollBarBottom = true;
    public boolean scrollBarRight = true;
    public boolean scrollBarsOnTop;
    public boolean scrollBarsVisible = true;
    public boolean scrollBarTouch = true;
    public boolean scrollingDisabledX;
    public boolean scrollingDisabledY;
    public boolean smoothScrolling = true;
    public boolean variableSizeKnobs = true;
    public Touchable touchable = Touchable.enabled;
    public boolean visible = true;
}
