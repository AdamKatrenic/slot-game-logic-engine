package sk.adamkatrenic.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlotEngineTest {

    private SlotEngine engine;
    private final double delta = 0.001;

    @BeforeEach
    void setUp() {
        engine = new SlotEngine();
    }

    private Symbol[][] createEmptyGrid() {
        Symbol[][] grid = new Symbol[3][5];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 5; c++) {
                grid[r][c] = Symbol.SCATTER;
            }
        }
        return grid;
    }

    @Test
    void testCalculateWinWithThreeCherries() {
        Symbol[][] mockGrid = createEmptyGrid();
        mockGrid[1][0] = Symbol.CHERRY;
        mockGrid[1][1] = Symbol.CHERRY;
        mockGrid[1][2] = Symbol.CHERRY;

        double win = engine.calculateTotalWin(mockGrid, 1.0);
        assertEquals(3.0, win, delta);
    }

    @Test
    void testWildSubstitutionForSeven() {
        Symbol[][] mockGrid = createEmptyGrid();
        // Línia: WILD - WILD - SEVEN - LEMON - LEMON
        mockGrid[1][0] = Symbol.WILD;
        mockGrid[1][1] = Symbol.WILD;
        mockGrid[1][2] = Symbol.SEVEN;
        mockGrid[1][3] = Symbol.LEMON;
        mockGrid[1][4] = Symbol.LEMON;

        double win = engine.calculateTotalWin(mockGrid, 1.0);
        // Payout 100.0 * (3 / 3.0) = 100.0
        assertEquals(100.0, win, delta, "WILDy by mali nahradiť SEVEN a vrátiť plný payout");
    }

    @Test
    void testOnlyWildsOnLine() {
        Symbol[][] mockGrid = createEmptyGrid();
        // Línia 1: WILD - WILD - WILD - SCATTER - SCATTER
        mockGrid[1][0] = Symbol.WILD;
        mockGrid[1][1] = Symbol.WILD;
        mockGrid[1][2] = Symbol.WILD;

        double win = engine.calculateTotalWin(mockGrid, 1.0);
        // Očakávame 100.0 (SEVEN payout), pretože target zostal null a count >= 3
        assertEquals(100.0, win, delta, "Tri WILD symboly by sa mali vyhodnotiť ako SEVEN");
    }

    @Test
    void testBrokenLineReturnsZero() {
        Symbol[][] mockGrid = createEmptyGrid();
        // SEVEN - SEVEN - LEMON - SEVEN - SEVEN (Línia prerušená na indexe 2)
        // Keďže celá mriežka je LEMON, musíme si dať pozor na horizontálnu líniu
        mockGrid[1][0] = Symbol.SEVEN;
        mockGrid[1][1] = Symbol.SEVEN;
        mockGrid[1][2] = Symbol.CHERRY; // LEMON by mohol vytvoriť výhru citrónov, preto CHERRY
        mockGrid[1][3] = Symbol.SEVEN;
        mockGrid[1][4] = Symbol.SEVEN;

        double win = engine.calculateTotalWin(mockGrid, 1.0);
        assertEquals(0.0, win, delta, "Prerušená línia (iba 2 symboly) nesmie vyplatiť nič");
    }

    @Test
    void testCountScatters() {
        Symbol[][] mockGrid = createEmptyGrid(); // Celá mriežka (15 políčok) je SCATTER
        int scatters = engine.countScatters(mockGrid);
        assertEquals(15, scatters, "V prázdnej mriežke vyplnenej Scatterni by malo byť 15 symbolov");
    }
}