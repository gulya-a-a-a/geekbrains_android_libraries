package geekbrains.ru.hw05.presenters;

import android.os.Bundle;

import androidx.annotation.NonNull;

import java.util.Date;
import java.util.List;

import geekbrains.ru.hw01.presenters.BasePresenter;
import geekbrains.ru.hw04.models.UsersRepository;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import geekbrains.ru.hw05.HW05Application;
import geekbrains.ru.hw05.database.realm.UserItemRealmMapper;
import geekbrains.ru.hw05.database.realm.UserItemRealmObject;
import geekbrains.ru.hw05.database.sqlite.UserItemModelMapper;
import geekbrains.ru.hw05.database.sqlite.UserItemRoomModel;
import geekbrains.ru.hw05.views.MainViewHW05;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;

public class PresenterHW05 extends BasePresenter<List<UserItemRoomModel>, MainViewHW05> {

    enum DbType {
        SQLITE,
        REALM
    }

    private UsersRepository mUsersRepository;
    private List<RetrofitUserItemModel> loadedUsers;
    private DbType mSelectedDb;

    public PresenterHW05() {
        mUsersRepository = new UsersRepository();
    }

    @Override
    public void updateView() {
        getView().showUserList(loadedUsers);
    }

    public void loadUserFromInternet() {
        if (mUsersRepository != null) {
            mUsersRepository.requestUsers(new UserListObserver());
        }
    }

    @Override
    public void bindView(@NonNull MainViewHW05 view) {
        super.bindView(view);
        updateView();
    }

    public void writeUsersToDb() {
        if (mSelectedDb == DbType.SQLITE)
            writeToSQlite();
        else
            writeToRealm();
    }

    private void writeToSQlite() {
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            HW05Application.getInstance().getDao().insertAll(UserItemModelMapper.transform(loadedUsers));
            Date finish = new Date();
            List<UserItemRoomModel> tempList = HW05Application.getInstance().getDao().getAll();
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

    private void writeToRealm() {
        Bundle bundle = new Bundle();
        HW05Application.getInstance().getRealm().executeTransactionAsync(realm -> {
            Number maxUid = realm.where(UserItemRealmObject.class).max("uid");
            Date start = new Date();
            realm.insertOrUpdate(UserItemRealmMapper.transform(loadedUsers,
                    maxUid != null ? maxUid.intValue() : 0));
            Date finish = new Date();

            List<UserItemRealmObject> tempList = realm.where(UserItemRealmObject.class).findAll();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msec", finish.getTime() - start.getTime());
        }, () -> {
            getView().showDbResult(bundle);
        });
    }


    public void readUsersFromDb() {
        if (mSelectedDb == DbType.SQLITE)
            readFromSqlite();
        else
            readFromRealm();
    }

    private void readFromRealm() {
        Bundle bundle = new Bundle();
        HW05Application.getInstance().getRealm().executeTransactionAsync(realm -> {
            Date start = new Date();
            List<UserItemRealmObject> tempList = realm.where(UserItemRealmObject.class).findAll();
            Date finish = new Date();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msec", finish.getTime() - start.getTime());
        }, () -> {
            getView().showDbResult(bundle);
        });
    }

    private void readFromSqlite() {
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            List<UserItemRoomModel> tempList = HW05Application.getInstance().getDao().getAll();
            Date finish = new Date();
            Bundle bundle = new Bundle();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msec", finish.getTime() - start.getTime());
            emitter.onSuccess(bundle);
        });
        singleSaveAllUsers = singleSaveAllUsers.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = singleSaveAllUsers.subscribe((bundle, throwable) -> {
            getView().showDbResult(bundle);
        });
    }


    public void removeUsersFromDb() {
        if (mSelectedDb == DbType.SQLITE)
            removeFromSqlite();
        else
            removeFromRealm();
    }

    private void removeFromRealm() {
        Bundle bundle = new Bundle();
        HW05Application.getInstance().getRealm().executeTransactionAsync(realm -> {
            Date start = new Date();
            RealmResults<UserItemRealmObject> tempList = realm.where(UserItemRealmObject.class).findAll();
            bundle.putInt("count", tempList.size());
            tempList.deleteAllFromRealm();
            Date finish = new Date();
            bundle.putLong("msec", finish.getTime() - start.getTime());
        }, () -> {
            getView().showDbResult(bundle);
        });
    }

    private void removeFromSqlite() {
        Single<Bundle> singleSaveAllUsers = Single.create(emitter -> {
            Date start = new Date();
            HW05Application.getInstance().getDao().deleteAll();
            Date finish = new Date();
            List<UserItemRoomModel> tempList = HW05Application.getInstance().getDao().getAll();
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

    private void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels) {
        loadedUsers = retrofitUserItemModels;
        updateView();
    }

    public void onDbTypeSelected(int type) {
        mSelectedDb = type == 0 ? DbType.SQLITE : DbType.REALM;
    }

    public class UserListObserver extends DefaultObserver<List<RetrofitUserItemModel>> {
        @Override
        public void onNext(List<RetrofitUserItemModel> retrofitUserItemModels) {
            showUserList(retrofitUserItemModels);
        }

        @Override
        public void onError(Throwable e) {
            getView().showError();
        }

        @Override
        public void onComplete() {
            getView().hideLoading();
        }
    }

}
