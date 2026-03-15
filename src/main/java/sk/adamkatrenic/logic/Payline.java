package sk.adamkatrenic.logic;

public class Payline {
    private final int[] positions;

    public Payline(int[] positions) {
        this.positions = positions;
    }

    public int[] getPositions() {
        return positions;
    }
}