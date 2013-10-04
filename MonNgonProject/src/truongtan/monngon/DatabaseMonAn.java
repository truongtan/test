package truongtan.monngon;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseMonAn extends SQLiteOpenHelper {
	private static final int DATABASE_VERSION = 1;
	public ArrayList<MonAnChiTiet> listMonAn = new ArrayList<MonAnChiTiet>();
	public SQLiteDatabase db;

	public DatabaseMonAn(Context context, String name, CursorFactory factory,
			int version) {
		super(context, "/sdcard/MonAnNgon/database.db", factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}


}
