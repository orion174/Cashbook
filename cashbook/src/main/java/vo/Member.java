package vo;

public class Member {
	private String memberId;
	private String memberPw;
	private String name;
	private String gender;
	private int age;
	private String createDate;
	
	@Override
	public String toString() {
		return "Member [memberId=" + memberId + ", memberPw=" + memberPw + ", name=" + name + ", gender=" + gender
				+ ", age=" + age + ", createDate=" + createDate + "]";
	}

	// getter + setter
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
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

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/*
	public Member(String memberId, String memberPw, String name, String gender, int age, String createDate) {
		super();
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.createDate = createDate;
	}
	*/
}