package geekbrains.ru.hw01.views;

import java.util.List;

import geekbrains.ru.hw01.models.Counter;

public interface MainView {
    void showCountersList(List<Counter> counters);
}
