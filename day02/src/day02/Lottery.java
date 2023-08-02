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
		System.out.println("���� ��ȣ "+ cnt + "�� ���ڸ� �Է����ּ��� (�������� �и�)");
		this.myLotto = new ArrayList<>();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer token = new StringTokenizer(br.readLine(), " ");
		for(int i = 0; i < cnt; i++) {
			myLotto.add(Integer.parseInt(token.nextToken()));
		}
		if(!checkValid()) {
			System.out.println("�Է��Ͻ� ��ȣ�� ��ȿ���� �ʽ��ϴ�.");
			manualGenerate();
		}
	}
	
	public void autoGenerate() {
		System.out.println("������ �ڵ� �߱޵Ǿ����ϴ�.");
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
