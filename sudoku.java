import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class sudoku extends JFrame
{
    long starttime = System.currentTimeMillis();
    public static final int gridDim = 9;//board size
    public static final int subGridDim = 3; //sub grid size
    public static final int cellSize = 60;
    public static final int width  = 630;
    public static final int height = 630;
    public static final Color emptyCell= Color.blue;
    public static final Color correct = new Color(0, 255, 0);
    public static final Color incorrect = Color.RED;
    public static final Color backgroundColor = new Color(240, 240, 240);
    public static final Color backgroundColorColor = Color.BLACK;
    public static final Font fontColor = new Font("Times New Roman", Font.PLAIN, 20);

    private JTextField[][] tfCells = new JTextField[9][9];

    String x;
    int y;
    private int[][] puzzle = {{5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}};

    private boolean[][] masks = {{true, false, false, false, false, true, false, false, false},
            {false, true, false, true, false, false, false, false, true},
            {true, false, false, false, false, false, false, false, false},
            {true, false, false, false, true, true, false, false, false},
            {false, false, false, false, false, false, false, false, false},
            {false, false, false, true, false, true, false, false, false},
            {true, true, false, false, false, false, false, false, false},
            {false, false, false, true, false, false, false, false, false},
            {true, false, true, false, false, true, false, false, false}};

    public sudoku()
    {
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(9,9));
        InputListener listener = new InputListener();

        for (int row = 0; row < gridDim; ++row) //build 9x9 text fields
        {
            for (int col = 0; col < gridDim; ++col)
            {
                tfCells[row][col] = new JTextField();
                cp.add(tfCells[row][col]);
                if (masks[row][col]) //if empty cell
                {
                    tfCells[row][col].setText("");
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(correct);
                    tfCells[row][col].addActionListener(listener);
                }
                else //if cell is filled
                {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(backgroundColorColor);
                    tfCells[row][col].setForeground(backgroundColor);
                }

                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER); // fix cells
                tfCells[row][col].setFont(fontColor);
            }
        }
        cp.setPreferredSize(new Dimension(width, height)); //set window size
        pack();

        setTitle("Sudoku");
        setVisible(true);
    }
    public static void main(String[] args)
    {
        sudoku app = new sudoku();
        app.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private class InputListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            int rowSelected = -1; //determines which row/column is being looked at
            int colSelected = -1;
            JTextField source = (JTextField)e.getSource();
            boolean found = false;
            for (int row = 0; row < gridDim && !found; ++row)// Scan fields for all rows and columns +
            //match with the source object
            {
                for (int col = 0; col < gridDim && !found; ++col)
                {
                    if (tfCells[row][col] == source)
                    {
                        rowSelected = row;
                        colSelected = col;
                        found = true;  // break the inner/outer loops
                    }
                }
            }
            x = tfCells[rowSelected][colSelected].getText(); //get input
            try {
                y = Integer.parseInt(x);
                System.out.println(y);
                if(y==puzzle[rowSelected][colSelected]) {
                    tfCells[rowSelected][colSelected].setBackground(Color.green);
                } else {
                    tfCells[rowSelected][colSelected].setBackground(Color.red);
                }
                if(masks[rowSelected][colSelected]) { //if all answers are correct
                    long endtime = System.currentTimeMillis();
                    long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(endtime - starttime); // time in seconds
                    JOptionPane.showMessageDialog(null, "Finished in: " + timeSeconds);
                }
            } catch (NumberFormatException exc){
                tfCells[rowSelected][colSelected].setBackground(Color.red);
            }
        }
    }
}