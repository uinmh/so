package controller;

import static model.Member.Entity.*;

public interface JdbcSql {
    // 문자열 포맷 : %s - 문자열, %d - 정수, %f - 실수

    // 전체 검색 - 회원정보 내림차순 정렬
    String SQL_SELECT_ALL = String.format(
            "select * from %s order by %s desc",
                    TBL_MEMBERLIST, COL_MEM_ID);
//  [회원관리] 회원 번호로 검색하기.
    String SQL_SELECT_BY_ID = String.format(
                    "select * from %s where %s = ?",
                    TBL_MEMBERLIST, COL_MEM_ID);

//  신규 멤버 저장.
    String SQL_INSERT = String.format(
                    "insert into %s (%s, %s, %s, %s, %s, %s) values (?,?,?,?,?,sysdate)", 
                    TBL_MEMBERLIST, COL_MEM_NAME, COL_MEM_CITY, COL_MEM_PHONE, COL_MEM_BIRTHDAY, COL_MEM_Q, COL_MEM_JOINDAY);
    
//  글 업데이트(수정). 
    String SQL_UPDATE = String.format(
                    "update %s set %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? where %s = ?",
                    TBL_MEMBERLIST, COL_MEM_JOINDAY, COL_MEM_BIRTHDAY, COL_MEM_NAME,COL_MEM_PHONE,COL_MEM_CITY,COL_MEM_Q,COL_MEM_ID);
//  멤버 삭제
    String SQL_DELETE = String.format(
                    "delete from %s where %s = ?",
                    TBL_MEMBERLIST, COL_MEM_ID);
    
//  이름으로 검색하기
    String SQL_SELECT_BY_NAME = String.format(
            "select * from %s where lower(%s) like ? order by %s desc",
            TBL_MEMBERLIST, COL_MEM_NAME, COL_MEM_ID);
//  지역으로 검색하기
    String SQL_SELECT_BY_CITY = String.format(
            "select * from %s where lower(%s) like ? order by %s desc",
            TBL_MEMBERLIST, COL_MEM_CITY, COL_MEM_ID);
//  전화번호로 검색하기
 String SQL_SELECT_BY_PHONE = String.format(
         "select * from %s where lower(%s) like ? order by %s desc",
         TBL_MEMBERLIST, COL_MEM_PHONE, COL_MEM_ID);    
// 가입월로 검색하기
String SQL_SELECT_BY_JOINDAY = String.format(
	"select * from %s where to_char(%s, 'MM') = ? order by %s",
   TBL_MEMBERLIST, COL_MEM_JOINDAY,COL_MEM_ID);    
    
// 생년월일로 검색하기
String SQL_SELECT_BY_BIRTHDAY = String.format(
    "select * from %s where to_char(%s, 'YYYY-MM-DD') = ? order by %s",
    TBL_MEMBERLIST, COL_MEM_BIRTHDAY,COL_MEM_ID);    
//===========================================================================

//게시물 등록번호 내림차순 정렬
String SQL_SELECT_BY_NOTICE = String.format(
        "select * from %s order by %s desc",
             TBL_MEMBER_NOTICE,COL_NOTICE_NO);

//게시글 번호로 검색하기.
String SQL_SELECT_BY_NOTICE_NO = String.format(
                "select * from %s where %s = ?",
                TBL_MEMBER_NOTICE, COL_NOTICE_NO);

//신규 글 저장.
String SQL_INSERT_NOTICE = String.format(
                "insert into %s (%s, %s, %s, %s, %s) values (?,?,?,sysdate,sysdate)", 
                TBL_MEMBER_NOTICE, COL_TITLE, COL_CONTENT, COL_MEM_NAME_NOTICE, COL_LOCAL_DATE, COL_MODIFIE_DATE);
//글 업데이트(수정). 
String SQL_UPDATE_NOTICE = String.format(
                "update %s set %s = ?, %s = ?, %s = ?, %s = sysdate where %s = ?",
                TBL_MEMBER_NOTICE, COL_TITLE, COL_CONTENT,COL_MEM_NAME_NOTICE,COL_MODIFIE_DATE,COL_NOTICE_NO);
//게시글 삭제
String SQL_DELETE_NOTICE = String.format(
                "delete from %s where %s = ?",
                TBL_MEMBER_NOTICE, COL_NOTICE_NO);



// ==================================================================================

// 회비 납입

// 입금 순으로 내림 차순 정렬
String SQL_SELECT_BY_DEPOSIT = String.format(
        "select * from %s order by %s desc",
             TBL_MEMBER_DEPOSIT, COL_DEPOSIT_DATE);
// 입금자 추가
String SQL_INSERT_DEPOSIT = String.format(
        "insert into %s (%s, %s, %s) values (?,?,?)", 
        TBL_MEMBER_DEPOSIT, COL_MEM_NAME_DEPOSIT, COL_MEM_DEPOSIT,COL_DEPOSIT_DATE);
// 입금 내역 수정. 
String SQL_UPDATE_DEPOSIT = String.format(
        "update %s set %s = ?, %s = ?, %s = ? where %s = ?",
              TBL_MEMBER_DEPOSIT, COL_MEM_NAME_DEPOSIT, COL_MEM_DEPOSIT, COL_DEPOSIT_DATE,COL_DEPOSIT_NO);

//입금 번호로 검색하기.
String SQL_SELECT_BY_DEPOSIT_NO = String.format(
              "select * from %s where %s = ?",
              TBL_MEMBER_DEPOSIT, COL_DEPOSIT_NO);
//게시글 삭제
String SQL_DELETE_DEPOSIT = String.format(
              "delete from %s where %s = ?",
              TBL_MEMBER_DEPOSIT, COL_DEPOSIT_NO);


//이름으로 검색하기
String SQL_SELECT_BY_DEPOSIT_NAME = String.format(
      "select * from %s where lower(%s) like ? order by %s desc",
      TBL_MEMBER_DEPOSIT, COL_MEM_NAME_DEPOSIT, COL_DEPOSIT_NO);
// 납입 "월"로 검색하기
String SQL_SELECT_BY_DEPOSIT_MONTH = String.format(

        "select * from %s where to_char(%s, 'MM') = ? order by %s desc",
        TBL_MEMBER_DEPOSIT, COL_DEPOSIT_DATE, COL_DEPOSIT_DATE);
// 납입 "년"으로 검색하기
String SQL_SELECT_BY_DEPOSIT_YEAR = String.format(

        "select * from %s where to_char(%s, 'YYYY') = ? order by %s desc",
        TBL_MEMBER_DEPOSIT, COL_DEPOSIT_DATE, COL_DEPOSIT_DATE);
// ==================================================================================

// 회비 지출
//글 작성순서 내림 차순 정렬
String SQL_SELECT_BY_PAY = String.format(
        "select * from %s order by %s desc",
        TBL_MEMBER_PAY, COL_MEM_PAY_NO);

// 지불 내역 등록
String SQL_INSERT_PAY = String.format(
     "insert into %s (%s, %s, %s, %s) values (?,?,?,?)", 
     TBL_MEMBER_PAY, COL_MEM_PAY_NAME, COL_MEM_PAY_DATE, COL_MEM_PAY_MONEY, COL_MEM_PAY_PLACE);

// 지출 내역 번호로 검색하기.
String SQL_SELECT_BY_PAY_NO = String.format(
              "select * from %s where %s = ?",
              TBL_MEMBER_PAY, COL_MEM_PAY_NO);
// 지출 내역 수정
String SQL_UPDATE_PAY = String.format(
        "update %s set %s = ?, %s = ?, %s = ?, %s = ? where %s = ?",
              TBL_MEMBER_PAY, COL_MEM_PAY_DATE, COL_MEM_PAY_MONEY, COL_MEM_PAY_PLACE, COL_MEM_PAY_NAME, COL_MEM_PAY_NO);
// 지출 내역 삭제
String SQL_DELETE_PAY = String.format(
            "delete from %s where %s = ?",
            TBL_MEMBER_PAY, COL_MEM_PAY_NO);

String SQL_SELECT_BY_PAYPLACE = String.format(
	      "select * from %s where lower(%s) like ? order by %s desc",
	      TBL_MEMBER_PAY, COL_MEM_PAY_PLACE, COL_MEM_PAY_DATE);

//지출 일자 검색하기
String SQL_SELECT_BY_PAY_DATE = String.format(
"select * from %s where to_char(%s, 'YYYY-MM') = ? order by %s",
TBL_MEMBER_PAY, COL_MEM_PAY_DATE, COL_MEM_PAY_DATE);    

// ==============================================================
//// 메인창 해당월 입금자 명단,
//String SQL_DEPOSIT_LIST = String.format("select %s %s, from %s m inner join %s d on m.lower(%s) = d.lower(%s) where to_char(d.%s, 'mm') = to_char (sysdate, 'mm')", 
//                                            COL_MEM_NAME COL_MEM_DEPOSIT)

// 회비 입금 총액 
String SQL_TOTAL_DEPOSIT_MONEY = String.format("select sum(%s) from %s", 
								COL_MEM_DEPOSIT, TBL_MEMBER_DEPOSIT);

// 지출 회비 총액 
String SQL_TOTAL_PAY_MONEY = String.format("select sum(%s) from %s", 
								COL_MEM_PAY_MONEY, TBL_MEMBER_PAY);
}
