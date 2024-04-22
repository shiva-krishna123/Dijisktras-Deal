package Loader;


import java.util.Scanner;

import Database.UserDataBase;



public class SignIn {

    public static void signIn (Scanner input, UserDataBase userDataBase,PlanJourney planner) {

        int failedCount = 0;

        while (failedCount < 5) {
            
            System.out.println("enter your email");
            String email = input.nextLine();
            System.out.println("enter your password");
            String password = input.nextLine();
            if (userDataBase.validAccount(email,password)) {
                System.out.println("you have successfully logged in");
                planner.planJourney(input);
                return;
            }
            else {
                failedCount++;
                if (failedCount == 5) {
                    System.out.println("your acccount is locked");
                    return;
                }
                else {
                    System.out.println("You have entered wrong credentials");
                    System.out.printf("you have %d chances else your account will be locked\n",5-failedCount);
                    System.out.println("if you want to return to main menu please enter 1");
                    System.out.println("if you want to continue enter 2");
                    int x = Integer.parseInt(input.nextLine());
                    if (x==1) return;
                }
            }
        }

    }

}
