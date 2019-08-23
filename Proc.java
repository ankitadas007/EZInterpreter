package Assignment4;

public class Proc {
	private String name;
	private String[] command;
	private int count;
	
	public Proc() {
		name = "";
		command = new String[100];
		count = 0;
	}
	
	public Proc(String n) {
		name = n;
		command = new String[100];
		count = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public String[] getCommand() {
		return command;
	}
	
	
	public void addCommand(String comName) {
		command[count] = comName;
		count++;
	}
	

}
