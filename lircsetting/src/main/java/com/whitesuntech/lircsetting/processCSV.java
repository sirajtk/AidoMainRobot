package com.whitesuntech.lircsetting;

import java.util.ArrayList;
import java.util.List;


public class processCSV {
	
	String _filename;
	String _delimiter;
	String _commentChars;
	
	int _readAfterLineNo = fileReadWrite.START_LINENO;
	int _lastLineNoToBeRead = fileReadWrite.EOF_LINENO;
	
	public processCSV(String filename, String delimiter, String CommentChars) {
		// TODO Auto-generated constructor stub
		_filename = filename;
		_delimiter = delimiter;
		_commentChars = CommentChars;
	}
	
	public processCSV(String filename, String delimiter, String CommentChars, int readAfterLineNo, int lastLineNo) {
		// TODO Auto-generated constructor stub
		_filename = filename;
		_delimiter = delimiter;
		_commentChars = CommentChars;
		_readAfterLineNo = readAfterLineNo;
		_lastLineNoToBeRead = lastLineNo;
	}
	
	
	public ListCustom getCSVColumn(int columnNo)
	{
		ListCustom retList = new ListCustom();
		
		
		//fileReadWrite fr = new fileReadWrite(_filename, 0);
		
		fileReadWrite fr = new fileReadWrite(_filename,_readAfterLineNo, _lastLineNoToBeRead, 0);
		
        fr.openFile();
        
        while(!fr.hasReachedEOF())
        {
        	String line = fr.readNextLine();
        	String linetrimmed = line.trim();
        	//// remove any comments
        	if(CommonlyUsed.stringIsNullOrEmpty(linetrimmed)) {continue;}
        	if(linetrimmed.startsWith(_commentChars)) {continue;}

            String[] tokens = CommonlyUsed.split(line, _delimiter);
            if(tokens.length < columnNo) {continue;}
            
            retList.addElement(tokens[columnNo - 1]);
        }
        
        
        fr.closeAll();
        
		return retList;
	}
	
	public String getCSVItemForOneValueOfColumn(int columnNo, String ValueToMatch, int retColumnNo)
	{
		ListCustom retList = new ListCustom();
		
		
		fileReadWrite fr = new fileReadWrite(_filename, _readAfterLineNo, _lastLineNoToBeRead,0);
        fr.openFile();
        
        while(!fr.hasReachedEOF())
        {
        	String line = fr.readNextLine();
        	String linetrimmed = line.trim();
        	//// remove any comments
        	if(CommonlyUsed.stringIsNullOrEmpty(linetrimmed)) {continue;}
        	if(linetrimmed.startsWith(_commentChars)) {continue;}

            String[] tokens = CommonlyUsed.split(line, _delimiter);
            if(tokens.length < columnNo || tokens.length < retColumnNo) {continue;}
            
            if(tokens[columnNo - 1].equals(ValueToMatch))
            {
                fr.closeAll();
            	return tokens[retColumnNo - 1];
            }
        }
        fr.closeAll();

		return "";
	}
	
	public List<String> getCSVallItemsForValueOfColumn(int columnNo, String ValueToMatch, int retColumnNo)
	{
		List<String> retList = new ArrayList<String>();		
		
		fileReadWrite fr = new fileReadWrite(_filename, _readAfterLineNo, _lastLineNoToBeRead,0);
        fr.openFile();
        
        while(!fr.hasReachedEOF())
        {
        	String line = fr.readNextLine();
        	String linetrimmed = line.trim();
        	//// remove any comments
        	if(CommonlyUsed.stringIsNullOrEmpty(linetrimmed)) {continue;}
        	if(linetrimmed.startsWith(_commentChars)) {continue;}

            String[] tokens = CommonlyUsed.split(line, _delimiter);
            if(tokens.length < columnNo || tokens.length < retColumnNo) {continue;}
            
            if(tokens[columnNo - 1].trim().equals(ValueToMatch.trim()))
            {
            	retList.add(tokens[retColumnNo - 1]);
            }
        }
        fr.closeAll();

		return retList;
	}
	
	public List<List<String>> getCSVallItems()
	{
		List<List<String>> retList = new ArrayList<List<String>>();
		
		
		fileReadWrite fr = new fileReadWrite(_filename, _readAfterLineNo, _lastLineNoToBeRead,0);
        fr.openFile();
        
        while(!fr.hasReachedEOF())
        {
        	String line = fr.readNextLine();
        	String linetrimmed = line.trim();
        	//// remove any comments
        	if(CommonlyUsed.stringIsNullOrEmpty(linetrimmed)) {continue;}
        	if(linetrimmed.startsWith(_commentChars)) {continue;}
        	
            List<String> tokens = CommonlyUsed.splitToList(line, _delimiter);
            
            
            	retList.add(tokens);
            
        }
        fr.closeAll();

		return retList;
	}

	public ListCustom getCSVallItemsForValueOfColumnRetListCustom(int columnNo, String ValueToMatch, int retColumnNo)
	{
		ListCustom retList = new ListCustom();
		
		
		fileReadWrite fr = new fileReadWrite(_filename, _readAfterLineNo, _lastLineNoToBeRead,0);
        fr.openFile();
        
        while(!fr.hasReachedEOF())
        {
        	String line = fr.readNextLine();
        	String linetrimmed = line.trim();
        	//// remove any comments
        	if(CommonlyUsed.stringIsNullOrEmpty(linetrimmed)) {continue;}
        	if(linetrimmed.startsWith(_commentChars)) {continue;}

            String[] tokens = CommonlyUsed.split(line, _delimiter);
            if(tokens.length < columnNo || tokens.length < retColumnNo) {continue;}
            
            if(tokens[columnNo - 1].trim().equals(ValueToMatch.trim()))
            {
            	retList.add(tokens[retColumnNo - 1]);
            }
        }
        fr.closeAll();

		return retList;
	}

}
