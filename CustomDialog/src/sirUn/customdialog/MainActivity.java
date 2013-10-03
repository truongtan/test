package sirUn.customdialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.NoCopySpan.Concrete;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	final Context context = this;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button =(Button)findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialog = new Dialog(context);
				dialog.setContentView(R.layout.customdialog);
				dialog.setTitle("Title...");
				TextView text = (TextView)dialog.findViewById(R.id.tv_View);
				text.setText("Welcome!");
				ImageView imv_View = (ImageView)dialog.findViewById(R.id.imv_View);
				imv_View.setImageResource(R.drawable.ic_launcher);
				Button btn_OK = (Button)dialog.findViewById(R.id.btn_OK);
				btn_OK.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
