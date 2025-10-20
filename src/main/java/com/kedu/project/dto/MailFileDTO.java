package com.kedu.project.dto;

public class MailFileDTO {
        // PK
    private Long mailSeq;    // 메일 seq FK
    private String sysname;  // 서버에 저장된 파일명
    private String orgname;  // 원본 파일명

    // 생성자
    public MailFileDTO() {}
    public MailFileDTO(Long mailSeq, String sysname, String orgname) {
        this.mailSeq = mailSeq;
        this.sysname = sysname;
        this.orgname = orgname;
    }

    // getter / setter
  
    public Long getMailSeq() { return mailSeq; }
    public void setMailSeq(Long mailSeq) { this.mailSeq = mailSeq; }
    public String getSysname() { return sysname; }
    public void setSysname(String sysname) { this.sysname = sysname; }
    public String getOrgname() { return orgname; }
    public void setOrgname(String orgname) { this.orgname = orgname; }
}
