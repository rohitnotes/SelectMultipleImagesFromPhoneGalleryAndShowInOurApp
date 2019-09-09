package select.multiple.images.from.phone.gallery.and.show.in.our.app;

import android.net.Uri;

public class ImageUriModel {

    private Uri uri=null;

    public ImageUriModel(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
