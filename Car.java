package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Car {
    private String mark;
    private CarNumber carNumber;
    private Calendar yearOfCrashed;

    public Car(String mark, CarNumber carNumber, Calendar yearOfCrashed){
        this.mark = mark;
        this.carNumber = carNumber;
        this.yearOfCrashed = yearOfCrashed;
    }

    public Car(String mark, String carNumber, Calendar yearOfCrashed) throws Exception {
        this.mark = mark;
        this.carNumber = new CarNumber(carNumber);
        this.yearOfCrashed = yearOfCrashed;
    }


    public String getMark() {
        return mark;
    }

    public String getCarNumber() {
        return carNumber.getNumber();
    }

    public Calendar getYearOfCrashed() {
        return yearOfCrashed;
    }

    @Override
    public String toString(){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return mark + "|" + getCarNumber() + "|" + format.format(yearOfCrashed.getTime());
    }

    public String toShowString(){
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return mark + " " + getCarNumber() + " " + format.format(yearOfCrashed.getTime());
    }
}
