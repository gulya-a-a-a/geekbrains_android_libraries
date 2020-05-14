package geekbrains.ru.hw01.presenters;

import androidx.annotation.NonNull;

import java.util.List;

import geekbrains.ru.hw01.models.Counter;
import geekbrains.ru.hw01.models.CounterRepo;
import geekbrains.ru.hw01.views.MainView;

public class MainPresenter extends BasePresenter<List<Counter>, MainView> {
    @Override
    public void updateView() {
        getView().showCountersList(mModel);
    }

    @Override
    public void bindView(@NonNull MainView view) {
        super.bindView(view);
        if (mModel == null)
            setModel(CounterRepo.getInstance().getCounterList());
    }

    public void onAddItemClicked() {
        Counter counter = new Counter();
        CounterRepo.getInstance().addCounter(counter);
        mModel.add(counter);
        updateView();
    }
}
