package sk.casino.logic;

import java.util.Random;

public class SlotEngine {
    private final Random random = new Random();

    public Symbol getRandomSymbol() {
        int totalWeight = 0;
        for (Symbol s : Symbol.values()) {
            totalWeight += s.weight;
        }

        int rnd = random.nextInt(totalWeight);
        int currentSum = 0;
        for (Symbol s : Symbol.values()) {
            currentSum += s.weight;
            if (rnd < currentSum) {
                return s;
            }
        }
        return Symbol.CHERRY;
    }

    public Symbol[][] generateGrid(int rows, int cols) {
        Symbol[][] grid = new Symbol[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = getRandomSymbol();
            }
        }
        return grid;
    }
}