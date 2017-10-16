package com.techm.rest.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techm.att.splunk.constants.SplunkyConstants;

public class ResourceLoader{
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);
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
	
	public static Map<String,String> getResultXMLFiles() {
		//List<String> fileList = new ArrayList<String>();
		System.out.println("User {} logged in getResultXMLFiles:: "+ SplunkyConstants.USER_LOGIN);
		Map<String, String> fileMap = new  HashMap<String, String>();
		try (Stream<Path> filePathStream = Files.walk(Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH+"/"+SplunkyConstants.USER_LOGIN))) {
			filePathStream.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					try {
						//fileMap.put(filePath.getFileName().toString(), filePath.toString().replace("\\", "/"));
						fileMap.put(filePath.getFileName().toString(), "resources/"+SplunkyConstants.USER_LOGIN+"/"+filePath.getFileName().toString());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						LOGGER.info("logger Exception -> "+e.getMessage());
					}

				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOGGER.info("logger IOException -> "+e.getMessage());
		}
		return fileMap;
	}

	/*private static String getKey(Path filePath) {
		// TODO Auto-generated method stub
		String key = "";
		if(!filePath.getFileName().toString().contains(".jpg")) {
			key = filePath.getFileName().toString();
		}
		if (!filePath.getFileName().toString().contains(".css")){
			key = filePath.getFileName().toString();
		}
		return key;
	}*/
	
	/*public static Map<String,String> loadPropertiesMap() {
		file = new File("userconfig.properties");
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(file));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			LOGGER.info("IOException err: ",e1.getMessage());
		}
		Map<String, String> mapProp = properties.entrySet().stream().collect(
				Collectors.toMap(
						e -> (String)e.getKey(),
						e -> (String)e.getValue()
						));
		return mapProp;
	}	*/
}

