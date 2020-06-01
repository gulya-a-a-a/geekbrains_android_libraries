package geekbrains.ru.hw05.database.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserItemRealmObject extends RealmObject {
    @PrimaryKey
    private long uid;
    private int id;
    private String login;
    private String avatar_url;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
