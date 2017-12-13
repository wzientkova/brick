package com.wzientkova.brick.asset;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.Logger;
import com.wzientkova.brick.Controller;
import com.wzientkova.brick.util.Constant;

public class AssetManager {

    static public final String DEFAULT_CHARS = "\u0000AÁBCČDĎEÉĚFGHIÍJKLMNOÓPQRŘSŠTŤUÚŮVWXYÝZŽaábcčdďeéěfghiíjklmnoópqrřsštťuúůvwxyýzž1234567890\"!`?'.,;:()[]{}<>|/@\\^$€-%+=#_&~*\u0080\u0081\u0082\u0083\u0084\u0085\u0086\u0087\u0088\u0089\u008A\u008B\u008C\u008D\u008E\u008F\u0090\u0091\u0092\u0093\u0094\u0095\u0096\u0097\u0098\u0099\u009A\u009B\u009C\u009D\u009E\u009F\u00A0\u00A1\u00A2\u00A3\u00A4\u00A5\u00A6\u00A7\u00A8\u00A9\u00AA\u00AB\u00AC\u00AD\u00AE\u00AF\u00B0\u00B1\u00B2\u00B3\u00B4\u00B5\u00B6\u00B7\u00B8\u00B9\u00BA\u00BB\u00BC\u00BD\u00BE\u00BF\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C6\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D0\u00D1\u00D2\u00D3\u00D4\u00D5\u00D6\u00D7\u00D8\u00D9\u00DA\u00DB\u00DC\u00DD\u00DE\u00DF\u00E0\u00E1\u00E2\u00E3\u00E4\u00E5\u00E6\u00E7\u00E8\u00E9\u00EA\u00EB\u00EC\u00ED\u00EE\u00EF\u00F0\u00F1\u00F2\u00F3\u00F4\u00F5\u00F6\u00F7\u00F8\u00F9\u00FA\u00FB\u00FC\u00FD\u00FE\u00FF";

    public static final TextureLoader.TextureParameter TEXTURE_PARAM_LINEAR = new TextureLoader.TextureParameter() {{
        minFilter = Texture.TextureFilter.Linear;
        magFilter = Texture.TextureFilter.Linear;
    }};

    public static final BitmapFontLoader.BitmapFontParameter BITMAP_FONT_PARAM_LINEAR = new BitmapFontLoader.BitmapFontParameter() {{
        minFilter = Texture.TextureFilter.Linear;
        magFilter = Texture.TextureFilter.Linear;
    }};
    private static com.badlogic.gdx.assets.AssetManager manager;

    private AssetManager() {
    }

    public static void init() {

        // czech language debug
        // Locale.setDefault(new Locale("cs"));

        manager = new com.badlogic.gdx.assets.AssetManager();
        setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(manager.getFileHandleResolver()));
        setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(manager.getFileHandleResolver()));
    }

    public static Drawable getDrawable(Asset<Texture> asset) {
        return new TextureRegionDrawable(new TextureRegion(manager.get(asset.getPath(), asset.getType())));
    }

    public static Drawable newDrawable(Asset<Texture> asset, Color tint) {
        TextureRegion region = ((TextureRegionDrawable) getDrawable(asset)).getRegion();
        Sprite sprite;
        if (region instanceof TextureAtlas.AtlasRegion) {
            sprite = new TextureAtlas.AtlasSprite((TextureAtlas.AtlasRegion) region);
        } else {
            sprite = new Sprite(region);
        }
        sprite.setColor(tint);
        return new SpriteDrawable(sprite);
    }

    public static TextureRegion getRegion(Asset<Texture> asset) {
        return new TextureRegion(get(asset));
    }

    public static TextureRegion getRegion(Regions page) {
        return manager.get(page.getParent().getPath(), page.getParent().getType()).findRegion(page.toString());
    }

    public static Drawable getRegionDrawable(Regions page) {
        return new TextureRegionDrawable(getRegion(page));
    }

    public static Drawable newRegionDrawable(Regions page, Color tint) {
        TextureRegion region = getRegion(page);
        Sprite sprite;
        if (region instanceof TextureAtlas.AtlasRegion) {
            sprite = new TextureAtlas.AtlasSprite((TextureAtlas.AtlasRegion) region);
        } else {
            sprite = new Sprite(region);
        }
        sprite.setColor(tint);
        return new SpriteDrawable(sprite);
    }

    public static <T> T get(Asset<T> asset) {
        return get(asset.getPath(), asset.getType());
    }

    public static <T> T get(String fileName) {
        return manager.get(fileName);
    }

    public static <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }

    public static <T> Array<T> getAll(Class<T> type, Array<T> out) {
        return manager.getAll(type, out);
    }

    public static <T> T get(AssetDescriptor<T> assetDescriptor) {
        return manager.get(assetDescriptor);
    }

    public static void unload(Asset<?> asset) {
        if (!isLoaded(asset)) return;
        unload(asset.getPath());
    }

    public static void unload(String fileName) {
        if (!manager.isLoaded(fileName)) return;
        manager.unload(fileName);
    }

    public static <T> boolean containsAsset(T asset) {
        return manager.containsAsset(asset);
    }

    public static <T> String getAssetFileName(T asset) {
        return manager.getAssetFileName(asset);
    }

    public static <T> boolean isLoaded(Asset<T> asset) {
        return isLoaded(asset.getPath(), asset.getType());
    }

    public static boolean isLoaded(String fileName) {
        return manager.isLoaded(fileName);
    }

    public static <T> boolean isLoaded(String fileName, Class<T> type) {
        return manager.isLoaded(fileName, type);
    }

    public static <T> AssetLoader<?, ?> getLoader(Class<T> type) {
        return manager.getLoader(type);
    }

    public static <T> AssetLoader<?, ?> getLoader(Class<T> type, String fileName) {
        return manager.getLoader(type, fileName);
    }

    public static <T> void load(Asset<T> asset) {
        if (isLoaded(asset)) return;
        manager.load(asset.getPath(), asset.getType(), asset.getParameter());
    }

    public static <T> void load(Asset<T> asset, AssetLoaderParameters<T> parameter) {
        if (isLoaded(asset)) return;
        manager.load(asset.getPath(), asset.getType(), asset.getParameter());
    }

    public static <T> void load(String fileName, Class<T> type) {
        if (isLoaded(fileName)) return;
        manager.load(fileName, type);
    }

    public static <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
        if (isLoaded(fileName)) return;
        manager.load(fileName, type, parameter);
    }

    public static void load(AssetDescriptor<?> desc) {
        if (isLoaded(desc.fileName)) return;
        manager.load(desc);
    }

    public static boolean update() {
        return manager.update();
    }

    public static boolean update(int millis) {
        return manager.update(millis);
    }

    public static void finishLoading() {
        manager.finishLoading();
    }

    public static <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
        manager.setLoader(type, loader);
    }

    public static <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, String suffix, AssetLoader<T, P> loader) {
        manager.setLoader(type, suffix, loader);
    }

    public static int getLoadedAssets() {
        return manager.getLoadedAssets();
    }

    public static int getQueuedAssets() {
        return manager.getQueuedAssets();
    }

    public static float getProgress() {
        return manager.getProgress();
    }

    public static void setErrorListener(AssetErrorListener listener) {
        manager.setErrorListener(listener);
    }

    public static void dispose() {
        manager.dispose();
    }

    public static void clear() {
        manager.clear();
    }

    public static Logger getLogger() {
        return manager.getLogger();
    }

    public static void setLogger(Logger logger) {
        manager.setLogger(logger);
    }

    public static int getReferenceCount(String fileName) {
        return manager.getReferenceCount(fileName);
    }

    public static String getDiagnostics() {
        return manager.getDiagnostics();
    }

    public static Array<String> getAssetNames() {
        return manager.getAssetNames();
    }

    public static Array<String> getDependencies(String fileName) {
        return manager.getDependencies(fileName);
    }

    public static void setReferenceCount(String fileName, int refCount) {
        manager.setReferenceCount(fileName, refCount);
    }

    public static Class<?> getAssetType(String fileName) {
        return manager.getAssetType(fileName);
    }

    public static com.badlogic.gdx.assets.AssetManager getAssetManager() {
        return manager;
    }


    public enum Fonts implements Asset<BitmapFont> {

        ROBOTO_MEDIUM_60(Constant.DIR_FONT + "Roboto-Medium.ttf", Texture.TextureFilter.Linear, 60),
        ROBOTO_MEDIUM_40(Constant.DIR_FONT + "Roboto-Medium.ttf", Texture.TextureFilter.Linear, 40);

        private final String path;
        private final FreetypeFontLoader.FreeTypeFontLoaderParameter parameter;

        Fonts(String path, Texture.TextureFilter filtering, int size) {

            this.path = size + "_" + path;

            this.parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
            this.parameter.fontParameters.characters = DEFAULT_CHARS;
            this.parameter.fontFileName = path;
            this.parameter.fontParameters.minFilter = filtering;
            this.parameter.fontParameters.magFilter = filtering;
            this.parameter.fontParameters.size = size;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Class<BitmapFont> getType() {
            return BitmapFont.class;
        }

        @Override
        public AssetLoaderParameters<BitmapFont> getParameter() {
            return parameter;
        }

        @Override
        public BitmapFont get() {
            return AssetManager.get(this);
        }

        @Override
        public void load() {
            AssetManager.load(this);
        }

        @Override
        public void unload() {
            AssetManager.unload(this);
        }
    }

    public enum I18N implements Asset<I18NBundle> {

        DEFAULT_BUNDLE(Constant.DIR_I18N + "lang");

        private final String path;

        I18N(String path) {
            this.path = path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Class<I18NBundle> getType() {
            return I18NBundle.class;
        }

        @Override
        public I18NBundle get() {
            return AssetManager.get(this);
        }

        @Override
        public AssetLoaderParameters<I18NBundle> getParameter() {
            return null;
        }

        @Override
        public void load() {
            AssetManager.load(this);
        }

        @Override
        public void unload() {
            AssetManager.unload(this);
        }
    }

    public enum Sounds implements Asset<Sound> {

        FALL(Constant.DIR_SOUND + "fall.ogg");

        private final String path;

        Sounds(String path) {
            this.path = path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Class<Sound> getType() {
            return Sound.class;
        }

        @Override
        public AssetLoaderParameters<Sound> getParameter() {
            return null;
        }

        @Override
        public Sound get() {
            return AssetManager.get(this);
        }

        @Override
        public void load() {
            AssetManager.load(this);
        }

        @Override
        public void unload() {
            AssetManager.unload(this);
        }
    }

    public enum Music implements Asset<com.badlogic.gdx.audio.Music> {

        BACKGROUND(Constant.DIR_MUSIC + "background.ogg");

        private final String path;

        Music(String path) {
            this.path = path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Class<com.badlogic.gdx.audio.Music> getType() {
            return com.badlogic.gdx.audio.Music.class;
        }

        @Override
        public AssetLoaderParameters<com.badlogic.gdx.audio.Music> getParameter() {
            return null;
        }

        @Override
        public com.badlogic.gdx.audio.Music get() {
            return AssetManager.get(this);
        }

        @Override
        public void load() {
            AssetManager.load(this);
        }

        @Override
        public void unload() {
            AssetManager.unload(this);
        }
    }

    public enum Atlases implements Asset<TextureAtlas> {

        ALL("all1.atlas");

        private final String path;

        Atlases(String path) {
            this.path = Constant.DIR_PACK + path;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Class<TextureAtlas> getType() {
            return TextureAtlas.class;
        }

        @Override
        public AssetLoaderParameters<TextureAtlas> getParameter() {
            return null;
        }

        @Override
        public TextureAtlas get() {
            return AssetManager.get(this);
        }

        @Override
        public void load() {
            AssetManager.load(this);
        }

        @Override
        public void unload() {
            AssetManager.unload(this);
        }
    }

    public enum Regions {

        BACKGROUND_CLASSIC(Atlases.ALL, 1020, 1812),
        BACKGROUND_WOOD(Atlases.ALL, 1020, 1812),
        BALL_CLASSIC(Atlases.ALL, 75, 75),
        BLOCKADE_1(Atlases.ALL, 1020, 49),
        BLOCKADE_2(Atlases.ALL, 1020, 49),
        BLOCKADE_3(Atlases.ALL, 1020, 49),
        BRICK_NORMAL_BLUE(Atlases.ALL, 116, 61),
        BRICK_NORMAL_GREEN(Atlases.ALL, 116, 61),
        BRICK_NORMAL_ORANGE(Atlases.ALL, 116, 61),
        BRICK_NORMAL_PURPLE(Atlases.ALL, 116, 61),
        BRICK_NORMAL_RED(Atlases.ALL, 116, 61),
        BUTTON_BIG(Atlases.ALL, 511, 105),
        BUTTON_FB(Atlases.ALL, 96, 85),
        BUTTON_GREEN(Atlases.ALL, 370, 102),
        BUTTON_PAUSE(Atlases.ALL, 96, 85),
        BUTTON_SETTINGS(Atlases.ALL, 96, 85),
        BUTTON_STORY(Atlases.ALL, 518, 147),
        COIN(Atlases.ALL, 50, 53),
        COIN_BIG(Atlases.ALL, 100, 105),
        DROP_BLOCKADE(Atlases.ALL, 66, 66),
        DROP_SPEED_DOWN(Atlases.ALL, 66, 66),
        DROP_SPEED_UP(Atlases.ALL, 66, 66),
        FOREGROUND_BOTTOM(Atlases.ALL, 1020, 203),
        FOREGROUND_TOP(Atlases.ALL, 1020, 247),
        LOGO_BIG(Atlases.ALL, 936, 516),
        PANEL_COIN(Atlases.ALL, 353, 94),
        PANEL_DIAMOND(Atlases.ALL, 245, 91),
        PANEL_STOPWATCH(Atlases.ALL, 245, 96),
        PLANK_CLASSIC(Atlases.ALL, 323, 87);

        private final Atlases parent;
        private final int width, height;

        Regions(Atlases parent, int width, int height) {
            this.parent = parent;
            this.width = width;
            this.height = height;
        }

        public Atlases getParent() {
            return parent;
        }

        public Drawable getDrawable() {
            return AssetManager.getRegionDrawable(this);
        }

        public TextureRegion getRegion() {
            return AssetManager.getRegion(this);
        }

        public Drawable newDrawable(Color tint) {
            return AssetManager.newRegionDrawable(this, tint);
        }

        public NinePatch getNinePatch() {
            return AssetManager.get(parent).createPatch(this.toString());
        }


        public int getWidth() {
            return width;
        }

        public float getWorldWidth() {
            return Controller.pixelsToWorld(width);
        }

        public int getHeight() {
            return height;
        }

        public float getWorldHeight() {
            return Controller.pixelsToWorld(height);
        }

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }

    public enum Textures implements Asset<Texture> {

        SPLASH("splash.png", TEXTURE_PARAM_LINEAR);

        private String path;
        private AssetLoaderParameters<Texture> parameter;

        Textures(String path, AssetLoaderParameters<Texture> parameter) {
            this.path = Constant.DIR_TEXTURE + path;
            this.parameter = parameter;
        }

        @Override
        public String getPath() {
            return path;
        }

        @Override
        public Class<Texture> getType() {
            return Texture.class;
        }

        @Override
        public AssetLoaderParameters<Texture> getParameter() {
            return parameter;
        }

        @Override
        public Texture get() {
            return AssetManager.get(this);
        }

        @Override
        public void load() {
            AssetManager.load(this);
        }

        @Override
        public void unload() {
            AssetManager.unload(this);
        }

    }

    interface Asset<T> {

        String getPath();

        Class<T> getType();

        T get();

        AssetLoaderParameters<T> getParameter();

        void load();

        void unload();
    }
}