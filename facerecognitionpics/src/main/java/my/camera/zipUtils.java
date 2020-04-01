package my.camera;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class zipUtils {
	private static final int BUFFER = 2048; 

	private String[] _files; 
	private String _zipFile; 
    private String _location; 

	public zipUtils () { 

	} 

	public void zip(String inputfile, String zipFile) {
		String [] files = new String[1];
		files[0] = inputfile;

		zip(files, zipFile);
	}
	public void zip(String[] files, String zipFile) { 
		_files = files; 
		_zipFile = zipFile; 
		try  { 
			BufferedInputStream origin = null; 
			FileOutputStream dest = new FileOutputStream(_zipFile); 

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte[] data = new byte[BUFFER];

			for(int i=0; i < _files.length; i++) { 
				//Log.v("Compress", "Adding: " + _files[i]); 
				FileInputStream fi = new FileInputStream(_files[i]); 
				origin = new BufferedInputStream(fi, BUFFER); 
				ZipEntry entry = new ZipEntry(_files[i].substring(_files[i].lastIndexOf("/") + 1)); 
				out.putNextEntry(entry); 
				int count; 
				while ((count = origin.read(data, 0, BUFFER)) != -1) { 
					out.write(data, 0, count); 
				} 
				origin.close(); 
			} 

			out.close(); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		} 

	}

	public static void zip(List<String> files, String zipFile) {
		try  {
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(zipFile);

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));

            byte[] data = new byte[BUFFER];

			for(int i=0; i < files.size(); i++) {
				//Log.v("Compress", "Adding: " + _files[i]);
				FileInputStream fi = new FileInputStream(files.get(i));
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files.get(i).substring(files.get(i).lastIndexOf("/") + 1));
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}

			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}


	public void unzip(String zipFile, String location) {
	    try  { 
	    	
		    _zipFile = zipFile; 
		    _location = location; 
		    
		    _dirChecker(""); 

	    	int count = 0;
	      FileInputStream fin = new FileInputStream(_zipFile); 
	      ZipInputStream zin = new ZipInputStream(fin); 
	      ZipEntry ze = null; 
	      while ((ze = zin.getNextEntry()) != null) { 
	 
	        if(ze.isDirectory()) { 
	          _dirChecker(ze.getName()); 
	        } else { 
	          FileOutputStream fout = new FileOutputStream(_location + ze.getName()); 
	          for (int c = zin.read(); c != -1; c = zin.read()) { 
	            fout.write(c); 
	          } 
	 
	          zin.closeEntry(); 
	          fout.close(); 
	          count++;
	        } 
	         
	      } 
	      zin.close(); 
	      
	    } catch(Exception e) { 
	      //Log.e("Decompress", "unzip", e); 
	    } 
	 
	  } 
	 
	  private void _dirChecker(String dir) { 
	    File f = new File(_location + dir); 
	 
	    if(!f.isDirectory()) { 
	      f.mkdirs(); 
	    } 
	  } 
}
