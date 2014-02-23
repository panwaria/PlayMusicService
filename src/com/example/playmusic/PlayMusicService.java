package com.example.playmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class PlayMusicService extends Service
{
	// An interface object used by clients to communicate with the service.
	private final IBinder mBinder = new MyBinder();
	
	MediaPlayer mPlayer;
	
	@Override
	public void onCreate()
	{
		// Set the Player
		mPlayer = MediaPlayer.create(this, R.raw.song);
		mPlayer.setLooping(false);
				
		Toast.makeText(this, "Service Created", Toast.LENGTH_SHORT).show();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// Start playing music
		mPlayer.start();
		
		Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
		return Service.START_STICKY;	// Service will be explicitly started and stopped as needed.
	}

	@Override
	public IBinder onBind(Intent i)
	{
		Toast.makeText(this, "Binding Service", Toast.LENGTH_SHORT).show();
		return mBinder;
	}

	public class MyBinder extends Binder
	{
		PlayMusicService getService()
		{
			return PlayMusicService.this;
		}
	}

	public void onDestroy()
	{
		// Stop playing music
		mPlayer.stop();
				
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}

}