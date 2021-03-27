package gui;

import main.Search;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private final JPanel[][] tiles = new JPanel[100][100];
    private final int[] startCoordinate = new int[2];
    private final int[] endCoordinate = new int[2];


    /*
     * Default constructor
     */
    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 800));
        setResizable(false);
        setTitle("A* Search (without diagonal movement)");
        setLayout(new GridLayout(100, 100));

        setupMenu();
        setupGrid();
    }


    /*
     * Method that creates user menu
     */
    public void setupMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu startAnimation = new JMenu("Start A*");
        JMenu selectStart = new JMenu("Create start-point");
        JMenu selectEnd = new JMenu("Create end-point");
        JMenu resetBarriers = new JMenu("Regenerate Nodes");

        JMenuItem start = new JMenuItem("Start");
        JMenuItem createStartPoint = new JMenuItem("New point");
        JMenuItem createEndPoint = new JMenuItem("New point");
        JMenuItem regenerateBarriers = new JMenuItem("Regenerate barriers");

        start.addActionListener(e -> {
            if ((startCoordinate[0] > -1 && startCoordinate[1] < 100) && (endCoordinate[0] > -1 && endCoordinate[1] < 100)) {
                Search search = new Search(this, tiles, startCoordinate, endCoordinate);
                search.start();
            } else {
                JOptionPane.showMessageDialog(this, "Please create a start and end-point.");
            }

        });

        createStartPoint.addActionListener(e -> {
            try {
                String startX = JOptionPane.showInputDialog("Enter start x coordinate: ");
                String startY = JOptionPane.showInputDialog("Enter start y coordinate: ");
                int x = Integer.parseInt(startX);
                int y = Integer.parseInt(startY);
                while (x < 0 || x > 100 || y < 0 || y > 100) {
                    startX = JOptionPane.showInputDialog("Re-enter x coordinate (must be inclusively between 0 and 14): ");
                    startY = JOptionPane.showInputDialog("Re-enter y coordinate (must be inclusively between 0 and 14): ");
                    x = Integer.parseInt(startX);
                    y = Integer.parseInt(startY);
                }
                if (startCoordinate[0] > -1 && startCoordinate[0] < 100) {
                    tiles[startCoordinate[1]][startCoordinate[0]].setBackground(Color.WHITE);
                }
                startCoordinate[0] = x;
                startCoordinate[1] = y;
                tiles[y][x].setBackground(Color.GREEN);
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only. ");
            }
        });

        createEndPoint.addActionListener(e -> {
            try {
                String endX = JOptionPane.showInputDialog("Enter end x coordinate: ");
                String endY = JOptionPane.showInputDialog("Enter end y coordinate: ");
                int x = Integer.parseInt(endX);
                int y = Integer.parseInt(endY);
                while (x < 0 || x > 99 || y < 0 || y > 99) {
                    endX = JOptionPane.showInputDialog("Re-enter x coordinate (must be inclusively between 0 and 14): ");
                    endY = JOptionPane.showInputDialog("Re-enter y coordinate (must be inclusively between 0 and 14): ");
                    x = Integer.parseInt(endX);
                    y = Integer.parseInt(endY);
                }
                if (endCoordinate[0] > -1 && endCoordinate[0] < 100) {
                    tiles[endCoordinate[1]][endCoordinate[0]].setBackground(Color.WHITE);
                }
                endCoordinate[0] = x;
                endCoordinate[1] = y;
                tiles[y][x].setBackground(Color.RED);
            } catch (NumberFormatException err) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only. ");
            }
        });

        regenerateBarriers.addActionListener(e -> {
            startCoordinate[0] = -1;
            startCoordinate[1] = -1;
            endCoordinate[0] = -1;
            endCoordinate[1] = -1;
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    tiles[i][j].setBackground(Color.WHITE);
                }
            }
            generateUnpathableWalls();
        });

        startAnimation.add(start);
        selectStart.add(createStartPoint);
        selectEnd.add(createEndPoint);
        resetBarriers.add(regenerateBarriers);

        menuBar.add(startAnimation);
        menuBar.add(selectStart);
        menuBar.add(selectEnd);
        menuBar.add(resetBarriers);

        setJMenuBar(menuBar);
    }


    /*
     * Method to setup grid
     */
    public void setupGrid() {
        startCoordinate[0] = -1;
        startCoordinate[1] = -1;
        endCoordinate[0] = -1;
        endCoordinate[1] = -1;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                JPanel panel = new JPanel();
                panel.setBackground(Color.WHITE);
                panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                tiles[i][j] = panel;
                add(panel);
            }
        }
        generateUnpathableWalls();
    }


    /*
     * Method that makes 10-15% of the grid unpathable
     */
    public void generateUnpathableWalls() {
        for (int i = 0; i < 5000; i++) {
            int randomI = (int)(Math.random() * 100);
            int randomJ = (int)(Math.random() * 100);
            while (tiles[randomI][randomJ].getBackground().equals(Color.GRAY)) {
                randomI = (int)(Math.random() * 100);
                randomJ = (int)(Math.random() * 100);
            }
            tiles[randomI][randomJ].setBackground(Color.GRAY);
        }
    }
}
