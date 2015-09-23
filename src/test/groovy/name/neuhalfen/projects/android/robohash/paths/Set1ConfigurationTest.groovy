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
        [0, 1, 2, 3, 4, 5] || ["sets/set1/blue/01Body/blue_body-01.png",
                               "sets/set1/blue/02Face/blue_face-02.png",
                               "sets/set1/blue/Mouth/blue_mouth-03.png",
                               "sets/set1/blue/Eyes/blue_eyes-04.png",
                               "sets/set1/blue/Accessory/blue_accessory-05.png",]
        [0, 1, 2, 3, 4, 10] || ["sets/set1/blue/01Body/blue_body-01.png",
                               "sets/set1/blue/02Face/blue_face-02.png",
                               "sets/set1/blue/Mouth/blue_mouth-03.png",
                               "sets/set1/blue/Eyes/blue_eyes-04.png",
                               "sets/set1/blue/Accessory/blue_accessory-10.png",]
    }
}
