import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Map.Entry;

public class CountMin {
	private final int D=4;
	private final int ARRAYSIZE=500000;
	private int[][] counter;
	private int[] randomArray;
	private LinkedHashMap<Long, Long> lhm;
	
	public CountMin() {
		counter=new int[D][ARRAYSIZE];
		lhm=new LinkedHashMap<>();
		Random r=new Random();
		randomArray=new int[D];
		for(int i=0;i<D;i++){
			randomArray[i]=r.nextInt(Integer.MAX_VALUE);
		}
	}
	
	public static void main(String[] args) throws IOException{
		String line=null;
		BufferedReader br=new BufferedReader(new FileReader("traffic.txt"));
		CountMin cm=new CountMin();
		int count=0;
		while((line=br.readLine())!=null){
			String[] splitLine=line.split("\\s+");
			if(count==0) {count++;
				continue;}
			Long sourceIp=Utility.ipaddressToInt(splitLine[0].trim());
			Long destinationIp=Utility.ipaddressToInt(splitLine[1].trim());
			int size=Integer.parseInt(splitLine[2].trim());
			cm.onlineCalculation(sourceIp, destinationIp,size);
		}
		cm.offlineCalculation();
		br.close();
	}
	
	public void onlineCalculation(Long sourceIp, Long destinationIp, int count){
		int sourceHash=Math.abs(sourceIp.hashCode());
		int destHash= Math.abs(destinationIp.hashCode());
		for(int i=0; i<D;i++){
			counter[i][(Math.abs((sourceHash+destHash)^randomArray[i]))%ARRAYSIZE]=count;
		}
		lhm.put(sourceIp, destinationIp);
	}
	
	public void  offlineCalculation() throws IOException{
		BufferedWriter bw=new BufferedWriter(new FileWriter("OutputCountMin.txt"));
		for(Entry<Long, Long> entry:lhm.entrySet()){
			bw.write(""+minCount(entry.getKey(),entry.getValue())+"\n");
		}
		bw.close();
	}
	
	public int minCount(Long sourceIp, Long destinationIp){
		int min=Integer.MAX_VALUE;
		int sourceHash=Math.abs(sourceIp.hashCode());
		int destHash= Math.abs(destinationIp.hashCode());
		for(int i=0; i<D;i++){
			int temp=counter[i][(Math.abs((sourceHash+destHash)^randomArray[i]))%ARRAYSIZE];
			if(temp<min)
				min=temp;
		}
		return min;
	}
}
