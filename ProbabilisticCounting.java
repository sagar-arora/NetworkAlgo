import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;

public class ProbabilisticCounting {

	private final int BITSIZE=256;
	private final int ARRAYSIZE=100000;
	public BitSet[] bitset;
	
	public ProbabilisticCounting() throws IOException{
		bitset=new BitSet[ARRAYSIZE];
	}
	
	
	public static void main(String[] args) throws IOException{
		String line=null;
		BufferedReader br=new BufferedReader(new FileReader("traffic.txt"));
		ProbabilisticCounting pc=new ProbabilisticCounting();
		int count=0;
		while((line=br.readLine())!=null){
			String[] splitLine=line.split("\\s+");
			if(count==0) {count++;
				continue;}
			Long sourceIp=Utility.ipaddressToInt(splitLine[0].trim());
			Long destinationIp=Utility.ipaddressToInt(splitLine[1].trim());
			pc.onlineCalculation(sourceIp, destinationIp);
		}
		pc.offlineCalculation();
		br.close();
	}
	

	public void onlineCalculation(Long sourceIp, Long destinationIp){
		int hash=Math.abs(sourceIp.hashCode());
		int index=hash%ARRAYSIZE;
		if(bitset[index]==null){
			bitset[index]=new BitSet(BITSIZE);
		}
		int bitIndex=Math.abs(destinationIp.hashCode())%BITSIZE;
		bitset[index].set(bitIndex);
	}
	
	public void  offlineCalculation() throws IOException{
		BufferedWriter bw=new BufferedWriter(new FileWriter("OutputProbabilisticCounting.txt"));
		for(int i=0;i<ARRAYSIZE;i++){
			if(bitset[i]!=null){
				bw.write(""+estimator(bitset[i]));
				bw.newLine();
			}
		}
		bw.close();
	}
	
	public double estimator(BitSet bs){
		int zero_bit_count = BITSIZE - bs.cardinality();
		double V = (double) zero_bit_count/BITSIZE;
		return -1*BITSIZE*(Math.log(V));	
	}
}
