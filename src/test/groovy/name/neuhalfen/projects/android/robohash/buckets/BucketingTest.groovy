package name.neuhalfen.projects.android.robohash.buckets

import spock.lang.*

class BucketingTest extends spock.lang.Specification {

    /**
     * Mainly tests "does not crash"
     */
    @Unroll
    def "bucketing from BigInteger works"(bucketSizes, BigInteger hash, expected) {

        given:
        Bucketing bucketing = new Bucketing(bucketSizes as byte[])

        expect:
        bucketing.createBuckets(hash) == expected as byte[]

        where:
        bucketSizes  | hash || expected
        [10]         | 3    || [3]
        [10]         | 10   || [1]  // the bucket is accesses two times: 10 % 10  + 1 % 10
        [10, 10]     | 11   || [1, 1]
        [7, 8]       | 12   || [5, 1]
        [2, 2]       | 13   || [0, 1]
        [10, 10]     | 14   || [4, 1]
        [10, 10]     | 24   || [4, 2]
        [10, 10]     | 124  || [5, 2]
        [3, 3, 5, 7] | 9999 || [1, 1, 4, 5]
    }

    /**
     * Mainly tests "does not crash"
     */
    @Unroll
    def "bucketing from UUID  works"(bucketSizes, String uuid, expected) {

        given:
        Bucketing bucketing = new Bucketing(bucketSizes as byte[])

        expect:
        bucketing.createBuckets(UUID.fromString(uuid)) == expected as byte[]

        where:
        bucketSizes  | uuid                                   || expected
        [10]         | "3b98785b-4b59-4cb6-8219-cebed9b8b959" || [9]
        [10, 10]     | "3b98785b-4b59-4cb6-8219-cebed9b8b959" || [3,6]
        [10, 10]     | "7b98785b-4b59-4cb6-8219-cebed9b8b959" || [9,3]
        [3, 3, 5, 7] | "3b98785b-4b59-4cb6-8219-cebed9b8b959" || [0,2,0,5]
    }
}
