import java.util.regex.Pattern;

public class Utility {

	public static long ipaddressToInt(String ipaddress){
		long result = 0L;
	    for(String part : ipaddress.split(Pattern.quote("."))) {
	        result = result << 8;
	        result |= Long.parseLong(part);
	    }
	    return result;
	}
}
