package geekbrains.ru.hw05.presenters;

import android.app.Application;
import android.os.Bundle;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import geekbrains.ru.hw01.presenters.BasePresenter;
import geekbrains.ru.hw04.models.UsersRepository;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import geekbrains.ru.hw04.view.UserListView;
import geekbrains.ru.hw05.HW05Application;
import geekbrains.ru.hw05.database.UserItemModelMapper;
import geekbrains.ru.hw05.database.UserItemRoomModel;
import geekbrains.ru.hw05.views.MainViewHW05;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;

public class PresenterHW05 extends BasePresenter<List<UserItemRoomModel>, MainViewHW05> {
    private UsersRepository mUsersRepository;
    private List<RetrofitUserItemModel> loadedUsers;

    public PresenterHW05() {
        mUsersRepository = new UsersRepository();
    }

    @Override
    public void updateView() {

    }

    public void loadUserFromInternet() {
        if (mUsersRepository != null) {
            mUsersRepository.requestUsers(new UserListObserver());
        }
    }


    public void writeUsersToDb() {
        List<UserItemRoomModel> userModels = UserItemModelMapper.transform(loadedUsers);
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            HW05Application.getInstance().getDao().insertAll(userModels);
            Date finish = new Date();
            List<UserItemRoomModel> tempList = HW05Application.getInstance().getDao().getAll();
            Bundle bundle = new Bundle();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msek", finish.getTime() - start.getTime());
            emitter.onSuccess(bundle);
        });
        singleSaveAllUsers = singleSaveAllUsers.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        singleSaveAllUsers.subscribe((bundle, throwable) -> {
            getView().showDbResult(bundle);
        });
    }


    public void readUsersFromDb() {
        List<UserItemRoomModel> userModels = UserItemModelMapper.transform(loadedUsers);
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            List<UserItemRoomModel> tempList = HW05Application.getInstance().getDao().getAll();
            Date finish = new Date();
            Bundle bundle = new Bundle();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msek", finish.getTime() - start.getTime());
            emitter.onSuccess(bundle);
        });
        singleSaveAllUsers = singleSaveAllUsers.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        singleSaveAllUsers.subscribe((bundle, throwable) -> {
            getView().showDbResult(bundle);
        });
    }


    public void removeUsersFromDb() {
        List<UserItemRoomModel> userModels = UserItemModelMapper.transform(loadedUsers);
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            HW05Application.getInstance().getDao().deleteAll();
            Date finish = new Date();
            List<UserItemRoomModel> tempList = HW05Application.getInstance().getDao().getAll();
            Bundle bundle = new Bundle();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msek", finish.getTime() - start.getTime());
            emitter.onSuccess(bundle);
        });
        singleSaveAllUsers = singleSaveAllUsers.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        singleSaveAllUsers.subscribe((bundle, throwable) -> {
            getView().showDbResult(bundle);
        });
    }

    private void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels) {
        loadedUsers = retrofitUserItemModels;
        getView().showUserList(retrofitUserItemModels);
    }

    public class UserListObserver extends DefaultObserver<List<RetrofitUserItemModel>> {
        @Override
        public void onNext(List<RetrofitUserItemModel> retrofitUserItemModels) {
            showUserList(retrofitUserItemModels);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

}
