package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

public class ProtoTable extends ProtoActor implements SimMultipleChildren {
    public Array<ProtoCell> cells = new Array<ProtoCell>();
    public String name;
    public ProtoDrawable background;
    public ProtoColor color;
    public float padLeft;
    public float padRight;
    public float padTop;
    public float padBottom;
    public int alignment = Align.center;
    public boolean fillParent;
}
