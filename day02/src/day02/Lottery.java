package day02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Lottery {
	int maxNum;
	int cnt;
	String mode;
	List<Integer> myLotto;
	
	public Lottery(int maxNum, int cnt, String mode) {
		this.maxNum = maxNum;
		this.cnt = cnt;
		this.mode = mode;
	}
	
	public List<Integer> generate() throws IOException {
		if(mode.equals("m")) {
			manualGenerate();
		} else if(mode.equals("a")) {
			autoGenerate();
		}
		return myLotto;
	}
	
	public void manualGenerate() throws IOException {
		System.out.println("복권 번호 "+ cnt + "개 숫자를 입력해주세요 (공백으로 분리)");
		this.myLotto = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer token = new StringTokenizer(br.readLine(), " ");
		for(int i = 0; i < cnt; i++) {
			myLotto.add(Integer.parseInt(token.nextToken()));
		}
		if(!checkValid()) {
			System.out.println("입력하신 번호가 유효하지 않습니다.");
			manualGenerate();
		}
	}
	
	public void autoGenerate() {
		System.out.println("복권이 자동 발급되었습니다.");
		Machine machine = new Machine(maxNum, cnt);
		this.myLotto = machine.generate();
	}

	public boolean checkValid() {
		Set set = new HashSet();
		for(int num : myLotto) {
			set.add(num);
			if(num > maxNum) {
				return false;
			}
		}
		if(set.size() != cnt) {
			return false;
		}
		return true;
	}
}
