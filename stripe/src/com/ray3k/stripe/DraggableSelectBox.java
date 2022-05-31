package com.ray3k.stripe;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RemoveActorAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ray3k.stripe.DraggableList.DraggableListListener;
import com.ray3k.stripe.DraggableList.DraggableListRemovedEvent;
import com.ray3k.stripe.DraggableList.DraggableListReorderedEvent;
import com.ray3k.stripe.DraggableList.DraggableListSelectedEvent;
import com.ray3k.stripe.DraggableTextList.DraggableTextListStyle;
import com.ray3k.stripe.PopTable.PopTableStyle;

public class DraggableSelectBox extends TextButton {
    private final DraggableTextList draggableTextList;
    private final PopTable popTable;
    private final ScrollPane scrollPane;
    private final Cell<ScrollPane> scrollPaneCell;
    private DraggableSelectBoxStyle style;
    
    public DraggableSelectBox(Skin skin) {
        this(skin, "default");
    }
    
    public DraggableSelectBox(Skin skin, String styleName) {
        this(skin.get(styleName, DraggableSelectBoxStyle.class));
    }
    
    public DraggableSelectBox(DraggableSelectBoxStyle style) {
        super("", style);
        this.style = style;
    
        getLabelCell().expandX();
        
        DraggableTextListStyle draggableTextListStyle = new DraggableTextListStyle(style.draggableTextListStyle);
        draggableTextList = new DraggableTextList(true, draggableTextListStyle);
        draggableTextList.addListener(new DraggableListListener() {
            @Override
            public void removed(Actor actor) {
                fire(new DraggableListRemovedEvent(actor));
            }
    
            @Override
            public void reordered(Actor actor, int indexBefore, int indexAfter) {
                fire(new DraggableListReorderedEvent(actor, indexBefore, indexAfter));
            }
    
            @Override
            public void selected(Actor actor) {
                fire(new DraggableListSelectedEvent(actor));
                setText(draggableTextList.getSelected().toString());
                popTable.hide();
            }
        });
        
        PopTableClickListener popTableClickListener = new PopTableClickListener(Align.bottom, Align.bottom, style.popTableStyle){
            @Override
            public void tableShown(Event event) {
                scrollPaneCell.minWidth(DraggableSelectBox.this.getWidth());
                getStage().setScrollFocus(scrollPane);
            }
    
            @Override
            public void tableHidden(Event event) {
                setChecked(false);
            }
        };
        popTable = popTableClickListener.popTable;
        addListener(popTableClickListener);
        
        scrollPane = new ScrollPane(draggableTextList, style.scrollPaneStyle);
        scrollPaneCell = popTable.add(scrollPane).growX();
    }
    
    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        if (stage == null) popTable.hide();
    }
    
    @Override
    public void setStyle(ButtonStyle style) {
        if (style == null) throw new NullPointerException("style cannot be null");
        if (!(style instanceof DraggableSelectBoxStyle)) throw new IllegalArgumentException("style must be a DraggableSelectBoxStyle.");
        super.setStyle(style);
        this.style = (DraggableSelectBoxStyle) style;
        if (popTable != null) popTable.setStyle(this.style.popTableStyle);
        if (draggableTextList != null) draggableTextList.setStyle(this.style.draggableTextListStyle);
        if (scrollPane != null) scrollPane.setStyle(((DraggableSelectBoxStyle) style).scrollPaneStyle);
    }
    
    public int getSelectedIndex() {
        return draggableTextList.getSelectedIndex();
    }
    
    public CharSequence getSelected() {
        return draggableTextList.getSelected();
    }
    
    public DraggableTextList getDraggableTextList() {
        return draggableTextList;
    }
    
    public void setSelected(int index) {
        draggableTextList.setSelected(index);
        setText(draggableTextList.getSelected().toString());
    }
    
    public void setSelected(String text) {
        draggableTextList.setSelected(text);
        setText(text);
    }
    
    public Array<String> getItems() {
        return draggableTextList.getTexts();
    }
    
    public void setItems(Array<String> items) {
        draggableTextList.clearChildren();
        draggableTextList.addAllTexts(items);
        setText(draggableTextList.getSelected().toString());
    }
    
    public void addItem(String item) {
        draggableTextList.addText(item);
    }
    
    public void clearItems() {
        draggableTextList.clearChildren();
    }
    
    public void setAlignment(int alignment) {
        getLabel().setAlignment(alignment);
    }
    
    public PopTable getPopTable() {
        return popTable;
    }
    
    public ScrollPane getScrollPane() {
        return scrollPane;
    }
    
    public static class DraggableSelectBoxStyle extends TextButtonStyle {
        public PopTableStyle popTableStyle;
        public DraggableTextListStyle draggableTextListStyle;
        public ScrollPaneStyle scrollPaneStyle;
        
        public DraggableSelectBoxStyle() {
        
        }
        
        public DraggableSelectBoxStyle(DraggableSelectBoxStyle style) {
            popTableStyle = style.popTableStyle;
            draggableTextListStyle = style.draggableTextListStyle;
            scrollPaneStyle = style.scrollPaneStyle;
        }
    }
}
