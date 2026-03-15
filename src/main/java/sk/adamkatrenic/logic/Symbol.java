package sk.adamkatrenic.logic;

public enum Symbol {
    // payout, icon, weight
    CHERRY(3, "🍒", 55),
    LEMON(7, "🍋", 35),
    ORANGE(14, "🍊", 18),
    SEVEN(100, "7️⃣", 4),
    WILD(0, "🃏", 4),
    SCATTER(0, "🌟", 4);

    public final int payout;
    public final String icon;
    public final int weight;

    Symbol(int payout, String icon, int weight) {
        this.payout = payout;
        this.icon = icon;
        this.weight = weight;
    }
}