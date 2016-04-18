# CS 3354 Group 5 Game Library

###Compiling

  1. Navigate to the location which contians the "games" package in the src folder.
  2. type the following command into the terminal:
        "javac games\*"
  3. You should see .class files now
  4. Next, type the following command into the terminal:
        "jar cfe Games.jar games.GameController games\*.class"


###Running
  1. Type the command "java -jar Games.jar" into the terminal.

###Description
There are three games available to play:
  1. Mastermind
  2. Hangman
  3. Tic-Tac-Toe

When you start the application, you will see the main menu where you will be prompted to input the corresponding number to the game you wish to play.

When each game starts, its instructions will be printed to the screen.

After each game, you will be redirected to the main menu again.

Enjoy!
