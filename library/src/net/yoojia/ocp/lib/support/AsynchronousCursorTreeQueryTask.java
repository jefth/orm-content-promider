package net.yoojia.ocp.lib.support;

import android.database.Cursor;
import android.widget.CursorTreeAdapter;

/**
 *
 * @author 桥下一粒砂 (chenyoca@gmail.com)
 * date   2013-4-7
 */
public class AsynchronousCursorTreeQueryTask extends AsynchronousQueryTask {

	private CursorTreeAdapter adapter;

	public AsynchronousCursorTreeQueryTask (CursorTreeAdapter adapter, CursorQueryDelegate delegate) {
		super(adapter.getCursor(),delegate);
		this.adapter = adapter;
	}

	@Override
	protected void changeCursor (Cursor newCursor) {
		adapter.changeCursor(newCursor);
	}
}