package ui;

import model.*;
import persistence.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Meal planner Application
public class MealPlanner extends JInternalFrame implements ActionListener, ItemListener {
    private WeeklyMealPlan week;
    private DailyMealPlan monday;
    private DailyMealPlan tuesday;
    private DailyMealPlan wednesday;
    private DailyMealPlan thursday;
    private DailyMealPlan friday;
    private DailyMealPlan saturday;
    private DailyMealPlan sunday;
    private JsonMealPlanReader jsonMealPlanReader;
    private JsonMealPlanWriter jsonMealPlanWriter;
    private JPanel addMealPanel;
    private JPanel removeMealPanel;
    private JComboBox dropMealType;
    private JTextArea textArea;

    // MODIFIES: this
    // EFFECTS: run meal planner GUI
    public MealPlanner() {
        setTitle("Planner");
        setSize(1000,500);
        setLocation(350,0);

        initialize();
        setJMenuBar(createMenuBar());
        leftBar();

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // EFFECTS: create menu bar for meal planner GUI
    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu save = new JMenu("Save Meal Plan");
        createMenuItem(save, new SaveMeals());

        JMenu load = new JMenu("Load Meal Plan");
        createMenuItem(load, new LoadMeals());

        menu.add(save);
        menu.add(load);

        return menu;
    }

    // EFFECTS: create menu item for menu bar
    private void createMenuItem(JMenu menu, AbstractAction action) {
        JMenuItem menuItem = new JMenuItem(action);
        menu.add(menuItem);
    }

    // EFFECTS: add tabbedPane to left bar
    private void leftBar() {
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        JPanel addMeals = planMeal();
        JPanel removeMeals = removeMeals();
        JPanel viewMeals = viewMeals();
        addMeals.setBackground(Color.decode("#94746A"));
        tabbedPane.addTab("Add Meals", addMeals);
        tabbedPane.addTab("Remove Meals", removeMeals);
        tabbedPane.addTab("View Meals", viewMeals);
        add(tabbedPane);
    }

    // MODIFIES: this
    // EFFECTS: initialize addMealPanel, removeMealPanel and WeeklyMealPlanner
    private void initialize() {
        List<DailyMealPlan> days = new ArrayList<>();
        days.add(monday = new DailyMealPlan("Monday"));
        days.add(tuesday = new DailyMealPlan("Tuesday"));
        days.add(wednesday = new DailyMealPlan("Wednesday"));
        days.add(thursday = new DailyMealPlan("Thursday"));
        days.add(friday = new DailyMealPlan("Friday"));
        days.add(saturday = new DailyMealPlan("Saturday"));
        days.add(sunday = new DailyMealPlan("Sunday"));
        week = new WeeklyMealPlan(days);
        addMealPanel = new JPanel(new CardLayout());
        removeMealPanel = new JPanel(new CardLayout());
    }

    // EFFECTS: return JPanel when planMeal tabbedPane is clicked
    private JPanel planMeal() {
        return dropDayOptions(new JPanel(new BorderLayout()));
    }

    // EFFECTS: return JPanel when removeMeal tabbedPane is clicked
    private JPanel removeMeals() {
        return removePlan(new JPanel(new GridBagLayout()));
    }

    // EFFECTS: return JPanel when viewMeals tabbedPane is clicked
    private JPanel viewMeals() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel insidePanel = new JPanel();
        insidePanel.setSize(900,50);
        JButton view = new JButton("view");
        textArea = new JTextArea(15,20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        view.addActionListener(e -> {
            textArea.setEditable(false);
            textArea.setText(printMeals());
        });
        insidePanel.add(new JLabel("Meal Plan"));
        insidePanel.add(view);
        panel.add(insidePanel,BorderLayout.PAGE_START,SwingConstants.CENTER);
        panel.add(scrollPane,BorderLayout.CENTER);
        return panel;
    }

    // EFFECTS: return week meal plan to string
    private String printMeals() {
        return week.viewPlan();
    }

    // MODIFIES: this
    // EFFECTS: set breakfast, lunch, and dinner from Monday to Thursday in a week
    private void setMondayToThursday(DailyMealPlan day) {
        switch (day.getDay()) {
            case "Monday":
                monday.setBreakfast(day.getBreakfast());
                monday.setLunch(day.getLunch());
                monday.setDinner(day.getDinner());
                break;
            case "Tuesday":
                tuesday.setBreakfast(day.getBreakfast());
                tuesday.setLunch(day.getLunch());
                tuesday.setDinner(day.getDinner());
                break;
            case "Wednesday":
                wednesday.setBreakfast(day.getBreakfast());
                wednesday.setLunch(day.getLunch());
                wednesday.setDinner(day.getDinner());
                break;
            case "Thursday":
                thursday.setBreakfast(day.getBreakfast());
                thursday.setLunch(day.getLunch());
                thursday.setDinner(day.getDinner());
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: set breakfast, lunch, and dinner from Friday to Sunday in a week
    private void setFridayToSunday(DailyMealPlan day) {
        switch (day.getDay()) {
            case "Friday":
                friday.setBreakfast(day.getBreakfast());
                friday.setLunch(day.getLunch());
                friday.setDinner(day.getDinner());
                break;
            case "Saturday":
                saturday.setBreakfast(day.getBreakfast());
                saturday.setLunch(day.getLunch());
                saturday.setDinner(day.getDinner());
                break;
            case "Sunday":
                sunday.setBreakfast(day.getBreakfast());
                sunday.setLunch(day.getLunch());
                sunday.setDinner(day.getDinner());
                break;
        }
    }

    // EFFECTS: create drop down options of days in a week and return panel
    private JPanel dropDayOptions(JPanel panel) {
        JPanel dropDownPane = new JPanel();
        String[] comboBoxItems = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
        JComboBox dropDayOption = new JComboBox(comboBoxItems);
        dropDayOption.setEditable(false);
        dropDayOption.addItemListener(this);
        dropDownPane.add(dropDayOption);
        JPanel monday = setMonday(new JPanel());
        addMealPanel.add(monday,"Monday");
        JPanel tuesday = setTuesday(new JPanel());
        addMealPanel.add(tuesday,"Tuesday");
        JPanel wednesday = setWednesday(new JPanel());
        addMealPanel.add(wednesday,"Wednesday");
        JPanel thursday = setThursday(new JPanel());
        addMealPanel.add(thursday,"Thursday");
        JPanel friday = setFriday(new JPanel());
        addMealPanel.add(friday,"Friday");
        JPanel saturday = setSaturday(new JPanel());
        addMealPanel.add(saturday,"Saturday");
        JPanel sunday = setSunday(new JPanel());
        addMealPanel.add(sunday,"Sunday");
        panel.add(dropDownPane, BorderLayout.PAGE_START);
        panel.add(addMealPanel, BorderLayout.CENTER);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for monday and return panel to enter breakfast, lunch, and dinner
    private JPanel setMonday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            monday.setBreakfast(enterBreakfast.getText());
            monday.setLunch(enterLunch.getText());
            monday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for tuesday and return panel to enter breakfast, lunch, and dinner
    private JPanel setTuesday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            tuesday.setBreakfast(enterBreakfast.getText());
            tuesday.setLunch(enterLunch.getText());
            tuesday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for wednesday and return panel to enter breakfast, lunch, and dinner
    private JPanel setWednesday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            wednesday.setBreakfast(enterBreakfast.getText());
            wednesday.setLunch(enterLunch.getText());
            wednesday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for thursday and return panel to enter breakfast, lunch, and dinner
    private JPanel setThursday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            thursday.setBreakfast(enterBreakfast.getText());
            thursday.setLunch(enterLunch.getText());
            thursday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for friday and return panel to enter breakfast, lunch, and dinner
    private JPanel setFriday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            friday.setBreakfast(enterBreakfast.getText());
            friday.setLunch(enterLunch.getText());
            friday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for saturday and return panel to enter breakfast, lunch, and dinner
    private JPanel setSaturday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            saturday.setBreakfast(enterBreakfast.getText());
            saturday.setLunch(enterLunch.getText());
            saturday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: set meals for sunday and return panel to enter breakfast, lunch, and dinner
    private JPanel setSunday(JPanel panel) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel breakfast = new JLabel("Breakfast:");
        JTextField enterBreakfast = new JTextField();
        JLabel lunch = new JLabel("Lunch:");
        JTextField enterLunch = new JTextField();
        JLabel dinner = new JLabel("Dinner:");
        JTextField enterDinner = new JTextField();
        JButton submitButton = new JButton("Enter");
        submitButton.addActionListener(e -> {
            sunday.setBreakfast(enterBreakfast.getText());
            sunday.setLunch(enterLunch.getText());
            sunday.setDinner(enterDinner.getText());
            JOptionPane.showMessageDialog(null,"Added");
        });
        panel.add(breakfast);
        panel.add(enterBreakfast);
        panel.add(lunch);
        panel.add(enterLunch);
        panel.add(dinner);
        panel.add(enterDinner);
        panel.add(submitButton);
        return panel;
    }

    // EFFECTS: return JPanel when removePlan tabbedPane is clicked and display drop down option
    private JPanel removePlan(JPanel panel) {
        JPanel dropDownPane = new JPanel();
        String[] comboBoxItems = { "Clear Breakfast", "Clear Lunch", "Clear Dinner", "Clear All" };
        dropMealType = new JComboBox(comboBoxItems);
        dropMealType.setEditable(false);
        dropMealType.addActionListener(this);
        dropDownPane.add(dropMealType);
        JPanel breakfast = clearInputDayBreakfast(new JPanel());
        removeMealPanel.add(breakfast,"Clear Breakfast");
        JPanel lunch = clearInputDayLunch(new JPanel());
        removeMealPanel.add(lunch,"Clear Lunch");
        JPanel dinner = clearInputDayDinner(new JPanel());
        removeMealPanel.add(dinner,"Clear Dinner");
        JPanel all = clearInputDayAll(new JPanel());
        removeMealPanel.add(all,"Clear All");
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 1;
        g.gridy = 0;
        panel.add(dropDownPane,g);
        g.gridy = 1;
        panel.add(removeMealPanel,g);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: remove meals for all meal plan and return panel with Monday to Sunday button
    private JPanel clearInputDayAll(JPanel panel) {
        panel.setLayout(new GridLayout(7,2));
        JButton mondayButton = new JButton("Monday");
        mondayButton.addActionListener(e -> monday.clearAll());
        panel.add(mondayButton);
        JButton tuesdayButton = new JButton("Tuesday");
        tuesdayButton.addActionListener(e -> tuesday.clearAll());
        panel.add(tuesdayButton);
        JButton wednesdayButton = new JButton("Wednesday");
        wednesdayButton.addActionListener(e -> wednesday.clearAll());
        panel.add(wednesdayButton);
        JButton thursdayButton = new JButton("Thursday");
        thursdayButton.addActionListener(e -> thursday.clearAll());
        panel.add(thursdayButton);
        JButton fridayButton = new JButton("Friday");
        fridayButton.addActionListener(e -> friday.clearAll());
        panel.add(fridayButton);
        JButton saturdayButton = new JButton("Saturday");
        saturdayButton.addActionListener(e -> saturday.clearAll());
        panel.add(saturdayButton);
        JButton sundayButton = new JButton("Sunday");
        sundayButton.addActionListener(e -> sunday.clearAll());
        panel.add(sundayButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: remove meals for dinner meal plan and return panel with Monday to Sunday button
    private JPanel clearInputDayDinner(JPanel panel) {
        panel.setLayout(new GridLayout(7,2));
        JButton mondayButton = new JButton("Monday");
        mondayButton.addActionListener(e -> monday.clearDinner());
        panel.add(mondayButton);
        JButton tuesdayButton = new JButton("Tuesday");
        tuesdayButton.addActionListener(e -> tuesday.clearDinner());
        panel.add(tuesdayButton);
        JButton wednesdayButton = new JButton("Wednesday");
        wednesdayButton.addActionListener(e -> wednesday.clearDinner());
        panel.add(wednesdayButton);
        JButton thursdayButton = new JButton("Thursday");
        thursdayButton.addActionListener(e -> thursday.clearDinner());
        panel.add(thursdayButton);
        JButton fridayButton = new JButton("Friday");
        fridayButton.addActionListener(e -> friday.clearDinner());
        panel.add(fridayButton);
        JButton saturdayButton = new JButton("Saturday");
        saturdayButton.addActionListener(e -> saturday.clearDinner());
        panel.add(saturdayButton);
        JButton sundayButton = new JButton("Sunday");
        sundayButton.addActionListener(e -> sunday.clearDinner());
        panel.add(sundayButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: remove meals for lunch meal plan and return panel with Monday to Sunday button
    private JPanel clearInputDayLunch(JPanel panel) {
        panel.setLayout(new GridLayout(7,2));
        JButton mondayButton = new JButton("Monday");
        mondayButton.addActionListener(e -> monday.clearLunch());
        panel.add(mondayButton);
        JButton tuesdayButton = new JButton("Tuesday");
        tuesdayButton.addActionListener(e -> tuesday.clearLunch());
        panel.add(tuesdayButton);
        JButton wednesdayButton = new JButton("Wednesday");
        wednesdayButton.addActionListener(e -> wednesday.clearLunch());
        panel.add(wednesdayButton);
        JButton thursdayButton = new JButton("Thursday");
        thursdayButton.addActionListener(e -> thursday.clearLunch());
        panel.add(thursdayButton);
        JButton fridayButton = new JButton("Friday");
        fridayButton.addActionListener(e -> friday.clearLunch());
        panel.add(fridayButton);
        JButton saturdayButton = new JButton("Saturday");
        saturdayButton.addActionListener(e -> saturday.clearLunch());
        panel.add(saturdayButton);
        JButton sundayButton = new JButton("Sunday");
        sundayButton.addActionListener(e -> sunday.clearLunch());
        panel.add(sundayButton);
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: remove meals for breakfast meal plan and return panel with Monday to Sunday button
    private JPanel clearInputDayBreakfast(JPanel panel) {
        panel.setLayout(new GridLayout(7,2));
        JButton mondayButton = new JButton("Monday");
        mondayButton.addActionListener(e -> monday.clearBreakfast());
        panel.add(mondayButton);
        JButton tuesdayButton = new JButton("Tuesday");
        tuesdayButton.addActionListener(e -> tuesday.clearBreakfast());
        panel.add(tuesdayButton);
        JButton wednesdayButton = new JButton("Wednesday");
        wednesdayButton.addActionListener(e -> wednesday.clearBreakfast());
        panel.add(wednesdayButton);
        JButton thursdayButton = new JButton("Thursday");
        thursdayButton.addActionListener(e -> thursday.clearBreakfast());
        panel.add(thursdayButton);
        JButton fridayButton = new JButton("Friday");
        fridayButton.addActionListener(e -> friday.clearBreakfast());
        panel.add(fridayButton);
        JButton saturdayButton = new JButton("Saturday");
        saturdayButton.addActionListener(e -> saturday.clearBreakfast());
        panel.add(saturdayButton);
        JButton sundayButton = new JButton("Sunday");
        sundayButton.addActionListener(e -> sunday.clearBreakfast());
        panel.add(sundayButton);
        return panel;
    }

    // EFFECTS: action when drop down option of remove meals is pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        CardLayout cardLayout = (CardLayout) (removeMealPanel.getLayout());
        String s = (String) dropMealType.getSelectedItem();
        cardLayout.show(removeMealPanel, s);
    }

    // EFFECTS: action when drop down option of add meals is pressed
    @Override
    public void itemStateChanged(ItemEvent e) {
        CardLayout cardLayout = (CardLayout) (addMealPanel.getLayout());
        cardLayout.show(addMealPanel, (String)e.getItem());
    }

    // private class to load grocery that extends AbstractAction
    public class LoadMeals extends AbstractAction   {
        LoadMeals() {
            super("Load Meal Plan");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ImageIcon loadIcon = new ImageIcon("data/output.png");
                loadIcon = new ImageIcon(loadIcon.getImage().getScaledInstance(45,45,
                        loadIcon.getImage().SCALE_SMOOTH));
                String fileName = JOptionPane.showInputDialog("Enter File Name: ");
                fileName = "data/" + fileName + ".json";
                jsonMealPlanReader = new JsonMealPlanReader(fileName);
                for (DailyMealPlan day : jsonMealPlanReader.read().getDailyMealPlan()) {
                    setMondayToThursday(day);
                    setFridayToSunday(day);
                }
                JOptionPane.showMessageDialog(null,fileName + " is found",
                        "Loaded", JOptionPane.INFORMATION_MESSAGE,loadIcon);
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, ioException.getMessage(), "File not Found",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // private class to save grocery that extends AbstractAction
    public class SaveMeals extends AbstractAction {
        public SaveMeals() {
            super("Save Meal Plan");
        }

        @Override
        public void actionPerformed(ActionEvent e) throws HeadlessException {
            try {
                ImageIcon saveIcon = new ImageIcon("data/saveIcon.png");
                saveIcon = new ImageIcon(saveIcon.getImage().getScaledInstance(45,45,
                        saveIcon.getImage().SCALE_SMOOTH));
                String fileName = JOptionPane.showInputDialog("Enter File Name: ");
                fileName = "data/" + fileName + ".json";
                jsonMealPlanWriter = new JsonMealPlanWriter(fileName);
                jsonMealPlanWriter.open();
                jsonMealPlanWriter.write(week);
                jsonMealPlanWriter.close();
                JOptionPane.showMessageDialog(null,"Saved to " + fileName,"Saved",
                        JOptionPane.INFORMATION_MESSAGE,saveIcon);
            } catch (FileNotFoundException fileNotFoundException) {
                JOptionPane.showMessageDialog(null, fileNotFoundException.getMessage(),
                        "File not found", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException nullPointerException) {
                JOptionPane.showMessageDialog(null, nullPointerException.getMessage(),
                        "File not saved", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}


