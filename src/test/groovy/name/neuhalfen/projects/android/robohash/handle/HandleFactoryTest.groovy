package name.neuhalfen.projects.android.robohash.handle

import spock.lang.*


class HandleFactoryTest extends spock.lang.Specification {
    def "CalculateHandleValue"(bytes, long expected) {
        expect:
        HandleFactory.calculateHandleValue(bytes as byte[]) == expected

        where:
        bytes                                                || expected
        [0]                                                  || 0x00
        [0xB]                                                || 0x0B
        [0xA, 0]                                             || 0xA0
        [0xA, 0xB]                                           || 0xAB
        [0xF, 0xE, 0, 0, 0, 0, 0, 0]                         || 0xFE000000
        [0xF, 0xE, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] || 0xFE00000000000000

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

    def "nibble #15 is unavailable bc. I am to lazy"() {

        when:
        HandleFactory.getNibbleAt(0, 15)

        then:
        thrown IllegalArgumentException
    }

}
