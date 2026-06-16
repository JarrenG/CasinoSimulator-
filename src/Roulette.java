import java.util.ArrayList;
import java.util.Scanner;

public class Roulette extends GameTable{

    private double tableBet;
    private Scanner input;

    public Roulette(String tableName, double minBet, double maxBet) {

        super(tableName, minBet, maxBet);
        this.tableBet = minBet;
        this.input = new Scanner(System.in);

    }

    @Override
    public void playRound() {

        System.out.println("\n--- New round of Roulette at " + getTableName() + " ---");

        ArrayList<Player> players = getPlayersAtTable();
        if (players.isEmpty()) {
            System.out.println("No players at the table.");
            return;
        }


        int[] betTypes = new int[players.size()];
        String[] chosenColors = new String[players.size()];
        int[] chosenNumbers = new int[players.size()];
        double[] playerBets = new double[players.size()];

        // 1. Process each player's bet and selections
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println("\n (Balance: $" + p.getMoney() + ")");

            if (!p.canAfford(getMinBet())) {
                System.out.println(p.getName() + " cannot afford the minimum table bet.");
                betTypes[i] = 0; // 0 marks them as not playing
                continue;
            }

            // Custom Bet Validation Loop 
            double currentBet = 0;
            boolean validBet = false;

            while (!validBet) {
                System.out.print("Enter your bet (Min: $" + getMinBet() + ", Max: $" + getMaxBet() + "): ");
                currentBet = input.nextDouble();
                input.nextLine(); // Clear the scanner buffer

                if (currentBet < getMinBet() || currentBet > getMaxBet()) {
                    System.out.println("Invalid amount! Bet must be between the minimum and maximum limits.");
                } else if (!p.canAfford(currentBet)) {
                    System.out.println("You don't have enough money for that bet! Current balance: $" + p.getMoney());
                } else {
                    validBet = true;
                }
            }

            // Save the validated bet and deduct funds
            playerBets[i] = currentBet;
            p.addMoney(-currentBet);
            System.out.println("-----------------------------------------");
            System.out.println(p.getName() + " places a bet of $" + currentBet);
            System.out.println("-----------------------------------------");


            // Let the player pick their bet type
            System.out.println("What would you like to bet on?");
            System.out.println("1 - A specific Color (Red or Black)");
            System.out.println("2 - A specific Number (0 - 36)");
            System.out.print("Enter choice (1 or 2): ");
            betTypes[i] = input.nextInt();
            input.nextLine(); // Clear buffer

            if (betTypes[i] == 1) {
                System.out.print("Enter color (Red/Black): ");
                chosenColors[i] = input.nextLine().trim();
            } else if (betTypes[i] == 2) {
                System.out.print("Enter number (0-36): ");
                chosenNumbers[i] = input.nextInt();
                input.nextLine(); // Clear buffer
            } else {
                System.out.println("Invalid choice! Forfeiting selection.");
                betTypes[i] = 0; // Treats it as a lost/invalid round, but keep their bet money
            }
        }

        // 2. Spin the wheel
        System.out.println("\n--- Spinning the Roulette Wheel! ---");
        int winningNumber = (int) (Math.random() * 37);
        String winningColor;

        if (winningNumber == 0) {
            winningColor = "Green";
        } else if (winningNumber % 2 == 0) {
            winningColor = "Black";
        } else {
            winningColor = "Red";
        }

        System.out.println("The ball landed on: " + winningNumber + " (" + winningColor + ")");

        // 3. Evaluate payouts based on what they picked
        System.out.println("\n--- Round Results ---");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);

            // Skip players who couldn't afford to play
            if (betTypes[i] == 0) continue;

            boolean won = false;
            double winnings = 0;
            double originalBet = playerBets[i];

            if (betTypes[i] == 1) {
                // Evaluated as a color bet (Pays 2x)
                if (chosenColors[i].equalsIgnoreCase(winningColor)) {
                    won = true;
                    winnings = originalBet * 2;
                }
            } else if (betTypes[i] == 2) {
                // Evaluated as a single number bet (Pays 35x)
                if (chosenNumbers[i] == winningNumber) {
                    won = true;
                    winnings = originalBet * 35;
                }
            }

            if (won) {
                p.addMoney(winnings);
                System.out.println(p.getName() + " wins! New balance: $" + p.getMoney());
            } else {
                System.out.println(p.getName() + " lost this round.");
            }
        }


    }

    @Override
    public void evaluatePayouts() {

    }



}
