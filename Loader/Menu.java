package Loader;


import java.util.ArrayList;
import java.util.List;

public class Menu {

    List<String> menu = new ArrayList<>();



    public Menu() {
        menu.add("New Admin user Registration");
        menu.add("Sign In");


    }



    public void printMenu () {

        for (int i = 0; i < menu.size(); i++) {
            System.out.println(i+1+" "+menu.get(i));
        }

    }



}
