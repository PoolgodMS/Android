package com.example.batteryinfo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class BatteryInfo extends Activity {
	//private BatteryManager batteryManager;

	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()  {

		@Override
		public void onReceive(Context context, Intent intent) {
			
			int level = intent.getIntExtra("level", 0);
			int health = intent.getIntExtra("health", 0);
			int status = intent.getIntExtra("status", 0);
			float temperature = (float) (intent.getIntExtra("temperature", 0) - 272.15);
			float voltage = (float) (intent.getIntExtra("voltage", 0) / 1000.0);
			String s = String.format("Level %d%%\nHealth %s\nStatus %s\nTemperature %.2fC (%.2fF)\nVoltage %.2fV", 
					level, getHealthString(health), getStatusString(status), 
					temperature, toFahrenheit(temperature), voltage);
			tv.setText(s);
			Log.i("HelloAndroid", "received broadcast " + s);
		}

	};
	
	private String getStatusString(int status)
	{
		String result = "error";
		switch (status)
		{
		case BatteryManager.BATTERY_STATUS_CHARGING:
			result = "Charging";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			result = "Discharging";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			result = "Full";
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			result = "Not charging";
			break;
		case BatteryManager.BATTERY_STATUS_UNKNOWN:
			result = "Unknown";
			break;
		}
		return result;
	}
	
	private String getHealthString(int health)
	{
		String result = "error";
		
		switch (health)
		{
		//COLD is available in API level 11
//		case BatteryManager.BATTERY_HEALTH_COLD:
//			result = "Cold";
//			break;
		case BatteryManager.BATTERY_HEALTH_DEAD:
			result = "Dead";
			break;
		case BatteryManager.BATTERY_HEALTH_GOOD:
			result = "Good";
			break;
		case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
			result = "Over voltage";
			break;
		case BatteryManager.BATTERY_HEALTH_OVERHEAT:
			result = "Overheat";
			break;
		case BatteryManager.BATTERY_HEALTH_UNKNOWN:
			result = "Unknown";
			break;
		case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
			result = "Unspecfied failure";
			break;
		}
		return result;	
	}
	private float toFahrenheit(float celsius)
	{
		return (float) (((celsius * 9.0) / 5.0) + 32.0);
	}

	private TextView tv;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("HelloAndroid", "Setting up TextView");
		tv = new TextView(this);
		tv.setTextSize((float) 20.0);
		tv.setText("Waiting for battery broadcast");
		setContentView(tv/*R.layout.main*/);
		Log.i("HelloAndroid", "Setting up IntentFilter");
		IntentFilter intentFilter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
		registerReceiver(mBroadcastReceiver, intentFilter);
		Log.i("HelloAndroid", "Done onCreate");
	}
}