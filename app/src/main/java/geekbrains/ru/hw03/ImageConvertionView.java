package geekbrains.ru.hw03;

import android.content.Intent;
import android.graphics.Bitmap;

public interface ImageConvertionView {

    enum ConvertionResult {
        SUCCESS,
        FAILURE
    };

    int REQUEST_IMAGE_CODE = 123;
    int WRITE_IMAGE_CODE = 321;

    void setConvertionControlsState(boolean state);

    void showImage(Bitmap bitmap);

    void openDialog(Intent intent, String dialogTitle, int requestCode);

    void showConvertionResult(ConvertionResult result);

}
