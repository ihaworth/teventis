# Event Sourced Tennis

## Teventis

### Scoring in a set

The first player/team to win six games wins that set, provided there is a margin of two games over the opponent(s). If the score reaches six games all, a tie-break game shall be played.

### Scoring in a match

A match can be played to the best of 3 sets (a player/team needs to win 2 sets to win the match) or to the best of 5 sets (a player/team needs to win 3 sets to win the match). 

### Scoring in a game

A standard game is scored as follows with the server’s score being called first:

```
No point - “Love”
First point - “15”
Second point - “30”
Third point - “40”
Fourth point - “Game”
```

If each player/team has won three points, the score is deuce. After deuce, the score is advantage for the player/team who wins the next point. If that same player/team also wins the next point, that player/team wins the game; if the opposing player/team wins the next point, the score is again deuce. A player/team needs to win two consecutive points immediately after deuce to win the game.

### Tiebreaks
During a tie-break game, points are scored “zero”, “1”, “2”, “3”, etc. The first player/team to win seven points wins the game and set, provided there is a margin of two points over the opponent(s). If necessary, the tie-break game shall continue until this margin is achieved. 