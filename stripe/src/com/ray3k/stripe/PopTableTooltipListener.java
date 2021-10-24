package com.ray3k.stripe;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Align;
import com.ray3k.stripe.PopTable.PopTableStyle;
import com.ray3k.stripe.PopTable.TableShowHideListener;

public class PopTableTooltipListener extends InputListener {
    protected PopTable popTable;
    private final int attachToMouseAlign;
    private final static Vector2 temp = new Vector2();
    
    public PopTableTooltipListener(int attachToMouseAlign, Skin skin) {
        this(attachToMouseAlign, skin.get(PopTableStyle.class));
    }
    
    public PopTableTooltipListener(int attachToMouseAlign, Skin skin, String style) {
        this(attachToMouseAlign, skin.get(style, PopTableStyle.class));
    }
    
    public PopTableTooltipListener(int attachToMouseAlign, PopTableStyle style) {
        popTable = new PopTable(style);
        popTable.setModal(false);
        popTable.setHideOnUnfocus(false);
        popTable.setTouchable(Touchable.disabled);
        this.attachToMouseAlign = attachToMouseAlign;
        popTable.addListener(new TableShowHideListener() {
            @Override
            public void tableShown(Event event) {
                PopTableTooltipListener.this.tableShown(event);
            }
            
            @Override
            public void tableHidden(Event event) {
                PopTableTooltipListener.this.tableHidden(event);
            }
        });
    }

    @Override
    public boolean mouseMoved(InputEvent event, float x, float y) {
        temp.set(x, y);
        event.getListenerActor().localToStageCoordinates(temp);
        switch (attachToMouseAlign) {
            case Align.bottomLeft:
            case Align.topLeft:
            case Align.left:
                temp.x -= popTable.getWidth();
                break;
            case Align.bottom:
            case Align.top:
            case Align.center:
                temp.x -= popTable.getWidth() / 2;
                break;
        }
        switch (attachToMouseAlign) {
            case Align.bottomLeft:
            case Align.bottomRight:
            case Align.bottom:
                temp.y -= popTable.getHeight();
                break;
            case Align.left:
            case Align.right:
            case Align.center:
                temp.y -= popTable.getHeight() / 2;
                break;
        }
        popTable.setPosition(temp.x, temp.y);
        return false;
    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if (event.getListenerActor().getStage() != null && (fromActor == null || !event.getListenerActor().isAscendantOf(fromActor))) {
            Stage stage = event.getListenerActor().getStage();
            Actor actor = event.getListenerActor();
            
            if (actor instanceof Disableable) {
                if (((Disableable) actor).isDisabled()) return;
            }

            temp.set(x, y);
            event.getListenerActor().localToStageCoordinates(temp);
            switch (attachToMouseAlign) {
                case Align.bottomLeft:
                case Align.topLeft:
                case Align.left:
                    temp.x -= popTable.getWidth();
                    break;
                case Align.bottom:
                case Align.top:
                case Align.center:
                    temp.x -= popTable.getWidth() / 2;
                    break;
            }
            switch (attachToMouseAlign) {
                case Align.bottomLeft:
                case Align.bottomRight:
                case Align.bottom:
                    temp.y -= popTable.getHeight();
                    break;
                case Align.left:
                case Align.right:
                case Align.center:
                    temp.y -= popTable.getHeight() / 2;
                    break;
            }
            
            popTable.show(stage);
            popTable.moveToInsideStage();
            popTable.setPosition(temp.x, temp.y);
        }
    }
    
    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
        if (toActor == null || !event.getListenerActor().isAscendantOf(toActor)) {
            popTable.hide();
        }
    }
    
    public PopTable getPopTable() {
        return popTable;
    }
    
    /**
     * Override this method to be performed when the popTable is hidden or dismissed.
     * @param event The event associated with the table being shown.
     */
    public void tableShown(Event event) {
    
    }
    
    /**
     * Override this method to be performed when the popTable is hidden or dismissed.
     * @param event The event associated with the table being hidden.
     */
    public void tableHidden(Event event) {
    
    }
}
