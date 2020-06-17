package geekbrains.ru.hw06.dagger;

import javax.inject.Singleton;

import dagger.Component;
import geekbrains.ru.hw06.presenters.PresenterHW06;

@Singleton
@Component(modules = {DaggerNetModule.class, DaggerRoomModule.class})
public interface AppComponent {
    void injectsToPresenter(PresenterHW06 presenterHW06);
}
