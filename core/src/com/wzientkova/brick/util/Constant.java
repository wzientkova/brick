package com.wzientkova.brick.util;

/**
 * This class is set of static constants of different types. It contains UI related constants, file system constants,
 * system/debug constants etc.
 */
public class Constant {

    // general
    public static final String TITLE = "Brick";

    public static final int VIRTUAL_HEIGHT = 1920;
    public static final int VIRTUAL_WIDTH = 1080;

    public static final int RANDOM_TOKEN_SIZE = 9999999;

    // desktop version specific
    public static final int WINDOW_WIDTH = 540;
    public static final int WINDOW_HEIGHT = 960;

    // debug
    public static final boolean DEBUG_UI = false;
    public static final boolean SHOW_INTRO = false;
    public static final boolean DEBUG_CONSOLE = true;

    // debug messages
    public static final String D_INFO = TITLE + " > ";
    public static final String D_WARN = TITLE + " ! ";
    public static final String D_ERROR = TITLE + " X ";

    // debug colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";

    // directories
    public static final String DIR_FONT = "font/";
    public static final String DIR_I18N = "i18n/";
    public static final String DIR_SOUND = "sound/";
    public static final String DIR_MUSIC = "music/";
    public static final String DIR_PACK = "pack/";
    public static final String DIR_TEXTURE = "texture/";
    public static final String DIR_LEVELS = "gamedata/level/";
    public static final String DIR_LEVELS_ANDROID = "assets/gamedata/level/" + DIR_LEVELS;
    public static final String DIR_LEVELS_EDITOR = "gamedata/level_editor/";
    public static final String DIR_LEVELS_EDITOR_ANDROID = "assets/gamedata/level_editor/" + DIR_LEVELS_EDITOR;

    public static final String PREFERENCES = "preferences";
    public static final String SCORE = "score";

}