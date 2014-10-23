package cn.edu.sjtu.jicapstone.bloodpressure;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * This class is the main activity.
 * It extends from Activity class.
 * @author Shaoxiang Su
 *
 */
public class MainActivity extends Activity {
	
	private static String TAG = "MainActivity";

	private static int REQUEST_ENABLE_BT = 2;
	
	private View measureView;
	private View historyView;
	private View loginView;
	private View cloudView;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		measureView = findViewById(R.id.measureLayout);
		measureView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				BluetoothAdapter mAdapter = null;
				mAdapter = BluetoothAdapter.getDefaultAdapter();
				if (mAdapter == null) {
					// notify the user that there is no Bluetooth and exit the program
					Dialog dialog = new AlertDialog.Builder(MainActivity.this)
										.setTitle("设备不兼容")
										.setMessage("没有找到蓝牙设备")
										.setPositiveButton("OK", new DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface dialog, int which) {
												MainActivity.this.finish();
											}
										})
					.create();
				}
				
				if (!mAdapter.isEnabled()) {
					Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
					startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
				} else {
					Intent intent = new Intent();
					intent.setClass(arg0.getContext(), MeasureActivity.class);
					MainActivity.this.startActivity(intent);
				}
			}
			
		});

		
		historyView = findViewById(R.id.recordLayout);
		historyView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(v.getContext(), ChartActivity.class);
				MainActivity.this.startActivity(intent);
				
			}
		});
		
		loginView = findViewById(R.id.loginLayout);
		cloudView = findViewById(R.id.cloudLayout);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
