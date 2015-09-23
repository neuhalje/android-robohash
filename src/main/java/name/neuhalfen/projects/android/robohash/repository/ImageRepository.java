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

    public Bitmap get(String path) throws IOException {
        // TODO caching
        InputStream inputStream = null;
        try {
            inputStream = assets.open(path);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
    }
}
