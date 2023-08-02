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
			System.out.println("수행할 동작을 선택해주세요: 1. 복권사기 2. 내 계좌 개설 3. 내 잔고 확인 q. 종료");
			String cmd = sc.next();
			if(cmd.equals("1")) {
				balance -= 5000;
				int total = (int)(Math.random() * 30000) + 5000;
				int[] result = new int[6];
				int maxNum = 1;
				int cnt = 0;
				
				System.out.println("구매하실 복권의 종류를 선택해주세요: 1: 645Lotto 2: 540Lotto");
				cmd = sc.next();
				if(cmd.equals("1")) {
					maxNum = 45;
					cnt = 6;
				} else if(cmd.equals("2")) {
					maxNum = 40;
					cnt = 5;
				}
				
				System.out.println("복권 구매 방식을 선택해주세요: m: manual(수동) a: auto(자동)");
				String mode = sc.next();
				
				System.out.println("복권을 구매합니다(-5000)");
				Machine lottoMachine = new Machine(maxNum, cnt);
				List<Integer> winNum = lottoMachine.generate();
				Lottery lotto = new Lottery(maxNum, cnt, mode);
				List<Integer> myLotto = lotto.generate();
				
				System.out.println("[내 로또 번호]");
				for(int num : myLotto) {
					System.out.print(num + " ");
				}
				
				int rankIdx = calRank(winNum, myLotto);

				result[rankIdx]++;
				System.out.println();
				System.out.println();
				
				System.out.println("==================");
				System.out.println("제 " + round + "회차 당첨번호");
				System.out.println("==================");
				for(int i = 0; i < winNum.size(); i++) {
					System.out.print(winNum.get(i) + " ");
				}
				System.out.println();
				System.out.println();
				
				// 전체 rank 집계
				for(int i = 1; i < total; i++) {
					List<Integer> others = lottoMachine.generate();
					rankIdx = calRank(winNum, others);
					result[rankIdx]++;
				}
				
				// rank별 인원수 출력
				for(int i = 1; i < result.length; i++) {
					System.out.println(i + "등: " + result[i] + "명");
				}
				
				if(cmd.equals("2")) {
					System.out.println("※ 540Lotto는 4등까지 존재합니다");
				}
				
				System.out.println("(총 구매자수: " + total + "명)");
				System.out.println("(총 판매액: " + 5000 * total + "원)");
				System.out.println();
				
				ResultDto resultDto = new ResultDto(total, result);
				Bank bank = new Bank(resultDto);
				double[] rewards = bank.calReward();
				
				if(rankIdx == 1 || rankIdx == 2 || rankIdx == 3) {
					System.out.println("축하합니다!!");
				}
				
				System.out.println(rankIdx + "등에 당첨되셨습니다.");
				System.out.println(rankIdx + "등 당첨금: " + (long)rewards[rankIdx] + "원");
				
				if(isExist) {
					System.out.println("해당 금액(" + rewards[rankIdx] +"원)은 " + accountName + " 계좌로 입급되었습니다.");
				} else {
					System.out.println("해당 금액(" + rewards[rankIdx] +"원)을 입금할 계좌를 개설해주세요");
				}
				
				balance += rewards[rankIdx];
				
				round++;
			} else if(cmd.equals("2")) { // 2. 내 계좌 생성
				if(isExist) {
					System.out.println("이미 만들어진 계좌가 있습니다.");
				} else {
					isExist = true;
					System.out.println("계좌명을 입력해주세요");
					accountName = sc.next();
					System.out.println("첫 계좌를 생성하셨음으로 1,000,000원 입급했습니다.");
					balance += 1_000_000;
				}
			} else if(cmd.equals("3")) { // 3. 내 잔고 확인
				if(!isExist) {
					System.out.println("개설된 계좌가 없습니다. 계좌를 먼저 개설해 주세요");
				} else {
					System.out.println("계좌명: " + accountName);
					System.out.println("잔액: " + (long)balance + "원");
				}
			} else if(cmd.equals("q")) {
				System.out.println("Bye ...");
				break;
			}
		}
		sc.close();
	}

}
