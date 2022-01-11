package ui;

import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

// This is a class that runs MealPlanner and GroceryPlanner
public class Planner extends JFrame {
    private static final int WIDTH = 1375;
    private static final int HEIGHT = 560;

    // MODIFIES: this
    // EFFECTS: create GUI for meal planner and grocery planner in the same frame
    public Planner() {
        JDesktopPane desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        ImageIcon food = new ImageIcon("data/food.jpg");
        food = new ImageIcon(food.getImage().getScaledInstance(330,210,
                Image.SCALE_SMOOTH));
        JOptionPane.showMessageDialog(null, "Plan\nyour\nmeals\nhere",
                "Meal Plan", JOptionPane.INFORMATION_MESSAGE,food);

        setJMenuBar(createMenuBar());
        add(new MealPlanner());
        add(new GroceryPlanner());

        setTitle("Planner");
        setSize(WIDTH,HEIGHT);
        setBackground(Color.decode("#FE5F55"));
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                e.getWindow().dispose();
                ConsolePrinter cp = new ConsolePrinter();
                cp.printLog(EventLog.getInstance());
            }
        });
    }

    // EFFECTS: create menu bar for options to clear and print logEvents
    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu eventLog = new JMenu("Event Log");
        JMenuItem printLog = new JMenuItem(new PrintEventLog());
        JMenuItem clearLog = new JMenuItem(new ClearLog());
        eventLog.add(printLog);
        eventLog.add(clearLog);
        menu.add(eventLog);

        return menu;
    }

    // This is a class that prints EventLog in another internal frame
    public class PrintEventLog extends AbstractAction {
        PrintEventLog() {
            super("Print Event Log");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ScreenPrinter lp = new ScreenPrinter();
            add(lp);
            lp.toFront();
            lp.printLog(EventLog.getInstance());
        }
    }

    // This is a class that clears EventLog
    public class ClearLog extends AbstractAction {
        ClearLog() {
            super("Clear Log");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            EventLog.getInstance().clear();
        }
    }
}
