package net.yoojia.ocp.lib;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-14
 * TODO
 */
public class UriUtil {

	public static Uri getUri(String authority,String uriPath) {
		return Uri.parse(ContentResolver.SCHEME_CONTENT + "://"+ authority + "/" + uriPath);
	}

	public static Uri getItemUri(Uri itemUri,long id) {
		return ContentUris.withAppendedId(itemUri, id);
	}

}
