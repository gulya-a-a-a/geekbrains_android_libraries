package geekbrains.ru.hw04.models;

import java.util.List;

import geekbrains.ru.hw04.retrofit.GithubRestApi;
import geekbrains.ru.hw04.retrofit.RetrofitRepoModel;
import geekbrains.ru.hw04.retrofit.RetrofitUserDetailModel;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersRepository {
    private GithubRestApi mGithubRestApi;

    public UsersRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mGithubRestApi = retrofit.create(GithubRestApi.class);
    }


    public void requestUsers(Observer<List<RetrofitUserItemModel>> userListObserver) {
        Observable<List<RetrofitUserItemModel>> observable =
                Observable.create(emitter -> {
                    mGithubRestApi.loadUsers().
                            enqueue(new Callback<List<RetrofitUserItemModel>>() {
                                @Override
                                public void onResponse(Call<List<RetrofitUserItemModel>> call,
                                                       Response<List<RetrofitUserItemModel>> response) {
                                    if (response.body() != null)
                                        emitter.onNext(response.body());
                                    else
                                        emitter.onError(new NullPointerException());
                                    emitter.onComplete();
                                }

                                @Override
                                public void onFailure(Call<List<RetrofitUserItemModel>> call, Throwable t) {
                                    emitter.onError(t);
                                }
                            });
                });

        makeRequest(observable, userListObserver);
    }


    public void requestDetailedData(Observer<RetrofitUserDetailModel> observer, String userName) {
        Observable<RetrofitUserDetailModel> observable
                = Observable.create(emitter -> {
            mGithubRestApi.loadUser(userName).
                    enqueue(new Callback<RetrofitUserDetailModel>() {
                        @Override
                        public void onResponse(Call<RetrofitUserDetailModel> call, Response<RetrofitUserDetailModel> response) {
                            if (response.body() != null) {
                                emitter.onNext(response.body());
                            } else {
                                emitter.onError(new NullPointerException());
                            }
                        }

                        @Override
                        public void onFailure(Call<RetrofitUserDetailModel> call, Throwable t) {
                            emitter.onError(new NullPointerException());
                        }
                    });
        });

        makeRequest(observable, observer);
    }

    public void requestRepoList(Observer<List<RetrofitRepoModel>> observer, String userName) {
        Observable<List<RetrofitRepoModel>> observable
                = Observable.create(emitter -> {
            mGithubRestApi.loadUserRepos(userName).
                    enqueue(new Callback<List<RetrofitRepoModel>>() {
                        @Override
                        public void onResponse(Call<List<RetrofitRepoModel>> call, Response<List<RetrofitRepoModel>> response) {
                            if (response.body() != null) {
                                emitter.onNext(response.body());
                            } else {
                                emitter.onError(new NullPointerException());
                            }
                        }

                        @Override
                        public void onFailure(Call<List<RetrofitRepoModel>> call, Throwable t) {
                            emitter.onError(new NullPointerException());
                        }
                    });
        });

        makeRequest(observable, observer);
    }

    private <T> void makeRequest(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
