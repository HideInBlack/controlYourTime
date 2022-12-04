package utils;

import service.clientS.PlanService;

/**
 * 线程工具类
 * 
 * @author 董
 *
 */
public class Th extends Thread {
	private final String name;

	public Th(String name) {
		this.name = name;
	}

	@Override
	public void run() {
		super.run();
		if(name.equals("t1")) {
			//PlanService.me.deletTodayExamplePlan(out, user_id, order);
		}else {
			//PlanService.me.importExamplePlan(out, user_id, id, order);
		}
	}

	public static void main(String[] args) throws Exception {
		Th t = new Th("t1");
		Th t2 = new Th("t2");
		t.start();
		t.join();
		t2.start();
	}
}
