package net.yoojia.ocp.lib.core;

import android.provider.BaseColumns;
import com.j256.ormlite.field.DatabaseField;

public class BaseEntity implements BaseColumns {

    public static final String TABLE_NAME_PFX = "ocp_";
    public static final String MIME_TYPE_PFX = "vnd.ocp.";

    @DatabaseField(columnName = _ID, generatedId = true)
    private final long id;

	public BaseEntity () {
        this.id = -1;
    }

    public BaseEntity (long id) {
        super();
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
