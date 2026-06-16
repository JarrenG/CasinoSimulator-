public class Player {

    private String name;
    private double money;

    public Player(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public boolean addMoney(double moneyToAdd) {
        // This prevents the Player from going in debt
        if (moneyToAdd < 0 && (money + moneyToAdd) < 0) {
            return false;
        }
        money += moneyToAdd;
        return true;
    }

    public boolean canAfford(double betAmount) {
        return money >= betAmount;
    }

    public String getName(){ return name; }

    public double getMoney() { return money; }

    @Override
    public String toString() {
        return String.format("Player: %-15s | Balance: $%,.2f", name, money);
    }

}
