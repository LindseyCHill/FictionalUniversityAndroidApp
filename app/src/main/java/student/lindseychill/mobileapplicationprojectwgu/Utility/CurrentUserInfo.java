package student.lindseychill.mobileapplicationprojectwgu.Utility;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class CurrentUserInfo {
    private static String user;

//    public CurrentUserInfo() {
//        this.user = "default";
//    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        CurrentUserInfo.user = user;
    }

    public static String getCurrentDateTime(){
        String format = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return formatter.format(LocalDateTime.now());
    }
}
