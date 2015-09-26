package name.neuhalfen.projects.android.robohash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.LruCache;
import name.neuhalfen.projects.android.robohash.buckets.VariableSizeHashing;
import name.neuhalfen.projects.android.robohash.handle.Handle;
import name.neuhalfen.projects.android.robohash.handle.HandleFactory;
import name.neuhalfen.projects.android.robohash.paths.Configuration;
import name.neuhalfen.projects.android.robohash.paths.Set1Configuration;
import name.neuhalfen.projects.android.robohash.repository.ImageRepository;

import java.io.IOException;
import java.util.UUID;

public class RoboHash {

    private final static HandleFactory handleFactory = new HandleFactory();

    private final Configuration configuration = new Set1Configuration();
    private final ImageRepository repository;
    private final VariableSizeHashing hashing = new VariableSizeHashing(configuration.getBucketSizes());


    // Optional
    private LruCache<String, Bitmap> memoryCache;

    public RoboHash(Context context) {
        this.repository = new ImageRepository(context.getAssets());
    }

    // //    untested
    // public Handle calculateHandleFromBinary(byte[] binary) {
    //     byte[] data = hashing.createBuckets(new BigInteger(binary));
    //     return handleFactory.calculateHandle(data);
    // }


    /**
     * @param memoryCache use the passed memory cache (can be null for no cache)
     */
    public void useCache(LruCache<String, Bitmap> memoryCache) {
        this.memoryCache = memoryCache;
    }

    public Handle calculateHandleFromUUID(UUID uuid) {
        byte[] data = hashing.createBuckets(uuid);
        return handleFactory.calculateHandle(data);
    }

    /**
     * This can be VERY slow (~15ms on a Nexus 5). Consider #useCache.
     *
     * @param handle which robot to retrieve
     * @return image (300x300) that identifies the handle
     * @throws IOException
     */
    public Bitmap imageForHandle(Handle handle) throws IOException {
        if (null != memoryCache) {
            Bitmap cached = memoryCache.get(handle.toString());
            if (null != cached) return cached;
        }

        byte[] bucketValues = handle.bucketValues();
        String[] paths = configuration.convertToFacetParts(bucketValues);

        int sampleSize = 1;

        Bitmap buffer = repository.createBuffer(configuration.width(), configuration.height());
        Bitmap target = buffer.copy(Bitmap.Config.ARGB_8888, true);

        Canvas merged = new Canvas(target);
        Paint paint = new Paint(0);

        // The first image is not added as copy form the buffer
        for (int i = 0; i < paths.length; i++) {
            merged.drawBitmap(repository.getInto(buffer, paths[i], sampleSize), 0, 0, paint);
        }
        repository.returnBuffer(buffer);

        if (null != memoryCache) {
            memoryCache.put(handle.toString(),target);
        }
        return target;
    }

}
