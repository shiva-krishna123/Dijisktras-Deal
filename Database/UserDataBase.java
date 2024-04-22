package Database;


import java.util.HashMap;

import Loader.UserDetails;

public class UserDataBase {
    private final HashMap<String, UserDetails> userDataBase = new HashMap<>();

    public UserDataBase (){}

    public void add (String email, UserDetails user){
        userDataBase.put(email,user);
    }

    public void remove (String email) {
        userDataBase.remove(email);
    }

    public boolean validAccount (String email, String password) {
        if (userDataBase.containsKey(email)){
            return userDataBase.get(email).getPassword().equals(password);
        }
        return false;

    }
}
