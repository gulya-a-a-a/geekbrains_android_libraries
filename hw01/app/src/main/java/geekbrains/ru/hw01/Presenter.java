package geekbrains.ru.hw01;

class Presenter {
    private Model mModel;
    private MainView mView;

    Presenter(MainView view) {
        mModel = new Model();
        mView = view;
    }

    private int calculateNewValue(int index) {
        return mModel.getElementValueAtIndex(index) + 1;
    }

    void buttonClick(int index) {
        int newValue = calculateNewValue(index);
        mModel.setElementValueAtIndex(index, newValue);
        mView.setButtonText(index, String.valueOf(newValue));
    }
}
