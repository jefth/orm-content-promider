package net.yoojia.ocp.lib;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.j256.ormlite.android.AndroidDatabaseResults;
import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import net.yoojia.ocp.lib.core.BaseEntity;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-14
 * TODO
 */
public class ContentDAO<E extends BaseEntity> {

	private final Uri groupUri;
	private final Uri itemUri;
	private final Class<E> clazz;
	private final ContentResolver contentResolver;
	private final DatabaseHelper databaseHelper;

	public ContentDAO(ContentResolver cr,String authority,String uriAuthority,Class<E> clazz){
		this.groupUri = UriUtil.getUri(authority,uriAuthority);
		this.itemUri = UriUtil.getUri(authority,uriAuthority+"/#");
		this.contentResolver = cr;
		this.databaseHelper = DatabaseHelper.getDatabaseHelper();
		this.clazz = clazz;
	}

	/**
	 * 插入一条数据
	 * @param item 数据对象
	 * @return 返回插入的最新行ID。
	 */
	public long insert(E item){
		return databaseHelper.create(item, clazz);
	}

	/**
	 * 插入或者更新一条数据
	 * @param item 数据对象
	 * @return 返回插入的最新行ID。如果发生异常，返回-1。
	 */
	public long insertOrUpdate(E item){
		try {
			int rows = databaseHelper.getDaoEx(clazz).createOrUpdate(item).getNumLinesChanged();
			if(rows>0){
				notifyItemUri(-1);
			}
			return rows;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * 批量插入数据
	 * @param dataSet 数据集
	 * @param replaceIfExists 如果数据存在，是否替换
	 * @return 影响的行数
	 */
	public long bulkInsert(final Collection<E> dataSet,final boolean replaceIfExists){
		final Dao<E,Long> dao = databaseHelper.getDaoEx(clazz);
		final AtomicInteger effectRows = new AtomicInteger(0);
		try{
			dao.callBatchTasks(new Callable<Void>(){
				@Override
				public Void call () throws Exception {
					// 分开两个循环，不要那么多判断
					if(replaceIfExists){
						for(E data : dataSet){
							dao.createOrUpdate(data);
							effectRows.incrementAndGet();
						}
					}else{
						for(E data : dataSet){
							dao.create(data);
							effectRows.incrementAndGet();
						}
					}
					return null;
				}
			});
		}catch (Exception ex){
			ex.printStackTrace();
		}
		int rows = effectRows.get();
		if(rows > 0){
			notifyGroupUri();
		}
		return rows;
	}

	/**
	 * 批量插入数据，如果某条数据存在，则更新此数据。
	 * @param dataSet 数据集
	 * @return 影响行数
	 */
	public long bulkInsert(Collection<E> dataSet){
		return bulkInsert(dataSet,true);
	}

	public void delete(E data){
		deleteById(data.getId());
	}

	public void deleteById(long id){
		databaseHelper.deleteById(id,clazz);
	}

	public void update(E data){
		databaseHelper.update(data,clazz);
	}

	/**
	 * 查询所有数据
	 * @return 数据库游标对象
	 */
	public Cursor queryAll(){
		return contentResolver.query(groupUri,null,null,null,null);
	}

	public Cursor query(QueryBuilder<E,Long> builder) throws SQLException {
		final Dao<E,Long> dao = databaseHelper.getDaoEx(clazz);
		CloseableIterator<E> iterator = dao.iterator(builder.prepare());
		try {
			AndroidDatabaseResults results = (AndroidDatabaseResults)iterator.getRawResults();
			return results.getRawCursor();
		} finally {
			iterator.close();
		}
	}

	private void notifyItemUri(int id){
		Uri uri = itemUri;
		if(-1 != id){
			uri = UriUtil.getItemUri(uri,id);
		}
		contentResolver.notifyChange(uri, null);
	}

	private void notifyGroupUri(){
		contentResolver.notifyChange(groupUri, null);
	}

}
