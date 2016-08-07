package com.sii.Utility;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Filewriter {
	
	public static void writeOnFile(TreeMap<String, Integer> map, String filename ){
		PrintWriter writer = null;
		//System.out.println(map.toString());
		try {
			writer = new PrintWriter(filename, "UTF-8");
			for(Entry<String, Integer> entry: map.entrySet()){
				System.out.println(entry.getKey() + "  "+ entry.getValue());
				writer.println(entry.getKey() +"    "+ entry.getValue());
				writer.flush();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.close();
	}
}

