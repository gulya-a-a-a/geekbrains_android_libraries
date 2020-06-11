package geekbrains.ru.hw05;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import geekbrains.ru.hw05.presenters.PresenterHW05;
import geekbrains.ru.hw05.views.MainViewHW05;

public class ActivityHW05 extends BaseActivity implements MainViewHW05, AdapterView.OnItemSelectedListener {
    private UserListItemAdapter mUserListItemAdapter;
    private RecyclerView mRecyclerView;

    private TextView mInfoText;
    private ProgressBar mLoading;

    private PresenterHW05 mPresenterHW05;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw05);

        initControls();
        initPresenter(savedInstanceState);
        initRecycler();
        initSpinner();
    }

    private void initControls() {
        findViewById(R.id.hw05_load_button).setOnClickListener(v -> {
            mRecyclerView.setVisibility(View.GONE);
            mLoading.setVisibility(View.VISIBLE);
            mPresenterHW05.loadUserFromInternet();
        });
        findViewById(R.id.hw05_write_to_db_button).setOnClickListener(v -> mPresenterHW05.writeUsersToDb());
        findViewById(R.id.hw05_read_from_db_button).setOnClickListener(v -> mPresenterHW05.readUsersFromDb());
        findViewById(R.id.hw05_remove_from_db_button).setOnClickListener(v -> mPresenterHW05.removeUsersFromDb());

        mInfoText = findViewById(R.id.hw05_info_text);
        mLoading = findViewById(R.id.hw05_progress_bar);
    }

    private void initPresenter(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null)
            mPresenterHW05 = new PresenterHW05();
        else
            mPresenterHW05 = (PresenterHW05) PresenterManager.getInstance()
                    .restorePresenter(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPresenterHW05 != null)
            mPresenterHW05.bindView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenterHW05 != null)
            mPresenterHW05.unbindView();
    }

    private void initRecycler() {
        mRecyclerView = findViewById(R.id.hw05_recycler);
        mUserListItemAdapter = new UserListItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mUserListItemAdapter);
    }

    private void initSpinner() {
        Spinner spinner = findViewById(R.id.hw05_db_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.db_types_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenterHW05, outState);
    }

    @Override
    public void showUserList(List<RetrofitUserItemModel> retrofitUserItemModels) {
        mUserListItemAdapter.setUserList(retrofitUserItemModels);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDbResult(Bundle bundle) {
        mInfoText.setText(String.format("Записей: %d. Время: %d мс.", bundle.getInt("count"), bundle.getLong("msec")));
    }

    @Override
    public void showError() {

    }

    @Override
    public void hideLoading() {
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPresenterHW05.onDbTypeSelected(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
