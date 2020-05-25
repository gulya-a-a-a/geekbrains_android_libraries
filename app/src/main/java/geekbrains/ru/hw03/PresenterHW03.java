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
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DefaultObserver;

import geekbrains.ru.hw01.presenters.BasePresenter;
import io.reactivex.schedulers.Schedulers;

import static geekbrains.ru.hw03.ImageConvertionView.REQUEST_IMAGE_CODE;
import static geekbrains.ru.hw03.ImageConvertionView.WRITE_IMAGE_CODE;


public class PresenterHW03 extends BasePresenter {

    private final String THREAD_INFO_TAG = "THREAD_INFO";

    private ImageConvertionView mView;
    private Bitmap mBitmap;
    private Disposable mCurrentDisposable;

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

    void getBitmapFromUri(Uri uri, ContentResolver contentResolver) {
        Observable<Bitmap> observable = Observable.create(emitter -> {
            try {
                Log.d(THREAD_INFO_TAG, Thread.currentThread().getName());
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

        Log.d(THREAD_INFO_TAG, Thread.currentThread().getName());
    }

    void writeConvertedImage(Uri uri, ContentResolver contentResolver) throws NullPointerException {
        Completable comp = Completable.fromAction(() -> {
            ParcelFileDescriptor pfd = contentResolver.openFileDescriptor(uri, "w");

            if (pfd != null) {
                FileOutputStream fos = new FileOutputStream(pfd.getFileDescriptor());
                Thread.sleep(2000);
                mBitmap.compress(Bitmap.CompressFormat.PNG, 10, fos);
                fos.close();
                pfd.close();
            } else {
                throw new NullPointerException();
            }

            Log.d(THREAD_INFO_TAG, Thread.currentThread().getName());
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        mCurrentDisposable = comp
                .subscribe(() -> mView.showConvertionResult(ImageConvertionView.ConvertionResult.SUCCESS),
                        throwable -> {
                            mView.showConvertionResult(ImageConvertionView.ConvertionResult.FAILURE);
                            Log.e("CONVERTION", Objects.requireNonNull(throwable.getMessage()));
                        });

        Log.d(THREAD_INFO_TAG, Thread.currentThread().getName());
    }

    void convertionFinished() {
        if (mCurrentDisposable.isDisposed())
            mCurrentDisposable.dispose();
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
