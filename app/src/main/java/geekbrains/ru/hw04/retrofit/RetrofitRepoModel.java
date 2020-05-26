package geekbrains.ru.hw04.retrofit;

import com.google.gson.annotations.SerializedName;

public class RetrofitRepoModel {
    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mRepoName;

    @SerializedName("login")
    private String mOwnerLogin;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getRepoName() {
        return mRepoName;
    }

    public void setRepoName(String repoName) {
        mRepoName = repoName;
    }

    public String getOwnerLogin() {
        return mOwnerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        mOwnerLogin = ownerLogin;
    }
}
