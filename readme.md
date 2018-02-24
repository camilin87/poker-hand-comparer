# Poker Hand Comparer  

[![Build Status](https://travis-ci.org/camilin87/poker-hand-comparer.svg?branch=master)](https://travis-ci.org/camilin87/poker-hand-comparer)

Compares multiple poker hands and displays the winner  

## Usage  

```bash
./build/install/PokerHandComparer/bin/PokerHandComparer "2H 3D 5S 9C KD" "2C 3H 4S 8C AH" "7H 8H 9H 6H 5H"
```

Prints out:

```
7H 8H 9H 6H 5H
```

## Development  

### Build the project  

```bash
gradle clean && gradle build && gradle installDist
```

### Run the tests  

```bash
gradle test
```

### Package the application  

```bash
gradle distZip
```

## Examples  

| Black          | White           | Winner                 |
|:-------------- |:--------------- |:---------------------- |
| 2H 3D 5S 9C KD | 2C 3H 4S 8C AH  | White (High Card: Ace) |
| 2H 4S 4C 2D 4H | 2S 8S AS QS 3S  | Black (Full House)     |
| 2H 3D 5S 9C KD | 2C 3H 4S 8C KH  | Black (High Card: 9)   |
| 2H 3D 5S 9C KD | 2D 3H 5C 9S KH  | Tie                    |

## Poker Scoring Rules    
A poker deck contains 52 cards - each card has a suit, which is one of clubs, diamonds, hearts, or spades (denoted C, D, H, and S in the input data). Each card also has a value which is one of  2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A). For scoring purposes, the suits are unordered while the values are ordered as given above, with 2 being the lowest and ace the highest value. A poker hand consists of 5 cards dealt from the deck. Poker hands are ranked by the following partial order from lowest to highest.

### High Card  
Hands that do not fit any higher category are ranked by the value of their highest card. If the highest cards have the same value, the hands are ranked by the next highest, and so on.

### Pair  
2 of the 5 cards in the hand have the same value.  Hands which both contain a pair are ranked by the value of the cards forming the pair. If these values are the same, the hands are ranked by the values of the cards not forming the pair, in decreasing order.

### Two Pairs  
The hand contains 2 different pairs. Hands which both contain 2 pairs are ranked by the value of their highest pair. Hands with the same highest pair are ranked by the value of their other pair. If these values are the same the hands are ranked by the value of the remaining card.

### Three of a Kind  
Three of the cards in the hand have the same value. Hands which both contain three of a kind are ranked by the value of the 3 cards.

### Straight  
Hand contains 5 cards with consecutive values. Hands which both contain a straight are ranked by their highest card.

### Flush  
Hand contains 5 cards of the same suit. Hands that are both flushes are ranked using the rules for High Card.

### Full House  
A full house consists of three cards of the same value, with the remaining 2 cards forming a pair. Ranked by the value of the 3 cards.

### Four of a Kind  
Four cards with the same value are four of a kind, ranked by the value of the 4 cards.

### Straight Flush  
5 cards of the same suit with consecutive values. Ranked by the highest card in the hand.
