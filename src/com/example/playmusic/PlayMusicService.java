package com.example.playmusic;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
		// Display notification in the notification bar
		createNotification();
				
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
		// Remove notification from the Notification bar.
		mNotificationMgr.cancel(R.string.hello_world);
				
		// Stop playing music
		mPlayer.stop();
				
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
	}
	
	private NotificationManager mNotificationMgr;
	private void createNotification()
	{
		// Prepare intent which is triggered if the notification is clicked.
		Intent intent = new Intent(this, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0 /* requestCode */, intent,
				Notification.FLAG_ONGOING_EVENT);

		// Build notification
		Notification notification = new Notification.Builder(this)
				.setContentTitle("PlayMusicService")
				.setContentText("Tap to go to application & stop the service.")
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(contentIntent)
				.build();
		
		notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;

		// Display the notification
		mNotificationMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNotificationMgr.notify(R.string.hello_world, notification);
	}

}