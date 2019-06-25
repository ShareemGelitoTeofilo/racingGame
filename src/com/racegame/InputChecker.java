package com.racegame;

public class InputChecker {

    public static Boolean checkIntInputValid(String input){

        Boolean valid = true;

        try {
            Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            valid = false;
        }

        return valid;
    }


    public static Boolean checkIntInputInRange(String input, int range){


        if(checkIntInputValid(input)) {
            int inputInt = Integer.parseInt(input);
            if (inputInt >= 1 && inputInt <= range) {
                return true;
            }
        }

        return false;
    }

}
