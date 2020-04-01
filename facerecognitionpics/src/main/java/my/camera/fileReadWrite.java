/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package my.camera;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Sumeendranath
 */
public class fileReadWrite {

	
	//// modes 
	public static int READ = 0;
	public static int WRITE = 1;
	
    private String string_nameoffile;
    
    private int int_filemode = 0; // 0 is for read, 1 is for write

    private boolean bool_reachedEOF;
    
    //RandomAccessFile fc_fconn;
    
    FileInputStream fis;
    UnicodeReader ur;
    BufferedReader fc_fconn;
    
    public static int EOF_LINENO = -2;
    public static int START_LINENO = 0;
    
    int _readAfterLine = START_LINENO;
    
    int _lastLineToBeRead = EOF_LINENO;
    
    int _currentLineNo = 0;
    
    public fileReadWrite(String filename, int mode_read0_write1)
    {
        string_nameoffile = filename;
        int_filemode = mode_read0_write1;
        bool_reachedEOF = false;
        System.out.println("file name " + string_nameoffile + "\n");
    }
    
    public fileReadWrite(String filename, int readAfterLineNo, int lastLineToBeRead, int mode_read0_write1)
    {
        string_nameoffile = filename;
        int_filemode = mode_read0_write1;
        bool_reachedEOF = false;
        _readAfterLine = readAfterLineNo;
        _lastLineToBeRead = lastLineToBeRead;
        System.out.println("file name " + string_nameoffile + "\n");
    }

        
    public boolean openFile() {
        boolean retValue = true;
        try {

        	if(int_filemode == READ)
        	{
        	    fis = new FileInputStream(new File(string_nameoffile));
        	    ur = new UnicodeReader(fis, "UTF-8");
        	    fc_fconn = new BufferedReader(ur);

        		//fc_fconn = new RandomAccessFile(string_nameoffile, "r");
        	}
        	else
        	{
        		//fc_fconn = new RandomAccessFile(string_nameoffile, "w");        		
        	}

            bool_reachedEOF = false;
            skipToLineNo(_readAfterLine);


        } catch (Exception ex2) {
        	retValue = false;

        }
        

         return retValue;
    }


    public void skipToLineNo(int lineNo)
    {
        
    	String retString = "";
    	
    	try {
    		while(_currentLineNo < lineNo)
    		{
    			_currentLineNo++;

    			retString = fc_fconn.readLine(); 
    			if(retString == null)
    			{
    				bool_reachedEOF = true;
    				retString = "";
    				break;
    			}
    		}

    	} catch (Exception ex2) {
    		bool_reachedEOF = true;
    	}
	
    }
    
         
    public String readNextLine()
    {
        String retString = "";

    	
    	
        try {
            
        	_currentLineNo++;
        	
        	retString = fc_fconn.readLine(); 
            //retString = retString.substring(0, retString.length() - 2);
                         //   System.out.println("return string is *" + retString + "*\n");
        	
        	
        	if(retString == null)
        	{
        		bool_reachedEOF = true;
        		retString = "";
        	}

        } catch (Exception ex2) {
            bool_reachedEOF = true;
        }
        
        if((_lastLineToBeRead != EOF_LINENO) && (_currentLineNo >= _lastLineToBeRead))
    	{
    		bool_reachedEOF = true;
    	}

        UnicodeBlock x;
        return retString;

    }
    
    public void writeNextLine(String stringToWrite)
    {
        try {
            
        	//fc_fconn.writeBytes(stringToWrite + "\n");

        } catch (Exception ex2) {
        }
    }
    
    

    public boolean hasReachedEOF()
    {
        return bool_reachedEOF;
    }

    public boolean closeAll()
    {
       
    	try
    	{
    		fis.close();
    		ur.close();
    		fc_fconn.close();
    		return true;
    	}
    	catch(Exception ex)
    	{
    		return false;
    	}
    }
    
    static public boolean removeFile(String filename)
	{
		File filetodel = new File(filename);
		
		return filetodel.delete();
	}
    
    static public String getDirectoryOfFile(String filename)
	{
		File fileHand = new File(filename);
		return fileHand.getParent();
	}
	
	static public boolean fileExists(String filename)
	{
		File filetochk = new File(filename);
        return filetochk.exists();
	}
	
	static public long getFileSize(String filename)
	{
		File filetochk = new File(filename);
		return filetochk.length();
	}
	
	static public boolean isFileEmpty(String filename)
	{
		File filetochk = new File(filename);
        return filetochk.length() <= 0;
    }
	
	static public String getLeafFileName(String filename)
	{
		File filetochk = new File(filename);
		return filetochk.getName();
	}
	
	static public boolean moveFile(String inputFileName, String outputFileName)
	{
		File inputFile = new File(inputFileName);
		File outputFile = new File(outputFileName);

		return inputFile.renameTo(outputFile);
	}
	
	static public boolean copyFile(String inputFileName, String outputFileName)
	{
		File inputFile = new File(inputFileName);
		File outputFile = new File(outputFileName);

		try
		{
			FileReader in = new FileReader(inputFile);
			FileWriter out = new FileWriter(outputFile);
			int c;

			while ((c = in.read()) != -1)
				out.write(c);

			in.close();
			out.close();
		}
		catch (Exception ex)
		{
			return false;
		}
		
		

		return true;
	}

	
	static public boolean removeLinesFromFile(String inputFileName, List<String> stringtoremove)
	{
		File inputFile = new File(inputFileName);
	    File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(inputFile));
			
			BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter(tempFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return false;
			}

			//String lineToRemove = "bbb";
			String currentLine;

			while((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if(stringtoremove.contains(trimmedLine)) continue;
			    writer.write(currentLine + "\n");
			}
			
			reader.close();
			writer.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			
			return false;
		}
		return tempFile.renameTo(inputFile);

		
	}
		static public boolean replaceLineWith(String inputFileName, String stringtoremove, String stringtoreplace)
		{
			File inputFile = new File(inputFileName);
		    File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(inputFile));
				
				BufferedWriter writer;
				try {
					writer = new BufferedWriter(new FileWriter(tempFile));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					return false;
				}

				//String lineToRemove = "bbb";
				String currentLine;

				while((currentLine = reader.readLine()) != null) {
				    // trim newline when comparing with lineToRemove
				    String trimmedLine = currentLine.trim();
				    if(stringtoremove.equals(trimmedLine)) 
				    	{
				    	
				    	writer.write(stringtoreplace + "\n");
				    		continue;
				    	}
				    else
				    {
				    	writer.write(currentLine + "\n");
				    }
				}
				
				reader.close();
				writer.close();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				
				return false;
			}
		
		return tempFile.renameTo(inputFile);
		
		
		}
	
	
		

	/*static public boolean reverseFileContents(String inputFileName, String outputFileName)
	{
		File inputFile = new File(inputFileName);
		try
		{
			FileReader in = new FileReader(inputFile);
			int c;

			StringBuffer sb = new StringBuffer();
			while ((c = in.read()) != -1)
				sb.append(c);

			writeIntoBinaryFile(outputFileName, sb.reverse())
			writeIntoFile(sb.reverse().toString(), outputFileName);
			in.close();
		}
		catch (Exception ex)
		{
			return false;
		}
		
		

		return true;
	}*/
	
	
	public static byte[] readDataFromFile(String filename) {


		byte[] data = new byte[1];


		try {
			RandomAccessFile raf = new RandomAccessFile(filename,"r");
			// Get and check length
			long longlength = raf.length();
			int length = (int) longlength;
			if (length != longlength) throw new IOException("File size >= 2 GB");

			// Read file and return data
			data = new byte[length];
			raf.readFully(data);
			raf.close();

		}
		catch(Exception ex)
		{

		}
		finally {
		}

		return data;
	}
    

		public static String convertStreamToString(InputStream is) throws Exception {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		      sb.append(line).append("\n");
		    }
		    reader.close();
		    return sb.toString();
		}

		public static String readStringFromFile (String filePath)  {
			
			String ret = "";
			
			File fl;
			FileInputStream fin = null;
			try
			{
		     fl = new File(filePath);
		     fin = new FileInputStream(fl);
		     ret = convertStreamToString(fin);
		    //Make sure you close all streams.
			}
			catch(Exception ex)
			{
				
			}
			
			
			try
			{
			    fin.close();        

			}
			catch(Exception ex)
			{
				
			}
		    return ret;
		}
	
	
	
	static public boolean addStringToTextFile(String stringToAdd, String inputFileName)
	{
		try
		{
			FileWriter f = new FileWriter(inputFileName, true);
			f.write(stringToAdd);
			f.flush();
			f.close();
		}
		catch (Exception ex)
		{
			return false;
		}
		
		

		return true;
	}
	
	static public boolean reverseFileContents(String inputFileName, String outputFileName)
	{
		
		byte[] bytesToWrite = readDataFromFile(inputFileName);
		//writeIntoBinaryFile(outputFileName, bytesToWrite);
		
		byte[] inverted = CommonlyUsed.invertByteArray(bytesToWrite);  //new byte[bytesToWrite.length];
		
		
		
		/*for(int i=0 ; i<bytesToWrite.length; i++)
		{
			reversed[i] = bytesToWrite[bytesToWrite.length - i - 1];
			
		}*/
		
		writeIntoBinaryFile(outputFileName, inverted);
		return true;
	}

	
	
	public static boolean writeIntoFile(String strToWrite, String fileName) {

    	
    	FileWriter fw;
		try {
			fw = new FileWriter(fileName,false);
			fw.write(strToWrite + "\n");
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//Toast.makeText(,"writing file failed " + e.getMessage(), Toast.LENGTH_SHORT).show();

			
		}
		
        return false;
    }
	
	
	
    public static boolean writeIntoBinaryFile(String fileName, byte[] bytesToWrite) {
    	
        try {
        	
        	File filehandle = new File(fileName);
        	FileOutputStream fout = new FileOutputStream(fileName, false);
        	if(!filehandle.exists())
        	{
        		//filehandle.createNewFile();
        	}
            try {
                int index = 0;
                int size = 1024;
                do {
                    if ((index + size) > bytesToWrite.length) {
                        size = bytesToWrite.length - index;
                    }
                    fout.write(bytesToWrite, index, size);
                    index += size;
                    fout.flush();
                } while (index < bytesToWrite.length);
            } catch (Exception exf) {
            }
            fout.close();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    
    public static String readLineFromFile(String filename, int lineNo) {
        String retString = "";
        try {
            RandomAccessFile fconn = new RandomAccessFile(filename, "r");
            int linenoRead = 1;
            while((retString = fconn.readLine()) != null)
            {
            	if(linenoRead == lineNo)
            	{
            		break;
            	}
            	linenoRead++;
            }          	
            
            fconn.close();

        } catch (Exception ex2) {
        }
        
        return retString;
    }
    
    public static int getNumberOfLinesInFile(String filename) {
        int retVal = 0;
        try {
            RandomAccessFile fconn = new RandomAccessFile(filename, "r");
            String retString = "";
            while((retString = fconn.readLine()) != null)
            {
            	
            	retVal++;
            }          	
            
            fconn.close();

        } catch (Exception ex2) {
        }
        
        return retVal;
    }
    
    
    public static int getLineNoWhichStartsWith(String filename,String startChars)
    {
    	int retLineNo = 0;
    	String line = "";
    	
         try {
             RandomAccessFile fconn = new RandomAccessFile(filename, "r");
             while(( line = fconn.readLine()) != null)
             {
            	 retLineNo++;
             	if(line.startsWith(startChars))
             	{
                    fconn.close();
             		return retLineNo;
             	}             	
             }          	
             
             fconn.close();

         } catch (Exception ex2) {
         }
         
         return fileReadWrite.START_LINENO;
    }
    
    public static String readFirstValidLineFromFile(String filename) {
        String retString = "";
        try {
            RandomAccessFile fconn = new RandomAccessFile(filename, "r");
            while((retString = fconn.readLine()) != null)
            {
            	if(CommonlyUsed.stringIsNullOrEmpty(retString) || retString.trim().startsWith("#"))
            	{
            		continue;
            	}
            	
            	break;
            }          	
            
            fconn.close();

        } catch (Exception ex2) {
        }
        
        return retString;
    }
    

    
    public static void copyAssetsFileToStorage(Context context, String inputfilename, String outputfilename) {
        try {
        	InputStream is = context.getAssets().open(inputfilename);
            OutputStream out = new FileOutputStream(outputfilename);
            
            byte[] buffer = new byte[1024];
            int read;
            while((read = is.read(buffer)) != -1){
              out.write(buffer, 0, read);
            }

        	 is.close();
        	 out.flush();
             out.close();
             out = null;
             is = null;

        } catch (Exception ex2) {
       	 
        	CommonlyUsed.showMsg(context, "issue-" + ex2.getMessage());

        }
        
    }
    
    
    public static void removeDirectoryComplete(String dirname) 
    {
    	deleteRecursive(new File(dirname));
    }

    
    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    
    static public List<String> getAllDirectoryNamesWithin(String dirname)
    {
		List<String> retList = new ArrayList<>();
    	
    	File dirhandle = new File(dirname);
    	
    	if (dirhandle.isDirectory())
    	{
            for (File child : dirhandle.listFiles())
            {
                if(child.isDirectory())
                {
                	retList.add(child.getName());
                }
            }
    	}

    	return retList;
    }

	static public List<String> getAllFilesWithin(String dirname)
	{
		List<String> retList = new ArrayList<>();

		File dirhandle = new File(dirname);

		if (dirhandle.isDirectory())
		{
			for (File child : dirhandle.listFiles())
			{
				if(child.isFile())
				{
					retList.add(child.getAbsolutePath());
				}
			}
		}

		return retList;
	}
    
    static public List<String> readFileLinesIntoList(String filename)
    {
    		List<String> retList = new ArrayList<String>();
    		
    		
    		fileReadWrite fr = new fileReadWrite(filename, 0);
            fr.openFile();
            
            while(!fr.hasReachedEOF())
            {
            	String line = fr.readNextLine();
            	String linetrimmed = line.trim();
            	//// remove any comments
            	if(CommonlyUsed.stringIsNullOrEmpty(linetrimmed)) {continue;}
                
                 retList.add(linetrimmed);
                
            }
            fr.closeAll();

    		return retList;

    }
    
   
}
