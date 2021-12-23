package com.company;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

public class Main {

    public static void main(String[] args) throws Exception{

    	MainWindow window = new MainWindow("database.txt");
    	window.setVisible(true);
//
//        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
//        Calendar calendar = new GregorianCalendar(2021, Calendar.FEBRUARY, 29);
//        System.out.println(format.format(calendar.getTime()));

    }
}
