/*
 *	This file is part of Transdroid Torrent Search 
 *	<http://code.google.com/p/transdroid-search/>
 *	
 *	Transdroid Torrent Search is free software: you can redistribute 
 *	it and/or modify it under the terms of the GNU Lesser General 
 *	Public License as published by the Free Software Foundation, 
 *	either version 3 of the License, or (at your option) any later 
 *	version.
 *	
 *	Transdroid Torrent Search is distributed in the hope that it will 
 *	be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *	warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 *	See the GNU Lesser General Public License for more details.
 *	
 *	You should have received a copy of the GNU Lesser General Public 
 *	License along with Transdroid.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.transdroid.search.test;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class TestListActivity extends ListActivity {
	
	// The ContentProvider colums and mapping to the list view items
	//String[] all = new String[] { "NAME", "TORRENTURL", "DETAILSURL", "SIZE", "ADDED", "SEEDERS", "LEECHERS" };
    private final String[] from = new String[] { "NAME", "SIZE", "ADDED", "LEECHERS", "SEEDERS" };
    private final int[] to = new int[] { R.id.result_title, R.id.result_size, R.id.result_date, R.id.result_leechers, R.id.result_seeds};
	
	private TextView emptyText;
	private EditText queryText;
	
	private Button btnInstall, btnSearch;
    
	private Spinner SpinnerSource, SpinnerSort; 
	private ArrayAdapter<CharSequence> AdapterSource, AdapterSort; 
	
	private String[] SortOrder = { "Combined", "BySeeders" };
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        emptyText = (TextView)findViewById(android.R.id.empty);
        queryText = (EditText)findViewById(R.id.query);
        
        btnInstall = (Button)findViewById(R.id.btnTorSearchInst);
        btnSearch = (Button)findViewById(R.id.querygo);
        
        SpinnerSource = (Spinner) findViewById(R.id.srcSpinner); 
        SpinnerSort = (Spinner) findViewById(R.id.sortSpinner); 

        AdapterSource = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item); 
        AdapterSource.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        SpinnerSource.setAdapter(AdapterSource); 
        
        AdapterSort = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item); 
        AdapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        SpinnerSort.setAdapter(AdapterSort); 

        
        for (int i = 0; i < SortOrder.length; i++) { 
        	AdapterSort.add(SortOrder[i]); 
        } 
        
        // Gather the supported torrent sites
        StringBuilder s = new StringBuilder();
        Cursor sites = getSupportedSites();
        if (sites != null) {
	        if (sites.moveToFirst()) {
		        do {
		        	s.append(sites.getString(1));
		        	s.append("\n");
		        	AdapterSource.add(sites.getString(1));
		        } while (sites.moveToNext());
	        }
	        emptyText.setText("Available sites:\n" + s.toString());
	        btnInstall.setVisibility(Button.GONE);
	        
	        // Attach button click handler
	        btnSearch.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					new TorrentSearchTask().execute(queryText.getText().toString());
				}
			});
        } else {
        	queryText.setVisibility(TextView.GONE);
        	btnSearch.setVisibility(Button.GONE);
        	SpinnerSort.setVisibility(Spinner.GONE);
        	SpinnerSource.setVisibility(Spinner.GONE);
        	emptyText.setText("Search provider not installed, click the button bellow to install it.\n\n Note: You will have to restart synodroid app to enable search after installing search provider...");
        	btnInstall.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					Intent goToMarket = null;
					goToMarket = new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=org.transdroid.search"));
					startActivity(goToMarket);
				}
			});
        }
    }
    
    private Cursor getSupportedSites() {
    	// Create the URI of the TorrentSitesProvider
    	String uriString = "content://org.transdroid.search.torrentsitesprovider/sites";
        Uri uri = Uri.parse(uriString);
        // Then query all torrent sites (no selection nor projection nor sort):
        return managedQuery(uri, null, null, null, null);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	// Add torrent to Transdroid via an Intent
    	Cursor c = ((SimpleCursorAdapter) getListAdapter()).getCursor();
    	c.moveToPosition(position);
    	startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(c.getString(c.getColumnIndex("TORRENTURL")))));
    }
    
    private class TorrentSearchTask extends AsyncTask<String, Void, Cursor> {

		@Override
		protected void onPreExecute() {
			emptyText.setText("Seaching for " + queryText.getText().toString());
			setListAdapter(null);
		}
		
		@Override
		protected Cursor doInBackground(String... params) {
			// Create the URI of the TorrentProvider
	        String uriString = "content://org.transdroid.search.torrentsearchprovider/search/"+params[0];
	        Uri uri = Uri.parse(uriString);
	        // Then query for this specific record (no selection nor projection nor sort):
	        return managedQuery(uri, null, "SITE = ?", 
	        		new String[] { SpinnerSource.getSelectedItem().toString() }, 
	        		SpinnerSort.getSelectedItem().toString());
		}
		
		@Override
		protected void onPostExecute(Cursor cur) {

	        if (cur == null) {
	        	emptyText.setText("Cursor is null (ContentProvider not installed?)");
	        }
	        
	        // Show results in the list
	        emptyText.setText("No results found for " + queryText.getText().toString());
	        setListAdapter(new SimpleCursorAdapter(TestListActivity.this, R.layout.search_row, cur, from, to));

		}
		
    }
    
}