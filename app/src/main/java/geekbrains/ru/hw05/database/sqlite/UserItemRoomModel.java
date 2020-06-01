package geekbrains.ru.hw05.database.sqlite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UserItemRoomModel {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "login")
    private String login;

    @ColumnInfo(name = "userID")
    private int userID;

    @ColumnInfo(name = "avatar_url")
    private String avatar_url;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
