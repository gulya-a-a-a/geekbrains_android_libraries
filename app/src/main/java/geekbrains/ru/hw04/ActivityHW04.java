package geekbrains.ru.hw04;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.activity.BaseActivity;
import geekbrains.ru.hw04.fragments.UserDetailFragment;
import geekbrains.ru.hw04.fragments.UserListFragment;

public class ActivityHW04 extends BaseActivity implements UserListFragment.UserListListener {

    private PresenterHW04 mPresenterHW04;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw04);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Retrofit");

        if (savedInstanceState == null)
            mPresenterHW04 = new PresenterHW04();
        else
            mPresenterHW04 = (PresenterHW04) PresenterManager.getInstance()
                    .restorePresenter(savedInstanceState);

        initList();
    }

    private void initList() {
        addFragment(R.id.list_container, new UserListFragment());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenterHW04, outState);
    }


    @Override
    public void onUserItemClicked(String userName) {
        addFragment(R.id.list_container, new UserDetailFragment(userName));
    }
}
