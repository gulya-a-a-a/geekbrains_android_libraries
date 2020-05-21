package geekbrains.ru.hw01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import geekbrains.ru.hw01.models.Counter;
import geekbrains.ru.hw01.presenters.MainPresenter;
import geekbrains.ru.hw01.recycler.CounterAdapter;
import geekbrains.ru.hw01.views.MainView;
import geekbrains.ru.hw02.ActivityHW02;

public class MainActivity extends AppCompatActivity implements MainView {
    MainPresenter mPresenter;
    CounterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null)
            mPresenter = new MainPresenter();
        else
            mPresenter = (MainPresenter) PresenterManager.getInstance().restorePresenter(savedInstanceState);
        initRecycler();
    }

    private void initRecycler() {
        RecyclerView recyclerView = findViewById(R.id.counter_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new CounterAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.bindView(this);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenter, outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.unbindView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.add_counter_action:
                mPresenter.onAddItemClicked();
                break;
            case R.id.hw02_item:
                startHomework2Activity();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showCountersList(List<Counter> counters) {
        mAdapter.updateCounters(counters);
    }


    private void startHomework2Activity() {
        Intent intent = new Intent(MainActivity.this, ActivityHW02.class);
        startActivity(intent);
    }

}
