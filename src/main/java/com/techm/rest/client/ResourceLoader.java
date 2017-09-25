package com.techm.rest.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResourceLoader{
	static File file = null;
	public static Set<String> getResources() throws IOException {

		file = new File("config.properties");
		BufferedReader br = null;
		final Set<String> params = new HashSet<String>();
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				params.add(line.trim());
			}
			
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return params;
	}

	public static void saveProperties(String sbf) throws IOException {
		BufferedWriter bw = null;
		try {
			// APPEND MODE SET HERE
			bw = new BufferedWriter(new FileWriter("config.properties", true));
			
			if(sbf != null){
				StringTokenizer st = new StringTokenizer(sbf.toString(), ",");
				while (st.hasMoreTokens()) {
					String s  = st.nextToken().trim();
					if (s.length()>0){
						bw.write(s);
						bw.newLine();
					}
					
				} 
			}
			
			bw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (bw != null)
				try {
					bw.close();
				} catch (IOException ioe2) {
				}
		}
		System.out.println("Writing completed!!");
	}

	
	
	
	/*public static Map<String, String> getResultXMLFiles() {
		List<String> results = new ArrayList<String>();
		Map<String, String> resultMap = new HashMap<String, String>();

		File[] files = new File("/resources").listFiles();
		// If this pathname does not denote a directory, then listFiles()
		// returns null.

		for (File file : files) {
			if (file.isFile()) {
				// results.add(file.getName());
				try {
					resultMap.put(file.getCanonicalPath(), file.getName());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return resultMap;
		
	}*/
	
	public static List<String> getResultXMLFiles() {
		List<String> fileList = new ArrayList<String>();
		try (Stream<Path> filePathStream = Files.walk(Paths.get("/resources"))) {
			filePathStream.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					// System.out.println(filePath);
					// System.out.println(filePath.getFileName());
					fileList.add(filePath.getFileName().toString());

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileList;
	}
}

