package geekbrains.ru.hw06;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;
import geekbrains.ru.hw04.activity.BaseActivity;
import geekbrains.ru.hw04.recycler.UserListItemAdapter;
import geekbrains.ru.hw04.retrofit.RetrofitUserItemModel;
import geekbrains.ru.hw05.views.MainViewHW05;
import geekbrains.ru.hw06.presenters.PresenterHW06;

public class ActivityHW06 extends BaseActivity implements MainViewHW05 {
    private UserListItemAdapter mUserListItemAdapter;
    private RecyclerView mRecyclerView;
    private ProgressBar mLoading;
    private TextView mInfoText;

    private PresenterHW06 mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw06);
        initPresenter(savedInstanceState);

        initControls();
        initRecycler();
    }

    private void initControls() {
        findViewById(R.id.hw06_load_button).setOnClickListener(v -> {
            showLoading();
            mPresenter.loadUserFromInternet();
        });
        findViewById(R.id.hw06_write_db_button).setOnClickListener(v -> mPresenter.writeUsersToSugar());
        mLoading = findViewById(R.id.hw06_progress_bar);
        mInfoText = findViewById(R.id.hw06_info_text);
    }

    private void showLoading() {
        mRecyclerView.setVisibility(View.GONE);
        mLoading.setVisibility(View.VISIBLE);
    }

    private void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            mPresenter = new PresenterHW06();
        else
            mPresenter = (PresenterHW06) PresenterManager.getInstance()
                    .restorePresenter(savedInstanceState);
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.hw06_recycler);
        mUserListItemAdapter = new UserListItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mUserListItemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenter != null)
            mPresenter.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null)
            mPresenter.unbindView();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    @Override
    public void showDbResult(Bundle bundle) {
        mInfoText.setText(String.format("Записей: %d. Время: %d мс.", bundle.getInt("count"), bundle.getLong("msec")));
    }

    @Override
    public void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels) {
        mUserListItemAdapter.setUserList(retrofitUserItemModels);
    }

    @Override
    public void showError() {

    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
