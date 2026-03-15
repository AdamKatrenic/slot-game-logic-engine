package sk.casino.logic;

public enum Symbol {
    CHERRY(2, "🍒", 50),
    LEMON(5, "🍋", 30),
    ORANGE(10, "🍊", 15),
    SEVEN(50, "7️⃣", 5);

    public final int payout;
    public final String icon;
    public final int weight;

    Symbol(int payout, String icon, int weight) {
        this.payout = payout;
        this.icon = icon;
        this.weight = weight;
    }
}