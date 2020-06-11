package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Array;

public class ProtoList extends ProtoActor {
    public String name;
    public ProtoStyle style;
    public Touchable touchable = Touchable.enabled;
    public boolean visible = true;
    public Array<String> list = new Array<String>();
}
