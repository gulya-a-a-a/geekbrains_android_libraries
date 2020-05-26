package geekbrains.ru.hw04.retrofit;

import com.google.gson.annotations.SerializedName;

public class RetrofitUserItemModel {
    @SerializedName("login")
    private String mLogin;

    @SerializedName("id")
    private int mId;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }
}
