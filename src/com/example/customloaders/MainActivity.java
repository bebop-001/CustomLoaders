package com.example.customloaders;

import java.util.ArrayList;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

// ArrayList<String> because that's what the loader returns.
public class MainActivity extends Activity 
	implements LoaderCallbacks<ArrayList<String>> {
	private TextView tv;
	// Loader has 3 steps: 1) create the loader. 2) finished -- return
	// the result and 3) reset the loader.
	@Override
	public Loader<ArrayList<String>> onCreateLoader(int id, Bundle bundle) {
		Log.i("MainActivity.onCreateLoader", "id:" + id);
		RandomStringLoader loader = new RandomStringLoader(this);
		return loader;
	}
	// loader is used to track which loader has completed in case that
	// multiple loaders are executing at the same time.  Then you can use
	// the loader's id to determine which loader has completed and 
	// where the data goes.
	@Override
	public void onLoadFinished(Loader<ArrayList<String>> loader,
			ArrayList<String> data) {
		for (String t : data)
			tv.setText(tv.getText() + t);
		
	}
	@Override
	public void onLoaderReset(Loader<ArrayList<String>> loader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		tv = (TextView)findViewById(R.id.textView);
		// loader id is 99, bundle is empty.
		// these get passed to the onCreateLoader method.
		this.getLoaderManager().initLoader(99, null, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
