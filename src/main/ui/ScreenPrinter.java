package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;

public class ScreenPrinter extends JInternalFrame implements LogPrinter {
    private JTextArea logArea;

    public ScreenPrinter() {
        super("Event log", false, true, false, false);
        setSize(300,300);
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setVisible(true);
    }

    @Override
    public void printLog(EventLog event) {
        for (Event e: event) {
            logArea.setText(logArea.getText() + e.toString() + "\n\n");
        }
        repaint();
    }
}
