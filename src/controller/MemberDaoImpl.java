package controller;

import static controller.JdbcSql.*;
import static model.Member.Entity.*;
import static ojdbc.OracleJdbc.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Member;
import oracle.jdbc.driver.OracleDriver;

public class MemberDaoImpl implements MemberDao {
    // Singleton 적용
  private static MemberDaoImpl instance = null;
  
  private MemberDaoImpl() {}
  
  public static MemberDaoImpl getInstance() {
      if (instance == null) {
          instance = new MemberDaoImpl();
      }
      
      return instance;
  }

  private Connection getConnection() throws SQLException {
      DriverManager.registerDriver(new OracleDriver());
      return DriverManager.getConnection(URL, USER, PASSWORD);
  }

  private void closeResources(Connection conn, Statement stmt) throws SQLException {
      stmt.close();
      conn.close();
  }

  private void closeResources(Connection conn, Statement stmt, ResultSet rs) 
          throws SQLException {
      rs.close();
      closeResources(conn, stmt);
  }
  @Override
  public List<Member> read() {

      List<Member> list = new ArrayList<>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_SELECT_ALL);
          
          rs = stmt.executeQuery();
          while (rs.next()) {
              
              Integer memId = rs.getInt(COL_MEM_ID);
              Date memJoinDay = rs.getDate(COL_MEM_JOINDAY);
              String memBirthDay = rs.getDate(COL_MEM_BIRTHDAY).toString();
              String memName = rs.getString(COL_MEM_NAME);
              String memPhone = rs.getString(COL_MEM_PHONE);
              String memCity = rs.getString(COL_MEM_CITY);
              String memQ = rs.getString(COL_MEM_Q);
              
              Member member = new Member(memId, memJoinDay, memBirthDay, memName, memPhone, memCity, memQ);
              
              list.add(member);
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      

  return list;

  }

  @Override
  public Member read(Integer memNo) {
     
      Member member = null;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_SELECT_BY_ID);
          stmt.setInt(1, memNo);
          
          rs = stmt.executeQuery();
          if(rs.next()) {
              Date memJoinDay = rs.getDate(COL_MEM_JOINDAY);
              String memBirthDay = rs.getDate(COL_MEM_BIRTHDAY).toString();
              String memName = rs.getString(COL_MEM_NAME);
              String memPhone = rs.getString(COL_MEM_PHONE);
              String memCity = rs.getString(COL_MEM_CITY);
              String memQ = rs.getString(COL_MEM_Q);
            
              member = new Member(memNo, memJoinDay, memBirthDay, memName, memPhone,
                    memCity, memQ);
             
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return member;
  }

  @Override
  public int insert(Member mem) {
      
      int result = 0;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_INSERT);
          
          stmt.setString(1, mem.getMemName());
          stmt.setString(2, mem.getMemCity());
          stmt.setString(3, mem.getMemPhone());
          stmt.setDate(4, transformDate(mem.getMemBirthDay()));
          stmt.setString(5, mem.getMemQ());
                  
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
        try {
          closeResources(conn, stmt);
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }  
      }
      
      
      return result;
  }

  @Override
  public int update(Member mem) {
    int result = 0;
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        conn = getConnection();
        stmt = conn.prepareStatement(SQL_UPDATE);
        stmt.setDate(1, mem.getMemJoinDay());
        stmt.setDate(2, Date.valueOf(mem.getMemBirthDay()));
        stmt.setString(3, mem.getMemName());
        stmt.setString(4, mem.getMemPhone());
        stmt.setString(5, mem.getMemCity());
        stmt.setString(6, mem.getMemQ());
        stmt.setInt(7,mem.getMemberId());
        
        result = stmt.executeUpdate();
        
    } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    } finally {
        try {
            closeResources(conn, stmt);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    return result;
  }

  @Override
  public int delete(Integer memNo) {
      int result = 0;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_DELETE);
          stmt.setInt(1, memNo);
          
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      
      return result;
  }

  @Override
  public List<Member> read(int type, String keyword) {
     
      List<Member> list = new ArrayList<>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          
          switch (type){
          case 0: // 이름
              stmt = conn.prepareStatement(SQL_SELECT_BY_NAME);
              stmt.setString(1, "%" + keyword.toLowerCase() + "%");
              break;
          case 1: //  지역 
              stmt = conn.prepareStatement(SQL_SELECT_BY_CITY);
              stmt.setString(1, "%" + keyword.toLowerCase() + "%");
              break;
          case 2: // 전화번호검색
              stmt = conn.prepareStatement(SQL_SELECT_BY_PHONE);
              stmt.setString(1, "%" + keyword.toLowerCase() + "%");
              break;
          case 3: // 가입월 검색
              stmt = conn.prepareStatement(SQL_SELECT_BY_JOINDAY);
              stmt.setString(1, keyword.toLowerCase());
              break;
          case 4: // 생년월일 검색
              stmt = conn.prepareStatement(SQL_SELECT_BY_BIRTHDAY);
              stmt.setString(1, keyword.toLowerCase());
              break;
          default:
          }
          
          rs = stmt.executeQuery();
          while (rs.next()) {
              Integer memId = rs.getInt(COL_MEM_ID);
              Date memJoinDay = rs.getDate(COL_MEM_JOINDAY);
              String memBirthDay = rs.getDate(COL_MEM_BIRTHDAY).toString();
              String memName = rs.getString(COL_MEM_NAME);
              String memPhone = rs.getString(COL_MEM_PHONE);
              String memCity = rs.getString(COL_MEM_CITY);
              String memQ = rs.getString(COL_MEM_Q);
              
              Member member = new Member(memId, memJoinDay, memBirthDay, memName, memPhone, memCity, memQ);
              list.add(member);
          }
          
          
          
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
        try {
          closeResources(conn, stmt, rs);
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }  
      }
      
      return list;
  }

  @Override
  public List<Member> readNotice() {
      
 List<Member> list = new ArrayList<>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_SELECT_BY_NOTICE);
          
          rs = stmt.executeQuery();
          while (rs.next()) {
              
              Integer noticeNo = rs.getInt(COL_NOTICE_NO);
              Date LocalDate = rs.getDate(COL_LOCAL_DATE);
              Date modifieDate = rs.getDate(COL_MODIFIE_DATE);
              String noticeName = rs.getString(COL_MEM_NAME_NOTICE);
              String title = rs.getString(COL_TITLE);
              String content = rs.getString(COL_CONTENT);
              
            Member member = new Member(noticeNo, title, content, noticeName, LocalDate, modifieDate);
              
              list.add(member);
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      

  return list;
  }

  @Override
  public Member readNotice(Integer noticeNo) {
 
      
      Member member = null;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_SELECT_BY_NOTICE_NO);
          stmt.setInt(1, noticeNo);
          
          rs = stmt.executeQuery();
          if(rs.next()) {
//              Integer No = rs.getInt(COL_NOTICE_NO);
              Date LocalDate = rs.getDate(COL_LOCAL_DATE);
              Date modifieDate = rs.getDate(COL_MODIFIE_DATE);
              String noticeName = rs.getString(COL_MEM_NAME_NOTICE);
              String title = rs.getString(COL_TITLE);
              String content = rs.getString(COL_CONTENT);
            
              member = new Member(noticeNo, title, content, noticeName, LocalDate, modifieDate);
              
              
             
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return member;
  }
  
  
  @Override
  public int insertNotice(Member mem) {
      
      int result = 0;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_INSERT_NOTICE);
          
          stmt.setString(1, mem.getTitle());
          stmt.setString(2, mem.getContent());
          stmt.setString(3, mem.getMemNameNotice());
                  
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
        try {
          closeResources(conn, stmt);
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }  
      }
      
      
      return result;
  }

  @Override
  public int updateNotice(Member mem) {
    
      int result = 0;
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_UPDATE_NOTICE);
          stmt.setString(1, mem.getTitle());
          stmt.setString(2, mem.getContent());
          stmt.setString(3, mem.getMemNameNotice());
          stmt.setInt(4, mem.getNoticeNo());
          
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return result;
  }

  @Override
  public int deleteNotice(Integer noticeNo) {
      
      int result = 0;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_DELETE_NOTICE);
          stmt.setInt(1, noticeNo);
          
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      
      return result;
  }

  @Override
  public List<Member> readNotice(int type, String keyword) {
      // TODO Auto-generated method stub
      return null;
  }

  @Override
  public List<Member> readDeposit() {
      
   List<Member> list = new ArrayList<>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_SELECT_BY_DEPOSIT);
          
          rs = stmt.executeQuery();
          while (rs.next()) {
              
              Integer depositNo = rs.getInt(COL_DEPOSIT_NO);
              String depositDate = rs.getDate(COL_DEPOSIT_DATE).toString();
              String depositName = rs.getString(COL_MEM_NAME_DEPOSIT);
              Integer deposit = rs.getInt(COL_MEM_DEPOSIT);
              Member member = new Member(depositNo, depositName, deposit, depositDate);
                              
              list.add(member);
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      

  return list;
  }

  @Override
  public Member readDeposit(Integer depositNo) {

      Member member = null;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_SELECT_BY_DEPOSIT_NO);
          stmt.setInt(1, depositNo);
          
          rs = stmt.executeQuery();
          if(rs.next()) {
              Integer no = rs.getInt(COL_DEPOSIT_NO);
              String depositDate = rs.getDate(COL_DEPOSIT_DATE).toString();
              String depositName = rs.getString(COL_MEM_NAME_DEPOSIT);
              Integer deposit = rs.getInt(COL_MEM_DEPOSIT);
              member = new Member(no, depositName, deposit, depositDate);
                  
             
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return member;
  }

  @Override
  public int insertDeposit(Member mem) {
      
      int result = 0;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_INSERT_DEPOSIT);
          stmt.setString(1, mem.getDepositName());
          stmt.setInt(2, mem.getDeposit());
          stmt.setDate(3, transformDate(mem.getDepositDate()));
                  
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
        try {
          closeResources(conn, stmt);
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }  
      }
      
      
      return result;
  }

  @Override
  public int updateDposit(Member mem) {
      int result = 0;
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_UPDATE_DEPOSIT);
          stmt.setString(1, mem.getDepositName());
          stmt.setInt(2, mem.getDeposit());
          stmt.setDate(3, Date.valueOf(mem.getDepositDate()));
          stmt.setInt(4, mem.getDepositNo());
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return result;
  }

  @Override
  public int deleteDeposit(Integer depositNo) {

  	int result = 0;
 
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_DELETE_DEPOSIT);
          stmt.setInt(1, depositNo);
          
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      
      return result;
  }

  @Override
  public List<Member> readDeposit(int type, String keyword) {

      
      List<Member> list = new ArrayList<>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          
          switch (type){
          case 0: // 이름
              stmt = conn.prepareStatement(SQL_SELECT_BY_DEPOSIT_NAME);
              stmt.setString(1, "%" + keyword.toLowerCase() + "%");
              break;
          case 1: //  월
              stmt = conn.prepareStatement(SQL_SELECT_BY_DEPOSIT_MONTH);
              stmt.setString(1, keyword.toLowerCase());
              break;
          case 2: // 년도
              stmt = conn.prepareStatement(SQL_SELECT_BY_DEPOSIT_YEAR);
              stmt.setString(1,keyword.toLowerCase());
              break;
          default:
          }
          
          rs = stmt.executeQuery();
          while (rs.next()) {
              Integer no = rs.getInt(COL_DEPOSIT_NO);
              String depositDate = rs.getDate(COL_DEPOSIT_DATE).toString();
              String depositName = rs.getString(COL_MEM_NAME_DEPOSIT);
              Integer deposit = rs.getInt(COL_MEM_DEPOSIT);
              Member member = new Member(no, depositName, deposit, depositDate);
              list.add(member);
          }

      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
        try {
          closeResources(conn, stmt, rs);
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }  
  }
      
      return list;
}

  
  // 사용내역 관리 기능
  
  @Override
  public List<Member> readPay() {
      
      List<Member> list = new ArrayList<>();
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_SELECT_BY_PAY);
          
          rs = stmt.executeQuery();
          while (rs.next()) {
              
              Integer payNo = rs.getInt(COL_MEM_PAY_NO);
              Integer depositPay = rs.getInt(COL_MEM_PAY_MONEY);
              String payDate = rs.getDate(COL_MEM_PAY_DATE).toString();
              String memName = rs.getString(COL_MEM_PAY_NAME);
              String payPlace = rs.getString(COL_MEM_PAY_PLACE);
              Blob bill = rs.getBlob(COL_MEM_PAY_BILL);
              
              Member member = new Member(payNo, memName, payDate, depositPay, payPlace, bill);
             
              
              list.add(member);
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      

  return list;
  }

  @Override
  public Member readPay(Integer payNo) {

      Member member = null;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_SELECT_BY_PAY_NO);
          stmt.setInt(1, payNo);
          rs = stmt.executeQuery();
          if(rs.next()) {
              
              Integer no = rs.getInt(COL_MEM_PAY_NO);
              Integer depositPay = rs.getInt(COL_MEM_PAY_MONEY);
              String payDate = rs.getDate(COL_MEM_PAY_DATE).toString();
              String memName = rs.getString(COL_MEM_PAY_NAME);
              String payPlace = rs.getString(COL_MEM_PAY_PLACE);
//              Blob bill = rs.getBlob(COL_MEM_PAY_BILL);
              
              member = new Member(no, memName, payDate, depositPay, payPlace, null);
              
              
             
          }
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt, rs);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return member;
      
      
  }

  @Override
  public int insertPay(Member mem) {
  int result = 0;
      
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try { // 이름 날짜 돈 장소 사진
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_INSERT_PAY);
          stmt.setString(1, mem.getPayMemName());
          stmt.setDate(2, transformDate(mem.getPayDate()));
          stmt.setInt(3, mem.getDepositPay());
          stmt.setString(4, mem.getMemPlace());
//          stmt.setBigDecimal(5, null); // 영수증 사진 첨부
                  
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
        try {
          closeResources(conn, stmt);
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }  
      }
      
      
      return result;
  }

  @Override
  public int updatePay(Member mem) {


      int result = 0;
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          stmt = conn.prepareStatement(SQL_UPDATE_PAY);
          stmt.setDate(1, Date.valueOf(mem.getPayDate()));
          stmt.setInt(2, mem.getDepositPay());
          stmt.setString(3, mem.getMemPlace());
          stmt.setString(4, mem.getPayMemName());
          stmt.setInt(5, mem.getPayNo());
//          stmt.setBigDecimal(5, null); // 영수증 사진 첨부
          
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      return result;
  }

  @Override
  public int deletePay(Integer payNo) {
  	
  	int result = 0;
  	   
      Connection conn = null;
      PreparedStatement stmt = null;
      
      try {
          conn = getConnection();
          
          stmt = conn.prepareStatement(SQL_DELETE_PAY);
          stmt.setInt(1, payNo);
          
          result = stmt.executeUpdate();
          
      } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } finally {
          try {
              closeResources(conn, stmt);
          } catch (SQLException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }
      }
      
      return result;
  }

  @Override
  public List<Member> readPay(int type, String keyword) {
  	 
  	 List<Member> list = new ArrayList<>();
       
       Connection conn = null;
       PreparedStatement stmt = null;
       ResultSet rs = null;
       
       try {
           conn = getConnection();
           
           switch (type){
           case 0: // 지출 장소
               stmt = conn.prepareStatement(SQL_SELECT_BY_PAYPLACE);
               stmt.setString(1, "%" + keyword.toLowerCase() + "%");
               break;
           case 1: // 지출 날짜
               stmt = conn.prepareStatement(SQL_SELECT_BY_PAY_DATE);
               stmt.setString(1, keyword.toLowerCase());
               break;
           default:
           }
           
           rs = stmt.executeQuery();
           
           while (rs.next()) {
          	 Integer no = rs.getInt(COL_MEM_PAY_NO);
               Integer depositPay = rs.getInt(COL_MEM_PAY_MONEY);
               String payDate = rs.getDate(COL_MEM_PAY_DATE).toString();
               String memName = rs.getString(COL_MEM_PAY_NAME);
               String payPlace = rs.getString(COL_MEM_PAY_PLACE);
//               Blob bill = rs.getBlob(COL_MEM_PAY_BILL);
               
               Member member = new Member(no, memName, payDate, depositPay, payPlace, null);
               list.add(member);
           }

       } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } finally {
         try {
           closeResources(conn, stmt, rs);
       } catch (SQLException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }  
   }
       
       return list;
  }


	@Override
	public String totalDepositMoney() {
			
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String totalDeposit = null ;
	    
	    try {
			conn = getConnection();
			stmt = conn.prepareStatement(SQL_TOTAL_DEPOSIT_MONEY);
			rs = stmt.executeQuery();
			while(rs.next()) {
			totalDeposit =	rs.getString("sum(" + COL_MEM_DEPOSIT + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
		return totalDeposit;
	}

	@Override
	public String usingDepositMoney() {
	
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String totalPay = null ;
	    
	    try {
			conn = getConnection();
			stmt = conn.prepareStatement(SQL_TOTAL_PAY_MONEY);
			rs = stmt.executeQuery();
			while(rs.next()) {
			totalPay =	rs.getString("sum(" + COL_MEM_PAY_MONEY + ")");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				closeResources(conn, stmt);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    
		return totalPay;
	}
	
	public Date transformDate(String date) {
		
		SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
		
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-mm-dd");
		
		java.util.Date tempDate = null;
				
		try {
			tempDate = beforeFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String transDate = afterFormat.format(tempDate);
		
		Date d = Date.valueOf(transDate);
		
		return d;
	}
}
