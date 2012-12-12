package org.hamisto.filmista;

public class SingleEpisode{
       String episodeNumber;
       String episodeName;
       String first_Aired;
       String filename; 
	public SingleEpisode(){
		
	}
	public String getEpisodeName() {
		return episodeName;
	}
	public void setEpisodeName(String episodeName) {
		this.episodeName = episodeName;
	}
	public String getEpisodeNumber() {
		return episodeNumber;
	}
	public void setEpisodeNumber(String episodeNumber) {
		this.episodeNumber = episodeNumber;
	}
	public String getFirst_Aired() {
		return first_Aired;
	}
	public void setFirst_Aired(String first_Aired) {
		this.first_Aired = first_Aired;
	}

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}