package name.neuhalfen.projects.android.robohash.repository;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;

public class ImageRepository {
    private final AssetManager assets;
    private Bitmap buffer = null;

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

    public Bitmap createBuffer(int width, int height) throws IOException {
        if (buffer != null) {
            return buffer;
        }

         buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888); // this creates a MUTABLE bitmap

        return buffer;

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


    public void returnBuffer(Bitmap buffer) {
            buffer.eraseColor(Color.TRANSPARENT);
    }
}
