package name.neuhalfen.projects.android.robohash.paths;


public interface Configuration {
    /**
     * @param bucketValues - the values for the buckets. bucketValues.length == getBuckets.length && bucketValues[i]>=0 && bucketValues[i]<getBuckets[i]
     * @return
     */
    String[] convertToFacetParts(byte[] bucketValues);

    /**
     * @return the list of buckets with their size (1..) as values.
     */
    byte[] getBuckets();
}
