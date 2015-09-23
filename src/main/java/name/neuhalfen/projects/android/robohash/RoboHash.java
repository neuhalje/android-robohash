package name.neuhalfen.projects.android.robohash;

import android.content.Context;
import android.graphics.Bitmap;
import name.neuhalfen.projects.android.robohash.buckets.VariableSizeHashing;
import name.neuhalfen.projects.android.robohash.handle.Handle;
import name.neuhalfen.projects.android.robohash.handle.HandleFactory;
import name.neuhalfen.projects.android.robohash.paths.Configuration;
import name.neuhalfen.projects.android.robohash.paths.Set1Configuration;
import name.neuhalfen.projects.android.robohash.rendering.Renderer;
import name.neuhalfen.projects.android.robohash.repository.ImageRepository;

import java.io.IOException;
import java.util.UUID;

public class RoboHash {

    private final static HandleFactory handleFactory = new HandleFactory();

    private final Configuration configuration = new Set1Configuration();
    private final Renderer renderer = new Renderer();
    private final ImageRepository repository;
    private final VariableSizeHashing hashing = new VariableSizeHashing(configuration.getBucketSizes());


    public RoboHash(Context context) {
        this.repository = new ImageRepository(context.getAssets());
    }

    public Handle calculateHandleFromBucketValues(byte[] data) {
        return handleFactory.calculateHandle(data);
    }


    public Handle calculateHandleFromUUID(UUID uuid) {
        byte[] data = hashing.createBuckets(uuid);
        return handleFactory.calculateHandle(data);
    }

    public byte[] getBucketSizes() {
        return configuration.getBucketSizes();
    }


    /**
     * This can be VERY slow
     *
     * @param handle
     * @return image (1024x1024) that identifies the handle
     * @throws IOException
     */
    public Bitmap imageForHandle(Handle handle) throws IOException {
        byte[] bucketValues = handle.bucketValues();
        String[] paths = configuration.convertToFacetParts(bucketValues);

        Bitmap[] facets = new Bitmap[paths.length];
        for (int i = 0; i < facets.length; i++) {
            facets[i] = repository.get(paths[i]);
        }
        return renderer.merge(facets);
    }
}
