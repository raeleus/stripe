package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.utils.Array;

public class ProtoNode extends ProtoActor implements SimMultipleChildren {
    public ProtoActor actor;
    public Array<ProtoNode> nodes = new Array<ProtoNode>();
    public boolean expanded;
    public ProtoDrawable icon;
    public boolean selectable = true;
}
