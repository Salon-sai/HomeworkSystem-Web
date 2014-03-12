package factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PDFtoSWFFactory {
	
	private static String EXE_PATH = "E:/SWFTools/pdf2swf.exe" ;
	
	
	public static synchronized String ConvertToSWF(String inputFilePath, String outputFileDir) throws IOException {
		
		String FileName = inputFilePath.substring((inputFilePath.lastIndexOf("/") + 1), inputFilePath.lastIndexOf(".")) ;
		
		Process process = null ;
		
		String outputFilePath = null ;
		
		if(isWindowsSystem()){
			System.out.println("converter SWF is running in Windows System");
			
			System.out.println("prepare to run...");
			
			File outputFiledir = new File(outputFileDir) ;
			
			if(!outputFiledir.exists()){
				outputFiledir.mkdirs() ;
			}
			
			
			/*
			 * The comand need exe path , inputFile path , outputFile path
			 */
			String command = EXE_PATH + " \"" + inputFilePath + " \" -o\"" + outputFileDir + "/" + FileName + ".swf" ;
			System.out.println("cmd command : " + command);
			process = Runtime.getRuntime().exec(command) ;
			
			outputFilePath = outputFileDir + "/" + FileName + ".swf" ;
			
		}else{
			
			System.out.println("converter SWF is running in other system");
			
			String[] command = new String[3] ;
			command[0] = EXE_PATH ;
			command[1] = inputFilePath ;
			command[2] = outputFileDir + "/" + FileName + ".swf" ;
			
			process = Runtime.getRuntime().exec(command) ;
			
			outputFilePath = outputFileDir + "/" + FileName + ".swf" ;
		}
		
		new DoOutput(process.getInputStream()) ;
		
		new DoOutput(process.getErrorStream()) ;
		
		try {
			process.waitFor() ;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace() ;
		}
		
		return outputFilePath ;
		
	}
	
	private static boolean isWindowsSystem(){
		
		String osName = System.getProperty("os.name") ;
		
		return osName.toLowerCase().indexOf("windows") >= 0 ? true : false ;
		
	}
	
	
	private static class DoOutput extends Thread {
		
		public InputStream is ;

		public DoOutput(InputStream is){
			this.is = is ;
		}

		public void run(){
			BufferedReader br = new BufferedReader(new InputStreamReader(this.is)) ;

			String str = null ;

			try{
				while((str = br.readLine()) != null)
					System.out.println(str);;
			}catch (IOException e){
				e.printStackTrace() ;
			} finally {
				if(br != null){
					try{
						br.close() ;
					} catch (IOException e){
						e.printStackTrace() ;
					}
				}
			}
		}
	}
	
}
