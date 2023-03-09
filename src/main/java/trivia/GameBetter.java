package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// REFACTOR ME
public class GameBetter implements IGame {
   public static final int POSITIONS_COUNT = 12;
   Queue<Player> players = new LinkedList<>();
   int[] places = new int[6];
   int[] purses = new int[6];
   boolean[] inPenaltyBox = new boolean[6];

   LinkedList popQuestions = new LinkedList();
   LinkedList scienceQuestions = new LinkedList();
   LinkedList sportsQuestions = new LinkedList();
   LinkedList rockQuestions = new LinkedList();

   boolean isGettingOutOfPenaltyBox;

   public GameBetter() {
      for (int i = 0; i < 50; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(createRockQuestion(i));
      }
   }

   public String createRockQuestion(int index) {
      return "Rock Question " + index;
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));
      places[howManyPlayers()] = 0;
      purses[howManyPlayers()] = 0;
      inPenaltyBox[howManyPlayers()] = false;

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());

      return true;
   }

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(getCurrentPlayer().getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (getCurrentPlayer().isInJail()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");
            getCurrentPlayer().setPosition((getCurrentPlayer().getPosition() + roll) % POSITIONS_COUNT);

            System.out.println(getCurrentPlayer().getName()
                               + "'s new location is "
                               + getCurrentPlayer().getPosition());
            System.out.println("The category is " + currentCategory().getName());
            askQuestion();
         } else {
            System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         getCurrentPlayer().setPosition((getCurrentPlayer().getPosition() + roll) % POSITIONS_COUNT);

         System.out.println(getCurrentPlayer().getName()
                            + "'s new location is "
                            + getCurrentPlayer().getPosition());
         System.out.println("The category is " + currentCategory().getName());
         askQuestion();
      }

   }

   private void askQuestion() {
      if (Category.POP.equals(currentCategory()))
         System.out.println(popQuestions.removeFirst());
      if (Category.SCIENCE.equals(currentCategory()))
         System.out.println(scienceQuestions.removeFirst());
      if (Category.SPORTS.equals(currentCategory()))
         System.out.println(sportsQuestions.removeFirst());
      if (Category.ROCK.equals(currentCategory()))
         System.out.println(rockQuestions.removeFirst());
   }


   private Category currentCategory() {
      int placeModulo = getCurrentPlayer().getPosition() % 4;
      if (placeModulo == 0) return Category.POP;
      if (placeModulo == 1) return Category.SCIENCE;
      if (placeModulo == 2) return Category.SPORTS;
      if (placeModulo == 3) return Category.ROCK;
      throw new RuntimeException("No category!");
   }

   public boolean wasCorrectlyAnswered() {
      if (getCurrentPlayer().isInJail()) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            getCurrentPlayer().increaseCoin();
            System.out.println(getCurrentPlayer().getName()
                               + " now has "
                               + getCurrentPlayer().getCoins()
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            nextPlayer();

            return winner;
         } else {
            nextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         getCurrentPlayer().increaseCoin();
         System.out.println(getCurrentPlayer().getName()
                            + " now has "
                            + getCurrentPlayer().getCoins()
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         nextPlayer();

         return winner;
      }
   }

   private void nextPlayer() {
      players.add(players.poll()); // add current player at the end of the queue
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(getCurrentPlayer().getName() + " was sent to the penalty box");
      getCurrentPlayer().setInJail(true);

      nextPlayer();
      return true;
   }

   private Player getCurrentPlayer() {
      return players.peek();
   }


   private boolean didPlayerWin() {
      return !(getCurrentPlayer().getCoins() == 6);
   }
}
