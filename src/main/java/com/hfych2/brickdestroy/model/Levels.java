package com.hfych2.brickdestroy.model;


import java.awt.*;

/**
 * This class is extracted from {@link Wall} of the original code
 *      and has been modified along with having additions.<br>
 *
 * This class defines all the possible levels of the game.<br>
 *
 * @author Carolyn
 */
public class Levels {

    private final Wall wall;

    private static final int LEVELS_COUNT = 6;
    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;
    private static final int GOLD = 4;
    private Brick[] bricks;

    private Brick[][] levels;//all levels
    private int level;//current level indicator


    /**
     * Class constructor.<br>
     * @param wall the wall.
     */
    public Levels(Wall wall) {
        this.wall = wall;
    }

    /**
     * Makes a level with wall of only consisting a single type of brick
     *      using {@link Levels#makeBrick(Point, Dimension, int)}.<br>
     * @param drawArea the area of the gameBoard.
     * @param brickCnt the total number of bricks.
     * @param lineCnt the total number of lines of bricks.
     * @param brickSizeRatio the ratio of the brick size.
     * @param type the type of the brick to create the level
     * @return a wall of only consisting a single type of brick.
     *
     * @see Point#setLocation(Point)
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int type) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] makeBricks = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < makeBricks.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);
            makeBricks[i] = makeBrick(p, brickSize, type);
        }

        for (double y = brickHgt; i < makeBricks.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            makeBricks[i] = makeBrick(p, brickSize, type);
        }
        return makeBricks;

    }

    /**
     * Makes the brick of type specified.<br>
     * @param point the point to put the brick.
     * @param size the size of the brick.
     * @param type the type of the brick.
     * @throws IllegalArgumentException if unknown brick type is passed in as argument.
     * @return the brick made of the specified type.
     */
    private Brick makeBrick(Point point, Dimension size, int type) {
        Brick brickType;
        switch (type) {
            case CLAY:
                brickType = new ClayBrick(point, size);
                break;
            case STEEL:
                brickType = new SteelBrick(point, size);
                break;
            case CEMENT:
                brickType = new CementBrick(point, size);
                break;
            case GOLD:
                brickType = new GoldBrick(point,size);
                break;
            default:
                throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        }
        return brickType;
    }

    /**
     * Makes a ChessBoardLevel with wall of consisting two types of brick
     *      using {@link Levels#makeBrick(Point, Dimension, int)}.<br>
     * @param drawArea the area of the gameBoard.
     * @param brickCnt the total number of bricks.
     * @param lineCnt the total number of lines of bricks.
     * @param brickSizeRatio the ratio of the brick size.
     * @param typeA the first type of brick.
     * @param typeB the second type of brick.
     * @return a ChessBoard wall consisting of 2 types of brick.
     *
     * @see Point#setLocation(Point)
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] makeBricks = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < makeBricks.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            makeBricks[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < makeBricks.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            makeBricks[i] = makeBrick(p, brickSize, typeA);
        }
        return makeBricks;
    }

    /**
     * Makes the levels by calling either {@link Levels#makeSingleTypeLevel(Rectangle, int, int, double, int)}
     *      or {@link Levels#makeChessboardLevel(Rectangle, int, int, double, int, int)}
     *      and passing in the respective arguments.<br>
     * @param drawArea the area of the gameBoard.
     * @param brickCount the total number of bricks.
     * @param lineCount the total number of lines of bricks.
     * @param brickDimensionRatio the ratio of the brick size.
     * @return the respective wall of bricks made.
     */
    protected Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {

        Brick[][] levelsCount = new Brick[LEVELS_COUNT][];

        levelsCount[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY);
        levelsCount[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
        levelsCount[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, STEEL);
        levelsCount[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, CEMENT);
        levelsCount[4] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL);
        levelsCount[5] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, GOLD, CEMENT);

        return levelsCount;
    }

    /**
     * Initialises the next level.
     */
    public void nextLevel() {
        bricks = levels[level++];
        wall.setBrickCount(bricks.length);
    }

    /**
     * Checks if there is still another level ahead.
     * @return true if there is still another level and false otherwise.
     */
    public boolean hasLevel() {
        return level < levels.length;
    }

    /**
     * Gets the bricks.
     * @return the bricks.
     */
    public Brick[] getBricks() {
        return bricks;
    }

    /**
     * Sets the levels.
     * @param levels the levels to set to.
     */
    public void setLevels(Brick[][] levels) {
        this.levels = levels;
    }

    /**
     * Sets the current level.
     * @param level the current level.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets the current level.
     * @return the current level.
     */
    public int getLevel() {
        return level;
    }

}