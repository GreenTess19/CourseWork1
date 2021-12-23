package com.company;

public class CarNumber {
    private String number;

    public CarNumber(String carNumber) throws Exception {
        if(Character.isDigit(carNumber.charAt(2)) &&
           Character.isDigit(carNumber.charAt(3)) &&
           Character.isDigit(carNumber.charAt(4)) &&
           Character.isLetter(carNumber.charAt(0)) &&
           Character.isLetter(carNumber.charAt(1)) &&
           Character.isLetter(carNumber.charAt(5))){

            number = carNumber;
        }
        else throw new Exception("Car number is invalid");
    }

    public String getNumber() {
        return number;
    }
}
