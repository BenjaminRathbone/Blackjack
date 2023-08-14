// Card.java
// Card class represents a playing Card 
// Modified version of Whatcom Community College CS 145 demo code

public class Card
{
   //fields
   private final String face; // face o card ("Ace", "Two", ...) 
   private final String suit; // suit of card ("Hearts", Diamonds", etc)
 
   // two-argument constructor initializes card's face and suit 
   public Card(String cardFace, String cardSuit)
   {
      this.face = cardFace; // initialize face of card
      this.suit = cardSuit; // initialize suit of card 
   }//end of Card Constructor 
   
   //methods
   
   //return card's face
   public String getFace()
   {
      return face;
   }
   
   //return card's suit
   public String getSuit()
   {
      return suit;
   }
   
   //returns the value of the card
   //accepts aceValue, which determines the value of aces
   //returns an int, which is the value of the card
   public int getValue(int aceValue)
   {
      switch (face)
      {
         case "Ace":    return aceValue;
         case "Two":    return 2;
         case "Three":  return 3;
         case "Four":   return 4;
         case "Five":   return 5;
         case "Six":    return 6;
         case "Seven":  return 7;
         case "Eight":  return 8;
         case "Nine":   return 9;
         default:       return 10;  //10s and face cards
      }//end of switch                   
   }//end of getValue method
   
   //return String representation of Card
   public String toString()
   {
      return face + " of " + suit; 
   } // end of toString method 
} // end class Card 