package com.ray3k.stripe;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.Scaling;

/**
 * A layout widget that allows the user to specify the preferred size of a child actor. If the resulting dimensions
 * after layout are lesser/greater than the preferred size, the child actor is scaled using {@link
 * Group#setTransform(boolean)} and an applied {@link Scaling} factor. This allows layouts to have fixed dimensions, but
 * scale gracefully depending on layout dimensions.
 */
public class ScaleContainer extends WidgetGroup {
    private Actor actor;
    private float prefWidth;
    private float prefHeight;
    private Scaling scaling;
    private int align = Align.center;
    private boolean clip;

    public ScaleContainer(float prefWidth, float prefHeight) {
        this(prefWidth, prefHeight, null);
    }

    public ScaleContainer(float prefWidth, float prefHeight, Actor actor) {
        this(prefWidth, prefHeight, Scaling.stretch, actor);
    }

    public ScaleContainer(float prefWidth, float prefHeight, Scaling scaling, Actor actor) {
        this.scaling = scaling;
        setPrefWidth(prefWidth);
        setPrefHeight(prefHeight);
        setActor(actor);
    }

    @Override
    public void layout() {
        if (actor == null) return;
        if (actor instanceof Group) ((Group) actor).setTransform(true);

        actor.setSize(prefWidth, prefHeight);
        float width = getWidth();
        float height = getHeight();
        Vector2 size = scaling.apply(prefWidth, prefHeight, getWidth(), getHeight());
        actor.setScale(size.x / actor.getWidth(), size.y / actor.getHeight());

        float alignX, alignY;
        if ((align & Align.left) != 0)
            alignX = 0;
        else if ((align & Align.right) != 0)
            alignX = (int)(width - size.x);
        else
            alignX = (int)(width / 2 - size.x / 2);

        if ((align & Align.top) != 0)
            alignY = (int)(height - size.y);
        else if ((align & Align.bottom) != 0)
            alignY = 0;
        else
            alignY = (int)(height / 2 - size.y / 2);
        actor.setPosition(alignX, alignY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (clip) {
            batch.flush();
            if (clipBegin(getX(), getY(), getWidth(), getHeight())) {
                drawChildren(batch, parentAlpha);
                batch.flush();
                clipEnd();
            }
        } else {
            super.draw(batch, parentAlpha);
        }
    }

    public @Null Actor hit (float x, float y, boolean touchable) {
        if (clip) {
            if (touchable && getTouchable() == Touchable.disabled) return null;
            if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) return null;
        }
        return super.hit(x, y, touchable);
    }

    @Override
    public float getMinWidth() {
        return 0;
    }

    @Override
    public float getMinHeight() {
        return 0;
    }

    public void setPrefWidth(float prefWidth) {
        this.prefWidth = prefWidth;
    }

    public void setPrefHeight(float prefHeight) {
        this.prefHeight = prefHeight;
    }

    public void setPrefSize(float prefWidth, float prefHeight) {
        this.prefWidth = prefWidth;
        this.prefHeight = prefHeight;
    }

    public void setPrefSize(float prefSize) {
        this.prefWidth = prefSize;
        this.prefHeight = prefSize;
    }

    @Override
    public float getPrefWidth() {
        return prefWidth;
    }

    @Override
    public float getPrefHeight() {
        return prefHeight;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        if (this.actor != null) this.actor.remove();

        this.actor = actor;
        addActor(actor);
    }

    public Scaling getScaling() {
        return scaling;
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }

    public int getAlign() {
        return align;
    }

    public void setAlign(int align) {
        this.align = align;
    }

    public boolean isClip() {
        return clip;
    }

    public void setClip(boolean clip) {
        this.clip = clip;
    }

    @Override
    @Deprecated
    public void addActor(Actor actor) {
        super.addActor(actor);
    }

    @Override
    @Deprecated
    public void addActorAfter(Actor actorAfter, Actor actor) {
        super.addActorAfter(actorAfter, actor);
    }

    @Override
    @Deprecated
    public void addActorAt(int index, Actor actor) {
        super.addActorAt(index, actor);
    }

    @Override
    @Deprecated
    public void addActorBefore(Actor actorBefore, Actor actor) {
        super.addActorBefore(actorBefore, actor);
    }
}
