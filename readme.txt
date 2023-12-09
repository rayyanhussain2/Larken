# AP Endsem Project Group 69
## Game Name: ADJUNGO 
(Latin) Adjungo means to connect\
/adˈjun.ɡo/, [äd̪ˈjuŋɡo]

- Rayyan Hussain 2022399
- Prabal Minotra 2022357

Explaining clearly how your code is supposed to be compiled and run from cmd.
## To Run
HOME_FOLDER = Larken
1. Download the entire assignment and unzip
2. Navigate to the folder generated after unzipping
3. The following commands are to be run inside the folder called "Larken"
```
$ mvn clean
$ mvn compile
$ mvn package
$ cd target
$ java -jar 
```

## Gameplay

- Hold Left-Click: To extend the stick
- Release Left-Click: To drop the stick
- Scroll-Down: To flip the hero down
- Scroll-Up: To flip the hero back up
- The Hero collects coins by passing through them
- Revival: Surrender 5 Coins to resume playing

## Code Logic

### 1. Scene Class

- Responsible for managing game scenes and the game loop.
- Picks two backgrounds randomly from a static ArrayList of background layers.
- Shifts each layer at a different pace when the character moves forward.

### 2. BG Class

- Manages background layers.
- Consists of three layers.
- Static ArrayList stores multiple instances of background layers.

### 3. Audio Abstract Class

- Abstract class implementing the `Runnable` interface for audio.
- Extended by BGM (Background Music) and Sound Effect classes.
- BGM loops continuously, while Sound Effect plays when required.

### 4. Collectible Class

- Manages collectible items (coins).
- Coins are randomly generated between two pillars at different y-axis locations.

### 5. Game Platform Class

- Manages the game platform with three pillars.
- Contains three pillars (rectangles).
- When the scene shifts, the current pillar moves beyond the last pillar, creating the illusion of continuity.

### 6. Pause Screen Class

- UI class for the pause screen.
- Contains a pane and multiple texts arranged using Scene Builder.
- Button visibility is triggered based on the required menu.

### 7. Player and Stick Classes

- Player Class:
  - Contains the image for the main character and various animations.
  - Animations implemented using timelines.
  - Singleton pattern implemented for PlayableCharacter class.
- Stick Class:
  - Contains a rectangle with various animations (elongate, rotate, reset).
  - Polymorphism across various locations.

## Design Patterns

- **Singleton Pattern:**
  - Implemented in PlayableCharacter class.
- **Factory Method Pattern:**
  - Used for selecting backgrounds.
- **Polymorphism:**
  - Implemented across various locations to facilitate interaction between different assets.

## Assets

- Sourced from [CraftPix](https://craftpix.net/file-licenses/) and [Pixabay](https://pixabay.com/service/license-summary/).

## Usage

1. Clone the repository.
2. Set up your development environment with Java and JavaFX.
3. Run the main class to start the game.

## Contributing

Feel free to contribute by opening issues or pull requests.
