import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.sound.sampled.*;

public class GameBoardPanel extends JPanel {
   private static final long serialVersionUID = 1L;  // to prevent serial warning


   // Define named constants for the game board properties
   public static final int GRID_SIZE = 9;    // Size of the board
   public static final int SUBGRID_SIZE = 3; // Size of the sub-grid
   // Define named constants for UI sizes
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int BOARD_WIDTH  = CELL_SIZE * GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
                                             // Board width/height in pixels
   // IMAGES ARE HERE
   ImageIcon gunpoint = new ImageIcon("gun.png");
   ImageIcon gigachad = new ImageIcon("gigachad.png");


   // Define properties
   /** The game board composes of 9x9 Cells (customized JTextFields) */
   private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
   /** It also contains a Puzzle with array numbers and isGiven */
   private Puzzle puzzle = new Puzzle();


   /** Constructor */
   public GameBoardPanel() {
      super.setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));  // JPanel

      // Allocate the 2D array of Cell, and added into JPanel.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col] = new Cell(row, col);
            super.add(cells[row][col]);   // JPanel
         }
      }

      // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
      //  Cells (JTextFields)
      CellInputListener listener = new CellInputListener();

      // [TODO 4] Adds this common listener to all editable cells
      for (int row = 0; row < 9; row++) {
         for (int col = 0; col < 9; col++) {
            if (cells[row][col].isEditable()) {
               cells[row][col].addActionListener(listener);
             }
         }
     }

      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
   }


   /**
    * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
    * You can call this method to start a new game.
    */
   public void newGame() {

      SoundEffect.GANGNAM_STYLE.loop();

      // Generate a new puzzle      
      if (GameDifficultyGUI.selectedDifficulty == Difficulty.EASY) puzzle.newPuzzle(5);
      if (GameDifficultyGUI.selectedDifficulty == Difficulty.MEDIUM) puzzle.newPuzzle(15);
      if (GameDifficultyGUI.selectedDifficulty == Difficulty.HARD) puzzle.newPuzzle(25);
      
       
      // Initialize all the 9x9 cells, based on the puzzle.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }
   }

   /*public void repaint() {
      // Initialize all the 9x9 cells, based on the puzzle.
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            cells[row][col].paint(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }
   } NEEDS DEBUGGING*/



   /**
    * Return true if the puzzle is solved
    * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
    */
   public boolean isSolved() {
      for (int row = 0; row < GRID_SIZE; ++row) {
         for (int col = 0; col < GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS || cells[row][col].status == CellStatus.INVALID_GUESS) {
               return false;
            }
         }
      }
      return true;
   }

  // [TODO 2] Define a Listener Inner Class for all the editable Cells
  private class CellInputListener implements ActionListener {
     @Override
     public void actionPerformed(ActionEvent e) {
       // Get a reference of the JTextField that triggers this action event
       Cell sourceCell = (Cell)e.getSource();
     
       try {
         // Retrieve the int entered
         int numberIn = Integer.parseInt(sourceCell.getText());
         // For debugging
         System.out.println("You entered " + numberIn);

       /*
         * [TODO 5] (later - after TODO 3 and 4)
         * Check the numberIn against sourceCell.number.
         * Update the cell status sourceCell.status,
         * and re-paint the cell via sourceCell.paint().
         */
        if (numberIn == sourceCell.number && (sourceCell.status == CellStatus.TO_GUESS || sourceCell.status == CellStatus.WRONG_GUESS)) {
            sourceCell.status = CellStatus.CORRECT_GUESS;
        } else if (numberIn != sourceCell.number && sourceCell.status == CellStatus.TO_GUESS) {
            sourceCell.status = CellStatus.WRONG_GUESS;
        }
            sourceCell.paint();   // re-paint this cell based on its status

      } catch (NumberFormatException ex) {
         // Handle the exception if the user entered a non-integer value
         SoundEffect.REVERB_FART.play();
         Object[] options = {"Sorry Prof"};
         int n = JOptionPane.showOptionDialog(null, "Invalid input. Please enter a digit!", 
             "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            gunpoint,     //do not use a custom Icon
            options,  //the titles of buttons
            options[0]); //default button title
        }

       /*
         * [TODO 6] (later)
         * Check if the player has solved the puzzle after this move,
         *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
         */


        if (isSolved()) {
            SoundEffect.GANGNAM_STYLE.stop();
            SoundEffect.LEGEND.play();
            Object[] options = {"Thank You Prof"};
            int n = JOptionPane.showOptionDialog(null, "Congratulations!", 
                "You Win", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
               gigachad,     //do not use a custom Icon
               options,  //the titles of buttons
               options[0]); //default button title
          }
      }
   }
}