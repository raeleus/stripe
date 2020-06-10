package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class ProtoStack extends ProtoActor implements SimMultipleChildren {
    public String name;
    public Touchable touchable = Touchable.enabled;
    public Array<ProtoActor> children = new Array<ProtoActor>();
}
