package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

public class ProtoSelectBox extends ProtoActor {
    public String name;
    public ProtoStyle style;
    public boolean disabled;
    public int maxListCount;
    public Array<String> list = new Array<String>();
    public int alignment = Align.center;
    public int selected;
    public boolean scrollingDisabled;
}
