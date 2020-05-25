package geekbrains.ru.hw03;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DefaultObserver;

import geekbrains.ru.hw01.presenters.BasePresenter;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

import static geekbrains.ru.hw03.ImageConvertionView.REQUEST_IMAGE_CODE;
import static geekbrains.ru.hw03.ImageConvertionView.WRITE_IMAGE_CODE;


abstract class DispCompl extends DisposableCompletableObserver {
    @Override
    public void onError(Throwable e) {
    }
}

public class PresenterHW03 extends BasePresenter {

    private ImageConvertionView mView;
    private Bitmap mBitmap;
    private String mLastFileName;

    void bindView(ImageConvertionView view) {
        mView = view;
        if (mBitmap != null)
            updateView();
    }

    @Override
    public void updateView() {
        mView.setConvertionControlsState(true);
        mView.showImage(mBitmap);
    }

    void initPictureOpen() {
        Intent intent = new Intent()
                .setType("image/jpeg")
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setAction(Intent.ACTION_GET_CONTENT);

        mView.openDialog(intent, "Select image", REQUEST_IMAGE_CODE);
    }


    void initPictureConvertion(String fileName) {
        Intent intent = new Intent()
                .setType("image/png")
                .addCategory(Intent.CATEGORY_OPENABLE)
                .setAction(Intent.ACTION_CREATE_DOCUMENT)
                .putExtra(Intent.EXTRA_TITLE, fileName);

        mView.openDialog(intent, "Create a file", WRITE_IMAGE_CODE);
    }

    void getBitmapFromUri(Uri uri, ContentResolver contentResolver) throws IOException {
        Observable<Bitmap> observable = Observable.create(emitter -> {
            try {
                Log.d("THREAD INFO", Thread.currentThread().getName());
                ParcelFileDescriptor parcelFileDescriptor
                        = contentResolver.openFileDescriptor(uri, "r");

                if (parcelFileDescriptor != null) {
                    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
                    Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                    parcelFileDescriptor.close();
                    emitter.onNext(bitmap);
                    emitter.onComplete();
                } else {
                    emitter.onError(new NullPointerException());
                }
            } catch (NullPointerException e) {
                emitter.onError(e);
            }
        });

        observable = observable.map(newBitmap -> mBitmap = newBitmap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new ImageObserver());
        Log.d("THREAD INFO", Thread.currentThread().getName());
    }


    void writeConvertedImage(Uri uri, ContentResolver contentResolver) throws IOException {
        Completable comp = Completable.fromAction(() -> {
            ParcelFileDescriptor pfd = contentResolver.openFileDescriptor(uri, "w");
            FileOutputStream fos = new FileOutputStream(Objects.requireNonNull(pfd).getFileDescriptor());
            mBitmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
            fos.close();
            pfd.close();
            Log.d("THREAD INFO", Thread.currentThread().getName());
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        Disposable disposable = comp
                .subscribe(() -> mView.showConvertionResult(ImageConvertionView.ConvertionResult.SUCCESS),
                        throwable -> {
                            mView.showConvertionResult(ImageConvertionView.ConvertionResult.FAILURE);
                            Log.e("TAG", Objects.requireNonNull(throwable.getMessage()));
                        });

        Log.d("THREAD INFO", Thread.currentThread().getName());
    }

    class ImageObserver extends DefaultObserver<Bitmap> {
        @Override
        public void onNext(Bitmap bitmap) {
            mView.showImage(bitmap);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
            mView.setConvertionControlsState(true);
        }
    }
}
