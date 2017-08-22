package com.glenwood.network.server;

import static gwtupload.shared.UConsts.*;
import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.glenwood.network.client.application.domain.Node;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  
 * This servlet saves all received files in a temporary folder, 
 * and deletes them when the user sends a remove request.
 *
 */
public class DataFileUploadServlet extends UploadAction {

	private static final long serialVersionUID = 1L;

	Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	/**
	 * Maintain a list with received files and their content types. 
	 */
	Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

	/**
	 * Override executeAction to save the received files in a custom place
	 * and delete this items from session.  
	 */
	public static String fileUploadPath=null;
	@Override
	public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		int cont = 0;
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				cont++;
				try {
					/// Create a new file based on the remote file name in the client
					fileUploadPath=this.getServletContext().getRealPath("uploadedFiles") ;
					File file =new File((item.getName()));
					item.write(file);

					/// Save a list with the received files
					receivedFiles.put(item.getFieldName(), file);
					receivedContentTypes.put(item.getFieldName(), item.getContentType());

					/// Compose a xml message with the full file information
					response += "<file-" + cont + "-field>" + item.getFieldName() + "</file-" + cont + "-field>\n";
					response += "<file-" + cont + "-name>" + item.getName() + "</file-" + cont + "-name>\n";
					response += "<file-" + cont + "-size>" + item.getSize() + "</file-" + cont + "-size>\n";
					response += "<file-" + cont + "-type>" + item.getContentType() + "</file-" + cont + "-type>\n";
					response += "<file-" + cont + "-arrayvalue>" + item.getContentType() + "</file-" + cont + "-arrayvalue>\n";

				} catch (Exception e) {
					System.out.println("Exception caught:"+e.getMessage());
					e.printStackTrace();
					throw new UploadActionException(e);
				}
			}
		}

		/// Remove files from session because we have a copy of them
		removeSessionFileItems(request);
		/// Send information of the received files to the client.
		return "<response>\n" + response + "</response>\n";
	}


	public HashMap<Integer, String> sendInputNodeNames(String fieldName, String networkId ){
		List<Node> networkNodesList = getNodeList(networkId);
		HashMap<Integer, String> nodelistFromFile = new HashMap<Integer, String>();
		File f = new File((fieldName));
		String[] nodeList=null;
		if( fieldName.endsWith(".CSV") || fieldName.endsWith(".csv") ){
			if (f != null) {
				BufferedReader br = null;
				String line = "";
				String cvsSplitBy = ",";

				try {
					int count=-1;
					br = new BufferedReader(new FileReader(f));
					while ((line = br.readLine()) != null) {
						count++;
					}
					br.close();

					br = new BufferedReader(new FileReader(f));

					while ((line = br.readLine()) != null) {
						String[] inputData = line.split(cvsSplitBy);
						int nodeListLength=inputData.length;
						nodeList=new String[nodeListLength];
						for(int i=0;i<nodeListLength;i++){
							nodeList[i]=inputData[i]+"~"+i+"~"+count;
						}
						break;
					}
				} catch (FileNotFoundException e) {
					System.out.println("Exception caught:"+e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("Exception caught:"+e.getMessage());
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							System.out.println("Exception caught:"+e.getMessage());
							e.printStackTrace();
						}
					}
				}
			} 

			for (int i = 0; i < nodeList.length ; i++) {
				if(nodeList[i] != null) {

					for (int j = 0; j < networkNodesList.size(); j++) {
						if ((networkNodesList.get(j).getNodeName()).equalsIgnoreCase(nodeList[i].split("~")[0]) && 
								( networkNodesList.get(j).getNodeType().equalsIgnoreCase("C") || networkNodesList.get(j).getNodeType().equalsIgnoreCase("V") )) {
							nodelistFromFile.put(Integer.parseInt(nodeList[i].split("~")[1]),	nodeList[i].split("~")[0]);

						}
					}
				}
			}
			nodelistFromFile.put(Integer.MIN_VALUE, nodeList[0].split("~")[2]);
			return nodelistFromFile;
		}
		else if( fieldName.toLowerCase().endsWith(".xlsx") || fieldName.toLowerCase().endsWith(".xls") ){


			// Finds the workbook instance for XLSX file
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			// Finds the workbook instance for XLSX file
			Workbook myWorkBook = null;
			try {
				if(fieldName.toLowerCase().endsWith("xlsx")){
					try {
						myWorkBook = new XSSFWorkbook(fis);
					} catch (IOException e) {
						System.out.println("Exception caught:"+e.getMessage());
						e.printStackTrace();
					} 
				}else if(fieldName.toLowerCase().endsWith("xls")){
					try {
						myWorkBook = new HSSFWorkbook(fis);
					} catch (IOException e) {
						System.out.println("Exception caught:"+e.getMessage());
						e.printStackTrace();
					}
				}

				Sheet mySheet = myWorkBook.getSheetAt(0);
				Iterator<Row> rowIterator = mySheet.iterator();
				Row row = rowIterator.next();
				nodeList = new String[row.getLastCellNum()];
				for(int i = 0 ; i < row.getLastCellNum() ; i++){
					Cell cell = row.getCell(i);
					nodeList[i] = cell.getStringCellValue()+"~"+i+"~"+(mySheet.getLastRowNum());
				}
			} catch (Exception e) {

			}finally {
				try {
					myWorkBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			for (int i = 0; i < nodeList.length ; i++) {
				if(nodeList[i] != null) {

					for (int j = 0; j < networkNodesList.size(); j++) {
						if ((networkNodesList.get(j).getNodeName()).equalsIgnoreCase(nodeList[i].split("~")[0]) && 
								( networkNodesList.get(j).getNodeType().equalsIgnoreCase("C") || networkNodesList.get(j).getNodeType().equalsIgnoreCase("V") )) {
							nodelistFromFile.put(Integer.parseInt(nodeList[i].split("~")[1]),	nodeList[i].split("~")[0]);

						}
					}
				}
			}
			nodelistFromFile.put(Integer.MIN_VALUE, nodeList[0].split("~")[2]);
			return nodelistFromFile;
		}

		return null;
	}

	/**
	 * Method to get the node list in the network
	 * @param networkId 
	 * **/
	private List<Node> getNodeList(final String networkId) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://localhost/NetworkREST/rest/node/list?networkId="+networkId);
			client.executeMethod(post);
			return mapper.readValue(post.getResponseBodyAsStream(), new TypeReference<List<Node>>() { });
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public HashMap<Integer, Double> collectValue(String fieldName,int csvIndex, int fromIndex, int toIndex ){
		File f = new File((fieldName));
		HashMap<Integer, Double> valueHash=null;
		if( fieldName.endsWith(".CSV") || fieldName.endsWith(".csv") ){
			BufferedReader br = null;
			String line = "";
			String cvsSplitBy = ",";

			try {
				valueHash=new HashMap<Integer, Double>();
				br = new BufferedReader(new FileReader(f));
				int i=0;
				int start=fromIndex;
				int stop=toIndex;
				while ((line = br.readLine()) != null) {
					String[] inputData = line.split(cvsSplitBy);
					if(i>=start){
						if(i>stop){
							break;
						}
						Double value=Double.parseDouble(inputData[csvIndex]);
						valueHash.put(i, value);
					}
					i++;
				}

			} catch (FileNotFoundException e) {
				System.out.println("Exception caught:"+e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Exception caught:"+e.getMessage());
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						System.out.println("Exception caught:"+e.getMessage());
						e.printStackTrace();
					}
				}
			}

		}
		else if( fieldName.toLowerCase().endsWith(".xlsx") || fieldName.toLowerCase().endsWith(".xls") ){
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(f);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			// Finds the workbook instance for XLSX file
			Workbook myWorkBook = null;
			if(fieldName.toLowerCase().endsWith("xlsx")){
				try {
					myWorkBook = new XSSFWorkbook(fis);
				} catch (IOException e) {
					System.out.println("Exception caught:"+e.getMessage());
					e.printStackTrace();
				}
			}else if(fieldName.toLowerCase().endsWith("xls")){
				try {
					myWorkBook = new HSSFWorkbook(fis);
				} catch (IOException e) {
					System.out.println("Exception caught:"+e.getMessage());
					e.printStackTrace();
				}
			}

			valueHash=new HashMap<Integer, Double>();
			Sheet mySheet = myWorkBook.getSheetAt(0);
			for(int i=fromIndex; i<=toIndex; i++){
				valueHash.put(i, mySheet.getRow(i).getCell(csvIndex).getNumericCellValue() );
			}

		}


		return valueHash;
	}

	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(PARAM_SHOW);
		File f = receivedFiles.get(fieldName);
		if (f != null) {
			response.setContentType(receivedContentTypes.get(fieldName));
			FileInputStream is = new FileInputStream(f);
			copyFromInputStreamToOutputStream(is, response.getOutputStream());
		} else {
			renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
		}
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)  throws UploadActionException {
		File file = receivedFiles.get(fieldName);
		receivedFiles.remove(fieldName);
		receivedContentTypes.remove(fieldName);
		if (file != null) {
			file.delete();
		}
	}
	public ArrayList<String> collectData(String fileFieldName) {
		ArrayList<String> content = new ArrayList<String>();

		try {

			//content = new Scanner(new File(fileFieldName)).useDelimiter("\\Z").next(); 
			for( Scanner s = new Scanner(new File(fileFieldName)).useDelimiter("\\n"); s.hasNext(); content.add(s.next()));


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return content;
	}

}
