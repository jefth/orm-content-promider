package net.yoojia.ocp.sample;

import android.util.Log;
import net.yoojia.ocp.lib.core.AbstractContentProvider;
import net.yoojia.ocp.lib.DatabaseHelper;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-14
 * TODO
 */
public class MainContentProvider extends AbstractContentProvider {

	public static final String AUTHORITY = "net.yoojia.ocp.provider";

	private static final String DATABASE_NAME = "test.db";
	private static final int DATABASE_VERSION = 1;

	@SuppressWarnings("rawtypes")
	private static final Class[] CLASSES = { UserEntity.class };

	@Override
	public boolean onCreate () {
		DatabaseHelper helper = new DatabaseHelper(CLASSES, DATABASE_NAME, DATABASE_VERSION,getContext());
		super.setHelper(helper);
		super.setAuthority(AUTHORITY);
		super.initialize(CLASSES);
		Log.e("INIT", ">>>> 初始化ContentProvider <<<<<");
		return true;
	}
}
