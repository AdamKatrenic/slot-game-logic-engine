package sk.adamkatrenic.logic;

import java.util.Scanner;

public class Main {
    private static final int BONUS_FREE_SPINS = 10;
    private static final int SCATTER_THRESHOLD = 3;

    private double balance;
    private final double betPerLine;
    private final double totalBet;
    private final SlotEngine engine;
    private final Scanner scanner;
    private final StatisticsManager stats;

    public Main() {
        ConfigLoader config = new ConfigLoader();
        this.engine = new SlotEngine();
        this.scanner = new Scanner(System.in);
        this.stats = new StatisticsManager();
        this.balance = config.getDoubleProperty("initial.balance", 100.0);
        this.betPerLine = config.getDoubleProperty("default.bet", 0.20);
        this.totalBet = betPerLine * 5;
    }

    public static void main(String[] args) {
        new Main().startGame();
    }

    public void startGame() {
        printWelcomeMessage();

        while (balance >= totalBet) {
            String input = promptAction();

            if (input.equalsIgnoreCase("exit")) break;
            if (input.equalsIgnoreCase("par")) {
                new ParSheetGenerator(engine).generateReport();
                continue;
            }

            processSpin();
        }

        printGameOver();
    }

    private void processSpin() {
        balance -= totalBet;
        Symbol[][] grid = engine.generateGrid(3, 5);
        displayGrid(grid);

        double win = engine.calculateTotalWin(grid, betPerLine);

        if (win > 0) {
            System.out.printf("💰 VÝHRA: %.2f €%n", win);

            System.out.print("👉 Chceš risknúť výhru? [R] Červená | [B] Čierna | [ENTER] Preskočiť > ");
            String gambleChoice = scanner.nextLine().toUpperCase();

            if (gambleChoice.equals("R") || gambleChoice.equals("B")) {
                win = engine.playGamble(win, gambleChoice);
            }

            balance += win;
        } else {
            System.out.println("❌ Žiadna výhra.");
        }

        stats.addSpin(totalBet, win);

        checkAndHandleBonus(grid);
        System.out.printf("💳 AKTUÁLNY BALANS: %.2f €%n", balance);
    }

    private void checkAndHandleBonus(Symbol[][] grid) {
        if (engine.countScatters(grid) >= SCATTER_THRESHOLD) {
            System.out.println("\n✨ BONUS! 10 FREE SPINY ZAČÍNAJÚ! ✨");
            double bonusTotal = 0;
            for (int i = 1; i <= BONUS_FREE_SPINS; i++) {
                Symbol[][] freeGrid = engine.generateGrid(3, 5);
                double fw = engine.calculateTotalWin(freeGrid, betPerLine);
                bonusTotal += fw;
                stats.addSpin(0, fw);
                System.out.printf("  🎰 Free Spin #%d: [%.2f €]%n", i, fw);
            }
            System.out.printf("🎁 Celková výhra z bonusu: %.2f €%n", bonusTotal);
            balance += bonusTotal;
        }
    }

    private String promptAction() {
        System.out.print("\n[ENTER] Spin | [PAR] Report | [EXIT] Koniec > ");
        if (scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }

    private void printWelcomeMessage() {
        System.out.println("============================================");
        System.out.println("        🎰  JAVA SLOT ENGINE v1.2  🎰        ");
        System.out.println("============================================");
        System.out.printf(" Balans: %.2f € | Stávka: %.2f €%n", balance, totalBet);
        System.out.println("--------------------------------------------");
    }

    private void displayGrid(Symbol[][] grid) {
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

    private void printGameOver() {
        System.out.println("\n============================================");
        System.out.printf(" HRA UKONČENÁ. Konečný balans: %.2f €%n", balance);

        stats.saveSessionReport();

        System.out.println(" Dovidenia v kasíne! 🍀");
        System.out.println("============================================");
        scanner.close();
    }
}