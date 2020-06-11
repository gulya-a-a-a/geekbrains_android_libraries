package geekbrains.ru.hw06.presenters;

import android.net.NetworkInfo;
import android.os.Bundle;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import geekbrains.ru.hw01.presenters.BasePresenter;
import geekbrains.ru.hw04.retrofit.GithubRestApi;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import geekbrains.ru.hw05.database.sqlite.UserItemModelDao;
import geekbrains.ru.hw05.database.sqlite.UserItemModelMapper;
import geekbrains.ru.hw05.database.sqlite.UserItemRoomModel;
import geekbrains.ru.hw05.views.MainViewHW05;
import geekbrains.ru.hw06.ApplicationHW06;
import geekbrains.ru.hw06.dagger.AppComponent;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterHW06 extends BasePresenter<List<RetrofitUserItemModel>, MainViewHW05> {

    @Inject
    NetworkInfo mNetworkInfo;

    @Inject
    GithubRestApi mGithubRestApi;

    @Inject
    UserItemModelDao mUserItemDao;

    public PresenterHW06() {
        AppComponent appComponent = ApplicationHW06.getAppComponent();
        appComponent.injectsToPresenter(this);
    }

    @Override
    public void updateView() {
        getView().showUserList(getModel());
    }

    public void loadUserFromInternet() {
        mGithubRestApi.loadUsers().enqueue(new Callback<List<RetrofitUserItemModel>>() {
            @Override
            public void onResponse(Call<List<RetrofitUserItemModel>> call, Response<List<RetrofitUserItemModel>> response) {
                if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                    getView().hideLoading();
                    setModel(response.body());
                    updateView();
                } else {
                    getView().showError();
                }
            }

            @Override
            public void onFailure(Call<List<RetrofitUserItemModel>> call, Throwable t) {
                getView().showError();
            }
        });
    }

    public void writeUsersToSugar() {
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            mUserItemDao.insertAll(UserItemModelMapper.transform(getModel()));
            Date finish = new Date();
            List<UserItemRoomModel> tempList = mUserItemDao.getAll();
            Bundle bundle = new Bundle();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msec", finish.getTime() - start.getTime());
            emitter.onSuccess(bundle);
        });
        singleSaveAllUsers = singleSaveAllUsers.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = singleSaveAllUsers.subscribe((bundle, throwable) -> {
            getView().showDbResult(bundle);
        });
    }
}
