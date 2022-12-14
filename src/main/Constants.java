package main;

public class Constants {
    // Screen Settings
    public static final int ORIGINAL_TILE_SIZE = 16; // 16x16 tile
    public static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48x48 tile
    public static final int MAX_SCREEN_COL = 16; // 16 tiles horizontally
    public static final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL; // 768 pixels
    public static final int MAX_SCREEN_ROW = 12; // 12 tiles vertically
    public static final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW; // 576 pixels
    public static final int FPS = 60; // 60 frames per second
    // World Settings
    public static final int MAX_WORLD_COL = 50;
    public static final int WORLD_WIDTH = TILE_SIZE * MAX_WORLD_COL;
    public static final int MAX_WORLD_ROW = 50;
    public static final int WORLD_HEIGHT = TILE_SIZE * MAX_WORLD_ROW;
}
