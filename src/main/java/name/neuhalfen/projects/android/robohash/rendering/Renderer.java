package name.neuhalfen.projects.android.robohash.rendering;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

// TODO:test
public class Renderer {

    /**
     * all facets have the same size
     *
     * @param facets
     * @return merged bitmap
     */
    public Bitmap merge(Bitmap[] facets) {
        Bitmap mergedbitmap = facets[0].copy(Bitmap.Config.ARGB_8888, true);
        Canvas merged = new Canvas(mergedbitmap);

        Paint paint = new Paint();
        for (int i = 1; i < facets.length; i++) {
            merged.drawBitmap(facets[i], 0, 0, paint);
        }

        return mergedbitmap;
    }
}
