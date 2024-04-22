package Loader;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.UserDataBase;


public class UserRegistration {

    public static boolean validMobileChecker(String mobile) {
        String regex = "^\\d{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();

    }
    public static boolean validEmailChecker(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public static void userRegistration (UserDataBase userDataBase,PlanJourney planner, Scanner input) {

        UserDetails userDetails = new UserDetails();

        System.out.println("welcome to user registration");
        System.out.println("please enter your firstName");
        userDetails.setFirstName(input.nextLine());
        System.out.println("please enter your LastName");
        userDetails.setLastName(input.nextLine());
        System.out.println("please enter your mobile number");
        String mobile = input.nextLine();
        while (!validMobileChecker(mobile)) {
            System.out.println("entered mobile number is invalid please enter valid mobile number");
            mobile = input.nextLine();
        }
        userDetails.setMobileNumber(mobile);
        System.out.println("enter your emailid");
        String email = input.nextLine();
        while (!validEmailChecker(email)) {
            System.out.println("entered email is invalid please enter valid email");
            email = input.nextLine();
        }
        userDetails.setEmailId(email);
        System.out.println("please enter the password");
        userDetails.setPassword(input.nextLine());
        userDataBase.add(email,userDetails);
        System.out.println(userDetails.toString());
        System.out.println("you have sucessfully registered");
        planner.planJourney(input);
        System.out.println();
    }
}
