import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputMatrix extends JFrame implements ActionListener {
    JLabel l1;
    JButton b1,b2,b3,b4;
    JPanel panel;
    Font font;
    JTable t1, t2, resultTable;
    DefaultTableModel model1, model2, resultModel;

    public InputMatrix() {
        l1 = new JLabel("Enter Operation of your choice:", SwingConstants.CENTER);
        b1 = new JButton("Addition");
        b2 = new JButton("Subtraction");
        b3 = new JButton("Multiplication");
        b4 = new JButton("Transpose");
        font = new Font("Arial", Font.BOLD, 22);
        l1.setFont(font);
        panel = new JPanel(new FlowLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        Dimension buttonSize = new Dimension(500, 30);
        b1.setPreferredSize(buttonSize);
        b2.setPreferredSize(buttonSize);
        b3.setPreferredSize(buttonSize);
        b4.setPreferredSize(buttonSize);
        panel.add(l1);
        panel.add(Box.createVerticalStrut(30));
        panel.add(b1);
        panel.add(Box.createVerticalStrut(30));
        panel.add(b2);
        panel.add(Box.createVerticalStrut(30));
        panel.add(b3);
        panel.add(Box.createVerticalStrut(30));
        panel.add(b4);
        
        getContentPane().add(panel);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        setVisible(true);
        setTitle("Matrix Calculator");
        setSize(500, 500);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == b1) {
            performOperation("Addition");
        } else if (ae.getSource() == b2) {
            performOperation("Subtraction");
        } else if(ae.getSource()==b3) {
        	performOperation("Multiplication");
        }
        else {
        	performTranspose();
        }
    }

    private void performOperation(String operation) {
        // Get dimensions for first matrix
        int rows1 = Integer.parseInt(JOptionPane.showInputDialog("Enter rows for matrix 1:"));
        int columns1 = Integer.parseInt(JOptionPane.showInputDialog("Enter columns for matrix 1:"));
        

        // Get dimensions for second matrix
        int rows2 = Integer.parseInt(JOptionPane.showInputDialog("Enter rows for matrix 2:"));
        int columns2 = Integer.parseInt(JOptionPane.showInputDialog("Enter columns for matrix 2:"));

        // Check if dimensions are valid for the operation
        if (operation.equals("Addition")) {
            if (rows1 != rows2 || columns1 != columns2) {
                JOptionPane.showMessageDialog(null, "Matrices must have the same dimensions for addition.");
                return;
            }
        } else if(operation.equals("Subtraction")) { // Subtraction
            if (rows1 != rows2 || columns1 != columns2) {
                JOptionPane.showMessageDialog(null, "Matrices must have the same dimensions for subtraction.");
                return;
            }
        }
            else if(operation.equals("Multiplication")) {
            	if(columns1!=rows2) {
            		 JOptionPane.showMessageDialog(null, "Matrices are not multipliable.");
                     return;
            	}
            }
  

        // Create table for first matrix
        model1 = new DefaultTableModel(rows1, columns1);
        t1 = new JTable(model1);
        t1.setPreferredScrollableViewportSize(new Dimension(300, 100));
        t1.setFillsViewportHeight(true);
        JScrollPane scrollPane1 = new JScrollPane(t1);
        getContentPane().add(scrollPane1, BorderLayout.NORTH);

        // Create table for second matrix
        model2 = new DefaultTableModel(rows2, columns2);
        t2 = new JTable(model2);
        t2.setPreferredScrollableViewportSize(new Dimension(300, 100));
        t2.setFillsViewportHeight(true);
        JScrollPane scrollPane2 = new JScrollPane(t2);
        getContentPane().add(scrollPane2, BorderLayout.CENTER);

        JButton calculateButton = new JButton("Calculate " + operation);
        calculateButton.addActionListener(e -> {
            int[][] matrix1 = getMatrixFromTable(model1);
            int[][] matrix2 = getMatrixFromTable(model2);
            int[][] result= {{0}};
            if (operation.equals("Addition")) {
                result = addMatrices(matrix1, matrix2);
            } else if(operation.equals("Subtraction")) {
                result = subtractMatrices(matrix1, matrix2);
            }else if(operation.equals("Multiplication")){
            	result= multiplyMatrices(matrix1 ,matrix2);
            }
          
            displayResult(result);
        });
        getContentPane().add(calculateButton, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    // Helper method to get matrix from table model
    private int[][] getMatrixFromTable(DefaultTableModel model) {
        int rows = model.getRowCount();
        int columns = model.getColumnCount();
        int[][] matrix = new int[rows][columns];

        // Populate the matrix with values from the table model
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Object value = model.getValueAt(i, j);
                matrix[i][j] = value != null ? Integer.parseInt(value.toString()) : 0;
            }
        }
        return matrix;
    }

    // Helper method to add two matrices
    private int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int columns = matrix1[0].length;
        int[][] result = new int[rows][columns];

        // Perform addition
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    // Helper method to subtract two matrices
    private int[][] subtractMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int columns = matrix1[0].length;
        int[][] result = new int[rows][columns];

        // Perform subtraction
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return result;
    }
    private int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
     int rows1 = matrix1.length;
        int columns1= matrix1[0].length;
        int columns2 = matrix2[0].length;
   
        
        int[][] result = new int[rows1][columns2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < columns2; j++) {
            	result[i][j]=0;
            	for(int k=0; k < columns1; k++) {	
                result[i][j]=result[i][j]+(matrix1[i][k]*matrix2[k][j]);
               }
 
            }
        }
        return result;
    }
    private int[][] transposeMatrix(int[][] matrix1) {
        int rows = matrix1.length;
        int columns = matrix1[0].length;
        int[][] result = new int[rows][columns];

        // Perform subtraction
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix1[j][i];
            }
        }
        return result;
    }
    

    // Helper method to display result in a new table
    private void displayResult(int[][] result) {

    	if (resultTable != null) {
            getContentPane().remove(resultTable);
        }
        resultModel = new DefaultTableModel(result.length, result[0].length);
        resultTable = new JTable(resultModel);
        resultTable.setPreferredScrollableViewportSize(new Dimension(300, 100));
        resultTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        getContentPane().add(scrollPane, BorderLayout.SOUTH);

        // Populate the result table
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                resultModel.setValueAt(result[i][j], i, j);
            }
        }

        revalidate();
        repaint();
    }
     public void performTranspose() {
    	 int rows = Integer.parseInt(JOptionPane.showInputDialog("Enter rows for matrix :"));
         int columns = Integer.parseInt(JOptionPane.showInputDialog("Enter columns for matrix :"));
         DefaultTableModel model = new DefaultTableModel(rows, columns);
         t1 = new JTable(model);
         t1.setPreferredScrollableViewportSize(new Dimension(300, 100));
         t1.setFillsViewportHeight(true);
         JScrollPane scrollPane1 = new JScrollPane(t1);
         getContentPane().add(scrollPane1, BorderLayout.NORTH);
         JButton calculate = new JButton("Calculate Transpose");
         calculate.addActionListener(e -> {
        	 int[][] matrix = getMatrixFromTable(model);
        	 int[][] result = new int[rows][columns];
        	 result= transposeMatrix(matrix);
        	 displayResult(result);
         });
         getContentPane().add(calculate, BorderLayout.SOUTH);

         revalidate();
         repaint();
         return;
     }
    public static void main(String[] args) {
        InputMatrix in = new InputMatrix();
    }
}
