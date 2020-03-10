import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;

public class VirtualBitMap {
	
	private final int BITSIZE=256;
	private final int ARRAYSIZE=200000;
	private BitSet virtualBitMap;
	private BitSet[] bitmap;
	private int[] randomArray;
	
	public VirtualBitMap() throws IOException{
		virtualBitMap=new BitSet(ARRAYSIZE);
		bitmap=new BitSet[ARRAYSIZE];
		randomArray=new int[BITSIZE];
		Random r=new Random();
		
		for(int i=0;i<BITSIZE;i++){
			randomArray[i]=r.nextInt(Integer.MAX_VALUE);
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		String line=null;
		BufferedReader br=new BufferedReader(new FileReader("traffic.txt"));
		VirtualBitMap vbm=new VirtualBitMap();
		int count=0;
		while((line=br.readLine())!=null){
			String[] splitLine=line.split("\\s+");
			if(count==0) {count++;
				continue;}
			Long sourceIp=Utility.ipaddressToInt(splitLine[0].trim());
			Long destinationIp=Utility.ipaddressToInt(splitLine[1].trim());
			vbm.onlineCalculation(sourceIp, destinationIp);
		}
		vbm.offlineCalculation();
		br.close();
	}
	
	public void onlineCalculation(Long sourceIp, Long destinationIp){
		int sourceHash=Math.abs(sourceIp.hashCode());
		int destHash= Math.abs(destinationIp.hashCode());
		virtualBitMap.set((sourceHash^randomArray[destHash%BITSIZE])%ARRAYSIZE);
		int index=sourceHash%ARRAYSIZE;
		if(bitmap[index]==null){
			bitmap[index]=new BitSet(BITSIZE);
		}
		int bitIndex=destHash%BITSIZE; 
		bitmap[index].set(bitIndex);
	}
	
	public void offlineCalculation() throws IOException{
		BufferedWriter bw=new BufferedWriter(new FileWriter("OutputVirtualMap.txt"));
		for(int i=0;i<ARRAYSIZE;i++){
			if(bitmap[i]!=null){
				bw.write(""+estimator(bitmap[i]));
				bw.newLine();
			}
		}
		bw.close();
	}
	
	public double estimator(BitSet bs){
		int zero_bit_count = BITSIZE - bs.cardinality();
		double Vs = (double) zero_bit_count/BITSIZE;
		int zero_bit_count_ARRAY = ARRAYSIZE - virtualBitMap.cardinality();
		double Vm= (double)zero_bit_count_ARRAY/ARRAYSIZE;
		return BITSIZE*Math.log(Vm)-1*BITSIZE*(Math.log(Vs));	
	}
}
