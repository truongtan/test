package truongtan.monngon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity {
	public String[] arrTenDanhMuc_Left = { "Tất cả", "Bánh", "Canh", "Chè",
			"Chiên", "Hấp", "Kho", "Nướng", "Sinh tố và đồ uống", "Xào" };
	public int[] arrAnhDanhMuc_Left = { R.drawable.tatca, R.drawable.banh,
			R.drawable.canh, R.drawable.che, R.drawable.chien, R.drawable.hap,
			R.drawable.kho, R.drawable.nuong, R.drawable.sinhto, R.drawable.xao };
	public String[] arrTenDanhMuc_Right = { "Tatca","Mien Bac", "Mien Trung",
			"Mien Nam" };
	public int[] arrAnhDanhMuc_Right = { R.drawable.tatca,R.drawable.mienbac,
			R.drawable.mientrung, R.drawable.miennam };
	private SearchView mSearchView;
	ListView lvDanhMuc_Left, lvDanhMuc_Right, lvLietKe, lv1;
	AdapterDanhMuc adapterDanhMuc_Left = null, adapterDanhMuc_Right = null;

	public ArrayList<MonAnChiTiet> listMonAn = new ArrayList<MonAnChiTiet>();
	public SQLiteDatabase db;
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.slidingdanhmuc);

		getSupportActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#B3C833")));
		SlidingMenu menu = new SlidingMenu(this);
		menu = getSlidingMenu();
		menu.setMode(SlidingMenu.LEFT_RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffset(200);
		menu.setFadeDegree(0.35f);
		menu.setSecondaryMenu(R.layout.rightmenu);
		lvLietKe = (ListView) findViewById(R.id.lvLietKe);
		lvDanhMuc_Left = (ListView) findViewById(R.id.lvDanhMuc_Left);
		lvDanhMuc_Right = (ListView) findViewById(R.id.lvDanhMuc_Right);

		adapterDanhMuc_Left = new AdapterDanhMuc(this,
				R.layout.layout_list_danhmuc, arrTenDanhMuc_Left,
				arrAnhDanhMuc_Left);
		lvDanhMuc_Left.setAdapter(adapterDanhMuc_Left);

		adapterDanhMuc_Right = new AdapterDanhMuc(this, R.layout.rightmenu,
				arrTenDanhMuc_Right, arrAnhDanhMuc_Right);

		lvDanhMuc_Right.setAdapter(adapterDanhMuc_Right);
		lvDanhMuc_Left.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position) {
				case 0:
					listMonAn.removeAll(listMonAn);
					loadDuLieu();
					toggle();
					break;
				case 1:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'BA'");
					toggle();
					break;
				case 2:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'CA'");
					toggle();
					break;
				case 3:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'CH'");
					toggle();
					break;
				case 4:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'CHI'");
					toggle();
					break;
				case 5:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'HA'");
					toggle();
					break;
				case 6:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'KH'");
					toggle();
					break;
				case 7:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'NU'");
					toggle();
					break;
				case 8:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'ST'");
					toggle();
					break;
				case 9:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoCachNau("'XA'");
					toggle();
					break;
				}

			}
		});
		checkAndCreateDatabase();
	
		lvDanhMuc_Right.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				switch (position) {
				case 0:
					listMonAn.removeAll(listMonAn);
					loadDuLieu();
					toggle();
					break;
				case 1:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoVungMien("'BAC'");
					toggle();
					break;
				case 2:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoVungMien("'TRUNG'");
					toggle();
					break;
				case 3:
					listMonAn.removeAll(listMonAn);
					loadDuLieuTheoVungMien("'NAM'");;
					toggle();
					break;
				}

			}
		});
		//Event OnItemClickListener
		lvLietKe.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				MonAnChiTiet maChiTiet = (MonAnChiTiet) parent.getItemAtPosition(position);
				int idMonAn = maChiTiet.getID();
				Intent itMainLV = new Intent(MainActivity.this, ChiTiet.class);
				itMainLV.putExtra("IDView", idMonAn);
				startActivity(itMainLV);
				
			}
		});
	}

	public void loadDuLieu() {
		String pName = this.getClass().getPackage().getName();
		String folder = "/data/data/" + pName + "/databases/";
		String dbPath = folder + "database.db";
		db = SQLiteDatabase.openDatabase(dbPath, null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cs = db
				.query("CHITIETMONAN", null, null, null, null, null, null);
		while (cs.moveToNext()) {
			int id = cs.getInt(cs.getColumnIndexOrThrow("ID"));
			String name = cs.getString(cs.getColumnIndexOrThrow("TENMON"));
			String image = cs.getString(cs.getColumnIndexOrThrow("IMAGE"));
			String nguyenlieu = cs.getString(cs
					.getColumnIndexOrThrow("NGUYENLIEU"));
			String congthuc = cs
					.getString(cs.getColumnIndexOrThrow("CONGTHUC"));
			String makieu = cs.getString(cs.getColumnIndexOrThrow("MAKIEU"));
			double vido = cs.getDouble(cs.getColumnIndexOrThrow("VIDO"));
			double kinhdo = cs.getDouble(cs.getColumnIndexOrThrow("KINHDO"));
			String search = cs.getString(cs.getColumnIndexOrThrow("SEARCH"));
			String vungmien = cs
					.getString(cs.getColumnIndexOrThrow("VUNGMIEN"));
			MonAnChiTiet monan = new MonAnChiTiet(id, name, image, makieu,
					nguyenlieu, congthuc, kinhdo, vido, search, vungmien);
			listMonAn.add(monan);
		}
		cs.close();
		db.close();
		lvLietKe.setAdapter(new AdapterViewMainActivity(listMonAn,
				MainActivity.this));
	}

	public void loadDuLieuTheoCachNau(String dieukien) {
		String pName = this.getClass().getPackage().getName();
		String folder = "/data/data/" + pName + "/databases/";
		String dbPath = folder + "database.db";
		db = SQLiteDatabase.openDatabase(dbPath, null,
				SQLiteDatabase.OPEN_READONLY);
		String sql = "SELECT * FROM CHITIETMONAN WHERE MAKIEU=" + dieukien;
		Cursor cs1 = db.rawQuery(sql, null);
		while (cs1.moveToNext()) {
			int id = cs1.getInt(cs1.getColumnIndexOrThrow("ID"));
			String name = cs1.getString(cs1.getColumnIndexOrThrow("TENMON"));
			String image = cs1.getString(cs1.getColumnIndexOrThrow("IMAGE"));
			String nguyenlieu = cs1.getString(cs1
					.getColumnIndexOrThrow("NGUYENLIEU"));
			String congthuc = cs1.getString(cs1
					.getColumnIndexOrThrow("CONGTHUC"));
			String makieu = cs1.getString(cs1.getColumnIndexOrThrow("MAKIEU"));
			double vido = cs1.getDouble(cs1.getColumnIndexOrThrow("VIDO"));
			double kinhdo = cs1.getDouble(cs1.getColumnIndexOrThrow("KINHDO"));
			String search = cs1.getString(cs1.getColumnIndexOrThrow("SEARCH"));
			String vungmien = cs1.getString(cs1
					.getColumnIndexOrThrow("VUNGMIEN"));
			MonAnChiTiet monan = new MonAnChiTiet(id, name, image, makieu,
					nguyenlieu, congthuc, kinhdo, vido, search, vungmien);
			listMonAn.add(monan);
		}
		cs1.close();
		db.close();
		lvLietKe.setAdapter(new AdapterViewMainActivity(listMonAn,
				MainActivity.this));
		
	}

	public void loadDuLieuTheoVungMien(String dieukien) {
		String pName = this.getClass().getPackage().getName();
		String folder = "/data/data/" + pName + "/databases/";
		String dbPath = folder + "database.db";
		db = SQLiteDatabase.openDatabase(dbPath, null,
				SQLiteDatabase.OPEN_READONLY);
		String sql = "SELECT * FROM CHITIETMONAN WHERE VUNGMIEN=" + dieukien;
		Cursor cs1 = db.rawQuery(sql, null);
		while (cs1.moveToNext()) {
			int id = cs1.getInt(cs1.getColumnIndexOrThrow("ID"));
			String name = cs1.getString(cs1.getColumnIndexOrThrow("TENMON"));
			String image = cs1.getString(cs1.getColumnIndexOrThrow("IMAGE"));
			String nguyenlieu = cs1.getString(cs1
					.getColumnIndexOrThrow("NGUYENLIEU"));
			String congthuc = cs1.getString(cs1
					.getColumnIndexOrThrow("CONGTHUC"));
			String makieu = cs1.getString(cs1.getColumnIndexOrThrow("MAKIEU"));
			double vido = cs1.getDouble(cs1.getColumnIndexOrThrow("VIDO"));
			double kinhdo = cs1.getDouble(cs1.getColumnIndexOrThrow("KINHDO"));
			String search = cs1.getString(cs1.getColumnIndexOrThrow("SEARCH"));
			String vungmien = cs1.getString(cs1
					.getColumnIndexOrThrow("VUNGMIEN"));
			MonAnChiTiet monan = new MonAnChiTiet(id, name, image, makieu,
					nguyenlieu, congthuc, kinhdo, vido, search, vungmien);
			listMonAn.add(monan);
		}
		cs1.close();
		db.close();
		lvLietKe.setAdapter(new AdapterViewMainActivity(listMonAn,
				MainActivity.this));
	}

	private void copyDatabase(Context context) throws IOException {
		String pName = this.getClass().getPackage().getName();
		String folder = "/data/data/" + pName + "/databases/";
		File CheckDirectory;
		CheckDirectory = new File(folder);
		if (!CheckDirectory.exists()) {
			CheckDirectory.mkdir();
		}
		File file = new File(folder, "database.db");
		if (!file.exists()) {
			Log.w("dsads", " not exist");
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else {
			Log.w("dsads", "exist");
			file.delete();
			file.createNewFile();
		}
		OutputStream databaseOutputStream = new FileOutputStream(folder
				+ "database.db");
		InputStream databaseInputStream;

		byte[] buffer = new byte[1024];
		int length;

		databaseInputStream = context.getResources().openRawResource(
				R.raw.database);
		while ((length = databaseInputStream.read(buffer)) > 0) {
			databaseOutputStream.write(buffer);
		}
		databaseInputStream.close();

		databaseInputStream.close();
		databaseOutputStream.flush();
		databaseOutputStream.close();
	}

	public void saveDataBase() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading...");
		progressDialog.show();
		final Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				progressDialog.cancel();
				loadDuLieu();
			}
		};
		new Thread() {
			public void run() {
				try {
					copyDatabase(getApplicationContext());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0);
			}
		}.start();

	}

	public void checkAndCreateDatabase() {
		SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
		boolean firstUse = sharedPreferences.getBoolean("firstUse", true);
		if (firstUse) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putBoolean("firstUse", false);
			editor.commit();
			saveDataBase();
		} else {
			loadDuLieu();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getSupportMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		mSearchView = (SearchView) searchItem.getActionView();

		return true;
	}
	  

}
