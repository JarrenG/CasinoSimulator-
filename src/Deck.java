import java.util.ArrayList;

public class Deck {

    private ArrayList<Card> cards;

    public Deck() {

        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9","10", "Jack", "Queen", "King", "Ace"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < cards.size(); i++) {
            // Pick a random position in the deck
            int randomPos = (int) (Math.random() * cards.size());

            // Remove the card from its current spot and pull it out
            Card removedCard = cards.remove(i);

            // Insert it back into the deck at the random position
            cards.add(randomPos, removedCard);
        }
    }

    public Card dealCard() {
        if(cards.isEmpty()) {
            return null;
        }
        //Remove and return the last card in the list
        return cards.remove(cards.size() - 1);
    }

}
