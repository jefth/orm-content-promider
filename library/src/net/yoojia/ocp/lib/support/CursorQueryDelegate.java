package net.yoojia.ocp.lib.support;

import android.database.Cursor;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-08
 * 查询代理
 */
public interface CursorQueryDelegate {

	/**
	 * 在后台查询数据库，并返回Cursor。
	 * @return 查询结果
	 */
	Cursor doQueryInBackground ();
}
