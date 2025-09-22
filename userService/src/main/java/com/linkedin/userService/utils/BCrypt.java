package com.linkedin.userService.utils;

import static org.mindrot.jbcrypt.BCrypt.checkpw;
import static org.mindrot.jbcrypt.BCrypt.gensalt;
import static org.mindrot.jbcrypt.BCrypt.hashpw;

public class BCrypt {

    public static String hash(String s){
        return hashpw(s, gensalt());
    }

    public static boolean match(String passwordText, String hashText) {
        return checkpw(passwordText, hashText);
    }

}
