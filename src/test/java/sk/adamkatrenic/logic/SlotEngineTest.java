package sk.adamkatrenic.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlotEngineTest {

    @Test
    void testCalculateWinWithThreeCherries() {
        SlotEngine engine = new SlotEngine();

        Symbol[][] mockGrid = {
                {Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON},
                {Symbol.CHERRY, Symbol.CHERRY, Symbol.CHERRY, Symbol.LEMON, Symbol.LEMON},
                {Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON}
        };

        double betPerLine = 1.0;
        double win = engine.calculateTotalWin(mockGrid, betPerLine);
        assertTrue(win >= 3.0, "Výhra za 3 čerešne by mala byť aspoň 3.0");
    }

    @Test
    void testWildSymbolSubstitution() {
        SlotEngine engine = new SlotEngine();

        // Grid: Čerešňa - WILD - Čerešňa (WILD musí nahradiť čerešňu)
        Symbol[][] mockGrid = {
                {Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON},
                {Symbol.CHERRY, Symbol.WILD, Symbol.CHERRY, Symbol.LEMON, Symbol.LEMON},
                {Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON}
        };

        double win = engine.calculateTotalWin(mockGrid, 1.0);
        assertTrue(win > 0, "WILD by mal nahradiť čerešňu a vytvoriť výhernú líniu");
    }

    @Test
    void testCountScatters() {
        SlotEngine engine = new SlotEngine();
        Symbol[][] mockGrid = {
                {Symbol.SCATTER, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON},
                {Symbol.LEMON, Symbol.SCATTER, Symbol.LEMON, Symbol.LEMON, Symbol.LEMON},
                {Symbol.LEMON, Symbol.LEMON, Symbol.SCATTER, Symbol.LEMON, Symbol.LEMON}
        };

        int scatters = engine.countScatters(mockGrid);
        assertEquals(3, scatters, "Mal by nájsť presne 3 symboly SCATTER");
    }

    @Test
    void testZigZagPaylineWin() {
        SlotEngine engine = new SlotEngine();

        // Vytvoríme grid pre tvar "V" (naša 4. línia: indexy {0, 1, 2, 1, 0})
        // Nastavíme symboly SEVEN na tieto pozície
        Symbol[][] mockGrid = new Symbol[3][5];

        // Najprv vyplníme celý grid citrónmi (aby bol prázdny od výhier)
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 5; c++) mockGrid[r][c] = Symbol.LEMON;
        }

        // Dosadíme SEVEN do tvaru "V" na prvých 3 stĺpcoch
        mockGrid[0][0] = Symbol.SEVEN;
        mockGrid[1][1] = Symbol.SEVEN;
        mockGrid[2][2] = Symbol.SEVEN;

        double win = engine.calculateTotalWin(mockGrid, 1.0);

        //SEVEN má payout 100. Pri 3 symboloch (100 * (3/3.0)) = 100.0
        assertTrue(win >= 100.0, "Cik-cak línia by mala rozpoznať výhru 100.0 pre symbol SEVEN");
    }

    @Test
    void testNoWinWhenLineIsBroken() {
        SlotEngine engine = new SlotEngine();

        // Vytvoríme grid, kde v každom riadku na treťom stĺpci (index 2)
        // bude SCATTER alebo LEMON, aby sme prerušili VŠETKY horizontálne línie.
        Symbol[][] mockGrid = {
                {Symbol.SEVEN, Symbol.SEVEN, Symbol.LEMON, Symbol.SEVEN, Symbol.SEVEN},
                {Symbol.SEVEN, Symbol.SEVEN, Symbol.LEMON, Symbol.SEVEN, Symbol.SEVEN},
                {Symbol.SEVEN, Symbol.SEVEN, Symbol.LEMON, Symbol.SEVEN, Symbol.SEVEN}
        };

        double win = engine.calculateTotalWin(mockGrid, 1.0);

        // Očakávame 0, pretože žiadna línia nemá 3 rovnaké symboly po sebe zľava
        assertEquals(0.0, win, "Prerušená línia nesmie vyplatiť výhru");
    }
}