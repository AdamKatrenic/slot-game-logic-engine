package sk.adamkatrenic.logic;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StatisticsManager {
    private double totalBet = 0;
    private double totalWin = 0;
    private int spinsCount = 0;

    public void addSpin(double bet, double win) {
        this.totalBet += bet;
        this.totalWin += win;
        this.spinsCount++;
    }

    public void saveSessionReport() {
        double sessionRtp = (totalBet > 0) ? (totalWin / totalBet) * 100 : 0;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        try (FileWriter fw = new FileWriter("session_report.txt", true);
             PrintWriter pw = new PrintWriter(fw)) {

            pw.println("--- Session Report [" + dtf.format(LocalDateTime.now()) + "] ---");
            pw.println("Počet spinov: " + spinsCount);
            pw.println("Celková stávka: " + totalBet + " €");
            pw.println("Celková výhra: " + totalWin + " €");
            pw.println("Reálne RTP relácie: " + String.format("%.2f", sessionRtp) + " %");
            pw.println("--------------------------------------------------");
            System.out.println("Štatistiky boli uložené do session_report.txt");

        } catch (IOException e) {
            System.err.println("Chyba pri ukladaní štatistík: " + e.getMessage());
        }
    }
}