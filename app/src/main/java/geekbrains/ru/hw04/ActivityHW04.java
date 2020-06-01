package geekbrains.ru.hw04;

import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.Objects;

import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.activity.BaseActivity;
import geekbrains.ru.hw04.fragments.UserDetailFragment;
import geekbrains.ru.hw04.fragments.UserListFragment;

public class ActivityHW04 extends BaseActivity implements UserListFragment.UserListListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw04);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Retrofit");

        initList();
    }

    private void initList() {
        addFragment(R.id.list_container, new UserListFragment());
    }

    @Override
    public void onUserItemClicked(String userName) {
        addFragment(R.id.list_container, new UserDetailFragment(userName));
    }
}
