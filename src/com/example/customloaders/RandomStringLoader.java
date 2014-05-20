package com.example.customloaders;

import java.util.ArrayList;
import java.util.Random;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class RandomStringLoader extends AsyncTaskLoader<ArrayList<String>> {

	public RandomStringLoader(Context context) {
		super(context);
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
			list.add(s);
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

	
}
