package factory;

import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

public class OfficeToPDFFactory {

	/*
	 * 
	 * Get openoffice.org Home
	 */
	private static String getOfficeHome(){
		String osName = System.getProperty("os.name") ;
		if(Pattern.matches("Linux.*", osName)){
			return "/opt/openoffice.org3";
		}else if(Pattern.matches("Window.*", osName)){
			return "E:/OpenOffice 4";
		}else if(Pattern.matches("Mac.*", osName)){
			return "/Application/OpenOffice.org.app/Contents";
		}
		return null ;
	}
	
	/*
	 * Get officeManager
	 */
	
	private static OfficeManager getOfficeManager(){
		
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration() ;
		
		String officeHome = getOfficeHome() ;
		
		configuration.setOfficeHome(officeHome) ;
		
		OfficeManager officeManager = configuration.buildOfficeManager();
		
		officeManager.start() ;
		
		return officeManager ;
	}
	
	/*
	 * it makes office documenet converte to pdf
	 */
	public static String OfficeToPDF(String inputPath, String outPathURL){
		
		long time_begin = new Date().getTime() ;
		
		String outputPath = null;
		
		if(null != inputPath){
			
			File inputFile = new File(inputPath) ;
			
			if(inputFile.exists()){
				
				outputPath = getOutputFilePath(inputPath, outPathURL) ;
				
				File outputFile = new File(outputPath) ;
				
				if(!outputFile.getParentFile().exists()){
					outputFile.getParentFile().mkdirs() ;
				}
				
				converterFile(inputFile, outputFile) ;
				
			}
		}else{
			System.out.println("con't find the resource");
		}
		
		long time_end = new Date().getTime() ;
		
		System.out.println("using time : " + (time_end - time_begin));
		
		return outputPath ;
	}
	
	private static String getFileName(String FilePath){
		return FilePath.substring((FilePath.lastIndexOf("/") + 1), FilePath.lastIndexOf(".")) ;
	}
	
	private static String getOutputFilePath(String inputPath,String url){
		System.out.println(getFileName(inputPath));
		return (url + "/" + getFileName(inputPath) + ".pdf") ;
	}
	
	
	/*
	 * converter file of main method
	 */
	private static void converterFile(File inputFile, File outputFile){
		
		OfficeManager officeManager = getOfficeManager() ;
		
		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager) ;
		
		converter.convert(inputFile, outputFile) ;
	}
	
}
