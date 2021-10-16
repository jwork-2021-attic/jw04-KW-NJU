package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Floor;

import asciiPanel.AsciiPanel;

import com.anish.maze.MazeGenerator;

public class WorldScreen implements Screen {

    private World world;
    private Calabash player;
    String[] sortSteps;
    int[][] maze;

    public WorldScreen() {
        int dimension = 30;
        world = new World();
        MazeGenerator mazeGenerator = new MazeGenerator(dimension);
        mazeGenerator.generateMaze();
        maze = mazeGenerator.getMaze();

        player = new Calabash(world, maze);
        // bros = new Calabash[7];

        // bros[3] = new Calabash(new Color(204, 0, 0), 1, world);
        // bros[5] = new Calabash(new Color(255, 165, 0), 2, world);
        // bros[1] = new Calabash(new Color(252, 233, 79), 3, world);
        // bros[0] = new Calabash(new Color(78, 154, 6), 4, world);
        // bros[4] = new Calabash(new Color(50, 175, 255), 5, world);
        // bros[6] = new Calabash(new Color(114, 159, 207), 6, world);
        // bros[2] = new Calabash(new Color(173, 127, 168), 7, world);
        for (int i = 0; i < dimension; ++i) {
            for (int j = 0; j < dimension; ++j) {
                if (maze[i][j] == 0) {
                    Wall wall = new Wall(world);
                    world.put(wall, i, j);
                } else {
                    Floor floor = new Floor(world);
                    world.put(floor, i, j);
                }
            }
        }
        world.put(player, 0, 0);
        // world.put(bros[0], 10, 10);
        // world.put(bros[1], 12, 10);
        // world.put(bros[2], 14, 10);
        // world.put(bros[3], 16, 10);
        // world.put(bros[4], 18, 10);
        // world.put(bros[5], 20, 10);
        // world.put(bros[6], 22, 10);

        // BubbleSorter<Calabash> b = new BubbleSorter<>();
        // b.load(bros);
        // b.sort();

        // sortSteps = this.parsePlan(b.getPlan());
    }

    // private String[] parsePlan(String plan) {
    // return plan.split("\n");
    // }

    // private void execute(Calabash[] bros, String step) {
    // String[] couple = step.split("<->");
    // getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros,
    // Integer.parseInt(couple[1])));
    // }

    // private Calabash getBroByRank(Calabash[] bros, int rank) {
    // for (Calabash bro : bros) {
    // if (bro.getRank() == rank) {
    // return bro;
    // }
    // }
    // return null;
    // }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        switch (key.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.manualmove(world, 27);
                break;
            case KeyEvent.VK_RIGHT:
                player.manualmove(world, 26);
                break;
            case KeyEvent.VK_UP:
                player.manualmove(world, 24);
                break;
            case KeyEvent.VK_DOWN:
                player.manualmove(world, 25);
                break;
            case KeyEvent.VK_ENTER:
                player.automove(world);
                break;
        }
        return this;

        // if (i < this.sortSteps.length) {
        // this.execute(bros, sortSteps[i]);
        // i++;
        // }

        // return this;
    }

}
