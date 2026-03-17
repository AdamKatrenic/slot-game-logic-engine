package sk.adamkatrenic.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SlotEngine {
    private final Random random = new Random();
    private final List<Payline> paylines;
    private final int totalWeight;

    public SlotEngine() {
        int tempWeight = 0;
        for (Symbol s : Symbol.values()) {
            tempWeight += s.weight;
        }
        this.totalWeight = tempWeight;

        paylines = new ArrayList<>();
        paylines.add(new Payline(new int[]{0, 0, 0, 0, 0}));
        paylines.add(new Payline(new int[]{1, 1, 1, 1, 1}));
        paylines.add(new Payline(new int[]{2, 2, 2, 2, 2}));
        paylines.add(new Payline(new int[]{0, 1, 2, 1, 0}));
        paylines.add(new Payline(new int[]{2, 1, 0, 1, 2}));
    }

    public Symbol getRandomSymbol() {
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

    public double calculateTotalWin(Symbol[][] grid, double betPerLine) {
        double totalWin = 0;

        for (Payline line : paylines) {
            int[] pos = line.getPositions();
            Symbol target = null;
            int count = 0;

            for (int col = 0; col < 5; col++) {
                Symbol current = grid[pos[col]][col];
                if (current == null || current == Symbol.SCATTER) break;

                if (target == null) {
                    if (current != Symbol.WILD) {
                        target = current;
                    }
                    count++;
                } else {
                    if (current == target || current == Symbol.WILD) {
                        count++;
                    } else {
                        break;
                    }
                }
            }

            // Ak línia obsahuje aspoň 3 symboly a sú to len WILDY (target ostal null)
            // ALEBO ak chceme, aby čistá WILD línia mala vždy cenu SEVEN:
            if (count >= 3 && target == null) {
                target = Symbol.SEVEN;
            }

            if (count >= 3 && target != null) {
                double multiplier = count / 3.0;
                totalWin += betPerLine * target.payout * multiplier;
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

    public double playGamble(double currentWin, String playerChoice) {
        boolean isRed = random.nextBoolean();
        String result = isRed ? "R" : "B";

        System.out.println("Karta je: " + (isRed ? "[ČERVENÁ]" : "[ČIERNA]"));

        if (playerChoice.equalsIgnoreCase(result)) {
            System.out.println("Gratulujem! Zdvojnásobil si výhru.");
            return currentWin * 2;
        } else {
            System.out.println("Bohužiaľ, prehral si všetko.");
            return 0;
        }
    }
}