import java.util.ArrayList;

public abstract class GameTable {

    private String tableName;
    private double minBet;
    private double maxBet;
    private ArrayList<Player> playersAtTable;

    public GameTable(String tableName, double minBet, double maxBet) {

        this.tableName = tableName;
        this.minBet = Math.max(0, minBet);
        this.maxBet = Math.max(this.minBet, maxBet);
        this.playersAtTable = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        playersAtTable.add(player);
        System.out.println(player.getName() + " has joined " + tableName);
    }

    public void removePlayer(Player player) {
        if (playersAtTable.remove(player)) {
            System.out.println(player.getName() + " has left " + tableName);
        }
    }


    public String getTableName() { return tableName; }

    public double getMinBet() { return minBet; }

    public double getMaxBet() { return maxBet; }

    public ArrayList<Player> getPlayersAtTable() {
        return new ArrayList<>(playersAtTable);
    }

    public void setPlayersAtTable(ArrayList<Player> playersAtTable) {
        this.playersAtTable = playersAtTable;
    }

    public abstract void playRound();

    public abstract void evaluatePayouts();
}
