# ChessJava
Terminal based Chess game written in Java

![Terminal interface](https://github.com/wendellavila/ChessJava/main/repo-assets/chessjava.jpg "Terminal interface")

## How to play
* To move a piece, specify the origin and destination square<br>
```e2e4``` ```d2-d3``` ```a1 a5```
* To visualize the possible moves of a piece, use the word "tip" followed by the square the piece is in<br>
```tip c2```
* To resign, type "resign".<br>
```resign```
* To propose a draw by agreement, type "draw".<br>
```draw```

## What works
* Any chess move
* 2 player game
* Board visualization
* List of recent moves at the right side of the board
* Seeing a piece's possible moves
* Bad input treatment
* Draw by agreement
* Resignation
* Replaying match history after match end

## What wasn't implemented
* Automatic checkmate detection (you need to actually capture the King for the checkmate to be registered)
* Draw by stalemate
* Player vs AI
* Draw by repeated moves, 50-moves no capture, etc.
