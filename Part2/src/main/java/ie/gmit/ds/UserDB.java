package ie.gmit.ds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDB {

    public static HashMap<Integer, User> users = new HashMap<>();
    static{
        users.put(1, new User(1, "Niall", "niall@gmit.ie"));
        users.put(2, new User(2, "John", "john@gmit.ie"));
        users.put(3, new User(3, "Ronan", "ronan@gmit.ie"));
    }


    public static List<User> getUsers(){
        return new ArrayList<User>(users.values());
    }


    public static User getUser(Integer id){
        return users.get(id);
    }

    public static void updateUser(Integer id, User user){
        users.put(id, user);
    }

    public static void createUser(Integer id, User user){
        PasswordClient client = new PasswordClient("localhost", 50551);

        RequestHash PasswordDetails = RequestHash.newBuilder()
                .setUserId(user.getUserId())
                .setPassword(user.getPassword())
                .build();
        try {
            ResponseHash HashedUserDetails = client.PasswordHashed(PasswordDetails);
            users.put(id, new User(id,user.getUserName(),user.getEmail(),HashedUserDetails.getHashedPassword(),HashedUserDetails.getSalt()));
        } finally {

        }

    }

    public static void removeUser(Integer id){
        users.remove(id);
    }
    public static boolean validateResponse(Integer id, User user){
        PasswordClient client = new PasswordClient("localhost", 50551);

        RequestValidate PasswordDetails = RequestValidate.newBuilder()
                .setPassword(user.getPassword())
                .setHashedPassword(user.getHashedPassword())
                .setSalt(user.getSalt())
                .build();

        return false;
    }
}
