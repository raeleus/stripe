package com.ray3k.stripe;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * An {@link com.badlogic.gdx.assets.loaders.AssetLoader} to load a {@link FreeTypeSkin}. This enables you to deserialize FreeType fonts from
 * a Skin JSON and load it through an {@link AssetManager}. See the
 * <a href="https://github.com/raeleus/skin-composer/wiki/Creating-FreeType-Fonts#using-a-custom-serializer">Skin Composer documentation</a>.
 */
public class FreeTypeSkinLoader extends SkinLoader {
    /**
     * Creates a loader with the associated resolver.
     * @param resolver Allows {@link AssetManager} to load resources from anywhere or implement caching strategies.
     */
    public FreeTypeSkinLoader(FileHandleResolver resolver) {
        super(resolver);
    }
    
    /** Override to allow subclasses of Skin to be loaded or the skin instance to be configured.
     * @param atlas The TextureAtlas that the skin will use.
     * @return A new Skin (or subclass of Skin) instance based on the provided TextureAtlas. */
    protected Skin newSkin (TextureAtlas atlas) {
        return new FreeTypeSkin(atlas);
    }
}
