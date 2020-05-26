package geekbrains.ru.hw04.retrofit;

import com.google.gson.annotations.SerializedName;

public class RetrofitUserDetailModel {
    @SerializedName("login")
    private String mLogin;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

    @SerializedName("name")
    private String mName;

    @SerializedName("public_repos")
    private int mPublicRepos;

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPublicRepos() {
        return mPublicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        mPublicRepos = publicRepos;
    }
}
