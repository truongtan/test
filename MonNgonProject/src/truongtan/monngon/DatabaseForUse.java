package truongtan.monngon;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseForUse extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	//databse name
	private static final String DATABASE_NAME = "database";
	private static String dbName;
	private static String path;
	//creat table CHITIETMONAN
	private static final String TABE_MONANCHITIET = "MONANCHITIET";
	private static final String ID = "ID";
	private static final String TENMON = "TENMON";
	private static final String IMAGE = "IMAGE";
	private static final String NGUYENLIEU = "NGUYENLIEU";
	private static final String CONGTHUC = "CONGTHUC";
	private static final String MAKIEU = "MAKIEU";
	private static final String VIDO = "VIDO";
	private static final String KINHDO = "KINHDO";
	private static final String SEARCH = "SEARCH";
	private static final String VUNGMIEN = "VUNGMIEN";
	public DatabaseForUse(Context context) {
		super(context, getDbName(context), null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	public static void setStorage(String appPackage) {
		path = "/data/data/" + appPackage + "/databases/";
	}
	public static String getDbName(Context context) {

		// If no database path, generate from application package
		if (path == null) {
			Package pack = context.getClass().getPackage();
			String appPackage = pack.getName();
			setStorage(appPackage);
		}

		dbName = path + DATABASE_NAME + ".db";
		return dbName;
	}
	public ArrayList<MonAnChiTiet> getAllFood(){
		ArrayList<MonAnChiTiet> list = new ArrayList<MonAnChiTiet>();
		String selectQuerry = "SELECT * FROM " + TABE_MONANCHITIET ;
		return list;
		
	}
	

}
