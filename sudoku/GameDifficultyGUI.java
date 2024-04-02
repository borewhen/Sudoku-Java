import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameDifficultyGUI extends JPanel implements ActionListener {
    public static Difficulty selectedDifficulty;
    private JRadioButton easyRadioButton, mediumRadioButton, hardRadioButton;
    private JLabel selectDifficultyLabel;

    public GameDifficultyGUI() {
        //Add text field
        selectDifficultyLabel = new JLabel("Select Game Difficulty:");

        // Create radio buttons for difficulty selection
        easyRadioButton = new JRadioButton("Easy");
        mediumRadioButton = new JRadioButton("Medium");
        hardRadioButton = new JRadioButton("Hard");

        // Create a button group and add radio buttons to it
        ButtonGroup difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyRadioButton);
        difficultyGroup.add(mediumRadioButton);
        difficultyGroup.add(hardRadioButton);

        // Set default selection to easy difficulty
        easyRadioButton.setSelected(true);
        selectedDifficulty = Difficulty.EASY;
        System.out.println("Selected difficulty: " + selectedDifficulty); // for testing purposes only

        // Add action listeners to radio buttons
        easyRadioButton.addActionListener(this);
        mediumRadioButton.addActionListener(this);
        hardRadioButton.addActionListener(this);

        // Add radio buttons to a panel
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.add(easyRadioButton);
        difficultyPanel.add(mediumRadioButton);
        difficultyPanel.add(hardRadioButton);

        // Add selectDifficultyLabel and difficultyPanel to this JPanel
        add(selectDifficultyLabel, BorderLayout.WEST);
        add(difficultyPanel, BorderLayout.EAST);

        //Adds blank space to the right so that difficultyPanel is left aligned
        difficultyPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    }
    
    public void actionPerformed(ActionEvent e) {
      // Update the selected difficulty when a radio button is selected
      if (e.getSource() == easyRadioButton) {
          selectedDifficulty = Difficulty.EASY;
      } else if (e.getSource() == mediumRadioButton) {
          selectedDifficulty = Difficulty.MEDIUM;
      } else if (e.getSource() == hardRadioButton) {
          selectedDifficulty = Difficulty.HARD;
      }
      System.out.println("Selected difficulty: " + selectedDifficulty); // for testing purposes only
  }
}