import java.util.ArrayList;
import java.util.Scanner;

public class BlackJack extends GameTable{

    private Deck deck;
    private ArrayList<Card> dealerHand;
    private double tableBet;
    private Scanner input;

    public BlackJack(String tableName, double minBet, double maxBet) {
        super(tableName, minBet, maxBet);
        this.deck = new Deck();
        this.dealerHand = new ArrayList<>();
        this.tableBet = minBet;
        this.input = new Scanner(System.in);
    }

    @Override
    public void playRound() {
        System.out.println("\n--- New round of BlackJack at " + getTableName() + " ---" );

        ArrayList<Player> players = getPlayersAtTable();
        if (players.isEmpty()){
            System.out.println("No players at this table");
            return;
        }

        // 1. Prepare the game
        deck.shuffle();
        dealerHand.clear();

        // Track score and bets for the player
        int[] playerScores = new int[players.size()];
        double[] playerBets = new double[players.size()];


        // The Game
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            System.out.println("\n (Balance: $" + p.getMoney() + ")");

            if (!p.canAfford(getMinBet())) {
                System.out.println(p.getName() + " can not afford the table bet");
                playerScores[i] = 0;
                continue;
            }

            double currentBet = 0;
            boolean validBet = false;

            while (!validBet) {
                System.out.print("Enter your bet (Min: $" + getMinBet() + ", Max: $" + getMaxBet() + "): ");
                currentBet = input.nextDouble();
                input.nextLine();

                if (currentBet < getMinBet() || currentBet > getMaxBet()) {
                    System.out.println("Invalid amount! Bet must be between the minimum and maximum limits.");
                } else if (!p.canAfford(currentBet)) {
                    System.out.println("You don't have enough money for that bet! Current balance: $" + p.getMoney());
                } else {
                    validBet = true;
                }
            }

            // Save their validated bet amount
            playerBets[i] = currentBet;
            p.addMoney(-currentBet);
            System.out.println("-----------------------------------------");
            System.out.println(p.getName() + " places a bet of $" + currentBet);
            System.out.println("-----------------------------------------");

            // Deal Cards to dealer
            dealerHand.add(deck.dealCard());
            dealerHand.add(deck.dealCard());
            System.out.println("Dealer Shows " + dealerHand.get(0) + " (Total: " +dealerHand.get(0).getValue() + " )");

            // Deal player their first 2 cards
            ArrayList<Card> hand = new ArrayList<>();
            hand.add(deck.dealCard());
            hand.add(deck.dealCard());

            int score = hand.get(0).getValue() + hand.get(1).getValue();
            System.out.println("You have " + hand.get(0) + " and " + hand.get(1) + " (Total: " + score + " )");

            // hit or stand
            boolean userTurn = true;
            while (userTurn && score < 21) {
                System.out.println("Would you like to Hit(1) or Stand(2)");
                int choice = input.nextInt();
                input.nextLine();

                if (choice == 1) {
                    Card newCard = deck.dealCard();
                    hand.add(newCard);
                    score += newCard.getValue();
                    System.out.println("Dealt: " + newCard + "(Total: " + score + " )");

                    if (score > 21){
                        System.out.println("You Bust!");
                    }
                }

                else {
                    userTurn = false;
                }


            playerScores[i] = score;

            }


        }

        //  Dealers turn until they have 17+
        int dealerScore = dealerHand.get(0).getValue() + dealerHand.get(1).getValue();
        System.out.println("\n Dealer reveals second card: " + dealerHand.get(1) + " (Total: " + dealerScore + " )");

        while (dealerScore < 17) {
            Card newCard = deck.dealCard();
            dealerHand.add(newCard);
            dealerScore += newCard.getValue();
            System.out.println("Dealer hits and gets " + newCard + " (Total: " + dealerScore + " )");


        }

        //  Evaluate Payouts
        System.out.println("\n--- Round Results ---");
        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            int pScore = playerScores[i];
            double pbet = playerBets[i];

            if (pScore == 0) continue; // Skipped the round

            if (pScore > 21) {
                System.out.println(p.getName() + " lost (Busted).");
            } else if (dealerScore > 21 || pScore > dealerScore) {
                double winnings = pbet * 2;
                p.addMoney(winnings);
                System.out.println(p.getName() + " wins! New balance: $" + p.getMoney());
            } else if (pScore == dealerScore) {
                // Push: return their original bet back
                p.addMoney(pbet);
                System.out.println(p.getName() + " pushes with the dealer. Bet returned.");
            } else {
                System.out.println(p.getName() + " lost to the dealer.");
            }
        }



    }

    @Override
    public void evaluatePayouts() {


    }


}
