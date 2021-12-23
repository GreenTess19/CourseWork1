package com.company;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CarManager {
    private ArrayList<Car> cars;
    private File dataBase;

    public CarManager(String dataBase) throws Exception {
        cars = new ArrayList<>();

        this.dataBase = new File(dataBase);

        if (!this.dataBase.exists()){
            FileWriter w = new FileWriter(dataBase, true);
            w.close();
        }
        else{
           LoadCars();
        }
    }

    public void add(Car car) throws Exception{
        cars.add(car);

        FileWriter w = new FileWriter(dataBase, true);
        w.write(car.toString().replace('|', '=') + "\n");
        w.close();

        LoadCars();
    }

    public Car get(CarNumber carNumber){
        for (Car c: cars) {
            if (c.getCarNumber().compareTo(carNumber.getNumber()) == 0){
                return c;
            }
        }
        return null;
    }

    public void delete(CarNumber carNumber) throws Exception{
        cars.remove(get(carNumber));
        SaveCars();
    }

    public void delete(String carNumber) throws Exception{
        delete(new CarNumber(carNumber));
    }

    @Override
    public String toString(){
        String result = "";

        for (Car c: cars) {
            result += "[" + c.toString() + "] ";
        }

        return result;
    }

    public String toShowString(){
        String result = "";

        for (Car c: cars) {
            result += c.toShowString() + ", ";
        }

        return result;
    }
    private void LoadCars() throws Exception {
        cars.clear();

        FileReader r = new FileReader(this.dataBase);
        int c = r.read();
        String buffer = "";
        while(c != -1){
            char symbol = (char)c;
            if(symbol == '\n'){
                //Ford|xy123z|30.11.2021

                String[] splitData = buffer.strip().split("=");

                String mark = splitData[0];
                CarNumber number = new CarNumber(splitData[1]);

                String[] dateString = splitData[2].split("\\.");
                int day = Integer.parseInt(dateString[0]);
                int month = (Integer.parseInt(dateString[1]) - 1);
                int year = Integer.parseInt(dateString[2]);
                Calendar date = new GregorianCalendar(year, month, day);


                Car car = new Car(mark, number, date);

                cars.add(car);

                buffer = "";

            }
            else{
                buffer += symbol;
            }

            c = r.read();
        }
        r.close();
    }

    private void SaveCars() throws Exception{
        FileWriter w = new FileWriter(dataBase);
        for (Car car: cars) {
            w.write(car.toString().replace('|', '=') + "\n");
        }
        w.close();
    }
}
