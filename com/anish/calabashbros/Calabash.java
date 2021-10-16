package com.anish.calabashbros;

import java.awt.Color;
import java.util.ArrayList;

public class Calabash extends Creature {

    int[][] maze;
    Dfs dfs;

    public Calabash(World world, int[][] maze) {
        super(Color.ORANGE, (char) 2, world);
        this.maze = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; ++i)
            for (int j = 0; j < maze[0].length; ++j)
                this.maze[i][j] = maze[i][j];
        dfs = new Dfs(maze);
    }

    public void move(World world, int pos) {
        int x = this.getX();
        int y = this.getY();
        int offsetx = 0, offsety = 0;
        if (pos == 24)
            offsety = -1;
        else if (pos == 25)
            offsety = 1;
        else if (pos == 26)
            offsetx = 1;
        else if (pos == 27)
            offsetx = -1;
        int newx = x + offsetx;
        int newy = y + offsety;
        if (newx >= 0 && newx < maze.length && newy >= 0 && newy < maze[x].length && maze[newx][newy] == 1) {
            this.moveTo(x + offsetx, y + offsety);
            world.put(new Shallow(pos, world), x, y);
        }
    }

    public void manualmove(World world, int pos) {
        dfs.clearPlan();
        move(world, pos);
    }

    public void automove(World world) {
        ArrayList<Integer> moveplan = dfs.getPlan();
        if (moveplan.size() == 0) {
            dfs.set(this.getX(), this.getY());
            dfs.makePlan();
            moveplan = dfs.getPlan();
        }
        if (moveplan.size() > 0){
            move(world, moveplan.get(0));
            moveplan.remove(0);
        }
    }

}
