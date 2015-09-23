package name.neuhalfen.projects.android.robohash.buckets

import name.neuhalfen.projects.android.robohash.handle.HandleFactory
import spock.lang.*

class BucketingTest extends spock.lang.Specification {

    /**
     * Mainly tests "does not crash"
     */
    def "bucketing from BigInteger works"(bucketSizes, BigInteger hash, expected) {

        given:
        VariableSizeHashing bucketing = new VariableSizeHashing(bucketSizes as byte[])

        expect:
        bucketing.createBuckets(hash) == expected as byte[]

        where:
        bucketSizes  | hash || expected
        [10]         | 3    || [3]
        [10]         | 10   || [0]
        [10, 10]     | 11   || [1, 1]
        [7, 8]       | 12   || [5, 1]
        [2, 2]       | 13   || [1, 0]
        [10, 10]     | 14   || [4, 1]
        [10, 10]     | 24   || [4, 2]
        [10, 10]     | 124  || [4, 2]
        [3, 3, 5, 7] | 9999 || [0, 0, 1, 5]
    }

    /**
     * Mainly tests "does not crash"
     */
    def "bucketing from UUID  works"(bucketSizes, String uuid, expected) {

        given:
        VariableSizeHashing bucketing = new VariableSizeHashing(bucketSizes as byte[])

        expect:
        bucketing.createBuckets(UUID.fromString(uuid)) == expected as byte[]

        where:
        bucketSizes  | uuid                                   || expected
        [10]         | "3b98785b-4b59-4cb6-8219-cebed9b8b959" || [9]
        [10, 10]     | "3b98785b-4b59-4cb6-8219-cebed9b8b959" || [9, 4]
        [10, 10]     | "7b98785b-4b59-4cb6-8219-cebed9b8b959" || [3, 1]
        [3, 3, 5, 7] | "3b98785b-4b59-4cb6-8219-cebed9b8b959" || [2, 2, 4, 3]
    }

    /**
     * Sampling
     */
    /*
    def "sample randomness - not really a test"() {

        final int SAMPLES = 1000

        given:
        def bucketSizes = [10, 10, 10, 10, 10, 10] as byte[]
        VariableSizeHashing bucketing = new VariableSizeHashing(bucketSizes)

        HandleFactory handleFactory = new HandleFactory()

        Set<Long> counter = new HashSet<>(SAMPLES)

        for (int i = 0; i < SAMPLES; i++) {
            def uuid = UUID.randomUUID()

            def buckets = bucketing.createBuckets(uuid)
            long handle = handleFactory.calculateHandle(buckets).pack()
            counter.add(handle)
        }
        expect:
        counter.size() * 100 / SAMPLES >= 99
    }


    def "sample randomness 2 - not really a test"() {

        final int SAMPLES = 1000

        given:
        def bucketSizes = [10] as byte[]
        VariableSizeHashing bucketing = new VariableSizeHashing(bucketSizes)

        HandleFactory handleFactory = new HandleFactory()


        int[] counter = new int[10];

        for (int i = 0; i < SAMPLES; i++) {

            def buckets = bucketing.createBuckets(UUID.randomUUID())

            //def buckets = bucketing.createBuckets(BigInteger.valueOf(i*17))
            counter[buckets[0]]++
        }


        expect:
        def ev = SAMPLES / bucketSizes[0]
        counter == [ev, ev, ev, ev, ev, ev, ev, ev, ev, ev] as int[]
    }



    def "sample randomness 3 - not really a test"() {

        final int SAMPLES = 10000

        given:
        def bucketSizes = [2] as byte[]
        VariableSizeHashing bucketing = new VariableSizeHashing(bucketSizes)

        HandleFactory handleFactory = new HandleFactory()


        int[] counter = new int[10];

        for (int i = 0; i < SAMPLES; i++) {

            def buckets = bucketing.createBuckets(UUID.randomUUID())

            //def buckets = bucketing.createBuckets(BigInteger.valueOf(i*17))
            counter[buckets[0]]++
        }


        expect:
        def ev = SAMPLES / bucketSizes[0]
        counter == [ev, ev] as int[]
    }


    def "sample counter 2 - not really a test"() {

        final int SAMPLES = 1000

        given:
        def bucketSizes = [10] as byte[]
        VariableSizeHashing bucketing = new VariableSizeHashing(bucketSizes)

        HandleFactory handleFactory = new HandleFactory()


        int[] counter = new int[10];

        for (int i = 0; i < SAMPLES; i++) {
            def buckets = bucketing.createBuckets(BigInteger.valueOf(i))
            counter[buckets[0]]++
        }


        expect:
        def ev = SAMPLES / bucketSizes[0]
        counter == [ev, ev, ev, ev, ev, ev, ev, ev, ev, ev] as int[]
    }

    */
}
