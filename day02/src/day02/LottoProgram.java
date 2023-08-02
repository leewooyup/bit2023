package day02;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class LottoProgram {
	static int round = 1;
	static String accountName = "";
	static double balance = 0;

	public static int calRank(List<Integer> winNum, List<Integer> myLotto) {
		int matchCnt = 0;
		boolean is645 = false;
		if(winNum.size() == 6) {
			is645 = true;
		}
		for(int num : winNum) {
			if(myLotto.contains(num)) {
				matchCnt++;
			}
		}
		switch(matchCnt) {
		case 6:
			return 1;
		case 5:
			if(is645) return 2;
			else return 1;
		case 4:
			if(is645) return 3;
			else return 2;
		case 3:
			if(is645) return 4;
			else return 3;
		default:
			if(is645) return 5;
			return 4;
		}
	}
	
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		boolean isExist = false;
		while(true) {
			System.out.println("������ ������ �������ּ���: 1. ���ǻ�� 2. �� ���� ���� 3. �� �ܰ� Ȯ�� q. ����");
			String cmd = sc.next();
			if(cmd.equals("1")) {
				balance -= 5000;
				int total = (int)(Math.random() * 30000) + 5000;
				int[] result = new int[6];
				int maxNum = 1;
				int cnt = 0;
				
				System.out.println("�����Ͻ� ������ ������ �������ּ���: 1: 645Lotto 2: 540Lotto");
				cmd = sc.next();
				if(cmd.equals("1")) {
					maxNum = 45;
					cnt = 6;
				} else if(cmd.equals("2")) {
					maxNum = 40;
					cnt = 5;
				}
				
				System.out.println("���� ���� ����� �������ּ���: m: manual(����) a: auto(�ڵ�)");
				String mode = sc.next();
				
				System.out.println("������ �����մϴ�(-5000)");
				Machine lottoMachine = new Machine(maxNum, cnt);
				List<Integer> winNum = lottoMachine.generate();
				Lottery lotto = new Lottery(maxNum, cnt, mode);
				List<Integer> myLotto = lotto.generate();
				
				System.out.println("[�� �ζ� ��ȣ]");
				for(int num : myLotto) {
					System.out.print(num + " ");
				}
				
				int rankIdx = calRank(winNum, myLotto);

				result[rankIdx]++;
				System.out.println();
				System.out.println();
				
				System.out.println("==================");
				System.out.println("�� " + round + "ȸ�� ��÷��ȣ");
				System.out.println("==================");
				for(int i = 0; i < winNum.size(); i++) {
					System.out.print(winNum.get(i) + " ");
				}
				System.out.println();
				System.out.println();
				
				// ��ü rank ����
				for(int i = 1; i < total; i++) {
					List<Integer> others = lottoMachine.generate();
					rankIdx = calRank(winNum, others);
					result[rankIdx]++;
				}
				
				// rank�� �ο��� ���
				for(int i = 1; i < result.length; i++) {
					System.out.println(i + "��: " + result[i] + "��");
				}
				
				if(cmd.equals("2")) {
					System.out.println("�� 540Lotto�� 4����� �����մϴ�");
				}
				
				System.out.println("(�� �����ڼ�: " + total + "��)");
				System.out.println("(�� �Ǹž�: " + 5000 * total + "��)");
				System.out.println();
				
				ResultDto resultDto = new ResultDto(total, result);
				Bank bank = new Bank(resultDto);
				double[] rewards = bank.calReward();
				
				if(rankIdx == 1 || rankIdx == 2 || rankIdx == 3) {
					System.out.println("�����մϴ�!!");
				}
				
				System.out.println(rankIdx + "� ��÷�Ǽ̽��ϴ�.");
				System.out.println(rankIdx + "�� ��÷��: " + (long)rewards[rankIdx] + "��");
				
				if(isExist) {
					System.out.println("�ش� �ݾ�(" + rewards[rankIdx] +"��)�� " + accountName + " ���·� �Ա޵Ǿ����ϴ�.");
				} else {
					System.out.println("�ش� �ݾ�(" + rewards[rankIdx] +"��)�� �Ա��� ���¸� �������ּ���");
				}
				
				balance += rewards[rankIdx];
				
				round++;
			} else if(cmd.equals("2")) { // 2. �� ���� ����
				if(isExist) {
					System.out.println("�̹� ������� ���°� �ֽ��ϴ�.");
				} else {
					isExist = true;
					System.out.println("���¸��� �Է����ּ���");
					accountName = sc.next();
					System.out.println("ù ���¸� �����ϼ������� 1,000,000�� �Ա��߽��ϴ�.");
					balance += 1_000_000;
				}
			} else if(cmd.equals("3")) { // 3. �� �ܰ� Ȯ��
				if(!isExist) {
					System.out.println("������ ���°� �����ϴ�. ���¸� ���� ������ �ּ���");
				} else {
					System.out.println("���¸�: " + accountName);
					System.out.println("�ܾ�: " + (long)balance + "��");
				}
			} else if(cmd.equals("q")) {
				System.out.println("Bye ...");
				break;
			}
		}
		sc.close();
	}

}
