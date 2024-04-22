package Loader;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.PriorityQueue;
import java.util.Scanner;

import Database.CityDataBase;
import Database.Info;
import Database.Pair;

public class PlanJourney {

    CityDataBase[] cityDataBases = new CityDataBase[3];

    public PlanJourney () {
        for (int i=0;i<cityDataBases.length;i++){
            cityDataBases[i] = new CityDataBase();
        }
    }


    public void planJourney (Scanner input) {
        CityDataBase cityDataBase = cityDataBases[0];
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        boolean terminator = false;

        while (!terminator) {


            System.out.println("choose the Departure city and enter its corresponding number");
            cityDataBase.printCities();
            int source = Integer.parseInt(input.nextLine())-1;
            System.out.println("choose the Destination city and enter its corresponding number");
            int destination = Integer.parseInt(input.nextLine())-1;
            if(source != destination){
                System.out.println("choose the Departeur date");
                System.out.println(1+". "+dateFormat.format(currentDate));
                for (int i = 2; i < 4; i++){
                    LocalDate nextDay =  currentDate.plusDays(i-1);
                    System.out.println(i+". "+dateFormat.format(nextDay));
                }
                int date = Integer.parseInt(input.nextLine());
                cityDataBase = cityDataBases[date-1];
                LocalDate bookingDate = currentDate.plusDays(date-1);
                DayOfWeek dayOfWeek = bookingDate.getDayOfWeek();
                String dayOfWeekStr = dayOfWeek.toString();
                System.out.println("enter the number of tickets");
                int tickets = Integer.parseInt(input.nextLine());
                priceGenerator(cityDataBase,source,destination,tickets,input,dayOfWeekStr,bookingDate);
                return;
            }
            else {
                System.out.printf("sorry we cannot provide travelling service from %s to %s\n",cityDataBase.citiesList.get(source),cityDataBase.citiesList.get(destination));
                System.out.println("if you want to exit please enter 1 else enter 2");
                int k = Integer.parseInt(input.nextLine());
                if (k==1) return;
            }
        }
    }

    public  void priceGenerator (CityDataBase cityDataBase, int source, int destination, int tickets, Scanner input, String day,LocalDate bookingDate) {

        Pair[] costArray = costArrayGenerator(source,cityDataBase);
        ArrayList<Integer> routeList = routeListGenerator(costArray,source,destination);
        if (vacancySpecifier(cityDataBase,routeList,tickets)) {
            routeListPrinter(routeList,cityDataBase.citiesList);
            int price = costArray[destination].cost;
            int totalPrice = price*tickets;
            int weekendPrice = 0;
            if(day.equals("SUNDAY") || day.equals("SATURDAY")){
                weekendPrice += 200;
            }
            double gst = (1.0/100)*(totalPrice+weekendPrice);
            double netAmount = totalPrice + weekendPrice + gst;
            System.out.println("Date : "+bookingDate);
            System.out.println("Price                   -->  "+price);
            System.out.println("No.of.tickets           -->  "+tickets);
            System.out.println("TotalPrice              -->  "+totalPrice);
            System.out.println("extra price on weekends -->  "+weekendPrice);
            System.out.println("GST (1%)                -->  "+gst);
            System.out.println("Net amount              -->  "+netAmount);
            System.out.println("\nThank You");
        }
        else {
            System.out.println("sorry we don't have any vacancies left");
        }
    }

    public  boolean vacancySpecifier (CityDataBase cityDataBase, ArrayList<Integer> routeList, int tickets){
        Info[][] infoMatrix = cityDataBase.infoMatrix;

        boolean isVacant = true;

        for (int i = 1; i < routeList.size(); i++) {
            if (infoMatrix[routeList.get(i-1)][routeList.get(i)].vacancies < tickets){
                isVacant = false;
                break;
            }
        }
        if (isVacant){
            for (int i = 1; i < routeList.size(); i++) {
                infoMatrix[routeList.get(i-1)][routeList.get(i)].vacancies--;
            }
        }

        return isVacant;

    }

    public  ArrayList<Integer> routeListGenerator (Pair[] costArray, int source, int destination) {

        ArrayList<Integer> routeList = new ArrayList<>();

        int i = destination;

        while (i!=source) {
            routeList.add(i);
            i = costArray[i].node;
        }

        routeList.add(source);

        int p1 = 0;
        int p2 = routeList.size()-1;
        // reversing of routeList
        while (p1 < p2) {
            int temp = routeList.get(p1);
            routeList.set(p1,routeList.get(p2));
            routeList.set(p2,temp);
            p1++;
            p2--;
        }

        return routeList;
    }

    public  void routeListPrinter (ArrayList<Integer> routeList, ArrayList<String> cityList) {
        System.out.printf("your journey is from %s to %s\n",cityList.get(routeList.get(0)),cityList.get(routeList.get(routeList.size()-1)));
        if (routeList.size()!=2) {
            System.out.print("via ");
            for (int i=1;i<routeList.size()-2;i++){
                System.out.print(cityList.get(routeList.get(i))+" ---> ");
            }
            System.out.println(cityList.get(routeList.get(routeList.size()-2)));
        }
    }

    public  Pair[] costArrayGenerator(int source, CityDataBase cityDataBase) {

        PriorityQueue<Pair> minHeap = new PriorityQueue<>((o1, o2) -> {
            return o1.cost != o2.cost ? o1.cost - o2.cost : o1.node - o2.node;
        });

        Info[][] infoMatrix = cityDataBase.infoMatrix;

        ArrayList<Integer>[] adjacencyList = cityDataBase.adjacencyList;


        Pair[] costArray = new Pair[5];

        for (int i = 0; i < costArray.length; i++) {
            costArray[i] = new Pair(Integer.MAX_VALUE,i);
        }

        costArray[source].cost = 0;
        costArray[source].node = source;

        minHeap.offer(new Pair(0,source));

        while (!minHeap.isEmpty()) {

            Pair p = minHeap.poll();

            for (int i : adjacencyList[p.node]){
                if (p.cost + infoMatrix[i][p.node].cost < costArray[i].cost) {
                    costArray[i].cost = p.cost + infoMatrix[i][p.node].cost;
                    minHeap.offer(new Pair(costArray[i].cost,i));
                    costArray[i].node = p.node;
                }
            }


        }

        return costArray;
    }
}
