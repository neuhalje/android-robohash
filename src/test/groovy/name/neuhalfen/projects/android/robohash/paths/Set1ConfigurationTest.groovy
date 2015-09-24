package name.neuhalfen.projects.android.robohash.paths

import spock.lang.*

class Set1ConfigurationTest extends spock.lang.Specification {


    def "path generation for set1 works as expected"(bucketValues, expectedPaths) {

        given:
        Configuration set1 = new Set1Configuration()

        expect:
        set1.convertToFacetParts(bucketValues as byte[]) == expectedPaths as String[]

        where:
        bucketValues       || expectedPaths
        [0, 1, 2, 3, 4, 5] || ["sets/set1/blue/01Body/blue_body-02.png",
                               "sets/set1/blue/02Face/blue_face-03.png",
                               "sets/set1/blue/Mouth/blue_mouth-04.png",
                               "sets/set1/blue/Eyes/blue_eyes-05.png",
                               "sets/set1/blue/Accessory/blue_accessory-06.png"]
        [1, 0, 1, 2, 3, 9] || ["sets/set1/brown/01Body/brown_body-01.png",
                               "sets/set1/brown/02Face/brown_face-02.png",
                               "sets/set1/brown/Mouth/brown_mouth-03.png",
                               "sets/set1/brown/Eyes/brown_eyes-04.png",
                               "sets/set1/brown/Accessory/brown_accessory-10.png"]
    }
}
