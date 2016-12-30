package com.jkanetwork.kson;

import java.io.*;
import java.util.*;

/**
 * Class to parse kson files to HashMap.
 *
 * @author JKA Network
 * @version 1.0
 * @since 1.0
 */
public class Kson{

	/**
	 * Convert a kson to a HashMap.
	 *
	 * @param fileRoute The route where the file is
	 * @return a HashMap with the values
	 * @throws KsonException When the kson file doesn't respect the kson format (key:value\n)
	 * @throws IOException When the file not exists or was a IOException closing or readint the file
	 */
	public static Map<String,String> parse(String fileRoute) throws KsonException, IOException{
		File f = new File(fileRoute);
		if(!f.canRead())
			throw new IOException("You haven't got permissions to read the file "+fileRoute);
		FileReader fr;
		try {
			fr = new FileReader(f);
		} catch (FileNotFoundException e) {
			throw new IOException("The file "+ fileRoute + " don't exists",e);
		}
		BufferedReader b = new BufferedReader(fr);
		Map<String,String> map;
		try{
			map = parsing(fileRoute,b);
		}finally{
			fr.close();
		}
		return map;

	}

	private static Map<String,String> parsing(String fileRoute, BufferedReader b) throws KsonException, IOException{
		Map<String,String> m = new HashMap<>();
		try {
			while(true){
				String value = b.readLine();
				String[] values = value.split(":");
				try{
					m.put(values[0], values[1]);
				}catch(IllegalArgumentException e){
					throw new KsonException("The kson "+fileRoute+" have duplicated keys");
				}catch(IndexOutOfBoundsException e){
					throw new KsonException("The key " + values[0] +" doesn't have a value");
				}
			}
		} catch (NullPointerException e1){
			//The String is null and the lines has end
		} finally {
			b.close();
		}
		return m;
	 }

	/**
	 * Convert a kson to a HashMap.
	 *
	 * @param b bufferedReader where get the kson stream
	 * @return a HashMap with the values. The bufferedReader is closed
	 * @throws KsonException When the kson file doesn't respect the kson format (key:value\n)
	 * @throws IOException When the file not exists or was a IOException closing or readint the file
	 */
	public static Map<String,String> parse(BufferedReader b) throws KsonException, IOException{
		return parsing("Unknow",b);
	}

	/**
	 * Save in a file the kson format from a map.
	 *
	 * @param map the source of the data to kson file
	 * @param fileRoute route for put the file
	 * @throws IOException the file have problems or you haven't got permissions to write in this route
	 */
	public static void save(Map<String,String> map,String fileRoute) throws IOException{
		FileWriter fw = new FileWriter(fileRoute);
		BufferedWriter b = new BufferedWriter(fw);
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			String key = it.next();
			b.write(key+":"+map.get(key));
			b.newLine();
		}
		b.close();
		fw.close();
	}

}
