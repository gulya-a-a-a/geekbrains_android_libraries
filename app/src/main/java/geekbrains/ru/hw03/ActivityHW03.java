package geekbrains.ru.hw03;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import geekbrains.ru.hw01.PresenterManager;
import geekbrains.ru.hw01.R;

public class ActivityHW03 extends AppCompatActivity implements ImageConvertionView {

    private EditText mEditText;
    private Button mConvertButton;
    private ImageView mImageView;

    private Bitmap mImageBitmap;

    private PresenterHW03 mPresenterHW03;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw03);
        Objects.requireNonNull(getSupportActionBar()).setTitle("RxJava 2");

        if (savedInstanceState == null)
            mPresenterHW03 = new PresenterHW03();
        else
            mPresenterHW03 = (PresenterHW03) PresenterManager.getInstance().restorePresenter(savedInstanceState);

        initControls();
    }

    private void initControls() {
        mEditText = findViewById(R.id.new_file_name);
        mImageView = findViewById(R.id.image_view);

        findViewById(R.id.open_image_button)
                .setOnClickListener(v -> mPresenterHW03.initPictureOpen());

        mConvertButton = findViewById(R.id.convert_image_button);
        mConvertButton.setOnClickListener(v -> mPresenterHW03.initPictureConvertion(mEditText.getText().toString()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenterHW03.bindView(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mPresenterHW03, outState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CODE) {
                try {
                    mPresenterHW03.getBitmapFromUri(Objects.requireNonNull(data).getData(), getContentResolver());
                } catch (IOException | NullPointerException e) {
                    setConvertionControlsState(false);
                    e.printStackTrace();
                }
            } else if (requestCode == WRITE_IMAGE_CODE) {
                try {
                    mPresenterHW03.writeConvertedImage(Objects.requireNonNull(data).getData(), getContentResolver());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void setConvertionControlsState(boolean state) {
        mConvertButton.setEnabled(state);
        mEditText.setEnabled(state);
    }

    @Override
    public void showImage(Bitmap bitmap) {
        mImageView.setImageBitmap(bitmap);
    }

    @Override
    public void showConvertionResult(ConvertionResult result) {
        Toast.makeText(this,
                result == ConvertionResult.SUCCESS ? "Image saved" : "An error occured",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void openDialog(Intent intent, String dialogTitle, int requestCode) {
        startActivityForResult(Intent.createChooser(intent, dialogTitle), requestCode);
    }
}
