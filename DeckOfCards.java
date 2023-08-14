// DeckOfCards.java
// DeckOfCards class represents a deck of playing cards.
// Modified version of Whatcom Community College CS 145 demo code

import java.security.SecureRandom;  //imports random
import java.util.*;                 //imports util

public class DeckOfCards
{
   // random number generator 
   private static final SecureRandom randomNumbers = new SecureRandom(); 
   private static final int NUMBER_OF_CARDS = 52; 
   private Stack<Card> deck = new Stack<Card>(); //stack that will represent the deck

   // constructor fills deck of Cards
   public DeckOfCards()
   {
      String[] faces = {"Ace", "Two", "Three", "Four", "Five", "Six",
                        "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
      String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};  
  
      //populate deck with Card objects
      for (int count = 0; count < NUMBER_OF_CARDS; count++)
      {
         deck.push(new Card(faces[count % 13], suits[count / 13])); 
      } // end of for loop 
   } // end of DeckOfCards Constructor 
 
   // shuffle deck of Cards with one-pass algorithm
   public void shuffle()
   {
      //moves all cards from the stack into an array
      Card[] tempDeck = new Card[deck.size()];  //create tempDeck array
      for (int i = 0; i < tempDeck.length; i++)
      {
         tempDeck[i] = deck.pop();  //move cards from stack to array
      }
      
      //shuffles the cards in the array
      //for each Card, pick another random Card and swap them 
      for (int first = 0; first < tempDeck.length; first++)
      {
         //select a random number between 0 and the length of the deck
         int second = randomNumbers.nextInt(tempDeck.length); 
         
         //swap current Card with randomly selected Card
         Card tempCard = tempDeck[first]; 
         tempDeck[first] = tempDeck[second];
         tempDeck[second] = tempCard;  
      }//end for loop
      
      //moves cards from array back to stack
      for (int j = 0; j < tempDeck.length; j++)
      {
         deck.push(tempDeck[j]);
      }
   }//end of shuffle method 
 
   //deal one card 
   public Card dealCard()
   {
      if (!deck.isEmpty())
      {
         return deck.pop(); //pop top card of the stack
      }
      else
      {
         return null;
      }
   }//end of dealCard method 
}//end of DeckOfCards class 