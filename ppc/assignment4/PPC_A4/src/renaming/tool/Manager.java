package renaming.tool;

import library.Actor;
import library.Message;
import library.SystemKillMessage;

public class Manager extends Actor {

	private int numEmployees;
	private Employee[] employees;
	private int nextFreeEmployee = 0;

	public Manager(int numEmployees) {

		this.numEmployees = numEmployees;
		this.employees = new Employee[numEmployees];
		for (int i=0; i<numEmployees; i++) {
			this.employees[i] = new Employee(this.getAddress());
		}
	}

	@Override
	protected void handleMessage(Message m) {
		if (m instanceof SystemKillMessage)  {
			for (Employee e : employees) {
				e.getAddress().sendMessage(m);
			}
		} else if (m instanceof WorkOnProblemMessage) {
			//System.out.println("Manager received WorkOnProblemMessage");
			employees[nextFreeEmployee].getAddress().sendMessage(m);
			nextFreeEmployee = (nextFreeEmployee + 1) % employees.length;
		}
	}
}
