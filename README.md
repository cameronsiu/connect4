# Connect 4
Connect 4 is a game where you play against another player. Each player take turns placing a checker, and the first one to get four consecutive checkers wins. This game is for people who want to be able to play Connect 4 digitally. I think that games are fun to play, but challenging to make.

## User Stories
User Stories List
- As a user, I want to be able to see where I can place my checker.
- As a user, I want to be able to choose where to drop my checker.
- As a user, I want to be able to see who's turn it is.
- As a user, I want to be able to see an updated board.
- As a user, I want to be able to win the game.

- As a user, when I select the quit option from the application menu, I want to be reminded to save my game and have the option to do so or not.
- As a user, when I start the application, I want to load the game.
- As a user, I want to be able to restart the game.
- As a user, I want to be able to play again or exit the application after a game is over.

PHASE 4: Task 2 (Here is an example of printing out the event logs)

Thu Nov 25 15:04:51 PST 2021\
RED checker added at row 5 and column 2 

Thu Nov 25 15:04:51 PST 2021\
YELLOW checker added at row 5 and column 3 

Thu Nov 25 15:04:52 PST 2021\
RED checker added at row 4 and column 2

Thu Nov 25 15:04:52 PST 2021\
YELLOW checker added at row 4 and column 3

Thu Nov 25 15:04:52 PST 2021\
RED checker added at row 3 and column 2

Thu Nov 25 15:04:53 PST 2021\
YELLOW checker added at row 3 and column 3

Thu Nov 25 15:04:53 PST 2021\
RED checker added at row 2 and column 2

Thu Nov 25 15:04:53 PST 2021\
RED wins the game.

Thu Nov 25 15:04:55 PST 2021\
Restarted game.

PHASE 4: Task 3

Looking at the UML diagram, I think that the three drawing classes in the ui package can be put into the model package. 
Those three classes are more models than part of the user interface. 
The GamePanel class creates an additional list of checker drawings, which I think may be unnecessary. Thus, 
it would be better if the application relies on just one 2D list of checkers.

