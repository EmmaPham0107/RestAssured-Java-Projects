package lab8_Example;

public class UserInfo {

	private String name;
	private String gender;
	private int age;
	private String job;
	
	public UserInfo (String name, String gender, int age, String job) {
		setName(name);
		setGender(gender);
		setAge(age);
		setJob(job);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
}
