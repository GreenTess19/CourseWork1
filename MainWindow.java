package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainWindow extends JFrame {
    private DefaultListModel<String> dlm = new DefaultListModel<String>();
    private final JList<String> list2;
    private final CarManager carManager;
    private final JTextField markTextField;
    private final JTextField carNumberTextField;
    private final JTextField dateTextField;
    private final JButton addButton;

    private final JTextField carNumberDeleteTextField;
    private final JButton deleteButton;

    private final int HEIGH_JTEXT_FIELD = 20;

    public MainWindow(String database) throws Exception {
        carManager = new CarManager(database);
        setSize(520, 500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        list2 = new JList<String>(dlm);

        Container container = new Container();

        JPanel panelForList = new JPanel();
        panelForList.setBackground(Color.RED);
        panelForList.setLocation(0, 0);
        panelForList.setSize(250, 500);
        panelForList.setLayout(new BorderLayout());

        panelForList.add(new JScrollPane(list2), BorderLayout.CENTER);

        markTextField = new JTextField();
        markTextField.setSize(60, HEIGH_JTEXT_FIELD);
        markTextField.setLocation(270, 30);

        carNumberTextField = new JTextField();
        carNumberTextField.setSize(60, HEIGH_JTEXT_FIELD);
        carNumberTextField.setLocation(350, 30);

        dateTextField = new JTextField();
        dateTextField.setSize(60, HEIGH_JTEXT_FIELD);
        dateTextField.setLocation(430, 30);

        addButton = new JButton("Добавить");
        addButton.setSize(220, 30);
        addButton.setLocation(270, 60);
        addButton.addActionListener(new ClickOnAddButton(this));

        carNumberDeleteTextField = new JTextField();
        carNumberDeleteTextField.setSize(60, HEIGH_JTEXT_FIELD);
        carNumberDeleteTextField.setLocation(350, 120);

        deleteButton = new JButton("Удалить");
        deleteButton.setSize(220, 30);
        deleteButton.setLocation(270, 150);
        deleteButton.addActionListener(new ClickOnDeleteButton(this));

        container.add(markTextField);
        container.add(carNumberTextField);
        container.add(dateTextField);
        container.add(addButton);

        container.add(carNumberDeleteTextField);
        container.add(deleteButton);

        container.add(panelForList);
        setContentPane(container);
        LoadCars();
    }

    private void LoadCars(){
        dlm.clear();
        String[] cars = carManager.toShowString().split(", ");
        for (String string : cars) {
            dlm.add(0, string);
        }
    }


    class ClickOnAddButton implements ActionListener{
        private final JFrame window;

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String mark = markTextField.getText();
                CarNumber carNumber = new CarNumber(carNumberTextField.getText());

                Car c = carManager.get(carNumber);
                if (c != null){
                    JOptionPane.showMessageDialog(window, "Машина с такими номерами уже есть!");
                }
                else {
                    if (!isDate(dateTextField.getText())) {
                        JOptionPane.showMessageDialog(window, "Дата неправильная");
                    }
                    else {
                        //20.06.2017 -> ["20"] ["06"] ["2017"]
                        String[] date = dateTextField.getText().split("\\.");

                        Calendar dateOfCrashed = new GregorianCalendar(Integer.parseInt(date[2]), Integer.parseInt(date[1]) - 1,
                                Integer.parseInt(date[0]));

                        Car car = new Car(mark, carNumber, dateOfCrashed);
                        carManager.add(car);
                        LoadCars();
                    }
                }
            }
            catch (Exception exc){
                JOptionPane.showMessageDialog(window, exc.getMessage());
            }

        }

        public ClickOnAddButton(JFrame window){
            this.window = window;
        }

        private boolean isDate(String date){
            if (date.split("\\.").length != 3){
                return false;
            }

            String[] d = date.split("\\.");
            if(!isNumber(d[0]) || !isNumber(d[1]) | !isNumber(d[2])){
                return false;
            }

            int day = Integer.parseInt(d[0]);
            int month = Integer.parseInt(d[1]);
            int year = Integer.parseInt(d[2]);


            Calendar calendar = new GregorianCalendar(year, month - 1, day);
            int dy = calendar.get(Calendar.DAY_OF_MONTH);
            int mn = calendar.get(Calendar.MONTH);
            int yr = calendar.get(Calendar.YEAR);
            return day == dy &&
                    month - 1 == mn &&
                    year == yr;
        }

        private boolean isNumber(String s){
            for(int i = 0; i < s.length(); i++){
                if (!Character.isDigit(s.charAt(i))){
                    return false;
                }
            }

            return true;
        }
    }

    class ClickOnDeleteButton implements ActionListener{
        private final JFrame window;

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                CarNumber carNumber = new CarNumber(carNumberDeleteTextField.getText());
                Car car = carManager.get(carNumber);

                if (car == null){
                    JOptionPane.showMessageDialog(window, "Машина с такими номерами отсутствует");
                }
                else{
                    carManager.delete(carNumber);
                    LoadCars();
                }
            }
            catch (Exception exc){
                JOptionPane.showMessageDialog(window, "Номер машины введен неправильно");
            }
        }

        public ClickOnDeleteButton(JFrame window){
            this.window = window;
        }
    }
}
