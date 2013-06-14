package net.yoojia.ocp.sample;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import net.yoojia.ocp.lib.ContentDAO;
import net.yoojia.ocp.lib.UriUtil;

import java.util.ArrayList;

public class MyActivity extends ListActivity {

	private View buttonAdd;
	private View buttonDel;
	private View buttonUpd;
	private View buttonSel;


	private Uri uri = UriUtil.getUri(MainContentProvider.AUTHORITY,UserEntity.URI_PERFIX);
	private SimpleCursorAdapter simpleCursorAdapter;

	private ContentDAO<UserEntity> dao;

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		View bar = findViewById(R.id.buttons_bar);
		buttonAdd = bar.findViewById(R.id.button_add);
		buttonDel = bar.findViewById(R.id.button_del);
		buttonUpd = bar.findViewById(R.id.button_upd);
		buttonSel = bar.findViewById(R.id.button_sel);

		ContentResolver contentResolver = getContentResolver();
		dao = new ContentDAO<UserEntity>(contentResolver,MainContentProvider.AUTHORITY,UserEntity.URI_PERFIX,UserEntity.class);

		Cursor cursor = dao.queryAll();

		String[] from = {UserEntity.USER_NAME};
		int[] to = {android.R.id.text1};
		simpleCursorAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,cursor,from,to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
		setListAdapter(simpleCursorAdapter);

		contentResolver.registerContentObserver(uri,true,new ContentObserver(new Handler()) {
			@Override
			public void onChange (boolean selfChange) {
				reQuery();
			}
		});

		bindButtons();
	}

	public void bindButtons(){
		buttonAdd.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick (View v) {
				int size = 100;
				ArrayList<UserEntity> users = new ArrayList<UserEntity>(size);
				for(int i=0;i<size;i++){
					UserEntity user = new UserEntity(i);
					user.setUserName("Name - "+i);
					user.setPassword("123123");
					user.setAdmin(false);
					user.setUserId(i);
					user.setRegisterTime(1234567890123L);
					users.add(user);
				}
				dao.bulkInsert(users);
			}
		});

	}

	public void reQuery(){
		Cursor oldCursor = simpleCursorAdapter.getCursor();
		if( oldCursor != null){
			oldCursor.close();
		}
		Cursor cursor = dao.queryAll();
		simpleCursorAdapter.changeCursor(cursor);
	}

}
