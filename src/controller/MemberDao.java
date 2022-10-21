package controller;

import java.util.List;

import model.Member;

public interface MemberDao {
	  List<Member> read();
	    
	    Member read(Integer memNo);
	    
	    int insert (Member mem);
	    
	    int update (Member mem);
	    
	    int delete(Integer memNo);
	    
	    List<Member> read(int type, String keyword);
	    
	   // ========================
	    
	    List<Member>readNotice();

	    Member readNotice(Integer noticeNo);
	    
	    int insertNotice (Member mem);
	    
	    int updateNotice (Member mem);
	    
	    int deleteNotice(Integer noticeNo);
	    
	    List<Member> readNotice(int type, String keyword);
	    
	    
	    // 입금 관리
	    
	    List<Member>readDeposit();
	    
	    Member readDeposit(Integer depositNo);
	    
	    int insertDeposit(Member mem);
	    
	    int updateDposit(Member mem);
	    
	    int deleteDeposit(Integer depositNo);
	    
	    List<Member> readDeposit(int type, String keyword);
	    
	    // 사용내역 관리
	    

	    List<Member>readPay();
	    
	    Member readPay(Integer payNo);
	    
	    int insertPay(Member mem);
	    
	    int updatePay(Member mem);
	    
	    int deletePay(Integer payNo);
	    
	    List<Member> readPay(int type, String keyword);
	    
	    String totalDepositMoney();
	    
	    String usingDepositMoney();
}
