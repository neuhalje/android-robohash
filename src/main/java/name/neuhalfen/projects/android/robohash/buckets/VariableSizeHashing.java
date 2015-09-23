package name.neuhalfen.projects.android.robohash.buckets;


import java.math.BigInteger;
import java.util.UUID;

/**
 * "Hash" a big integer (expected: hash or uuid) into buckets. The goal is to deterministically
 * "repack" the randomness of the hash into the bucket.
 * <p/>
 * Each bucket is defined by a maximum value. The implementation guarantees that the values in bucket n is in the
 * range 0..(bucketSize[n]-1).
 * 
 */
public class VariableSizeHashing {
    private final byte[] bucketSizes;

    public VariableSizeHashing(byte[] bucketSizes) {
        this.bucketSizes = bucketSizes;
    }

    static BigInteger uuidToBigInteger(UUID uuid) {
        return BigInteger.valueOf(uuid.getMostSignificantBits()).shiftLeft(64).add(BigInteger.valueOf(uuid.getLeastSignificantBits()));
    }

    public byte[] createBuckets(UUID uuid) {
        return createBuckets(uuidToBigInteger(uuid));
    }

    /**
     * Takes the hash value and distributes it over the buckets.
     * <p/>
     * Assumption: the value of hash is (much) larger than 16^bucketSizes.length and uniformly distributed (e.g. random)
     *
     * @param hash
     * @return buckets
     */
    public byte[] createBuckets(BigInteger hash) {

        int currentBucket = 0;
        byte[] ret = new byte[this.bucketSizes.length];

        while (hash.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divisorReminder = hash.divideAndRemainder(BigInteger.valueOf(bucketSizes[currentBucket]));

            hash = divisorReminder[0];
            long reminder = divisorReminder[1].longValue();

            ret[currentBucket] = (byte) ((ret[currentBucket] + reminder) % bucketSizes[currentBucket]);

            currentBucket = (currentBucket + 1) % this.bucketSizes.length;
        }

        return ret;
    }
}
