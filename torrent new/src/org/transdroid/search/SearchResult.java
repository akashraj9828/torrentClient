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
package org.transdroid.search;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

//import com.sun.jndi.toolkit.url.Uri;

/**
 * Represents a search result form the torent site.
 * 
 * @author Eric Kok
 *
 */
public class SearchResult {

	final private String title;
	final private String torrentUrl;
	final private String detailsUrl;
	final private String size;
	final private Date added;
	final private int seeds;
	final private int leechers;

	public String getTitle() { return title; }
	public String getTorrentUrl() { return torrentUrl; }
	public String getDetailsUrl() { return detailsUrl; }
	public String getSize() { return size; }
	public Date getAddedDate() { return added; }
	public int getSeeds() { return seeds; }
	public int getLeechers() { return leechers; }
	
	public SearchResult(String title, String torrentUrl, String detailsUrl, String size, Date added, int seeds, int leechers) {
		this.title = title;
		this.torrentUrl = torrentUrl;
		this.detailsUrl = detailsUrl;
		this.size = size;
		this.added = added;
		this.seeds = seeds;
		this.leechers = leechers;
	}

	public URL getDetailsUri() {
		try {
			return new URL(getDetailsUrl());
		} catch (MalformedURLException e) {
			InternalError err = new InternalError();
			err.initCause(e);
			throw err;
		}
	}

	public URL getTorrentUri() {
		try {
			return new URL(getTorrentUrl());
		} catch (MalformedURLException e) {
			InternalError err = new InternalError();
			err.initCause(e);
			throw err;
		}
	}
	
	@Override
	public String toString() {
		return title;
	}
	
}
