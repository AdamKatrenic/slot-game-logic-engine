package sk.adamkatrenic.logic;

import java.util.ArrayList;
import java.util.List;
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

    private List<Payline> paylines;

    public SlotEngine() {
        paylines = new ArrayList<>();

        paylines.add(new Payline(new int[]{0, 0, 0, 0, 0}));
        paylines.add(new Payline(new int[]{1, 1, 1, 1, 1}));
        paylines.add(new Payline(new int[]{2, 2, 2, 2, 2}));

        paylines.add(new Payline(new int[]{0, 1, 2, 1, 0}));

        paylines.add(new Payline(new int[]{2, 1, 0, 1, 2}));

        paylines.add(new Payline(new int[]{0, 1, 2, 2, 2}));
    }

    public double calculateTotalWin(Symbol[][] grid, double betPerLine) {
        double totalWin = 0;

        for (Payline line : paylines) {
            int[] pos = line.getPositions();

            Symbol firstSymbol = grid[pos[0]][0];

            if (firstSymbol == Symbol.SCATTER) continue;

            Symbol target = firstSymbol;
            if (target == Symbol.WILD) {
                for (int col = 1; col < 5; col++) {
                    Symbol s = grid[pos[col]][col];
                    if (s != Symbol.WILD && s != Symbol.SCATTER) {
                        target = s;
                        break;
                    }
                }
            }

            if (target == Symbol.WILD || target == Symbol.SCATTER) {
                target = Symbol.SEVEN;
            }

            int count = 0;
            for (int col = 0; col < 5; col++) {
                Symbol current = grid[pos[col]][col];

                if (current == target || current == Symbol.WILD) {
                    count++;
                } else {
                    break;
                }
            }

            if (count >= 3) {
                totalWin += betPerLine * target.payout;
            }
        }
        return totalWin;
    }

    public int countScatters(Symbol[][] grid) {
        int count = 0;
        for (Symbol[] row : grid) {
            for (Symbol s : row) {
                if (s == Symbol.SCATTER) count++;
            }
        }
        return count;
    }

}
