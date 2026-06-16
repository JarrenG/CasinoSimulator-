import java.util.Scanner;

public class Game {

    private Player player;
    private Scanner input;
    private boolean isRunning;

    private BlackJack blackJack;
    private Roulette roulette;

    public Game() {
        this.input = new Scanner(System.in);
        this.isRunning = true;

        this.blackJack = new BlackJack("VIP BlackJack", 1, 100000);
        this.roulette = new Roulette("Luxury Roulette", 1, 100000);
    }

    public void Start() {

        System.out.println("==========================");
        System.out.println("  WELCOME TO JG'S CASINO  ");
        System.out.println("==========================");

        // 1. User enters their name
        System.out.print("Enter Your Name: ");
        String name = input.nextLine();

        this.player = new Player(name, 10000.0);
        System.out.println("\n Welcome " + player.getName() + "!, you have a starting balance of $" + player.getMoney());

        // 2. Game loop
        while (isRunning) {
            if (player.getMoney() <= 0) {
                System.out.println("\nGame over, you lost all your money and now you're broke");
                isRunning = false;
                break;
            }

            displayMainMenu();
        }
    }

    public void displayMainMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("Current Balance: $" + player.getMoney());
        System.out.println("1 - BlackJack");
        System.out.println("2 - Roulette ");
        System.out.println("3 - Exit Game");
        System.out.print("Select an option: ");

        int choice = input.nextInt();
        input.nextLine().trim();

        switch (choice) {

            case 1:
                blackJack.addPlayer(player);
                boolean keepPlaying = true;

                while (keepPlaying && player.getMoney() >= blackJack.getMinBet()) {
                    blackJack.playRound();

                    if (player.getMoney() < blackJack.getMinBet()) {
                        System.out.println("You don't have enough money to play this game.");
                        break;
                    }

                    System.out.print("Play again(1) or Leave BlackJack(2): ");
                    int tableChoice = input.nextInt();
                    input.nextLine().trim();

                    if (tableChoice == 2) {
                        keepPlaying = false;
                        break;
                    }
                }

                blackJack.removePlayer(player);
                break;


            case 2:
                roulette.addPlayer(player);
                keepPlaying = true;

                while (keepPlaying && player.getMoney() >= roulette.getMinBet()) {
                    roulette.playRound();

                    if (player.getMoney() < roulette.getMinBet()) {
                        break;
                    }

                    System.out.print("Play again(1) or Leave Roulette(2): ");
                    int tableChoice = input.nextInt();
                    input.nextLine().trim();

                    if (tableChoice == 2) {
                        keepPlaying = false;
                        break;
                    }

                }

                roulette.removePlayer(player);
                break;

            case 3:
                System.out.println("Cashing out");
                isRunning = false;
                break;

            default:
                System.out.println("Invalid option, choose 1, 2, or 3.");
                break;
        }

    }
}
