import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashMap;;

public class DoubleHashMap {
	
	private LinkedHashMap<Long, LinkedList<Long>> doubleHashMap;
	
	public DoubleHashMap() throws IOException{
		doubleHashMap=new LinkedHashMap<>();
	}
	
	
	public static void main(String[] args) throws IOException{
		String line=null;
		BufferedReader br=new BufferedReader(new FileReader("traffic.txt"));
		DoubleHashMap dhm=new DoubleHashMap();
		int count=0;
		while((line=br.readLine())!=null){
			String[] splitLine=line.split("\\s+");
			if(count==0) {count++;
				continue;}
			Long sourceIp=Utility.ipaddressToInt(splitLine[0].trim());
			Long destinationIp=Utility.ipaddressToInt(splitLine[1].trim());
			dhm.onlineCalculation(sourceIp, destinationIp);
		}
		dhm.offlineCalculation();
		br.close();
	}
	

	public void onlineCalculation(Long sourceIp, Long destinationIp){
		//System.out.println("sagar");
		if(doubleHashMap.containsKey(sourceIp)){
			doubleHashMap.get(sourceIp).add(destinationIp);
		}
		else{
			LinkedList<Long> ll=new LinkedList<>();
			ll.add(destinationIp);
			doubleHashMap.put(sourceIp,ll );
		}
	}
	
	/*public int findKeyInList(List<HashMap<Integer,BitSet>> list, int key){
		int i=0;
		Iterator<HashMap<Integer, BitSet>> iterator=list.iterator();
		while(iterator.hasNext()){
			if(iterator.next().containsKey(key)){
				return i;
			}
			i++;
		}
		return -1;
	}*/
	
	public void  offlineCalculation() throws IOException{
		BufferedWriter bw=new BufferedWriter(new FileWriter("OutputDoubleHash.txt"));
		for(Entry<Long, LinkedList<Long>> entry:doubleHashMap.entrySet()){
			//System.out.println("sagar");
			bw.write(""+count(entry.getValue())+"\n");
		}
		bw.close();
	}
	
	public int count(LinkedList<Long> list){
		int counter=0;
		for(int i=0;i<list.size();i++) counter++;
		return counter;
	}
	
}
