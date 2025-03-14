package principal;

import java.util.Timer;
import java.util.TimerTask;

public class Cronometru {
	
	Timer cronometru = new Timer();
	public int maiEsteTimp = 100;
	public Cronometru(Panou panou) {
		cronometru.scheduleAtFixedRate(new TimerTask() {
			public Panou panouCronometru;
			public void run() {
				panouCronometru = panou;
				maiEsteTimp--;
				if(maiEsteTimp>0) {
					panouCronometru.ceas++;
				}
			}
		}, 1000, 1000);
	}
	
}
