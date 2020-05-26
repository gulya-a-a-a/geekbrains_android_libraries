package geekbrains.ru.hw04.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubRestApi {
    @GET("users")
    Call<List<RetrofitUserItemModel>> loadUsers();

    @GET("users/{user}")
    Call<RetrofitUserDetailModel> loadUser(@Path("user") String user);

    @GET("users/{user}/repos")
    Call<List<RetrofitRepoModel>> loadUserRepos(@Path("user") String user);
}
