package geekbrains.ru.hw01.presenters;

import geekbrains.ru.hw01.views.CounterView;
import geekbrains.ru.hw01.models.Counter;

public class CounterPresenter extends BasePresenter<Counter, CounterView> {

    @Override
    public void updateView() {
        getView().setCounterName(mModel.getName());
        getView().setCounterValues(mModel.getValue());
    }

    public void onCounterButtonClicked() {
        mModel.setValue(mModel.getValue() + 1);
        updateView();
    }
}
