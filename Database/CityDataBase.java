package Database;


import java.util.ArrayList;


public class CityDataBase {
    public ArrayList<String> citiesList = new ArrayList<>();
    @SuppressWarnings("unchecked")
    
    public ArrayList<Integer>[] adjacencyList = new ArrayList[5];

    public Info[][] infoMatrix = new Info[5][5];

    public CityDataBase() {
        citiesList.add("Nirmal");
        citiesList.add("Hyderabad");
        citiesList.add("Banglore");
        citiesList.add("Khammam");
        citiesList.add("Tirupathi");

        for (int i=0;i<5;i++){
            for (int j=0;j<5;j++){
                infoMatrix[i][j] = new Info();
            }
        }

        for (int i = 0; i < 5; i++) {
            adjacencyList[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < 5; i++) {
            infoMatrix[i][i].canTravel = false;
            infoMatrix[i][i].cost = 0;
            infoMatrix[i][i].vacancies = 0;
        }

        infoMatrix[0][1] = new Info(true,30,450);
        infoMatrix[1][0] = new Info(true,30,450);
        infoMatrix[1][2] = new Info(true,30,700);
        infoMatrix[2][1] = new Info(true,30,700);
        infoMatrix[1][3] = new Info(true,30,320);
        infoMatrix[3][1] = new Info(true,30,320);
        infoMatrix[1][4] = new Info(true,30,1000);
        infoMatrix[4][1] = new Info(true,30,1000);
        infoMatrix[2][1] = new Info(true,30,270);
        infoMatrix[1][2] = new Info(true,30,270);
        infoMatrix[3][4] = new Info(true,30,1700);
        infoMatrix[4][3] = new Info(true,30,1700);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (infoMatrix[i][j].canTravel){
                    adjacencyList[i].add(j);
                }
            }
        }



    }

    public void printCities () {
        for (int i = 0; i< citiesList.size(); i++) {
            System.out.println(i+1+" "+ citiesList.get(i));
        }
    }



}
