package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class ProtoSplitPane extends ProtoActor implements SimMultipleChildren {
    public String name;
    public ProtoStyle style;
    public ProtoActor childFirst;
    public ProtoActor childSecond;
    public boolean vertical;
    public float split = .5f;
    public float splitMin;
    public float splitMax = 1;
    public Touchable touchable = Touchable.enabled;
    public boolean visible = true;
    public transient Array<ProtoActor> tempChildren = new Array<ProtoActor>();
}
