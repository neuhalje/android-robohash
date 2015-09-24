package name.neuhalfen.projects.android.robohash.repository;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

// TODO:test
public class ImageRepository {
    private final AssetManager assets;

    public ImageRepository(AssetManager assets) {
        this.assets = assets;
    }


    public Bitmap get(String path, int sampleSize) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inMutable = false;

        InputStream inputStream = null;
        try {
            inputStream = assets.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }

    public Bitmap createBuffer(String path, int sampleSize) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inMutable = true;

        InputStream inputStream = null;
        try {
            inputStream = assets.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }

    public Bitmap getInto(Bitmap target, String path, int sampleSize) throws IOException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        options.inBitmap = target;
        options.inMutable = false;

        InputStream inputStream = null;
        try {
            inputStream = assets.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }


}
