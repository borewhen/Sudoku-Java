import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   GameDifficultyGUI difficultyGUI = new GameDifficultyGUI();
   JButton btnNewGame = new JButton("New Game");

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER); //Adds sudoku board to center

      //add the GUIs
      JPanel northPanel = new JPanel();
      northPanel.add(difficultyGUI, BorderLayout.WEST);
      cp.add(northPanel, BorderLayout.NORTH);
      cp.add(btnNewGame, BorderLayout.SOUTH);

      btnNewGame.addMouseListener (new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              board.newGame();
          }
      });  

      // Initialize the game board to start the game
      board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("amogus");
      setVisible(true);
   }

   /** The entry main() entry method */
   public static void main(String[] args) {
      // [TODO 1] Check "Swing program template" on how to run
      //  the constructor of "SudokuMain"
      SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            SoundEffect.init();
            new SudokuMain();
         }
      });
   }
}