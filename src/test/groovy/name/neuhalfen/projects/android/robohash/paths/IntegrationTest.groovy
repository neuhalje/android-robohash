package name.neuhalfen.projects.android.robohash.paths

import name.neuhalfen.projects.android.robohash.buckets.VariableSizeHashing
import spock.lang.*

class IntegrationTest extends spock.lang.Specification {


    def "uuid to paths works"(uuidString) {

        given:
        Configuration configuration = new Set1Configuration()
        VariableSizeHashing bucketeer = new VariableSizeHashing(configuration.buckets)

        def uuid = UUID.fromString(uuidString)

        when:
        def paths = configuration.convertToFacetParts(bucketeer.createBuckets(uuid))

        then:
        // expected length of items
        paths.length == Set1Configuration.FACET_COUNT

        // the whole thing is stable
        paths == configuration.convertToFacetParts(bucketeer.createBuckets(uuid))

        where:
        uuidString << ["0203d7f9-6909-45f6-888d-34ff693aaa1f",
                       "f8d9f4ea-64ce-45df-88a3-53527bd00afe",
                       "ffffffff-ffff-ffff-ffff-ffffffffffff"]
    }
}
