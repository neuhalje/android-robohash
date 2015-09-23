package name.neuhalfen.projects.android.robohash.handle

import spock.lang.*


class HandleFactoryTest extends spock.lang.Specification {


    def "CalculateHandleValue"(bytes, long expected) {
        expect:
        HandleFactory.calculateHandleValue(bytes as byte[]) == expected

        where:
        bytes                                          || expected
        [0]                                            || 0x100000000000000
        [0xB]                                          || 0x10000000000000B
        [0xA, 0]                                       || 0x2000000000000A0
        [0xA, 0xB]                                     || 0x2000000000000AB
        [0xF, 0xE, 0, 0, 0, 0, 0, 0]                   || 0x8000000FE000000
        [0xF, 0xE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]    || 0xD0FE00000000000
        [0xF, 0xE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] || 0xEFE000000000000

    }

    def "size is correctly stored"(bytes, long expected) {
        given:
        def value = HandleFactory.calculateHandleValue(bytes as byte[])

        expect:
        HandleFactory.getSize(value) == expected

        where:
        bytes                                          || expected
        [0]                                            || 1
        [0xB]                                          || 1
        [0xA, 0]                                       || 2
        [0xA, 0xB]                                     || 2
        [0xF, 0xE, 0, 0, 0, 0, 0, 0]                   || 8
        [0xF, 0xE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] || 14

    }

    def "nibbles from the handle can be read"(long v, byte index, int expected) {

        expect:
        HandleFactory.getNibbleAt(v, index) == expected

        where:
        v                  | index || expected
        0x00000000         | 0     || 0x00
        0x000000AB         | 0     || 0x0B
        0x000000AB         | 1     || 0x0A
        0x0000AB00         | 2     || 0x0B
        0x0000AB00         | 3     || 0x0A
        0xFE000000         | 6     || 0x0E
        0xFE000000         | 7     || 0x0F
        0xFE00000000000000 | 14    || 0x0E

    }

    def "nibble >15 is unavailable bc. I am to lazy"() {

        when:
        HandleFactory.getNibbleAt(0, 16)

        then:
        thrown IllegalArgumentException
    }


    def "handle: to and from handle values works"(bytes) {
        given:
        def value = HandleFactory.calculateHandleValue(bytes as byte[])

        expect:
        HandleFactory.bucketValues(value) == bytes

        where:
        bytes << [
                [0],
                [0xB],
                [0xA, 0],
                [0xA, 0xB],
                [0xF, 0xE, 0, 0, 0, 0, 0, 0],
                [0xE, 0xD, 0xC, 0xB, 0xA, 9, 8, 7, 6, 5, 4, 3, 2, 1]]
    }

}
