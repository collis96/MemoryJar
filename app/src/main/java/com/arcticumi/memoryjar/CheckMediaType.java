package com.arcticumi.memoryjar;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public class CheckMediaType {

    public static String check(Context context, Uri uriPath){
        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        String type = mime.getExtensionFromMimeType(cR.getType(uriPath));
        return type;
    }

}
