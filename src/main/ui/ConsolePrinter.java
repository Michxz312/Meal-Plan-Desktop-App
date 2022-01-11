package ui;

import model.Event;
import model.EventLog;

public class ConsolePrinter implements LogPrinter {

    @Override
    public void printLog(EventLog event) {
        for (Event e: event) {
            System.out.print(e.toString() + "\n\n");
        }
    }
}
