package Library2;

public class Member {

	
	private String Id;  // 리스트를 String형으로 만들었기 때문에 우선은 String 형으로 시작해보기
	private String passwd;
	private String name;
	private String birth; // 생년월일과 전화번호는 형식 지정해야함
	private String phone;
	
	
	public Member() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Member(String Id, String passwd, String name,String birth , String phone) {
		super();
		this.Id= Id;
		this.passwd = passwd;
		this.name = name;
		this.birth = birth;
		this.phone= phone;
	}
	
	public String getId() {
		return Id;
	}
	
	public String getPasswd() {
		return passwd;
	}
	
	public void setId(String id) {
		Id = id;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String toString() {
		return "Member [Id=" + Id + ", passwd=" + passwd + ", name=" + name + ", birth=" + birth + ", phone=" + phone
				+ "]";
	}
	
}