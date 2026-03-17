package sk.adamkatrenic.logic;

import java.util.Locale;

public class RtpSimulator {
    public static void main(String[] args) {
        // Nastavenie bodky v desatinných číslach pre konzolu
        Locale.setDefault(Locale.US);

        SlotEngine engine = new SlotEngine();
        long totalSpins = 1_000_000;
        double betPerLine = 0.20;
        double betPerSpin = betPerLine * 5; // Celková stávka na jeden spin (5 línií)

        double totalWagered = 0;
        double totalWon = 0;
        int totalBonusTriggers = 0;

        System.out.println("🚀 Simulácia Monte Carlo spustená... (Java 25)");
        System.out.println("Cieľ: " + totalSpins + " základných spinov.");

        long startTime = System.currentTimeMillis();

        for (long i = 0; i < totalSpins; i++) {
            // 1. Základný spin
            totalWagered += betPerSpin;
            Symbol[][] grid = engine.generateGrid(3, 5);
            totalWon += engine.calculateTotalWin(grid, betPerLine);

            // 2. Kontrola Scatterov (Bonusová hra)
            if (engine.countScatters(grid) >= 3) {
                totalBonusTriggers++;
                // Free spiny nezvyšujú totalWagered, len totalWon
                for (int f = 0; f < 10; f++) {
                    Symbol[][] freeGrid = engine.generateGrid(3, 5);
                    totalWon += engine.calculateTotalWin(freeGrid, betPerLine);
                }
            }

            // Progress bar (voliteľné, vypíše každých 25%)
            if (i % (totalSpins / 4) == 0 && i > 0) {
                System.out.println("...pracujem... " + (i * 100 / totalSpins) + "%");
            }
        }

        long endTime = System.currentTimeMillis();
        double rtp = (totalWon / totalWagered) * 100;

        System.out.println("\n--- VÝSLEDOK MONTE CARLO ---");
        System.out.printf("Počet základných spinov : %d%n", totalSpins);
        System.out.printf("Počet trafených bonusov : %d (1 k %d)%n", totalBonusTriggers, (totalSpins / totalBonusTriggers));
        System.out.printf("Celkovo stavené         : %.2f €%n", totalWagered);
        System.out.printf("Celkovo vyplatené       : %.2f €%n", totalWon);
        System.out.println("-------------------------");
        System.out.printf("NAMERANÉ CELKOVÉ RTP    : %.2f %%%n", rtp);
        System.out.println("-------------------------");
        System.out.println("Čas simulácie           : " + (endTime - startTime) + " ms");
        System.out.printf("Rýchlosť                : %.0f spinov/s%n", (double)totalSpins / (endTime - startTime) * 1000);
    }
}