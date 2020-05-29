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

import com.badlogic.gdx.utils.Align;

class ProtoTextField extends ProtoActor {
    public String name;
    public ProtoStyle style;
    public String text;
    public char passwordCharacter = '\u2022';//•
    public boolean passwordMode;
    public int alignment = Align.center;
    public boolean disabled;
    public int cursorPosition;
    public int selectionStart;
    public int selectionEnd;
    public boolean selectAll;
    public boolean focusTraversal = true;
    public int maxLength;
    public String messageText;
}
