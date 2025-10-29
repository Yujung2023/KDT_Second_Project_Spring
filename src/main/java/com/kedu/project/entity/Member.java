package com.kedu.project.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "MEMBER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column(name = "ID", length = 200)
    private String id;   // 아이디 (PK)

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "ENGLISHNAME")
    private String englishName;

    @Column(name = "EMPLOYMENTTYPE", nullable = false)
    private String employmentType;

    @Temporal(TemporalType.DATE)
    @Column(name = "HIRE_DATE", nullable = false)
    private Date hire_date;

    @Column(name = "DEPT_CODE", nullable = false)
    private String dept_code;

    @Column(name = "RANK_CODE", nullable = false)
    private String rank_code;

    @Column(name = "JOB_CODE")
    private String job_code;

    @Column(name = "OFFICEEMAIL")
    private String officeEmail;

    @Column(name = "PERSONALEMAIL")
    private String personalEmail;

    @Column(name = "OFFICEPHONE")
    private String officePhone;

    @Column(name = "MOBILEPHONE")
    private String mobilePhone;

    @Column(name = "BIRTHDATE", nullable = false)
    private String birthDate;

    @Column(name = "CALENDARTYPE", nullable = false)
    private String calendarType;

    @Column(name = "ZIP_CODE")
    private String zip_code;

    @Column(name = "ADDRESS_LINE1")
    private String address_line1;

    @Column(name = "ADDRESS_LINE2")
    private String address_line2;

    @Column(name = "WORK_STATUS", nullable = false)
    private String work_status;

    @Column(name = "EMPLOYEE_NO", unique = true, nullable = false)
    private String employee_no;

    @Column(name = "PROFILEIMAGE_SERVNAME")
    private String profileImage_servName;

    @Column(name = "PROFILEIMAGE_ORINAME")
    private String profileImage_oriName;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_TIME", nullable = false, updatable = false)
    private Date created_time;
}
