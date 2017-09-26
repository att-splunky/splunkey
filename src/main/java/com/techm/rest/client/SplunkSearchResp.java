package com.techm.rest.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.techm.att.splunk.constants.SplunkyConstants;




public class SplunkSearchResp {
	
	//private String searchUrl = "http://api360.web.att.com/atw-service/log/splunk/search?search=";
	private final String searchUrl = SplunkyConstants.SPLUNKY_URL;
	private String searchText;
	private String requestParamters;
	private Set<String> paramters= null;
	//final String rootFolder = createRootFolder("resources/");
	
	private ConversationBean conversationBean = null;
	
	private List<ConversationBean> xmlList = null;
	private static Map<String, List<ConversationBean>> listMap= new HashMap<String, List<ConversationBean>>();

	public static Map<String, List<ConversationBean>> getListMap() {
		return listMap;
	}

	public static void setListMap(Map<String, List<ConversationBean>> listMap) {
		SplunkSearchResp.listMap = listMap;
	}

	public SplunkSearchResp(String searchText, String requestParamters) {
		super();
		this.searchText = searchText;
		this.requestParamters =requestParamters;
	}

	private final String createRootFolder(String root) {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_hh-mm-ss");
		final String subfolder = sdf.format(new Date())+"/";
		new File(root + subfolder).mkdir();
		return root + subfolder;
	}

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String searchTxt = "csitest~CNG-CSI~c6b6a817-c1bd-4bb8-a91c-650d8d746c87";
		String searchTxt = "csitest~CNG-CSI~c703d488-9c45-4f5f-be22-aa4f1f884a01";
		StringBuffer requestParamters = null;	
		new SplunkSearchResp(searchTxt, requestParamters).getLogfromSplunkWithText();
	}*/
	public static void init(String searchTxt, String requestParamters){
		System.out.println("_________  InPrgress...\n");
		System.out.println(searchTxt+" :: "+requestParamters);
		new SplunkSearchResp(searchTxt, requestParamters).getLogfromSplunkWithText();
	}
	public void getLogfromSplunkWithText() {
		//System.out.println("inside the method::: "+this.searchText+" :: "+this.requestParamters);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new ByteArrayHttpMessageConverter());
		
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		//String rootFolder = "resource/";
		xmlList = new ArrayList<ConversationBean>();
		
		try {
			ResponseEntity<byte[]> response = restTemplate.exchange(searchUrl
					+ searchText, HttpMethod.GET, entity, byte[].class, "1");

			// if (response.getStatusCode() == HttpStatus.OK)
			{
				//System.out.println("Saving to Resources___ "+ requestParamters);
				ResourceLoader.saveProperties(requestParamters);
				
				
				//new File(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/").mkdirs();
				//new File(rootFolder + searchText + "/output").mkdirs();
				
				Files.write(Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH+"/temp/splunklogSearchText4.txt"),
						response.getBody());

				final String str = new String(response.getBody(),
						StandardCharsets.UTF_8);
				conversationBean = new ConversationBean();
				conversationBean.setName("splunklogSearchText_log.txt");
				conversationBean.setData(str);
				xmlList.add(conversationBean);
				
				// String(Files.readAllBytes(Paths.get("resource/splunklogSearchText2.txt")),StandardCharsets.UTF_8);
				
				paramters = ResourceLoader.getResources();
				for (final String queryParam : paramters) {
					if (queryParam.length() > 0) {
						if (str.contains(queryParam)) {
							//System.out.println(queryParam + " :: exist");

							String res = executeParamXml(str, queryParam);

							if (res != null) {
								conversationBean = new ConversationBean();
								conversationBean.setName(queryParam + ".xml");
								conversationBean.setData(res);
								xmlList.add(conversationBean);
								if(queryParam.endsWith("Response")){
								Files.write(
										Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText + "/" 
												+ "/temp/"+queryParam
												+ ".xml"),
										res.getBytes(StandardCharsets.UTF_8));
								}else{
									Files.write(
											Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText + "/" 
													+ "/temp/"+queryParam
													+ ".xml"),
											res.getBytes(StandardCharsets.UTF_8));
								}
							}
						}
					}
				}
				
				
				/*String extract = str
						.split("### Log Debug: Output for Transform:")[1];

				String dirtyRequest[] = extract.split("interfaces");
				for (int i = 1; i < dirtyRequest.length - 1; i++) {
					String request = dirtyRequest[i].substring(
							dirtyRequest[i].indexOf("<"),
							dirtyRequest[i].lastIndexOf(">") + 1);
					System.out.println("Request :[" + i + "]\n" + request);
					Files.write(
							Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/"
									+ searchText + "_" + i + ".xml"),
							request.getBytes(StandardCharsets.UTF_8));

				}
				
				String dirtyResponse = extract.split("\\n\\n")[0];
				String response1 = dirtyResponse.substring(dirtyResponse
						.indexOf("<"));
				System.out.println("Response : \n" + response1);
				Files.write(
						Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/"
								+ searchText + "_respone.xml"),
						response1.getBytes(StandardCharsets.UTF_8)); */
				
				listMap.put(searchText, xmlList);
				//System.out.println("Mapp22 ::::::::::: "+listMap );	
				
				try {
					Set<String> uniqConvId = null;
					if (numMatches(str, "spm2async~") != null) {
						uniqConvId = new HashSet<String>();
						StringTokenizer st = new StringTokenizer(numMatches(str, "spm2async~"));
						while (st.hasMoreTokens()) {
							uniqConvId.add(st.nextToken().trim());
						}
						
						if (!uniqConvId.isEmpty()) {
							for (String ss : uniqConvId) {
								//System.out.println("Conv Id:::   "+ss);
								if (ss.trim().startsWith("spm2async~")){
									getAsyncLogfromSplunkWithText(restTemplate,
											entity, ss);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} 
				

				/*
				 * Set<String> uniqConvId =
				 * convert_file_to_string_java_bufferedreader();
				 * //System.out.println("******************* :: "+ uniqConvId);
				 * 
				 * if(!uniqConvId.isEmpty()){ for(String ss: uniqConvId)
				 * getAsyncLogfromSplunkWithText(restTemplate,entity,ss); }
				 */				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter write = new StringWriter();
			PrintWriter out = new PrintWriter(write);
			e.printStackTrace(out);
			try {
				//new File(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText 
						//+ "/error").mkdirs();
				Files.write(Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText 
						//+ "/error/"
						+ "/temp/"+searchText + "_error.txt"), write.toString()
						.getBytes());
				conversationBean = new ConversationBean();
				conversationBean.setName("error");
				conversationBean.setData(write.toString());
				xmlList.add(conversationBean);
				listMap.put(searchText, xmlList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	private String executeParamXml(String mainString, String queryParam) {
		// TODO Auto-generated method stub

		String res = null;
		try {
			if (!mainString.contains("<" + queryParam.trim())) {
				String nameSpace = mainString.split(mainString
						.substring(mainString.indexOf(queryParam.trim())))[0];
				//System.out.println(queryParam + " *************namespace**** "
				//		+ nameSpace);
				if (nameSpace.substring(nameSpace.lastIndexOf("<")).contains(
						":")) {
					nameSpace = nameSpace.substring(nameSpace.lastIndexOf("<"))
							.split(":")[0];
					nameSpace = nameSpace.replaceAll("</", "");
					//System.out.println("nameSpace ::: " + nameSpace);
					final String openStr = "<" + nameSpace + ":"
							+ queryParam.trim();
					final String closedStr = "</" + nameSpace + ":"
							+ queryParam.trim() + ">";
					res = mainString.substring(mainString.indexOf(openStr),
							mainString.indexOf(closedStr) + closedStr.length());
					//System.out.println("With nameSpace String ::: " + res);
				}
			} else {
				final String openStr = "<" + queryParam.trim();
				final String closedStr = "</" + queryParam.trim() + ">";
				res = mainString.substring(mainString.indexOf(openStr),
						mainString.indexOf(closedStr) + closedStr.length());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return res;
	}

	public Set<String> convert_file_to_string_java_bufferedreader()
			throws IOException {

		File file = new File(SplunkyConstants.SPLUNKY_RESOURCES_PATH + "/temp/splunklogSearchText4.txt");

		BufferedReader br = new BufferedReader(new FileReader(file));
		Set<String> uniqAsyncConverId = new HashSet<String>();
		String line = br.readLine();
		while (line != null) {
			if (line.contains("spm2async~")) {
				line = "spm2async~".concat(line.split("spm2async~")[1]);
				uniqAsyncConverId.add(line);
			}
			line = br.readLine();
		}
		br.close();
		return uniqAsyncConverId;
	}
	public static String numMatches (String str, String query) {
	    int index = str.indexOf(query);
	    if (index == -1)
	        return null;
	    else{
	    	return query+str.substring(index + query.length()).split(" ")[0]+" "+ numMatches(str.substring(index + query.length()), query);
	    }
	        
	}
	public void getAsyncLogfromSplunkWithText(RestTemplate restTemplate, HttpEntity<String> entity, String searchText) {
		//String rootFolder = "resource/";
		xmlList = new ArrayList<ConversationBean>();
		try {
			ResponseEntity<byte[]> response = restTemplate.exchange(searchUrl	+ searchText, HttpMethod.GET, entity, byte[].class, "1");

			//if (response.getStatusCode() == HttpStatus.OK) 
			{

				//new File(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/").mkdirs();
				//new File(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/").mkdirs();

				Files.write(Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH + "/temp/"+searchText+".txt"),	response.getBody());

				String str = new String(response.getBody(),	StandardCharsets.UTF_8);
				// String str = new String(Files.readAllBytes(Paths.get("resource/splunklogSearchText2.txt")),StandardCharsets.UTF_8);
				
				conversationBean = new ConversationBean();
				conversationBean.setName("spm2Async_log.txt");
				conversationBean.setData(str);
				xmlList.add(conversationBean);
				
				paramters = ResourceLoader.getResources();
				for (final String queryParam : paramters) {
					if (queryParam.length() > 0) {
						if (str.contains(queryParam)) {
							//System.out.println(queryParam + " :: exist");

							String res = executeParamXml(str, queryParam);

							if (res != null) {
								conversationBean = new ConversationBean();
								conversationBean.setName(queryParam+ ".xml");
								conversationBean.setData(res);
								xmlList.add(conversationBean);
								
								if(queryParam.endsWith("Response")){
								Files.write(
										Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText + "/" 
												+ "/temp/"+queryParam
												+ ".xml"),
										res.getBytes(StandardCharsets.UTF_8));
								}else{
									Files.write(
											Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText + "/" 
													+ "/temp/"+queryParam
													+ ".xml"),
											res.getBytes(StandardCharsets.UTF_8));
								}
							}
						}
					}
				}
				
				
				/*String extract = str
						.split("### Log Debug: Output for Transform:")[1];

				String dirtyRequest[] = extract.split("interfaces");
				for (int i = 1; i < dirtyRequest.length - 1; i++) {
					String request = dirtyRequest[i].substring(
							dirtyRequest[i].indexOf("<"),
							dirtyRequest[i].lastIndexOf(">") + 1);
					System.out.println("Request :[" + i + "]\n" + request);
					Files.write(
							Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/"
									+ searchText + "_" + i + ".xml"),
							request.getBytes(StandardCharsets.UTF_8));

				}

				String dirtyResponse = extract.split("\\n\\n")[0];
				String response1 = dirtyResponse.substring(dirtyResponse
						.indexOf("<"));
				System.out.println("Response : \n" + response1);
				Files.write(
						Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH + searchText + "/"
								+ searchText + "_respone.xml"),
						response1.getBytes(StandardCharsets.UTF_8));*/
				
				listMap.put(searchText, xmlList);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			StringWriter write = new StringWriter();
			PrintWriter out = new PrintWriter(write);
			e.printStackTrace(out);
			try {
				//new File(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText 
				//		+ "/error").mkdirs();
				Files.write(Paths.get(SplunkyConstants.SPLUNKY_RESOURCES_PATH //+ searchText 
						//+ "/error/"
						+ "/temp/"+searchText + "_error.txt"), write.toString().getBytes());
				conversationBean = new ConversationBean();
				conversationBean.setName("error");
				conversationBean.setData(write.toString());
				xmlList.add(conversationBean);
				listMap.put(searchText, xmlList);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
