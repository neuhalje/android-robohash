package name.neuhalfen.projects.android.robohash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import name.neuhalfen.projects.android.robohash.handle.Handle;
import name.neuhalfen.projects.android.robohash.handle.HandleFactory;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RoboHashTest {


    public static final long HANDLE_NO_ROBOT_1 = 432345564229149746l;
    public static final long HANDLE_NO_ROBOT_2 = 432345564232372834l;

    @Test
    public void creatingOneRobot_createsTheCorrectRobot() throws IOException {
        RoboHash sut = new RoboHash(InstrumentationRegistry.getTargetContext());

        testEqualForHandleNo(sut, HANDLE_NO_ROBOT_1);
    }

    @Test
    public void creatingOneRobot_createsTheCorrectRobot2() throws IOException {
        RoboHash sut = new RoboHash(InstrumentationRegistry.getTargetContext());

        testEqualForHandleNo(sut, HANDLE_NO_ROBOT_2);
    }

    @Test
    public void creatingOneRobot_afterAnother_createsTheCorrectRobots() throws IOException {
        RoboHash sut = new RoboHash(InstrumentationRegistry.getTargetContext());

        testEqualForHandleNo(sut, HANDLE_NO_ROBOT_1);
        testEqualForHandleNo(sut, HANDLE_NO_ROBOT_2);
    }


    private void testEqualForHandleNo(RoboHash sut ,long handleNo) throws IOException {
        Handle handle = new HandleFactory().unpack(handleNo);

        Bitmap imageForHandle = sut.imageForHandle(handle);

        Bitmap expected = loadFromClasspath(handle);

        assertEquals("same width", expected.getWidth(), imageForHandle.getWidth());
        assertEquals("same height", expected.getHeight(), imageForHandle.getHeight());
        assertEquals("same byteCount", expected.getByteCount(), imageForHandle.getByteCount());

        assertTrue("pixels are equal", pixelsAreEqual(expected,imageForHandle));
        assertTrue(imageForHandle.sameAs(expected));
    }



    private Bitmap loadFromClasspath(Handle handle) throws IOException {
        InputStream is = null;
        try {
            String resName = "" + handle.pack() + ".png";
            is = InstrumentationRegistry.getTargetContext().getResources().getAssets().open(resName);

            if (null == is) {
                throw new RuntimeException("could not load from resource: " + resName );
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (null == bitmap) {
                throw new RuntimeException("could not decodeBitmap from resource: " + resName );
            }
            return bitmap;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static boolean pixelsAreEqual(Bitmap b1, Bitmap b2) {
        if (b1.getWidth() == b2.getWidth() && b1.getHeight() == b2.getHeight()) {
            int[] pixels1 = new int[b1.getWidth() * b1.getHeight()];
            int[] pixels2 = new int[b2.getWidth() * b2.getHeight()];
            b1.getPixels(pixels1, 0, b1.getWidth(), 0, 0, b1.getWidth(), b1.getHeight());
            b2.getPixels(pixels2, 0, b2.getWidth(), 0, 0, b2.getWidth(), b2.getHeight());
            return  (Arrays.equals(pixels1, pixels2));
        } else {
            return false;
        }
    }
}