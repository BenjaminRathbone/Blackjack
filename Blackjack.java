//Programmer: Ben Rathbone
//CS 145
//Date: 7-11-23
//Assignment: Lab 4 - Deck of Cards
//Purpose: A program that allows the user to play blackjack against a computer-controlled dealer.

import java.util.*;  //imports Scanner

public class Blackjack
{
	public static void main(String[] args)
	{
      int aceValue = 1;       //initial value of aces
      int dealerStand = 12;   //if the dealer's hand is equal to or greater than
                              //    this value, the dealer will stand
      
      Scanner console = new Scanner(System.in); //creates Scanner object
      
      gameIntro();   //calls the gameIntro method to explain the rules
      
      //user menu
      while (true)
      {
         menuText(); //calls the menuText method to explain commands
         
         //accepts user input
         char userInput;
         try 
         {
            userInput = console.nextLine().charAt(0);   //saves input as char "userInput"
            userInput = Character.toUpperCase(userInput);  //converts char to uppercase
         }
         catch (StringIndexOutOfBoundsException e)
         {
            userInput = 'X';
         }
         
         if (userInput == 'P')   //play a game
         {
            System.out.println();
            playGame(aceValue, dealerStand, console);
         }
         else if (userInput == 'A') //toggle the value of aces
         {
            if (aceValue == 1)
            {
               aceValue = 11;
            }
            else if (aceValue == 11)
            {
               aceValue = 1;
            }
            System.out.println("Ace value set to " + aceValue + ".");
         }
         else if (userInput == 'Q') //quit game
         {
            System.out.println("Thanks for playing!");
            break;
         }
         else  //invalid input
         {
            System.out.println("Invalid input.  Please try again.");
         }
      }//end of user menu while loop
   }//end of main method
   
   //plays a single game of blackjack
   //accpets int aceValue, which is the value of aces
   //accepts int dealerStand, which is the hand value where the dealer will stand
   //accepts Scanner console 
   public static void playGame(int aceValue, int dealerStand, Scanner console)
   {
      //initializes booleans used later in the method
      boolean gameOver = false;
      boolean stand = false;
      
      //creates and shuffles a deck of cards
      DeckOfCards deck = new DeckOfCards();
      deck.shuffle();
      
      //deals the dealer's hand
      ArrayList<Card> dealerHand = new ArrayList<Card>();
      int dealerValue = 0;
      dealerHand.add(deck.dealCard());
      dealerHand.add(deck.dealCard());
      
      //deals the player's hand
      ArrayList<Card> playerHand = new ArrayList<Card>();
      int playerValue = 0;
      playerHand.add(deck.dealCard());
      playerHand.add(deck.dealCard());
      
      while (!gameOver)
      {
         //calculate the value of both player's hands
         dealerValue = calculateValue(dealerHand, aceValue);
         playerValue = calculateValue(playerHand, aceValue);
         
         //checks if the game is over
         if (dealerValue == 21 && playerValue == 21)
         {
            System.out.println("----\nDRAW\n----");
            showFinalScore(dealerHand, playerHand, aceValue);
            System.out.println("Both players got 21.");
            gameOver = true;
         }
         else if (dealerValue == 21)
         {
            System.out.println("--------\nYOU LOSE\n--------");
            showFinalScore(dealerHand, playerHand, aceValue);
            System.out.println("The dealer got 21.");
            gameOver = true;
         }
         else if (playerValue == 21)
         {
            System.out.println("--------\nYOU WIN!\n--------");
            showFinalScore(dealerHand, playerHand, aceValue);
            System.out.println("You got 21!");
            gameOver = true;
         }
         else if (dealerValue > 21 && playerValue > 21)
         {
            System.out.println("----\nDRAW\n----");
            showFinalScore(dealerHand, playerHand, aceValue);
            System.out.println("Both players bust.");
            gameOver = true;
         }
         else if (dealerValue > 21)
         {
            System.out.println("--------\nYOU WIN!\n--------");
            showFinalScore(dealerHand, playerHand, aceValue);
            System.out.println("The dealer busts!");
            gameOver = true;
         }
         else if (playerValue > 21)
         {
            System.out.println("--------\nYOU LOSE\n--------");
            showFinalScore(dealerHand, playerHand, aceValue);
            System.out.println("You bust.");
            gameOver = true;
         }
         
         //if the game is not over
         else
         {
            //show the player's hands
            System.out.println("Dealer's hand:");
            printHand(dealerHand, aceValue, true);
            
            System.out.println("Your hand:");
            printHand(playerHand, aceValue, false);
            
            //prompts the player to hit or stand
            while (true)
            {
               System.out.println("\nWhat would you like to do?\n" +
                                  "   \"H\" to hit\n" +
                                  "   \"S\" to stand");
               //accepts user input
               char userInput;
               try 
               {
                  userInput = console.nextLine().charAt(0);   //saves input as char "userInput"
                  userInput = Character.toUpperCase(userInput);  //converts char to uppercase
               }
               catch (StringIndexOutOfBoundsException e)
               {
                  userInput = 'X';
               }
      
               if (userInput == 'H')   //hit
               {
                  System.out.println("You hit.");
                  playerHand.add(deck.dealCard()); //adds a card to the player's hand
                  break;
               }
               else if (userInput == 'S') //stand
               {
                  System.out.println("You stand.");
                  gameOver = true;  //causes the gameOver while loop to end
                  stand = true;  //enables future if statements
                  break;
               }
               else  //invalid input
               {
                  System.out.println("Invalid input.  Please try again.");  
               }
            }//end of while loop
            
            //determines if the dealer will hit or stand
            if (dealerValue < dealerStand)
            {
               System.out.println("The dealer hits.\n");
               dealerHand.add(deck.dealCard()); //adds a card to the dealer's hand
               dealerValue = calculateValue(dealerHand, aceValue);   //updates dealer's score
            }
            else
            {
               System.out.println("The dealer stands.\n");
            }
         }//end of "if the game is not over" else statement
      }//end of gameOver while loop
      
      //determines the winner if there is none yet
      if (stand && dealerValue > 21)
      {
         System.out.println("--------\nYOU WIN!\n--------");
         showFinalScore(dealerHand, playerHand, aceValue);
         System.out.println("The dealer busts!");
         gameOver = true;
      }
      else if (stand && dealerValue == playerValue)
      {
         System.out.println("----\nDRAW\n----");
         showFinalScore(dealerHand, playerHand, aceValue);
         System.out.println("Both players tied.");
      }
      else if (stand && dealerValue > playerValue)
      {
         System.out.println("--------\nYOU LOSE\n--------");
         showFinalScore(dealerHand, playerHand, aceValue);
         System.out.println("The dealer scored higher.");
      }
      else if (stand && dealerValue < playerValue)
      {
         System.out.println("--------\nYOU WIN!\n--------");
         showFinalScore(dealerHand, playerHand, aceValue);
         System.out.println("You scored higher!");
      }
      else if (stand)  //pretty sure it isn't possible to reach this
      {
         System.out.println("Huh. It seems you've reached an unexpected outcome.");
         System.out.println("To be honest, I've got no clue who won.");
         System.out.println("Better luck next time, I guess?");
      }
   }
   
   //prints the cards in a player's hand
   //if it's the user's, it will also print the value of their hand
   //if it's the dealer's, it will hide the first card
   //accepts an ArrayList of cards, which is a player's hand
   //accepts int aceValue, which is the value of aces
   //accepts boolean dealer, which determines if it's the dealer's hand or not
   public static void printHand(ArrayList<Card> hand, int aceValue, boolean dealer)
   {
      int handValue = calculateValue(hand, aceValue); //calls the calculateValue method
      int i = 0;
      
      if (dealer) //if this is the dealer's hand (and the game is not over)
      {
         i++;  //increase i by 1 tp skip the first card in the for loop
         System.out.print("[???] ");   //print a "face-down" card
      }
      
      for (i = i; i < hand.size(); i++)   //print the player's hand
      {
         System.out.print("[" + hand.get(i).toString() + "] ");   //print a card
      }
      
      if (dealer)
      {
         System.out.println();
      }
      else  //if this is not the dealer's hand
      {
         System.out.println("= " + handValue);  //print the value of the hand
      }
   }
   
   //calculates the value of a player's hand
   ////accepts an ArrayList of cards, which is a player's hand
   //accepts int aceValue, which is the value of aces
   //returns int handValue, which is the value of the hand
   public static int calculateValue(ArrayList<Card> hand, int aceValue)
   {
      int handValue = 0;
      
      for (int i = 0; i < hand.size(); i++)  //for every card in hand
      {
         handValue += hand.get(i).getValue(aceValue); //add its value to handValue
      }
      
      return handValue;
   }
   
   //prints both player's hands with no hidden cards
   //also prints the value of both player's hands
   //accepts two Card ArrayLists, which are both hands
   //accepts int aceValue, which is the value of aces
   public static void showFinalScore(ArrayList<Card> dealerHand,
                                     ArrayList<Card> playerHand, int aceValue)
   {
      System.out.println("Dealer's hand:");
      printHand(dealerHand, aceValue, false);   //prints the dealer's hand
      System.out.println("Your hand:");
      printHand(playerHand, aceValue, false);   //prints the player's hand
   }
   
   //explains the rules of blackjack
   public static void gameIntro()
   {
      System.out.println("_____________");
      System.out.println("| BLACKJACK |");
      System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾");
      System.out.println("Rules: https://bicyclecards.com/how-to-play/blackjack/");
      System.out.println("By default, aces are worth 1 point, but you can toggle " +
                         "between 1 and 11 points.");
      System.out.println("Good luck!");  
      System.out.println("_____________"); 
   }//end of gameIntro method
   
   //displays menu text explaining the functions to the user
   public static void menuText()
   {
      System.out.println("\nWhat would you like to do?\n" +
                         "   \"P\" to play a game of blackjack\n" +
                         "   \"A\" to toggle the value of aces\n" +
                         "   \"Q\" to quit the game");
   }//end of menuText method
}//end of program