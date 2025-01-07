# Minesweeper Game

This repository contains a Java implementation of a **Minesweeper Game**, designed to mimic the original Minesweeper experience while adhering to specific rules and gameplay mechanics. The game starts with a minefield of user-defined dimensions and mine count, and players can explore cells or mark potential mines. The solution ensures fairness by making the first explored cell safe and providing an interactive, recursive exploration mechanism.

---

## Features

### Gameplay Rules:
1. **Minefield Initialization**:
    - The user specifies the number of mines at the start.
    - The game initializes a grid with unexplored cells (`.`).

2. **First Move Safety**:
    - The first cell explored by the user (`free` command) is guaranteed to be safe and cannot contain a mine. This safety extends to its adjacent cells.

3. **Player Commands**:
    - `free`: Explore a cell.
        - Reveals the number of adjacent mines (if any).
        - Automatically uncovers surrounding cells if no adjacent mines exist.
    - `mine`: Mark or unmark a cell as potentially containing a mine.

4. **Cell States**:
    - `.`: Unexplored cell.
    - `/`: Explored cell with no adjacent mines.
    - `1-8`: Explored cell showing the number of adjacent mines.
    - `*`: Marked cell (potential mine).
    - `X`: Mine revealed when the player steps on one.

5. **Win/Loss Conditions**:
    - **Win**: The player wins by either:
        - Marking all mines correctly.
        - Exploring all safe cells, leaving only mines unexplored.
    - **Loss**: The player loses if they explore a cell containing a mine.

---

## How the Solution Works

### **1. Ensuring First Move Safety**
The first move is handled separately:
- Mines are not placed until the first move is made.
- When mines are generated, the first cell and its adjacent cells are excluded from mine placement.

### **2. Recursive Exploration**
When a cell with no adjacent mines is explored:
- All neighboring cells are automatically explored.
- This recursive exploration continues until no further cells can be safely uncovered.

### **3. Marking Mines**
Players can mark cells they suspect contain mines using the `mine` command. Marking is toggle-based:
- Mark an unexplored cell as a mine (`*`).
- Unmark a previously marked cell by issuing the `mine` command again.

### **4. Mine Generation**
Mines are placed randomly across the grid using a random number generator:
- Placement excludes the first cell and its neighbors.
- A 2D boolean array tracks mine positions.

---

## Code Structure

### **Key Classes and Methods**
1. **`Minesweeper` Class**:
    - Contains the game logic and handles the gameplay loop.

2. **Core Methods**:
    - `generateMines(int safeRow, int safeCol)`: Places mines while ensuring the first move is safe.
    - `exploreCell(int row, int col)`: Explores the specified cell and handles recursive exploration.
    - `countAdjacentMines(int row, int col)`: Counts the number of mines adjacent to a cell.
    - `markCell(int row, int col)`: Toggles the marking of a cell as a potential mine.
    - `revealMines()`: Reveals all mines when the game ends due to a loss.
    - `checkWin()`: Verifies whether the player has met the win conditions.

3. **Game Loop**:
    - Repeatedly prompts the user for input.
    - Processes the `free` and `mine` commands until the game is won or lost.

4. **Utility Methods**:
    - `printField()`: Displays the current state of the minefield.

---

## Running the Game

### Prerequisites
- **Java 8+** installed on your system.

### Steps
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/minesweeper-game.git
   cd minesweeper-game
   ```
2. Compile the code:
   ```bash
   javac Minesweeper.java
   ```
3. Run the game:
   ```bash
   java Minesweeper
   ```

### Sample Run
```
How many mines do you want on the field? > 10
 |123456789|
-|---------|
1|.........|
2|.........|
3|.........|
4|.........|
5|.........|
6|.........|
7|.........|
8|.........|
9|.........|
-|---------|
Set/unset mine marks or claim a cell as free: > 5 5 free
 |123456789|
-|---------|
1|..1//1...|
2|111//2...|
3|/////1...|
4|/////11..|
5|//////1..|
6|/111//1..|
7|23.1//111|
8|..21/////|
9|..1//////|
-|---------|
```

---

## Design Choices and Challenges

### **First Move Handling**
- Ensuring the first move is safe required delaying mine placement until the first move was made, introducing complexity in mine generation.

### **Recursive Exploration**
- Implementing efficient recursive exploration of cells involved tracking explored states and preventing redundant checks.

### **Balancing Randomness and Fairness**
- Random mine placement had to be balanced with the fairness of excluding the first move and its neighbors.

---

## Future Improvements
1. Add difficulty levels (e.g., grid size and mine count).
2. Implement a graphical user interface (GUI) for enhanced player experience.
3. Optimize recursive exploration for larger grids.
4. Include a timer and leaderboard for competitive play.

---

## Contributing
Feel free to fork this repository and submit pull requests for enhancements or bug fixes.

---

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

---
