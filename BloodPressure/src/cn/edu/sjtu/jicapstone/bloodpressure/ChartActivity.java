package cn.edu.sjtu.jicapstone.bloodpressure;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity class is to display the chart.
 * It extends from Activity class
 * @author Shaoxiang Su
 *
 */
public class ChartActivity extends Activity {
	
	private static String TAG = "ChartActivity";
	private TextView startDateView;
	private TextView endDateView;
	private Date startDate;
	private Date endDate;
	private ImageView searchView;
	
	private ImageView measureView;
	private ImageView settingView;
	
	private ListView resultView;
	private ListItemAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
		
		startDateView = (TextView)findViewById(R.id.textView1);
		endDateView = (TextView)findViewById(R.id.textView2);
		searchView = (ImageView)findViewById(R.id.imageView1);
		// for other two buttons
		measureView = (ImageView)findViewById(R.id.imageView3);
		settingView = (ImageView)findViewById(R.id.imageView2);
		
		resultView = (ListView)findViewById(R.id.ListView1);
		
		final Date today = new Date();
		Log.i(TAG, today.getYear() + " " + today.getMonth() + " " + today.getDate());
		final DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		String dateString = df.format(today);
		startDateView.setText(dateString);
		endDateView.setText(dateString);
		startDate = new Date();
		endDate = new Date();
		
		final Vector<UserData> dataVector = DataAccess.getInstance().readData();
		UserDataVectorDecorator decorator = new SingleDecorator(dataVector, today);
		View mChartView = new TimeLineChart(decorator).execute(this);
		LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout3);
		layout.removeAllViews();
		layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		resultView.setClickable(false);
		
		// set up the listview
		adapter = new ListItemAdapter(ChartActivity.this, decorator.getDates(), decorator.getValueLists().get(0), 
				decorator.getValueLists().get(1), decorator.getRateList(), "HH:mm");
		resultView.setAdapter(adapter);
		
		startDateView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "click on startDateView");
				new DatePickerDialog(ChartActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								Log.i(TAG, "setup " + year + " " + monthOfYear + " " + dayOfMonth);
								startDate = new Date(year-1900, monthOfYear, dayOfMonth);
								startDateView.setText(df.format(startDate));
							}
						}, today.getYear() + 1900, today.getMonth(), today.getDate()).show();
			}
		});
		
		endDateView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "click on endDateView");
				new DatePickerDialog(ChartActivity.this, 
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								endDate = new Date(year - 1900, monthOfYear, dayOfMonth, 23, 59, 59);
								endDateView.setText(df.format(endDate));
							}
						}, today.getYear() + 1900, today.getMonth(), today.getDate()).show();
			}
		});
		
		searchView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!endDate.after(startDate)) {
					Toast.makeText(ChartActivity.this, "�������ô���", Toast.LENGTH_SHORT).show();
				} else {
					
					
					UserDataVectorDecorator decorator = new RangeDecorator(dataVector, startDate, endDate);
					View mChartView = new TimeLineChart(decorator).execute(ChartActivity.this);
					LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout3);
					layout.removeAllViews();
					layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
					
					adapter = new ListItemAdapter(ChartActivity.this, decorator.getDates(), decorator.getValueLists().get(0), 
							decorator.getValueLists().get(1), decorator.getRateList(), "yyyy-MM-dd");
					resultView.setAdapter(adapter);
					resultView.setClickable(true);
					
					final List<Date> tempDateList = decorator.getDates(); 
					resultView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							UserDataVectorDecorator tempDecorator = new SingleDecorator(dataVector, tempDateList.get(position));
							View mChartView = new TimeLineChart(tempDecorator).execute(ChartActivity.this);
							LinearLayout layout = (LinearLayout)findViewById(R.id.LinearLayout3);
							layout.removeAllViews();
							layout.addView(mChartView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
							
							// set up the listview
							adapter = new ListItemAdapter(ChartActivity.this, tempDecorator.getDates(), tempDecorator.getValueLists().get(0), 
									tempDecorator.getValueLists().get(1), tempDecorator.getRateList(), "HH:mm");
							resultView.setAdapter(adapter);
							resultView.setOnItemClickListener(null);
							Log.i(TAG, "resultView can be clicked? " + resultView.isClickable());
						}
						
					});
					
				}
			}			
		});
		
		measureView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(v.getContext(), MeasureActivity.class);
				ChartActivity.this.startActivity(intent);
			}
			
		});
		
	}
}
