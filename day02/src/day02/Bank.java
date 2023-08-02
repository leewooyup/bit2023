package day02;

public class Bank {
	int total;
	int[] result;
	
	public Bank(ResultDto resultDto) {
		total = resultDto.total;
		result = resultDto.result;
	}
	
	public double[] calReward() {
		double[] rewards = new double[6];
		long totalReward = (5000 * total)/2;
		int fourthCnt = result[4];
		int fifthCnt = result[5];
		totalReward -= (fourthCnt*50000) + (fifthCnt*5000);
		rewards[1] = totalReward * 0.75;
		rewards[2] = totalReward * 0.125;
		rewards[3] = rewards[2];
		rewards[4] = 50000;
		rewards[5] = 5000;
		return rewards;
	}
}
