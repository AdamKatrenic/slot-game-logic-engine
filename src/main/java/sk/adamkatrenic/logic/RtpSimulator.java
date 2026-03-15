package sk.adamkatrenic.logic;

public class RtpSimulator {
    public static void main(String[] args) {
        SlotEngine engine = new SlotEngine();
        long totalSpins = 1_000_000;
        double betPerSpin = 1.0;
        double totalWagered = 0;
        double totalWon = 0;

        System.out.println("Simulácia spustená... (Java 25)");
        long startTime = System.currentTimeMillis();

        for (long i = 0; i < totalSpins; i++) {
            Symbol[][] grid = engine.generateGrid(3, 5);
            double win = engine.calculateTotalWin(grid, 0.20); // 0.20€ na líniu

            totalWagered += betPerSpin;
            totalWon += win;
        }

        long endTime = System.currentTimeMillis();
        double rtp = (totalWon / totalWagered) * 100;

        System.out.println("--- VÝSLEDOK MONTE CARLO ---");
        System.out.println("Počet spinov: " + totalSpins);
        System.out.println("Celkovo stavené: " + totalWagered + " €");
        System.out.println("Celkovo vyplatené: " + totalWon + " €");
        System.out.println("Namerané RTP: " + String.format("%.2f", rtp) + " %");
        System.out.println("Čas simulácie: " + (endTime - startTime) + " ms");
    }
}