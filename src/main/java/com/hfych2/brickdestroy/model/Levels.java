package com.hfych2.brickdestroy.model;


import java.awt.*;


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

    public int getLevel() {
        return level;
    }

    public Levels(Wall wall) {
        this.wall = wall;
    }

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

    public void nextLevel() {
        bricks = levels[level++];
        wall.setBrickCount(bricks.length);
    }

    public boolean hasLevel() {
        return level < levels.length;
    }

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

    public Brick[] getBricks() {
        return bricks;
    }

    public void setLevels(Brick[][] levels) {
        this.levels = levels;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}