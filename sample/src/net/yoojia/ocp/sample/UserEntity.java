package net.yoojia.ocp.sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import net.yoojia.ocp.lib.annotations.DefaultSortOrder;
import net.yoojia.ocp.lib.annotations.MimeType;
import net.yoojia.ocp.lib.annotations.UriPaths;
import net.yoojia.ocp.lib.core.BaseEntity;

/**
 * author : 桥下一粒砂 (chenyoca@gmail.com)
 * date   : 2013-06-14
 * TODO
 */

@DatabaseTable(tableName = BaseEntity.TABLE_NAME_PFX + UserEntity.ENTITY)
@UriPaths({ UserEntity.URI_PERFIX, UserEntity.URI_PERFIX+"/#"})
@MimeType(BaseEntity.MIME_TYPE_PFX + UserEntity.ENTITY)
@DefaultSortOrder(UserEntity.USER_ID + " ASC")
public class UserEntity extends BaseEntity {

	public static final String ENTITY = "user";
	public static final String ENTITY_PL = ENTITY + "s";

	public static final String URI_PERFIX = ENTITY_PL;

	public static final String USER_ID = "user_id";
	public static final String USER_NAME = "user_name";
	public static final String PASSWORD = "password";
	public static final String ADMIN = "admin";
	public static final String REGISTER_TIME = "register_time";
	public static final String WEIGHT = "weight";
	public static final String HEIGHT = "height";

	@DatabaseField(columnName = USER_ID, unique = true, canBeNull = false)
	private int userId;

	@DatabaseField(columnName = USER_NAME, unique = true, canBeNull = false)
	private String userName;

	@DatabaseField(columnName = PASSWORD)
	private String password;

	@DatabaseField(columnName = ADMIN)
	private boolean admin;

	@DatabaseField(columnName = REGISTER_TIME)
	private long registerTime;

	@DatabaseField(columnName = WEIGHT)
	private float weight;

	@DatabaseField(columnName = HEIGHT)
	private double height;


	public UserEntity() {
		super();
		this.userId = 0;
		this.userName = "";
		this.password = null;
		this.admin = false;
		this.registerTime = 0L;
		this.weight = 0;
		this.height = 0;
	}

	public UserEntity (long id) {
		super(id);
	}

	public int getUserId () {
		return userId;
	}

	public void setUserId (int userId) {
		this.userId = userId;
	}

	public String getUserName () {
		return userName;
	}

	public void setUserName (String userName) {
		this.userName = userName;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public boolean isAdmin () {
		return admin;
	}

	public void setAdmin (boolean admin) {
		this.admin = admin;
	}

	public long getRegisterTime () {
		return registerTime;
	}

	public void setRegisterTime (long registerTime) {
		this.registerTime = registerTime;
	}

	public float getWeight () {
		return weight;
	}

	public void setWeight (float weight) {
		this.weight = weight;
	}

	public double getHeight () {
		return height;
	}

	public void setHeight (double height) {
		this.height = height;
	}
}
