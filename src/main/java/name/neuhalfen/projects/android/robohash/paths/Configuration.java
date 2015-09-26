package name.neuhalfen.projects.android.robohash.paths;


public interface Configuration {
    /**
     * @param bucketValues - the values for the buckets. bucketValues.length == getBucketSizes.length && bucketValues[i]>=0 && bucketValues[i]<getBucketSizes[i]
     * @return The paths for the robot elements. Merge them by drawing the image at @ret[n] on @ret[n-1]
     */
    String[] convertToFacetParts(byte[] bucketValues);

    /**
     * @return the list of buckets with their size (1..) as values.
     */
    byte[] getBucketSizes();

    /**
     *
     * @return The width of the robot images in pixel
     */
    int width();

    /**
     *
     * @return The height of the robot images in pixel
     */
    int height();
}
