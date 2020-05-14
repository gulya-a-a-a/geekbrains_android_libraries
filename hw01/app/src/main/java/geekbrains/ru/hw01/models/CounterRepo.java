package geekbrains.ru.hw01.models;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounterRepo {
    private static int INIT_COUNTER_QUANTITY = 5;
    private static CounterRepo instance;
    private static int nextID = 1;

    private Map<Integer, Counter> mCounters;

    private CounterRepo() {
        mCounters = new HashMap<>();
        initCounters();
    }

    private void initCounters() {
        for (int i = 0; i < INIT_COUNTER_QUANTITY; i++) {
            Counter counter = new Counter();
            addCounter(counter);
        }
    }

    public static CounterRepo getInstance() {
        if (instance == null) {
            instance = new CounterRepo();
        }
        return instance;
    }

    @SuppressLint("DefaultLocale")
    private void addCounter(@NonNull Counter counter) {
        int id = nextID++;
        counter.setId(id);
        counter.setName(String.format("Счетчик %d", id));
        mCounters.put(id, counter);
    }

    public Counter getCounter(int id) {
        return mCounters.get(id);
    }

    public List<Counter> getCounterList() {
        return new ArrayList<>(mCounters.values());
    }
}
