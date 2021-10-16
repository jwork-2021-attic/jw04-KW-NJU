package com.anish.calabashbros;

import java.util.ArrayList;
import java.util.Stack;
import com.anish.maze.Node;

public class Dfs {
    int[][] maze;
    int[][] mat;
    int x, y;
    ArrayList<Integer> plan;

    Dfs(int[][] maze) {
        this.maze = new int[maze.length][maze[0].length];
        mat = new int[maze.length][maze[0].length];
        for (int i = 0; i < maze.length; ++i)
            for (int j = 0; j < maze[0].length; ++j)
                this.maze[i][j] = maze[i][j];
        plan = new ArrayList<Integer>();
    }

    public void makePlan() {
        if (x == maze.length - 1 && y == maze[0].length - 1)
            return;
        for (int i = 0; i < maze.length; ++i)
            for (int j = 0; j < maze[0].length; ++j)
                mat[i][j] = maze[i][j];
        Stack<Node> s = new Stack<>();
        s.push(new Node(x, y));
        while (mat[mat.length - 1][mat[0].length - 1] == 1) {
            Node cur = s.peek();
            x = cur.x;
            y = cur.y;
            int pos = 0;
            if (x + 1 < mat.length && mat[x + 1][y] == 1) {
                ++x;
                pos = 26;
            } else if (y + 1 < mat[0].length && mat[x][y + 1] == 1) {
                ++y;
                pos = 25;
            } else if (x - 1 >= 0 && mat[x - 1][y] == 1) {
                --x;
                pos = 27;
            } else if (y - 1 >= 0 && mat[x][y - 1] == 1) {
                --y;
                pos = 24;
            }
            if (x == cur.x && y == cur.y) {
                Node temp1 = s.pop();
                Node temp2 = s.peek();
                if (temp2.x - temp1.x == 1)
                    plan.add(26);
                else if (temp2.x - temp1.x == -1)
                    plan.add(27);
                else if (temp2.y - temp1.y == 1)
                    plan.add(25);
                else if (temp2.y - temp1.y == -1)
                    plan.add(24);
            } else {
                s.push(new Node(x, y));
                mat[x][y] = 2;
                plan.add(pos);
            }
        }
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void clearPlan() {
        plan.clear();
    }

    public ArrayList<Integer> getPlan() {
        return plan;
    }

}
