package geekbrains.ru.hw06;

import android.app.Application;

import geekbrains.ru.hw06.dagger.AppComponent;
import geekbrains.ru.hw06.dagger.ContextModule;
import geekbrains.ru.hw06.dagger.DaggerAppComponent;

public class ApplicationHW06 extends Application {
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent
                .builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
