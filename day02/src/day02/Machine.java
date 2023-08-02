package day02;

import java.util.ArrayList;
import java.util.List;

public class Machine {
	int maxNum;
	int cnt;
	
	public Machine(int maxNum, int cnt) {
		this.maxNum = maxNum;
		this.cnt = cnt;
	}
	
	public List<Integer> generate() {
		List<Integer> lotto = new ArrayList<>();
		for(int i = 0; i < cnt; i++) {
			int cur = (int)(Math.random() * maxNum) + 1;
			if(lotto.contains(cur)) {
				i--;
				continue;
			}
			lotto.add(cur);
		}
		return lotto;		
	}
}