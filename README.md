- Organise files into packages
    - Adhere MVC pattern
    
- Remove redundancies
- Improve encapsulation
- Organise order of methods and variables in the classes
- Rename variables
- Rename classes
  
- Extract new classes from classes of the original code
  to adhere single-responsibility and open-closed principle
  - Impacts extracted from Wall
  - Levels extracted from Wall
  - GameController extracted from GameBoard
  - GameView extracted from GameBoard
  - HomeMenuController extracted from HomeMenu
    
- DebugPanel as view class
- DebugConsole as controller class
    
- Newly created classes for additions that are 
  not from the original code:
    - InfoView
      - INFO screen
    - InfoController
      - INFO screen
    - User
      - score saving
    - ScoreSorting
      - score sorting
    - GoldBrick
      - new level
    
- Additions/Modifications with the reason 
  and the class where it is implemented/used
    - Background image in HomeMenu
    - New Brick type: GoldBrick  
      - GoldBrick, Levels
    - Additional Levels:
        - New singleTypeLevel of SteelBrick
        - New chessBoardLevel of CementBrick and new brick type GoldBrick
    - Permanent high scores list
      > Reason: This list is not sorted because 
        it is easier for the users to track their progress as it
        appends the newest record to the file.
    - Permanent sorted high scores list
      >Reason: This list is sorted for the users to view their position
        in the leaderboard regardless of the level of the score achieved
        to keep them motivated to play the game.
    - Shows the all-time best score using JOptionPane for each completed
        level
      >Reason: As motivation for the user to keep playing the game to have
        their name shown after the completion of each level.
    - INFO screen
      - InfoView, ViewManager
      >Reason: To provide instructions for playing the game 
        and have a better understanding of the game.
    - INFO button in HomeMenu
      - HomeMenu, HomeMenuController
      >Reason: To access INFO screen
    - HIGHSCORES screen to show sortedHighScoresList.txt
      - HomeMenu, HomeMenuController
      >Reason: To display high scores save to sortedHighScoresList.txt
    - HIGHSCORES button in HomeMenu
      - HomeMenu, HomeMenuController
      >Reason: To access HIGHSCORES screen.
    - Score saving option for each completed level
      - GameController, User
      >Reason: Gives the user the freedom to choose 
        if the score is to be saved.
    - Changed DebugConsole to change ball speed 
      and reset balls separately
      >Reason: Allows the user to perform the desired action.
    - Changed DebugConsole to change both x and y of the ballspeed 
      at once
      >Reason: Not all users will understand what x and y of the speed
        represents as they may only be interested in changing the ballspeed.
    - Changed DebugConsole to disable skipLevelsButton 
      upon reaching teh final level
      >Reason: To avoid exceptions and inform the user upon last level reached.
    - Modified the button to change ball speed in DebugConsole 
      to display the speed changed to
      >Reason: To inform the user that the ball speed has 
        been changed to the desired speed.
    - Score calculations using timer
      >Reason: By using time taken to complete the level as the score indicator,
        it provides multiple score variations for each level and for each round 
        even for the same user which may help to motivate the users 
        to keep playing the game.
    - Penalties
      - InfoView
      >Reason: To make the game more interesting.
    - Title for the user of the all-time best score
      >Reason: To keep the users motivated to play the game 
       and compete to achieve the title.
 