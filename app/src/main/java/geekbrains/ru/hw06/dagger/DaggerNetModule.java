package geekbrains.ru.hw06.dagger;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dagger.Module;
import dagger.Provides;
import dagger.internal.Preconditions;
import geekbrains.ru.hw04.retrofit.GithubRestApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ContextModule.class)
public class DaggerNetModule {
    @Provides
    public GithubRestApi getGithubRestApi(Retrofit retrofit) {
        return retrofit.create(GithubRestApi.class);
    }

    @Provides
    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        Preconditions.checkNotNull(connectivityManager);
        return connectivityManager.getActiveNetworkInfo();
    }
}
