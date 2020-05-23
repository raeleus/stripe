package com.ray3k.stripe;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.Hinting;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

public class FreeTypeSkinLoader extends AsynchronousAssetLoader<Skin, SkinParameter> {
    public FreeTypeSkinLoader(FileHandleResolver resolver) {
        super(resolver);
    }
    
    @Override
    public Array<AssetDescriptor> getDependencies (String fileName, FileHandle file, com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter parameter) {
        Array<AssetDescriptor> deps = new Array();
        if (parameter == null || parameter.textureAtlasPath == null)
            deps.add(new AssetDescriptor(file.pathWithoutExtension() + ".atlas", TextureAtlas.class));
        else if (parameter.textureAtlasPath != null) deps.add(new AssetDescriptor(parameter.textureAtlasPath, TextureAtlas.class));
        return deps;
    }
    
    @Override
    public void loadAsync (AssetManager manager, String fileName, FileHandle file, com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter parameter) {
    }
    
    @Override
    public Skin loadSync (AssetManager manager, String fileName, FileHandle file, com.badlogic.gdx.assets.loaders.SkinLoader.SkinParameter parameter) {
        String textureAtlasPath = file.pathWithoutExtension() + ".atlas";
        ObjectMap<String, Object> resources = null;
        if (parameter != null) {
            if (parameter.textureAtlasPath != null) {
                textureAtlasPath = parameter.textureAtlasPath;
            }
            if (parameter.resources != null) {
                resources = parameter.resources;
            }
        }
        TextureAtlas atlas = manager.get(textureAtlasPath, TextureAtlas.class);
        Skin skin = newSkin(atlas);
        
        if (resources != null) {
            for (Entry<String, Object> entry : resources.entries()) {
                skin.add(entry.key, entry.value);
            }
        }
        skin.load(file);
        return skin;
    }
    
    /** Override to allow subclasses of Skin to be loaded or the skin instance to be configured.
     * @param atlas The TextureAtlas that the skin will use.
     * @return A new Skin (or subclass of Skin) instance based on the provided TextureAtlas. */
    protected Skin newSkin (TextureAtlas atlas) {
        return new FreeTypeSkin(atlas);
    }
    
    static public class SkinParameter extends AssetLoaderParameters<Skin> {
        public final String textureAtlasPath;
        public final ObjectMap<String, Object> resources;
        
        public SkinParameter () {
            this(null, null);
        }
        
        public SkinParameter (ObjectMap<String, Object> resources) {
            this(null, resources);
        }
        
        public SkinParameter (String textureAtlasPath) {
            this(textureAtlasPath, null);
        }
        
        public SkinParameter (String textureAtlasPath, ObjectMap<String, Object> resources) {
            this.textureAtlasPath = textureAtlasPath;
            this.resources = resources;
        }
    }
}
