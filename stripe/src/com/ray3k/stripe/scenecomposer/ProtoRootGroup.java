package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.utils.Array;

public class ProtoRootGroup extends ProtoActor implements SimMultipleChildren {
    public Array<ProtoActor> children = new Array<ProtoActor>();
    public ProtoColor backgroundColor;
    public String skinPath = "skin.json";
    public String packageString = "com.mygdx.game";
    public String classString = "Core";
}
