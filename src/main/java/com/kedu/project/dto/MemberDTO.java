package com.kedu.project.dto;

<<<<<<< HEAD
public class MemberDTO {

    // DB 컬럼명과 1:1 동일
    private Integer seq;
    private String id;
    private String member_name;
    private String password;
    private String nickName;      // DDL에 nickName으로 되어 있어 그대로 사용
    private Integer zip_code;
    private String address_line1;
    private String address_line2;
    private String status;
    private String employee_no;
    private String hire_date;
    private String job_code;
    private String rank_code;

    // 기본 생성자
    public MemberDTO() {}

    // 전체 생성자
    public MemberDTO(Integer seq, String id, String member_name, String password, String nickName,
                     Integer zip_code, String address_line1, String address_line2, String status,
                     String employee_no, String hire_date, String job_code, String rank_code) {
        this.seq = seq;
        this.id = id;
        this.member_name = member_name;
        this.password = password;
        this.nickName = nickName;
        this.zip_code = zip_code;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.status = status;
        this.employee_no = employee_no;
        this.hire_date = hire_date;
        this.job_code = job_code;
        this.rank_code = rank_code;
    }

    public Integer getSeq() { return seq; }
    public void setSeq(Integer seq) { this.seq = seq; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMember_name() { return member_name; }
    public void setMember_name(String member_name) { this.member_name = member_name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNickName() { return nickName; }
    public void setNickName(String nickName) { this.nickName = nickName; }

    public Integer getZip_code() { return zip_code; }
    public void setZip_code(Integer zip_code) { this.zip_code = zip_code; }

    public String getAddress_line1() { return address_line1; }
    public void setAddress_line1(String address_line1) { this.address_line1 = address_line1; }

    public String getAddress_line2() { return address_line2; }
    public void setAddress_line2(String address_line2) { this.address_line2 = address_line2; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getEmployee_no() { return employee_no; }
    public void setEmployee_no(String employee_no) { this.employee_no = employee_no; }

    public String getHire_date() { return hire_date; }
    public void setHire_date(String hire_date) { this.hire_date = hire_date; }

    public String getJob_code() { return job_code; }
    public void setJob_code(String job_code) { this.job_code = job_code; }

    public String getRank_code() { return rank_code; }
    public void setRank_code(String rank_code) { this.rank_code = rank_code; }
}
=======
import java.sql.Timestamp;

public class MemberDTO {
	private String id;
	private String password;
	private String name;
	private String englishName;
	private String employmentType;
	private String zip_code; 
	private String address_line1;
	private String address_line2;
	private String status;
	private String employee_no;
	private Timestamp hire_date;
	private String dept_code;
	private String job_code;
	private String rank_code; 
	private Timestamp created_time;
	private String officeEmail;
	private String personalEmail;
	private String officePhone;
	private String mobilePhone;
	private Timestamp birthDate;
	private String calendarType;
	
	
	
	
	public MemberDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MemberDTO(String id, String password, String name, String englishName, String employmentType,
			String zip_code, String address_line1, String address_line2, String status, String employee_no,
			Timestamp hire_date, String dept_code, String job_code, String rank_code, Timestamp created_time,
			String officeEmail, String personalEmail, String officePhone, String mobilePhone, Timestamp birthDate,
			String calendarType) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.englishName = englishName;
		this.employmentType = employmentType;
		this.zip_code = zip_code;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.status = status;
		this.employee_no = employee_no;
		this.hire_date = hire_date;
		this.dept_code = dept_code;
		this.job_code = job_code;
		this.rank_code = rank_code;
		this.created_time = created_time;
		this.officeEmail = officeEmail;
		this.personalEmail = personalEmail;
		this.officePhone = officePhone;
		this.mobilePhone = mobilePhone;
		this.birthDate = birthDate;
		this.calendarType = calendarType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getEmploymentType() {
		return employmentType;
	}
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getAddress_line1() {
		return address_line1;
	}
	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}
	public String getAddress_line2() {
		return address_line2;
	}
	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public Timestamp getHire_date() {
		return hire_date;
	}
	public void setHire_date(Timestamp hire_date) {
		this.hire_date = hire_date;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getJob_code() {
		return job_code;
	}
	public void setJob_code(String job_code) {
		this.job_code = job_code;
	}
	public String getRank_code() {
		return rank_code;
	}
	public void setRank_code(String rank_code) {
		this.rank_code = rank_code;
	}
	public Timestamp getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Timestamp created_time) {
		this.created_time = created_time;
	}
	public String getOfficeEmail() {
		return officeEmail;
	}
	public void setOfficeEmail(String officeEmail) {
		this.officeEmail = officeEmail;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public Timestamp getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Timestamp birthDate) {
		this.birthDate = birthDate;
	}
	public String getCalendarType() {
		return calendarType;
	}
	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}
	


	

	

}

>>>>>>> 29a2a6656e479b23beb52c2383a0387d3cd8d6d8
