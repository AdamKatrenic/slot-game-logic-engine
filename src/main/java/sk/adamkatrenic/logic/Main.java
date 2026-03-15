package sk.casino.logic;

public class Main {
    public static void main(String[] args) {
        SlotEngine engine = new SlotEngine();
        Symbol[][] result = engine.generateGrid(3, 5);

        System.out.println("--- TVOJ SPIN ---");
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 5; c++) {
                System.out.print("[" + result[r][c].icon + "] ");
            }
            System.out.println();
        }
    }
}