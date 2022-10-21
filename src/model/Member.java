package model;

import java.sql.Blob;
import java.sql.Date;

public class Member {
	public interface Entity {
        String TBL_MEMBERLIST = "MEMBERLIST"; // 테이블 이름
        String COL_MEM_ID = "MEM_ID"; // 회원번호
        String COL_MEM_JOINDAY = "MEM_JOINDAY"; // 가입일
        String COL_MEM_BIRTHDAY = "MEM_BIRTHDAY"; // 생년월일
        String COL_MEM_NAME = "MEM_NAME"; // 멤버이름
        String COL_MEM_PHONE = "MEM_PHONE"; // 전화번호
        String COL_MEM_CITY = "MEM_CITY"; // 사는 지역
        String COL_MEM_Q = "MEM_Q"; // 비고

        // ========================================//
        String TBL_MEMBER_NOTICE = "MEMBER_NOTICE";
        String COL_NOTICE_NO = "NOTICE_NO";
        String COL_TITLE = "TITLE";
        String COL_CONTENT = "CONTENT";
        String COL_MEM_NAME_NOTICE = "MEM_NAME";
        String COL_LOCAL_DATE = "LOCAL_DATE";
        String COL_MODIFIE_DATE = "MODIFIE_DATE";
        
        // =========================================//
        
        
        String TBL_MEMBER_DEPOSIT = "MEM_DEPOSIT_LIST";
        String COL_DEPOSIT_NO = "DEPOSIT_NO";
        String COL_MEM_NAME_DEPOSIT = "MEM_NAME";
        String COL_MEM_DEPOSIT = "MEM_DEPOSIT";
        String COL_DEPOSIT_DATE = "DEPOSIT_DATE";
    
        // =========================================//
        
        String TBL_MEMBER_PAY = "MEM_PAY";
        String COL_MEM_PAY_NO = "PAY_NO";
        String COL_MEM_PAY_NAME = "MEM_NAME";
        String COL_MEM_PAY_DATE = "PAY_DATE";
        String COL_MEM_PAY_MONEY = "DEPOSIT_PAY";
        String COL_MEM_PAY_PLACE = "MEM_PLACE";
        String COL_MEM_PAY_BILL = "DEPOSIT_BILL";
    
        
    }
    
    private Integer memberId;
    private Date memJoinDay;
    private String memBirthDay;
    private String memName;
    private String memPhone;
    private String memCity;
    private String memQ;
    
    
    private Integer noticeNo;
    private String title;
    private String content;
    private String memNameNotice;
    private Date localDateTime;
    private Date modifieDateTime;
    

    private Integer depositNo;
    private String depositName;
    private Integer deposit;
    private String depositDate;
    
    
    private Integer payNo;
    private String payMemName;
    private String payDate;
    private Integer depositPay;
    private String memPlace;
    private Blob bill;
    
    
    
    
    
    
    public Integer getPayNo() {
        return payNo;
    }




    public void setPayNo(Integer payNo) {
        this.payNo = payNo;
    }




    public String getPayMemName() {
        return payMemName;
    }




    public void setPayMemName(String payMemName) {
        this.payMemName = payMemName;
    }




    public String getPayDate() {
        return payDate;
    }




    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }




    public Integer getDepositPay() {
        return depositPay;
    }




    public void setDepositPay(Integer depositPay) {
        this.depositPay = depositPay;
    }




    public String getMemPlace() {
        return memPlace;
    }




    public void setMemPlace(String memPlace) {
        this.memPlace = memPlace;
    }




    public Blob getBill() {
        return bill;
    }




    public void setBill(Blob bill) {
        this.bill = bill;
    }




    /**
     * @param payNo
     * @param payMemName
     * @param payDate
     * @param depositPay
     * @param memPlace
     * @param bill
     */
    public Member(Integer payNo, String payMemName, String payDate, Integer depositPay, String memPlace, Blob bill) {
        this.payNo = payNo;
        this.payMemName = payMemName;
        this.payDate = payDate;
        this.depositPay = depositPay;
        this.memPlace = memPlace;
        this.bill = bill;
    }




    /**
     * @param depositNo
     * @param depositName
     * @param deposit
     * @param depositDate
     */
    public Member(Integer depositNo, String depositName, Integer deposit, String depositDate) {
        this.depositNo = depositNo;
        this.depositName = depositName;
        this.deposit = deposit;
        this.depositDate = depositDate;
    }



 
    public Member(Integer memberId, Date memJoinDay, String memBirthDay, String memName, String memPhone, String memCity,
            String memQ) {
        this.memberId = memberId;
        this.memJoinDay = memJoinDay;
        this.memBirthDay = memBirthDay;
        this.memName = memName;
        this.memPhone = memPhone;
        this.memCity = memCity;
        this.memQ = memQ;
    }
    

    
    
    public Member(Integer noticeNo, String title, String content, String memNameNotice, Date localDateTime,
            Date modifieDateTime) {
        this.noticeNo = noticeNo;
        this.title = title;
        this.content = content;
        this.memNameNotice = memNameNotice;
        this.localDateTime = localDateTime;
        this.modifieDateTime = modifieDateTime;
    }


    



    public Integer getMemberId() {
        return memberId;
    }

    public Date getMemJoinDay() {
        return memJoinDay;
    }

    public String getMemBirthDay() {
        return memBirthDay;
    }

    public String getMemName() {
        return memName;
    }

    public String getMemPhone() {
        return memPhone;
    }

    public String getMemCity() {
        return memCity;
    }

    public String getMemQ() {
        return memQ;
    }
    
    

    public Integer getNoticeNo() {
        return noticeNo;
    }




    public void setNoticeNo(Integer noticeNo) {
        this.noticeNo = noticeNo;
    }




    public String getTitle() {
        return title;
    }




    public void setTitle(String title) {
        this.title = title;
    }




    public String getContent() {
        return content;
    }




    public void setContent(String content) {
        this.content = content;
    }




    public String getMemNameNotice() {
        return memNameNotice;
    }




    public void setMemNameNotice(String memNameNotice) {
        this.memNameNotice = memNameNotice;
    }




    public Date getLocalDateTime() {
        return localDateTime;
    }




    public void setLocalDateTime(Date localDateTime) {
        this.localDateTime = localDateTime;
    }




    public Date getModifieDateTime() {
        return modifieDateTime;
    }




    public void setModifieDateTime(Date modifieDateTime) {
        this.modifieDateTime = modifieDateTime;
    }


    


    public Integer getDepositNo() {
        return depositNo;
    }




    public void setDepositNo(Integer depositNo) {
        this.depositNo = depositNo;
    }




    public String getDepositName() {
        return depositName;
    }




    public void setDepositName(String depositName) {
        this.depositName = depositName;
    }




    public Integer getDeposit() {
        return deposit;
    }




    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }




    public String getDepositDate() {
        return depositDate;
    }




    public void setDepositDate(String depositDate) {
        this.depositDate = depositDate;
    }




    @Override
    public String toString() {
        return String.format("Member(MemberId = %d, MemJoinDay = %s, MemBirthDay = %s, MemName = %s, MemPhone = %s, MemCity = %s, MemQ = %s" 
                , memberId,memJoinDay,memBirthDay,memName,memPhone,memCity,memQ);
    }
}
