package net.yoojia.ocp.lib.support;

import android.database.Cursor;
import android.os.AsyncTask;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-08
 * 异步查询
 */
public abstract class AsynchronousQueryTask extends AsyncTask<Void, Void, Cursor> {

	protected Cursor usingCursor;
	protected CursorQueryDelegate delegate;

	public AsynchronousQueryTask (Cursor usingCursor,CursorQueryDelegate delegate) {
		this.delegate = delegate;
		this.usingCursor = usingCursor;
	}

	protected Cursor doInBackground(Void... params) {
		return delegate.doQueryInBackground();
	}

	protected void onPostExecute(Cursor newCursor) {
		changeCursor(newCursor);
		if (usingCursor != null) {
			usingCursor.close();
		}
		usingCursor = newCursor;
	}

	protected abstract void changeCursor(Cursor newCursor);
}
