package factory;

public class StringFactory {
	
	public static String cutPreStringBysplit(String str,String split){
		return str.substring(0, str.lastIndexOf(split)) ;
	}
	
	public static String replace(String str,String target,String repalcement){
		return null ;
	}
}
