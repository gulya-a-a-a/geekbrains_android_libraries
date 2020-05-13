package geekbrains.ru.hw01;

import java.util.Arrays;
import java.util.List;

class Model {
    private List<Integer> mList = Arrays.asList(0, 0, 0);

    int getElementValueAtIndex(int _index) {
        return mList.get(_index);
    }

    void setElementValueAtIndex(int _index, int value) {
        mList.set(_index, value);
    }
}

