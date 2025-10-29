package com.kedu.project.dto;
import java.sql.Date;

public class MemberDTO {

    private String id;                         // 아이디
    private String password;                   // 비밀번호
    private String name;                       // 이름
    private String englishName;                // 영어이름
    private String employmentType;             // 근로형태
    private Date hire_date;                    // 입사일
    private String dept_code;                  // 부서
    private String rank_code;                  // 직위
    private String job_code;                   // 직무
    private String officeEmail;                // 회사 이메일
    private String personalEmail;              // 개인 이메일
    private String officePhone;                // 회사 번호
    private String mobilePhone;                // 개인 번호
    private String birthDate;                  // 생일
    private String calendarType;               // 양력/음력
    private String zip_code;                   // 주소 코드
    private String address_line1;              // 주소
    private String address_line2;              // 상세주소
    private String work_status;                // 근무상태
    private String employee_no;                // 사번(고유번호)
    private String profileImage_servName;      // 프로필 이미지 서버 이름
    private String profileImage_oriName;       // 프로필 이미지 원본 이름
    private String status;                     // 상태(현직, 퇴사)
    private Date created_time;                 // 가입일
    
    
    
    public MemberDTO() {};
	public MemberDTO(String id, String password, String name, String englishName, String employmentType, Date hire_date,
			String dept_code, String rank_code, String job_code, String officeEmail, String personalEmail,
			String officePhone, String mobilePhone, String birthDate, String calendarType, String zip_code,
			String address_line1, String address_line2, String work_status, String employee_no,
			String profileImage_servName, String profileImage_oriName, String status, Date created_time) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.englishName = englishName;
		this.employmentType = employmentType;
		this.hire_date = hire_date;
		this.dept_code = dept_code;
		this.rank_code = rank_code;
		this.job_code = job_code;
		this.officeEmail = officeEmail;
		this.personalEmail = personalEmail;
		this.officePhone = officePhone;
		this.mobilePhone = mobilePhone;
		this.birthDate = birthDate;
		this.calendarType = calendarType;
		this.zip_code = zip_code;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.work_status = work_status;
		this.employee_no = employee_no;
		this.profileImage_servName = profileImage_servName;
		this.profileImage_oriName = profileImage_oriName;
		this.status = status;
		this.created_time = created_time;
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
	public Date getHire_date() {
		return hire_date;
	}
	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}
	public String getDept_code() {
		return dept_code;
	}
	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}
	public String getRank_code() {
		return rank_code;
	}
	public void setRank_code(String rank_code) {
		this.rank_code = rank_code;
	}
	public String getJob_code() {
		return job_code;
	}
	public void setJob_code(String job_code) {
		this.job_code = job_code;
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
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getCalendarType() {
		return calendarType;
	}
	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
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
	public String getWork_status() {
		return work_status;
	}
	public void setWork_status(String work_status) {
		this.work_status = work_status;
	}
	public String getEmployee_no() {
		return employee_no;
	}
	public void setEmployee_no(String employee_no) {
		this.employee_no = employee_no;
	}
	public String getProfileImage_servName() {
		return profileImage_servName;
	}
	public void setProfileImage_servName(String profileImage_servName) {
		this.profileImage_servName = profileImage_servName;
	}
	public String getProfileImage_oriName() {
		return profileImage_oriName;
	}
	public void setProfileImage_oriName(String profileImage_oriName) {
		this.profileImage_oriName = profileImage_oriName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreated_time() {
		return created_time;
	}
	public void setCreated_time(Date created_time) {
		this.created_time = created_time;
	}

    
    
    
}