/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2020 Raymond Buckley
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package com.ray3k.stripe.scenecomposer;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton.ImageTextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane.SplitPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad.TouchpadStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Tree.TreeStyle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class used to generate Scene2D widgets exported to JSON by Scene Composer. Scene Composer is a module from Skin
 * Composer that allows users to lay out a Scene2D stage visually.
 */
public class SceneComposerStageBuilder {
    private final Json json;
    
    /**
     * Creates a builder and associated JSON loader.
     */
    public SceneComposerStageBuilder() {
        json = new Json();
        json.setSerializer(ProtoColor.class, new Json.Serializer<ProtoColor>() {
            @Override
            public void write(Json json, ProtoColor object, Class knownType) {
            
            }
        
            @Override
            public ProtoColor read(Json json, JsonValue jsonData, Class type) {
                if (jsonData.isNull()) return null;
                return new ProtoColor(jsonData.asString());
            }
        });
        json.setSerializer(ProtoDrawable.class, new Json.Serializer<ProtoDrawable>() {
            @Override
            public void write(Json json, ProtoDrawable object, Class knownType) {
            
            }
        
            @Override
            public ProtoDrawable read(Json json, JsonValue jsonData, Class type) {
                if (jsonData.isNull()) return null;
                return new ProtoDrawable(jsonData.asString());
            }
        });
        json.setSerializer(ProtoStyle.class, new Json.Serializer<ProtoStyle>() {
            @Override
            public void write(Json json, ProtoStyle object, Class knownType) {
            
            }
        
            @Override
            public ProtoStyle read(Json json, JsonValue jsonData, Class type) {
                if (jsonData.isNull()) return null;
                return new ProtoStyle(jsonData.getString("name"));
            }
        });
        json.setOutputType(JsonWriter.OutputType.json);
        json.setUsePrototypes(false);
        json.addClassTag("Actor", ProtoActor.class);
        json.addClassTag("Button", ProtoButton.class);
        json.addClassTag("Cell", ProtoCell.class);
        json.addClassTag("CheckBox", ProtoCheckBox.class);
        json.addClassTag("Container", ProtoContainer.class);
        json.addClassTag("HorizontalGroup", ProtoHorizontalGroup.class);
        json.addClassTag("Image", ProtoImage.class);
        json.addClassTag("ImageButton", ProtoImageButton.class);
        json.addClassTag("ImageTextButton", ProtoImageTextButton.class);
        json.addClassTag("Label", ProtoLabel.class);
        json.addClassTag("List", ProtoList.class);
        json.addClassTag("Node", ProtoNode.class);
        json.addClassTag("ProgressBar", ProtoProgressBar.class);
        json.addClassTag("Root", ProtoRootGroup.class);
        json.addClassTag("ScrollPane", ProtoScrollPane.class);
        json.addClassTag("SelectBox", ProtoSelectBox.class);
        json.addClassTag("Slider", ProtoSlider.class);
        json.addClassTag("SplitPane", ProtoSplitPane.class);
        json.addClassTag("Stack", ProtoStack.class);
        json.addClassTag("Table", ProtoTable.class);
        json.addClassTag("TextArea", ProtoTextArea.class);
        json.addClassTag("TextButton", ProtoTextButton.class);
        json.addClassTag("TextField", ProtoTextField.class);
        json.addClassTag("TouchPad", ProtoTouchPad.class);
        json.addClassTag("Tree", ProtoTree.class);
        json.addClassTag("VerticalGroup", ProtoVerticalGroup.class);
    }
    
    /**
     * Loads widgets from the provided JSON file. The widgets are dependent on a properly defined skin with styles that
     * correlate to the ones defined in Scene Composer.
     * @param skin The skin to construct the widgets from
     * @param jsonFile The JSON file to load the layout from.
     * @return An array of tables containing the layout.
     */
    public Array<Table> build(Skin skin, FileHandle jsonFile) {
        ProtoRootGroup rootActor = json.fromJson(ProtoRootGroup.class, jsonFile);
    
        Array<Table> tables = new Array<Table>();
        for (ProtoActor proto : rootActor.children) {
            tables.add((Table) createPreviewWidget(proto, skin));
        }
        
        return tables;
    }
    
    /**
     * Loads widgets from the provided JSON file and adds them to the supplied Stage. The widgets are dependent on a
     * properly defined skin with styles that correlate to the ones defined in Scene Composer.
     * @param stage The stage to add the layout to.
     * @param skin The skin to construct the widgets from
     * @param jsonFile The JSON file to load the layout from.
     * @return An array of tables containing the layout.
     */
    public Array<Table> build(Stage stage, Skin skin, FileHandle jsonFile) {
        Array<Table> tables = build(skin, jsonFile);
        for (Table table : tables) {
            stage.addActor(table);
        }
        return tables;
    }
    
    private String convertEscapedCharacters(String string) {
        string = string.replaceAll("(?<!\\\\)\\\\n", "\n")
                .replace("(?<!\\\\)\\\\t", "\t")
                .replace("(?<!\\\\)\\\\r", "\r");
        String result = string;
        Pattern pattern = Pattern.compile("(?<!\\\\)(\\\\u[\\d,a-f,A-F]{4})");
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            result = result.replaceFirst("(?<!\\\\)(\\\\u[\\d,a-f,A-F]{4})", new String(Character.toChars(Integer.parseInt(matcher.group().substring(2), 16))));
        }
        return result.replace("\\\\", "\\");
    }
    
    private Actor createPreviewWidget(ProtoActor protoActor, Skin skin) {
        Actor actor = null;
        
        if (protoActor instanceof ProtoTable) {
            ProtoTable protoTable = (ProtoTable) protoActor;
            Table table = new Table();
            actor = table;
            
            table.setName(protoTable.name);
            
            if (protoTable.background != null) {
                table.setBackground(skin.getDrawable(protoTable.background.name));
            }
            
            if (protoTable.color != null) {
                table.setColor(skin.getColor(protoTable.color.name));
            }
            
            table.pad(protoTable.padTop, protoTable.padLeft, protoTable.padBottom, protoTable.padRight);
            
            table.align(protoTable.alignment);
            
            table.setFillParent(protoTable.fillParent);
            
            int row = 0;
            for (ProtoCell protoCell : protoTable.cells) {
                if (protoCell.row > row) {
                    table.row();
                    row = protoCell.row;
                }
                
                Actor child = createPreviewWidget(protoCell.child, skin);
                Cell cell = table.add(child).fill(protoCell.fillX, protoCell.fillY).expand(protoCell.expandX, protoCell.expandY);
                cell.pad(protoCell.padTop, protoCell.padLeft, protoCell.padBottom, protoCell.padRight);
                cell.space(protoCell.spaceTop, protoCell.spaceLeft, protoCell.spaceBottom, protoCell.spaceRight);
                cell.align(protoCell.alignment).uniform(protoCell.uniformX, protoCell.uniformY).colspan(protoCell.colSpan);
                
                if (protoCell.minWidth >= 0) {
                    cell.minWidth(protoCell.minWidth);
                }
                
                if (protoCell.minHeight >= 0) {
                    cell.minHeight(protoCell.minHeight);
                }
                
                if (protoCell.maxWidth >= 0) {
                    cell.maxWidth(protoCell.maxWidth);
                }
                
                if (protoCell.maxHeight >= 0) {
                    cell.maxHeight(protoCell.maxHeight);
                }
                
                if (protoCell.preferredWidth >= 0) {
                    cell.prefWidth(protoCell.preferredWidth);
                }
                
                if (protoCell.preferredHeight >= 0) {
                    cell.prefHeight(protoCell.preferredHeight);
                }
            }
        } else if (protoActor instanceof ProtoTextButton) {
            ProtoTextButton protoTextButton = (ProtoTextButton) protoActor;
            if (protoTextButton.style != null) {
                TextButtonStyle style = skin.get(protoTextButton.style.name, TextButtonStyle.class);
                TextButton textButton = new TextButton(protoTextButton.text == null ? "" : convertEscapedCharacters(protoTextButton.text), style);
                textButton.setName(protoTextButton.name);
                textButton.setChecked(protoTextButton.checked);
                textButton.setDisabled(protoTextButton.disabled);
                if (protoTextButton.color != null) {
                    textButton.setColor(skin.getColor(protoTextButton.color.name));
                }
                
                if (!MathUtils.isZero(protoTextButton.padTop)) textButton.padTop(protoTextButton.padTop);
                if (!MathUtils.isZero(protoTextButton.padBottom)) textButton.padTop(protoTextButton.padBottom);
                if (!MathUtils.isZero(protoTextButton.padLeft)) textButton.padTop(protoTextButton.padLeft);
                if (!MathUtils.isZero(protoTextButton.padRight)) textButton.padTop(protoTextButton.padRight);
                
                actor = textButton;
            }
        } else if (protoActor instanceof ProtoButton) {
            ProtoButton protoButton = (ProtoButton) protoActor;
            if (protoButton.style != null) {
                ButtonStyle style = skin.get(protoButton.style.name, ButtonStyle.class);
                Button button = new Button(style);
                button.setName(protoButton.name);
                button.setChecked(protoButton.checked);
                button.setDisabled(protoButton.disabled);
                if (protoButton.color != null) {
                    button.setColor(skin.getColor(protoButton.color.name));
                }
                button.pad(protoButton.padTop, protoButton.padLeft, protoButton.padBottom, protoButton.padRight);
                actor = button;
            }
        }  else if (protoActor instanceof ProtoImageButton) {
            ProtoImageButton protoImageButton = (ProtoImageButton) protoActor;
            if (protoImageButton.style != null) {
                ImageButtonStyle style = skin.get(protoImageButton.style.name, ImageButtonStyle.class);
                ImageButton imageButton = new ImageButton(style);
                imageButton.setName(protoImageButton.name);
                imageButton.setChecked(protoImageButton.checked);
                imageButton.setDisabled(protoImageButton.disabled);
                if (protoImageButton.color != null) {
                    imageButton.setColor(skin.getColor(protoImageButton.color.name));
                }
                
                if (!MathUtils.isZero(protoImageButton.padTop)) imageButton.padTop(protoImageButton.padTop);
                if (!MathUtils.isZero(protoImageButton.padBottom)) imageButton.padTop(protoImageButton.padBottom);
                if (!MathUtils.isZero(protoImageButton.padLeft)) imageButton.padTop(protoImageButton.padLeft);
                if (!MathUtils.isZero(protoImageButton.padRight)) imageButton.padTop(protoImageButton.padRight);
                
                actor = imageButton;
            }
        } else if (protoActor instanceof ProtoImageTextButton) {
            ProtoImageTextButton protoImageTextButton = (ProtoImageTextButton) protoActor;
            if (protoImageTextButton.style != null) {
                ImageTextButtonStyle style = skin.get(protoImageTextButton.style.name, ImageTextButtonStyle.class);
                ImageTextButton imageTextButton = new ImageTextButton(protoImageTextButton.text == null ? "" : convertEscapedCharacters(protoImageTextButton.text), style);
                imageTextButton.setName(protoImageTextButton.name);
                imageTextButton.setChecked(protoImageTextButton.checked);
                imageTextButton.setDisabled(protoImageTextButton.disabled);
                if (protoImageTextButton.color != null) {
                    imageTextButton.setColor(skin.getColor(protoImageTextButton.color.name));
                }
                
                if (!MathUtils.isZero(protoImageTextButton.padTop)) imageTextButton.padTop(protoImageTextButton.padTop);
                if (!MathUtils.isZero(protoImageTextButton.padBottom)) imageTextButton.padTop(protoImageTextButton.padBottom);
                if (!MathUtils.isZero(protoImageTextButton.padLeft)) imageTextButton.padTop(protoImageTextButton.padLeft);
                if (!MathUtils.isZero(protoImageTextButton.padRight)) imageTextButton.padTop(protoImageTextButton.padRight);
                
                actor = imageTextButton;
            }
        } else if (protoActor instanceof ProtoCheckBox) {
            ProtoCheckBox protoCheckBox = (ProtoCheckBox) protoActor;
            if (protoCheckBox.style != null) {
                CheckBoxStyle style = skin.get(protoCheckBox.style.name, CheckBoxStyle.class);
                CheckBox checkBox = new CheckBox(protoCheckBox.text == null ? "" : convertEscapedCharacters(protoCheckBox.text), style);
                checkBox.setName(protoCheckBox.name);
                checkBox.setChecked(protoCheckBox.checked);
                checkBox.setDisabled(protoCheckBox.disabled);
                if (protoCheckBox.color != null) {
                    checkBox.setColor(skin.getColor(protoCheckBox.color.name));
                }
                
                if (!MathUtils.isZero(protoCheckBox.padTop)) checkBox.padTop(protoCheckBox.padTop);
                if (!MathUtils.isZero(protoCheckBox.padBottom)) checkBox.padTop(protoCheckBox.padBottom);
                if (!MathUtils.isZero(protoCheckBox.padLeft)) checkBox.padTop(protoCheckBox.padLeft);
                if (!MathUtils.isZero(protoCheckBox.padRight)) checkBox.padTop(protoCheckBox.padRight);
                
                actor = checkBox;
            }
        } else if (protoActor instanceof ProtoImage) {
            ProtoImage protoImage = (ProtoImage) protoActor;
            if (protoImage.drawable != null) {
                Image image = new Image(skin.getDrawable(protoImage.drawable.name));
                image.setScaling(protoImage.scaling);
                actor = image;
            }
        } else if (protoActor instanceof ProtoLabel) {
            ProtoLabel protoLabel = (ProtoLabel) protoActor;
            if (protoLabel.style != null) {
                LabelStyle style = skin.get(protoLabel.style.name, LabelStyle.class);
                Label label = new Label(protoLabel.text == null ? "" : convertEscapedCharacters(protoLabel.text), style);
                label.setName(protoLabel.name);
                label.setAlignment(protoLabel.textAlignment);
                if (protoLabel.ellipsis) {
                    label.setEllipsis(protoLabel.ellipsisString);
                }
                label.setWrap(protoLabel.wrap);
                if (protoLabel.color != null) label.setColor(skin.getColor(protoLabel.color.name));
                actor = label;
            }
        } else if (protoActor instanceof ProtoList) {
            ProtoList protoList = (ProtoList) protoActor;
            if (protoList.style != null) {
                ListStyle style = skin.get(protoList.style.name, ListStyle.class);
                List<String> list = new List<String>(style);
                list.setName(protoList.name);
                Array<String> newList = new Array<String>();
                for (String item : protoList.list) {
                    newList.add(convertEscapedCharacters(item));
                }
                list.setItems(newList);
                actor = list;
            }
        } else if (protoActor instanceof ProtoProgressBar) {
            ProtoProgressBar proto = (ProtoProgressBar) protoActor;
            if (proto.style != null) {
                ProgressBarStyle style = skin.get(proto.style.name, ProgressBarStyle.class);
                ProgressBar progressBar = new ProgressBar(proto.minimum, proto.maximum, proto.increment, proto.vertical, style);
                progressBar.setName(proto.name);
                progressBar.setDisabled(proto.disabled);
                progressBar.setValue(proto.value);
                progressBar.setAnimateDuration(proto.animationDuration);
                progressBar.setAnimateInterpolation(proto.animateInterpolation.interpolation);
                progressBar.setRound(proto.round);
                progressBar.setVisualInterpolation(proto.visualInterpolation.interpolation);
                actor = progressBar;
            }
        } else if (protoActor instanceof ProtoSelectBox) {
            ProtoSelectBox proto = (ProtoSelectBox) protoActor;
            if (proto.list.size > 0 && proto.style != null) {
                SelectBoxStyle style = skin.get(proto.style.name, SelectBoxStyle.class);
                SelectBox<String> selectBox = new SelectBox<String>(style);
                selectBox.setName(proto.name);
                selectBox.setDisabled(proto.disabled);
                selectBox.setMaxListCount(proto.maxListCount);
                Array<String> newList = new Array<String>();
                for (String item : proto.list) {
                    newList.add(convertEscapedCharacters(item));
                }
                selectBox.setItems(newList);
                selectBox.setAlignment(proto.alignment);
                selectBox.setSelectedIndex(proto.selected);
                selectBox.setScrollingDisabled(proto.scrollingDisabled);
                actor = selectBox;
            }
        } else if (protoActor instanceof ProtoSlider) {
            ProtoSlider proto = (ProtoSlider) protoActor;
            if (proto.style != null) {
                SliderStyle style = skin.get(proto.style.name, SliderStyle.class);
                Slider slider = new Slider(proto.minimum, proto.maximum, proto.increment, proto.vertical, style);
                slider.setName(proto.name);
                slider.setDisabled(proto.disabled);
                slider.setValue(proto.value);
                slider.setAnimateDuration(proto.animationDuration);
                slider.setAnimateInterpolation(proto.animateInterpolation.interpolation);
                slider.setRound(proto.round);
                slider.setVisualInterpolation(proto.visualInterpolation.interpolation);
                actor = slider;
            }
        } else if (protoActor instanceof ProtoTextField) {
            ProtoTextField proto = (ProtoTextField) protoActor;
            if (proto.style != null) {
                TextFieldStyle style = skin.get(proto.style.name, TextFieldStyle.class);
                TextField textField = new TextField(proto.text == null ? "" : convertEscapedCharacters(proto.text), style);
                textField.setName(proto.name);
                textField.setPasswordCharacter(proto.passwordCharacter);
                textField.setPasswordMode(proto.passwordMode);
                textField.setAlignment(proto.alignment);
                textField.setDisabled(proto.disabled);
                textField.setCursorPosition(proto.cursorPosition);
                if (proto.selectAll) {
                    textField.setSelection(0, Math.max(textField.getText().length() - 1, 0));
                } else {
                    textField.setSelection(proto.selectionStart, proto.selectionEnd);
                }
                textField.setFocusTraversal(proto.focusTraversal);
                textField.setMaxLength(proto.maxLength);
                textField.setMessageText(proto.messageText);
                actor = textField;
            }
        } else if (protoActor instanceof ProtoTextArea) {
            ProtoTextArea proto = (ProtoTextArea) protoActor;
            if (proto.style != null) {
                TextFieldStyle style = skin.get(proto.style.name, TextFieldStyle.class);
                TextArea textArea = new TextArea(proto.text == null ? "" : convertEscapedCharacters(proto.text), style);
                textArea.setName(proto.name);
                textArea.setPasswordCharacter(proto.passwordCharacter);
                textArea.setPasswordMode(proto.passwordMode);
                textArea.setAlignment(proto.alignment);
                textArea.setDisabled(proto.disabled);
                textArea.setCursorPosition(proto.cursorPosition);
                if (proto.selectAll) {
                    textArea.setSelection(0, textArea.getText().length() - 1);
                } else {
                    textArea.setSelection(proto.selectionStart, proto.selectionEnd);
                }
                textArea.setFocusTraversal(proto.focusTraversal);
                textArea.setMaxLength(proto.maxLength);
                textArea.setMessageText(proto.messageText);
                textArea.setPrefRows(proto.preferredRows);
                actor = textArea;
            }
        } else if (protoActor instanceof ProtoTouchPad) {
            ProtoTouchPad proto = (ProtoTouchPad) protoActor;
            if (proto.style != null) {
                TouchpadStyle style = skin.get(proto.style.name, TouchpadStyle.class);
                Touchpad touchPad = new Touchpad(proto.deadZone, style);
                touchPad.setResetOnTouchUp(proto.resetOnTouchUp);
                actor = touchPad;
            }
        } else if (protoActor instanceof ProtoContainer) {
            ProtoContainer proto = (ProtoContainer) protoActor;
            Container container = new Container();
            container.align(proto.alignment);
            if (proto.background != null) {
                container.setBackground(skin.getDrawable(proto.background.name));
            }
            container.fill(proto.fillX, proto.fillY);
            if (proto.minWidth > 0) container.minWidth(proto.minWidth);
            if (proto.minHeight > 0) container.minHeight(proto.minHeight);
            if (proto.maxWidth > 0) container.maxWidth(proto.maxWidth);
            if (proto.maxHeight > 0) container.maxHeight(proto.maxHeight);
            if (proto.preferredWidth > 0) container.prefWidth(proto.preferredWidth);
            if (proto.preferredHeight > 0) container.prefHeight(proto.preferredHeight);
            container.padLeft(proto.padLeft);
            container.padRight(proto.padRight);
            container.padTop(proto.padTop);
            container.padBottom(proto.padBottom);
            if (proto.child != null) container.setActor(createPreviewWidget(proto.child, skin));
            actor = container;
        } else if (protoActor instanceof ProtoHorizontalGroup) {
            ProtoHorizontalGroup proto = (ProtoHorizontalGroup) protoActor;
            HorizontalGroup horizontalGroup = new HorizontalGroup();
            horizontalGroup.align(proto.alignment);
            horizontalGroup.expand(proto.expand);
            horizontalGroup.fill(proto.fill ? 1f : 0f);
            horizontalGroup.padLeft(proto.padLeft);
            horizontalGroup.padRight(proto.padRight);
            horizontalGroup.padTop(proto.padTop);
            horizontalGroup.padBottom(proto.padBottom);
            horizontalGroup.reverse(proto.reverse);
            horizontalGroup.rowAlign(proto.rowAlignment);
            horizontalGroup.space(proto.space);
            horizontalGroup.wrap(proto.wrap);
            horizontalGroup.wrapSpace(proto.wrapSpace);
            for (ProtoActor child : proto.children) {
                Actor widget = createPreviewWidget(child, skin);
                if ( widget != null) {
                    horizontalGroup.addActor(widget);
                }
            }
            
            actor = horizontalGroup;
        } else if (protoActor instanceof ProtoScrollPane) {
            ProtoScrollPane proto = (ProtoScrollPane) protoActor;
            if (proto.style != null) {
                ScrollPaneStyle style = skin.get(proto.style.name, ScrollPaneStyle.class);
                ScrollPane scrollPane = new ScrollPane(createPreviewWidget(proto.child, skin), style);
                scrollPane.setName(proto.name);
                scrollPane.setFadeScrollBars(proto.fadeScrollBars);
                scrollPane.setClamp(proto.clamp);
                scrollPane.setFlickScroll(proto.flickScroll);
                scrollPane.setFlingTime(proto.flingTime);
                scrollPane.setForceScroll(proto.forceScrollX, proto.forceScrollY);
                scrollPane.setOverscroll(proto.overScrollX, proto.overScrollY);
                scrollPane.setupOverscroll(proto.overScrollDistance, proto.overScrollSpeedMin, proto.overScrollSpeedMax);
                scrollPane.setScrollBarPositions(proto.scrollBarBottom, proto.scrollBarRight);
                scrollPane.setScrollbarsOnTop(proto.scrollBarsOnTop);
                scrollPane.setScrollbarsVisible(proto.scrollBarsVisible);
                scrollPane.setScrollBarTouch(proto.scrollBarTouch);
                scrollPane.setScrollingDisabled(proto.scrollingDisabledX, proto.scrollingDisabledY);
                scrollPane.setSmoothScrolling(proto.smoothScrolling);
                scrollPane.setVariableSizeKnobs(proto.variableSizeKnobs);
                actor = scrollPane;
            }
        } else if (protoActor instanceof ProtoStack) {
            ProtoStack proto = (ProtoStack) protoActor;
            Stack stack = new Stack();
            stack.setName(proto.name);
            for (ProtoActor child : proto.children) {
                stack.add(createPreviewWidget(child, skin));
            }
            actor = stack;
        } else if (protoActor instanceof ProtoSplitPane) {
            ProtoSplitPane proto = (ProtoSplitPane) protoActor;
            if (proto.style != null) {
                SplitPaneStyle style = skin.get(proto.style.name, SplitPaneStyle.class);
                SplitPane splitPane = new SplitPane(createPreviewWidget(proto.childFirst, skin), createPreviewWidget(proto.childSecond, skin), proto.vertical, style);
                splitPane.setName(proto.name);
                splitPane.setSplitAmount(proto.split);
                splitPane.setMinSplitAmount(proto.splitMin);
                splitPane.setMaxSplitAmount(proto.splitMax);
                actor = splitPane;
            }
        } else if (protoActor instanceof ProtoTree) {
            ProtoTree proto = (ProtoTree) protoActor;
            if (proto.style != null) {
                TreeStyle style = skin.get(proto.style.name, TreeStyle.class);
                Tree tree = new Tree(style);
                tree.setName(proto.name);
                tree.setPadding(proto.padLeft, proto.padRight);
                tree.setIconSpacing(proto.iconSpaceLeft, proto.iconSpaceRight);
                tree.setIndentSpacing(proto.indentSpacing);
                tree.setYSpacing(proto.ySpacing);
                for (ProtoNode child : proto.children) {
                    Tree.Node node = createPreviewNode(child, skin);
                    if (node != null) {
                        tree.add(node);
                    }
                }
                actor = tree;
            }
        } else if (protoActor instanceof ProtoVerticalGroup) {
            ProtoVerticalGroup proto = (ProtoVerticalGroup) protoActor;
            VerticalGroup verticalGroup = new VerticalGroup();
            verticalGroup.align(proto.alignment);
            verticalGroup.expand(proto.expand);
            verticalGroup.fill(proto.fill ? 1f : 0f);
            verticalGroup.padLeft(proto.padLeft);
            verticalGroup.padRight(proto.padRight);
            verticalGroup.padTop(proto.padTop);
            verticalGroup.padBottom(proto.padBottom);
            verticalGroup.reverse(proto.reverse);
            verticalGroup.columnAlign(proto.columnAlignment);
            verticalGroup.space(proto.space);
            verticalGroup.wrap(proto.wrap);
            verticalGroup.wrapSpace(proto.wrapSpace);
            for (ProtoActor child : proto.children) {
                Actor widget = createPreviewWidget(child, skin);
                if (widget != null) verticalGroup.addActor(widget);
            }
            
            actor = verticalGroup;
        }
        return actor;
    }
    
    private Tree.Node createPreviewNode(ProtoNode protoNode, Skin skin) {
        if (protoNode.actor != null) {
            GenericNode node = new GenericNode();
            Actor actor = createPreviewWidget(protoNode.actor, skin);
            if (actor == null) return null;
            node.setActor(actor);
            if (protoNode.icon != null) node.setIcon(skin.getDrawable(protoNode.icon.name));
            node.setSelectable(protoNode.selectable);
            for (ProtoNode child : protoNode.nodes) {
                Tree.Node newNode = createPreviewNode(child, skin);
                if (newNode != null) {
                    node.add(newNode);
                }
            }
            return node;
        }
        return null;
    }
    
    private static class GenericNode extends Tree.Node {
    
    }
}
