package name.neuhalfen.projects.android.robohash.handle;


public class HandleFactory {

    public Handle calculateHandle(byte[] data) {
        return new Handle(calculateHandleValue(data));
    }

    public Handle unpack(long value) {
        return new Handle(value);
    }

    static long calculateHandleValue(byte[] data) {
        if (data.length > 16) throw new IllegalArgumentException();
        long val = 0;
        for (int i = 0; i < data.length ; i++) {

            int nibble = data[i];
            if (nibble > 0xf)
                throw new IllegalArgumentException(String.format("nibble to large @%d: %02X", i, nibble));
            val <<= 4;
            val |= nibble;
        }
        return val;
    }

    /**
     * @param index 0..14  -- no support for nibble 15
     * @return A value 0..15
     */
    static int getNibbleAt(long value, int index) {

        if (index < 0 || index > 14) throw new IllegalArgumentException(String.format("index @%d", index));

        long mask = (long) 0xf << index * 4;
        long maskedValue = (value & mask);
        int nibbleValue = (int) (maskedValue >> index * 4);

        return nibbleValue;
    }

}
