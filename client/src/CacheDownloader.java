import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.net.URL;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.Enumeration;

import sign.signlink;

public class CacheDownloader {

	private client client;

	private final int BUFFER = 1024;

	private final int VERSION = 2; // Version of cache
	private String cacheLink = "http://dragonicpk.com/cache//cache.zip"; // Link to cache
	
	private String fileToExtract = getCacheDir() + getArchivedName();
	public static final String VERSION_URL = "http://foxtrot-pk.com/bin/version.txt";
	public static final String VERSION_FILE = signlink.findcachedir() + "version.dat";

	
	public CacheDownloader(client client) {
		this.client = client;
	}

	private void drawLoadingText(String text) {
		client.drawLoadingText(35, text);
		System.out.println(text);
	}


	private void drawLoadingText(int amount, String text) {
		client.drawLoadingText(amount, text);
		System.out.println(text);
	}

	private String getCacheDir() {
		return signlink.findcachedir();
	}

	private String getCacheLink() {
		return cacheLink;
	}
	
	public static void main(String[] args) {
		new CacheDownloader(null).downloadCache();
	}

	public CacheDownloader downloadCache() {
		try {
			File cacheLocation = new File(getCacheDir());
			
			double current = getCurrentVersion();
			double newVers = getNewestVersion();
			
			if (cacheLocation.exists()) {
				if (current != newVers) {
					
					for (File file : cacheLocation.listFiles()) {
						file.delete();
					}
					System.out.println("Deleted old files...");
					downloadFile(getCacheLink(), getArchivedName());
					unZip();
					writeNewVersion(Double.toString(newVers));
				} else {
					System.out.println("No updated needed.");
				}
			} else {
				cacheLocation.mkdir();
				downloadFile(getCacheLink(), getArchivedName());
				unZip();
				writeNewVersion(Double.toString(newVers));
			}
			
			if (current != newVers) {
				writeNewVersion(Double.toString(newVers));
			}
			
			System.out.println(current + " - " + newVers);
			
			//File location = new File(getCacheDir());
			//File version = new File(getCacheDir() + "/cacheVersion" + getCacheVersion() + ".dat");
			
			/*if(!location.exists()) {
				//startDownload();
			} else {
				//if (version.exists())
				//	return null;
				//startDownload();
			}*/
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static long bytesWritten = 0;
	private static int kbPerSec = 0;
	
	private void downloadFile(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn;
		InputStream in = null;

		try {
			out = new BufferedOutputStream(new FileOutputStream(getCacheDir() + "/" +localFileName)); 

			conn = new URL(address).openConnection();
			in = conn.getInputStream(); 

			byte[] data = new byte[BUFFER]; 

			int numRead;
			long numWritten = 0;
			int length = conn.getContentLength();
			int lastPercent = 0;
			
			final double NANOS_PER_SECOND = 1000000000.0;
			final double BYTES_PER_MIB = 1024 * 1024;

			long start = System.nanoTime();
			
			while((numRead = in.read(data)) != -1) {
				out.write(data, 0, numRead);
				numWritten += numRead;
				
				double speed = NANOS_PER_SECOND / BYTES_PER_MIB * numWritten / (System.nanoTime() - start + 1);
				int newSpeed = 0;
				
				int percentage = (int)(((double)numWritten / (double)length) * 100D);
				
				if (percentage > lastPercent) {
					if (speed < 1.0) {
						newSpeed = (int) (speed * 1024);
						drawLoadingText(percentage, "Downloading: " + percentage + "% @ " + newSpeed + " kb/s");
					} else {
						drawLoadingText(percentage, "Downloading: " + percentage + "% @ " + round(speed, 2)+" mb/s");
					}
					lastPercent = percentage;
				}
			}
			drawLoadingText("Finished downloading "+getArchivedName()+"!");
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			} catch (IOException ioe) {
				
			}
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) 
	    	throw new IllegalArgumentException();
	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	private String getArchivedName() {
		int lastSlashIndex = getCacheLink().lastIndexOf('/');
		if (lastSlashIndex >= 0  && lastSlashIndex < getCacheLink().length() -1) { 
			return getCacheLink().substring(lastSlashIndex + 1);
		} else {
			System.err.println("error retreiving archive name.");
		}
		return "";
	}


	private void unZip() {
		try (InputStream in = new BufferedInputStream(new FileInputStream(fileToExtract))){
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry e;
			while((e = zin.getNextEntry()) != null) {
				if(e.isDirectory()) {
					new File(getCacheDir() + e.getName()).mkdir();
               	} else {
					if (e.getName().equals(fileToExtract)) {
						unzip(zin, fileToExtract);
						break;
					}
       				unzip(zin, getCacheDir() + e.getName());
				}
				System.out.println("unzipping2 " + e.getName());
			}
			zin.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void unzip(ZipInputStream zin, String s) throws IOException {
		FileOutputStream out = new FileOutputStream(s);
		byte [] b = new byte[BUFFER];
		int len = 0;
		while ((len = zin.read(b)) != -1) {
			out.write(b,0,len);
		}
		out.close();
	}
	
	public static double getCurrentVersion() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(VERSION_FILE)));
			double version = Double.parseDouble(br.readLine());
			br.close();
			return version;
		} catch (Exception e) {
			return 0.1;
		}
	}

	public static double getNewestVersion() {
		try (InputStream stream = new URL(VERSION_URL).openStream()) {
			BufferedReader br = new BufferedReader(new InputStreamReader(stream));
			return Double.parseDouble(br.readLine());
		} catch (Exception e) {
			return 0.1;
		}
	}
	
	public static void writeNewVersion(String version) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VERSION_FILE))) {
            writer.write(version);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
	
}