package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;

import com.anish.monsters.BubbleSorter;
import com.anish.monsters.QuickSorter;
import com.anish.monsters.Monster;
import com.anish.monsters.World;

import asciiPanel.AsciiPanel;

import java.util.Random;

public class WorldScreen implements Screen {

    private World world;
    private Monster[][] monster;
    String[] sortSteps;

    public WorldScreen() {
        world = new World();

        monster = new Monster[16][16];

        boolean[] used = new boolean[16 * 16];

        for (int i = 0; i < 16 * 16; ++i) {
            Random r = new Random();
            int ran1 = r.nextInt(16 * 16);
            while (used[ran1]) {
                ran1 = (ran1 + 1) % 256;
            }
            used[ran1] = true;
            int x = ran1 / 16, y = ran1 % 16;
            monster[x][y] = new Monster(new Color((i >> 4) << 4, ((i >> 2) & 0xf) << 4, (i & 0xf) << 4), i + 1, world);
        }

        // bros[3] = new Calabash(new Color(204, 0, 0), 1, world);
        // bros[5] = new Calabash(new Color(255, 165, 0), 2, world);
        // bros[1] = new Calabash(new Color(252, 233, 79), 3, world);
        // bros[0] = new Calabash(new Color(78, 154, 6), 4, world);
        // bros[4] = new Calabash(new Color(50, 175, 255), 5, world);
        // bros[6] = new Calabash(new Color(114, 159, 207), 6, world);
        // bros[2] = new Calabash(new Color(173, 127, 168), 7, world);
        for (int i = 0; i < 16; ++i)
            for (int j = 0; j < 16; ++j)
                world.put(monster[i][j], j * 2 + 2, i + 2);
        // world.put(bros[0], 10, 10);
        // world.put(bros[1], 12, 10);
        // world.put(bros[2], 14, 10);
        // world.put(bros[3], 16, 10);
        // world.put(bros[4], 18, 10);
        // world.put(bros[5], 20, 10);
        // world.put(bros[6], 22, 10);

        Monster[] monster_line = new Monster[16 * 16];

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                monster_line[i * 16 + j] = monster[i][j];
            }
        }

        QuickSorter<Monster> b = new QuickSorter<>();
        b.load(monster_line);
        b.sort();

        sortSteps = this.parsePlan(b.getPlan());
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Monster[][] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Monster getBroByRank(Monster[][] bros, int rank) {
        for(int i=0;i<bros.length;++i){
            for(int j=0;j<bros[0].length;++j){
                if (bros[i][j].getRank() == rank) {
                    return bros[i][j];
                }
    }
        }
        return null;
    }

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

        if (i < this.sortSteps.length) {
            this.execute(monster, sortSteps[i]);
            i++;
        }

        return this;
    }

}
