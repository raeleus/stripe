/*
 * The MIT License
 *
 * Copyright 2021 Raymond Buckley.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.ray3k.stripe;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * A sublass of {@link Skin} that includes a serializer for FreeType fonts from JSON. These JSON's are typically exported by
 * Skin Composer. See the
 * <a href="https://github.com/raeleus/skin-composer/wiki/Creating-FreeType-Fonts#using-a-custom-serializer">Skin Composer documentation</a>.
 * If you are using Asset Manager, use {@link FreeTypeSkinLoader}
 */
public class FreeTypeSkin extends Skin {
    /** Creates an empty skin. */
    public FreeTypeSkin() {
    }
    
    /** Creates a skin containing the resources in the specified skin JSON file. If a file in the same directory with a ".atlas"
     * extension exists, it is loaded as a {@link TextureAtlas} and the texture regions added to the skin. The atlas is
     * automatically disposed when the skin is disposed.
     * @param  skinFile The JSON file to be read.
     */
    public FreeTypeSkin(FileHandle skinFile) {
        super(skinFile);
        
    }
    
    /** Creates a skin containing the resources in the specified skin JSON file and the texture regions from the specified atlas.
     * The atlas is automatically disposed when the skin is disposed.
     * @param skinFile The JSON file to be read.
     * @param atlas The texture atlas to be associated with the {@link Skin}.
     */
    public FreeTypeSkin(FileHandle skinFile, TextureAtlas atlas) {
        super(skinFile, atlas);
    }
    
    /** Creates a skin containing the texture regions from the specified atlas. The atlas is automatically disposed when the skin
     * is disposed.
     * @param atlas The texture atlas to be associated with the {@link Skin}.
     */
    public FreeTypeSkin(TextureAtlas atlas) {
        super(atlas);
    }
    
    /**
     * Overrides the default JSON loader to process FreeType fonts from a Skin JSON.
     * @param skinFile The JSON file to be processed.
     * @return The {@link Json} used to read the file.
     */
    @Override
    protected Json getJsonLoader(final FileHandle skinFile) {
        Json json = super.getJsonLoader(skinFile);
        final Skin skin = this;

        json.setSerializer(FreeTypeFontGenerator.class, new Json.ReadOnlySerializer<FreeTypeFontGenerator>() {
            @Override
            public FreeTypeFontGenerator read(Json json,
                                              JsonValue jsonData, Class type) {
                String path = json.readValue("font", String.class, jsonData);
                jsonData.remove("font");

                FreeTypeFontGenerator.Hinting hinting = FreeTypeFontGenerator.Hinting.valueOf(json.readValue("hinting",
                        String.class, "AutoMedium", jsonData));
                jsonData.remove("hinting");

                Texture.TextureFilter minFilter = Texture.TextureFilter.valueOf(
                        json.readValue("minFilter", String.class, "Nearest", jsonData));
                jsonData.remove("minFilter");

                Texture.TextureFilter magFilter = Texture.TextureFilter.valueOf(
                        json.readValue("magFilter", String.class, "Nearest", jsonData));
                jsonData.remove("magFilter");

                FreeTypeFontGenerator.FreeTypeFontParameter parameter = json.readValue(FreeTypeFontGenerator.FreeTypeFontParameter.class, jsonData);
                parameter.hinting = hinting;
                parameter.minFilter = minFilter;
                parameter.magFilter = magFilter;
                FreeTypeFontGenerator generator = new FreeTypeFontGenerator(skinFile.parent().child(path));
                generator.setMaxTextureSize(FreeTypeFontGenerator.NO_MAXIMUM);
                BitmapFont font = generator.generateFont(parameter);
                skin.add(jsonData.name, font);
                if (parameter.incremental) {
                    generator.dispose();
                    return null;
                } else {
                    return generator;
                }
            }
        });

        return json;
    }
}
