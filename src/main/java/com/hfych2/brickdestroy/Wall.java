/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.hfych2.brickdestroy;


import java.awt.*;


public class Wall {

    final Levels levels = new Levels(this);

    private Rectangle area;
    private int brickCount;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio){

        levels.setLevels(levels.makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio));
        levels.setLevel(0);

        area = drawArea;

    }

    public int getBrickCount(){

        return brickCount;
    }

    public void setBrickCount(int brickCount) {

        this.brickCount = brickCount;
    }

    public void wallReset(){
        for(Brick b : levels.getBricks())
            b.repair();
        brickCount = levels.getBricks().length;

    }

    public boolean isDone(){

        return brickCount == 0;
    }

    public void nextLevel(){

        levels.nextLevel();
    }

    public boolean hasLevel(){

        return levels.hasLevel();
    }

    public Levels getLevels() {
        return levels;
    }

    public Rectangle getArea() {
        return area;
    }

}
