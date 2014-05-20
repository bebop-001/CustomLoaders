package com.example.customloaders;

import java.util.ArrayList;
import java.util.Random;

import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class RandomStringLoader extends AsyncTaskLoader<ArrayList<String>> {
	private Receiver receiver;
	public static final String StringLoader_RELOAD =
			"RandomStringLoader.RELOAD";
	public RandomStringLoader(Context context) {
		super(context);
	}
	
	@Override
	protected void onStartLoading() {
		receiver = new Receiver(this);
		// IntentFilter needed to start broadcast receiver.
		IntentFilter filter = new IntentFilter();
		filter.addAction(StringLoader_RELOAD);
		// USE GetContext!  because if change of orientation
		// occurs, the action gets rebuilt.  But if you 
		// have a local copy of the context in the broadcast
		// receiver, the old context can't be gc'ed.
		// (since this is not an action, I don't see how 
		// its this or RandomStringLoader.this would
		// be affected.??  And also, we created the
		// receiver with this so I'd expect it to hang
		// onto the reference too...??)
		getContext().registerReceiver(receiver, filter);
		
		// The loader wouldn't start without this.??
		forceLoad();
		super.onStartLoading();
	}

	@Override
	public ArrayList<String> loadInBackground() {
		// Create maxLen random strings of length between 5 & 15 chars.
		final int maxLen = 20;
		ArrayList<String> list = new ArrayList<String>();
		char[] chars = "*&6987653uhj*&OT(ghjhgi7654854^&%jhg9876754kjhnOIUYOI"
				.toCharArray();
		Random rand = new Random();
		for (int i = 0; i < maxLen; i++){
			String s = "";
			int len = rand.nextInt(15) + 5;
			for (int j = 0; j < len; j++)
				s += chars[rand.nextInt(chars.length)];
			list.add(s + "\n");
		}
		// add sleep pause for demo effect.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deliverResult(ArrayList<String> data) {
		// If we've been canceled, don't return anything. 
		if (isStarted())
			super.deliverResult(data); 
	}

	@Override
	protected void onReset() {
		getContext().unregisterReceiver(receiver);
		this.stopLoading();
		super.onReset();
	}

	// for demo...
	public class Receiver extends BroadcastReceiver {
		private RandomStringLoader loader;
		public Receiver (RandomStringLoader loader) {
			this.loader = loader;
		}

		@Override
		public void onReceive(Context context, Intent intent) {
			loader.onContentChanged();
		}
	}

	
}
