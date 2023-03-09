package trivia;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// REFACTOR ME
public class GameBetter implements IGame {
   public static final int POSITIONS_COUNT = 12;
   Queue<Player> players = new LinkedList<>();

   boolean isGettingOutOfPenaltyBox;
   private QuestionManager questionManager;

   public GameBetter() {
      questionManager = new QuestionManager();
   }

   public boolean add(String playerName) {
      players.add(new Player(playerName));

      System.out.println(playerName + " was added");
      System.out.println("They are player number " + players.size());

      return true;
   }

   public void roll(int roll) {
      System.out.println(getCurrentPlayer().getName() + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (getCurrentPlayer().isInJail()) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(getCurrentPlayer().getName() + " is getting out of the penalty box");
            setPositionByRoll(roll);
            askQuestion();
         } else {
            System.out.println(getCurrentPlayer().getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {
         setPositionByRoll(roll);
         askQuestion();
      }

   }

   private void setPositionByRoll(int roll) {
      getCurrentPlayer().setPosition((getCurrentPlayer().getPosition() + roll) % POSITIONS_COUNT);

      System.out.println(getCurrentPlayer().getName()
                         + "'s new location is "
                         + getCurrentPlayer().getPosition());
      System.out.println("The category is " + currentCategory().getName());
   }

   private void askQuestion() {
      System.out.println(questionManager.getQuestionForCategory(currentCategory()));
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
            return isWinner();
         } else {
            nextPlayer();
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         return isWinner();
      }
   }

   private boolean isWinner() {
      getCurrentPlayer().increaseCoin();
      System.out.println(getCurrentPlayer().getName()
                         + " now has "
                         + getCurrentPlayer().getCoins()
                         + " Gold Coins.");

      boolean winner = didPlayerWin();
      nextPlayer();
      return winner;
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
