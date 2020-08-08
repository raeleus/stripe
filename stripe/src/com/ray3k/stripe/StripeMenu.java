package com.ray3k.stripe;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.ray3k.stripe.StripeMenuBar.KeyboardShortcut;

public interface StripeMenu {
    StripeMenu menu(String name, EventListener... listeners);
    StripeMenu item(String name, EventListener... listeners);
    StripeMenu item(String name, KeyboardShortcut keyboardShortcut,
                    EventListener... listeners);
    StripeMenu parent();
    TextButton getParentButton();
    TextButton findButton(String name);
    StripeMenu findMenu(String name);
    Cell findCell(String name);
    void setDisabled(boolean disabled);
    boolean isDisabled();
    void clear();
}