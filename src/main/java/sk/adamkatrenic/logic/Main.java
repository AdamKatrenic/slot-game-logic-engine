package sk.adamkatrenic.logic;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConfigLoader config = new ConfigLoader();
        SlotEngine engine = new SlotEngine();
        Scanner scanner = new Scanner(System.in);

        double balance = config.getDoubleProperty("initial.balance", 2.0);
        double betPerLine = config.getDoubleProperty("default.bet", 0.10);
        int numberOfLines = 5;
        double totalBet = betPerLine * numberOfLines;

        System.out.println("===  POĎ TOČIŤ OVOCKO  ===");
        System.out.println("Pravidlá: Stlač ENTER pre spin, napíš 'exit' pre koniec.");
        System.out.printf("Tvoj balans: %.2f € | Stávka na spin: %.2f €%n", balance, totalBet);
        System.out.println("--------------------------------------------");

        while (balance >= totalBet) {
            System.out.print("\n[ENTER] - Spin | [EXIT] - Koniec > ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            balance -= totalBet;

            Symbol[][] grid = engine.generateGrid(3, 5);
            displayGrid(grid);

            double win = engine.calculateTotalWin(grid, betPerLine);
            int scatters = engine.countScatters(grid);

            if (win > 0) {
                System.out.printf("VÝHRA: %.2f €%n", win);
                balance += win;
            } else {
                System.out.println("Žiadna výhra.");
            }

            if (scatters >= 3) {
                System.out.println("BONUS! 10 FREE SPINY!");
                double bonusWin = 0;
                for (int i = 1; i <= 10; i++) {
                    Symbol[][] freeGrid = engine.generateGrid(3, 5);
                    double fw = engine.calculateTotalWin(freeGrid, betPerLine);
                    bonusWin += fw;
                    System.out.printf("  Free Spin #%d: [%.2f €]%n", i, fw);
                }
                System.out.printf("Celková výhra z bonusu: %.2f €%n", bonusWin);
                balance += bonusWin;
            }

            System.out.printf("AKTUÁLNY BALANS: %.2f €%n", balance);

            if (balance < totalBet) {
                System.out.println("DOŠLI TI PENIAZE. Kasíno vždy vyhráva!");
            }
        }

        System.out.println("--------------------------------------------");
        System.out.printf("Hra ukončená. Odchádzaš s: %.2f €%n", balance);
        System.out.println("Vďaka za hru!");
        scanner.close();
    }

    private static void displayGrid(Symbol[][] grid) {
        System.out.println();
        for (int r = 0; r < 3; r++) {
            System.out.print("  ");
            for (int c = 0; c < 5; c++) {
                System.out.print("[" + grid[r][c].icon + "] ");
            }
            System.out.println();
        }
        System.out.println("--------------------------------------------");
    }
}