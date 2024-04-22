package Database;


public class Info {
    public boolean canTravel = false;

    public int vacancies = 0;

    public int cost = Integer.MAX_VALUE;

    Info (boolean canTravel, int vacancies, int cost) {
        this.canTravel = canTravel;
        this.vacancies = vacancies;
        this.cost = cost;
    }


    public Info() {

    }
}
