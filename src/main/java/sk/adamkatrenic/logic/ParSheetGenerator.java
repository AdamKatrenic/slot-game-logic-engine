package sk.adamkatrenic.logic;

import java.util.EnumSet;

public class ParSheetGenerator {

    private final SlotEngine engine;

    public ParSheetGenerator(SlotEngine engine) {
        this.engine = engine;
    }

    public void generateReport() {
        int totalWeight = 0;
        for (Symbol s : Symbol.values()) {
            totalWeight += s.weight;
        }

        // Získame váhu WILDu pre efektívnu pravdepodobnosť
        double wildProb = (double) Symbol.WILD.weight / totalWeight;

        System.out.println("=== TEORETICKÝ PAR SHEET (Matematický model) ===");
        System.out.println(String.format("%-10s | %-15s | %-12s | %-10s", "Symbol", "Efektívna P(1)", "Šanca 5 v rade", "Prínos k RTP"));
        System.out.println("----------------------------------------------------------------------");

        double totalTheoreticalRtp = 0;

        for (Symbol symbol : EnumSet.allOf(Symbol.class)) {
            if (symbol == Symbol.SCATTER || symbol == Symbol.WILD) continue;

            // Efektívna pravdepodobnosť: šanca, že padne daný symbol ALEBO žolík
            double p = (double) (symbol.weight + Symbol.WILD.weight) / totalWeight;
            double q = 1.0 - p; // Pravdepodobnosť, že línia nepokračuje

            // MATEMATICKY PRESNÉ PRAVDEPODOBNOSTI LÍNIÍ:
            // P(presne 3) = P*P*P * Q (štvrtý symbol to musí prerušiť)
            double prob3 = Math.pow(p, 3) * q;

            // P(presne 4) = P*P*P*P * Q (piaty symbol to musí prerušiť)
            double prob4 = Math.pow(p, 4) * q;

            // P(presne 5) = P*P*P*P*P
            double prob5 = Math.pow(p, 5);

            // Výpočet prínosu k RTP (payouty podľa tvojej logiky)
            double rtpContrib = (prob3 * symbol.payout * (3.0 / 3.0)) +
                    (prob4 * symbol.payout * (4.0 / 3.0)) +
                    (prob5 * symbol.payout * (5.0 / 3.0));

            totalTheoreticalRtp += rtpContrib;

            System.out.println(String.format("%-10s | %-15.2f%% | %-12.4f%% | %-10.4f%%",
                    symbol.name(),
                    p * 100,
                    prob5 * 100,
                    rtpContrib * 100));
        }

        System.out.println("----------------------------------------------------------------------");
        System.out.printf("CELKOVÉ TEORETICKÉ RTP (Línie + WILD): %.2f %%%n", totalTheoreticalRtp * 100);
        System.out.println("Poznámka: Zostávajúce RTP do 100% tvoria SCATTER výhry a Free Spiny.");
    }
}