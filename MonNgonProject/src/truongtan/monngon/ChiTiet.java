package truongtan.monngon;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ChiTiet extends FragmentActivity {
	GoogleMap gmap;
	ArrayList<MonAnChiTiet> listChiTiets = new ArrayList<MonAnChiTiet>();
	public SQLiteDatabase db;
	int idView;
	TextView tvTenChiTiet, tvNguyenLieu, tvCachLam;
	ImageView imgMonAn;

	public ImageLoader imvLoader;
	DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chi_tiet);
		// Show the Up button in the action bar.
		// setupActionBar();
		setTitle("Chi tiết món ăn");
		gmap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		gmap.setMyLocationEnabled(true);
		tvTenChiTiet = (TextView) findViewById(R.id.tvTenChiTiet);
		tvNguyenLieu = (TextView) findViewById(R.id.tvNguyenLieu);
		tvCachLam = (TextView) findViewById(R.id.tvCachLam);
		imgMonAn = (ImageView) findViewById(R.id.imgMonAn);
		loadTabs();
		loadDuLieu();

		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.ic_launcher)
				.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
				.cacheOnDisc(true).displayer(new RoundedBitmapDisplayer(20))
				.build();
		imvLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
		Intent itGetView = getIntent();
		idView = itGetView.getExtras().getInt("IDView");
		for (MonAnChiTiet m : listChiTiets) {
			if (idView == m.getID()) {
				tvTenChiTiet.setText(m.tenmonan);
				tvNguyenLieu.setText(m.nguyenlieu);
				tvCachLam.setText(m.congthuc);
				ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub

					}
				};
				imvLoader.displayImage(m.anhmonan, imgMonAn, options,
						imageLoadingListener);
			}

		}

	}

	public void loadTabs() {
		// Lấy Tabhost id ra trước (cái này của built - in android
		final TabHost tab = (TabHost) findViewById(android.R.id.tabhost);
		// gọi lệnh setup
		tab.setup();
		TabHost.TabSpec spec;
		// Tạo tab1
		spec = tab.newTabSpec("t1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Chi tiết");

		tab.addTab(spec);
		// Tạo tab2
		spec = tab.newTabSpec("t2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Vị trí");
		tab.addTab(spec);
		// Thiết lập tab mặc định được chọn ban đầu là tab 0
		tab.setCurrentTab(0);
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
			listChiTiets.add(monan);
		}
		cs.close();
		db.close();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}
}
