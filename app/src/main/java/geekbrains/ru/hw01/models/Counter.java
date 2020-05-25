package geekbrains.ru.hw01.models;

public class Counter {
    private int mId;
    private String mName;
    private int mValue;

    public int getId() {
        return mId;
    }

    void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    void setName(String name) {
        mName = name;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }
}
