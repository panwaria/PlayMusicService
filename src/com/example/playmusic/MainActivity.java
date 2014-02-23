package com.example.playmusic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private ServiceConnection mConnection = new ServiceConnection()
	{
		public void onServiceConnected(ComponentName className, IBinder binder)
		{
			Toast.makeText(MainActivity.this, "Connected to the service", Toast.LENGTH_SHORT)
					.show();
		}

		public void onServiceDisconnected(ComponentName className)
		{
			Toast.makeText(MainActivity.this, "Disconnected from the service", Toast.LENGTH_SHORT)
					.show();
		}
	};

	private boolean mIsBound = false;
	
	public void onButtonClick(View v)
	{
		switch (v.getId())
		{
		case R.id.button_start:

			doBindService();
			startService(new Intent(MainActivity.this, PlayMusicService.class));

			break;

		case R.id.button_stop:

			doUnbindService();
			stopService(new Intent(MainActivity.this, PlayMusicService.class));

			break;

		default:
		}
	}

	void doUnbindService()
	{
		if (mIsBound)
		{
			unbindService(mConnection);
			mIsBound = false;

			Toast.makeText(this, "Unbinding Service", Toast.LENGTH_SHORT).show();
		}
	}

	void doBindService()
	{
		mIsBound = true;
		bindService(new Intent(this, PlayMusicService.class), mConnection, Context.BIND_AUTO_CREATE);
	}

}
