package view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import controller.MemberDaoImpl;
import model.Member;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
@SuppressWarnings("serial")
public class MemberSignFrame extends JFrame {

    public interface MemberSignListener {
        void MemberSign();
    }
    
    
    private JPanel contentPane;
    private MemberSignListener listener;
    private Component parent;
    private MemberDaoImpl dao;
    private JTextField textName;
    private JTextField textPhone;
    private JTextField textCity;
    private JTextField textBirthday;
    private JLabel lblNewLabel;
    private JTextArea textQ;
    private JButton btnInsert;
    
    /**
     * Launch the application.
     */
    public static void newMemberSignFrame(Component parent, MemberSignListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberSignFrame frame = new MemberSignFrame(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    
    public MemberSignFrame(Component parent, MemberSignListener listener) {
    	setResizable(false);
        this.parent = parent;
        this.listener = listener;
        this.dao = MemberDaoImpl.getInstance();
        initialize();
    }
    
    /**
     * Create the frame.
     */
    public void initialize() {
        setTitle("신규 멤버 등록");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x + 500, y , 450, 258);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblName = new JLabel("이름");
        lblName.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setBounds(18, 32, 70, 20);
        contentPane.add(lblName);
        
        textName = new JTextField();
        textName.setBounds(100, 32, 116, 21);
        contentPane.add(textName);
        textName.setColumns(10);
        
        JLabel 전화번호 = new JLabel("전화번호");
        전화번호.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        전화번호.setHorizontalAlignment(SwingConstants.CENTER);
        전화번호.setBounds(18, 71, 70, 20);
        contentPane.add(전화번호);
        
        textPhone = new JTextField();
        textPhone.setColumns(10);
        textPhone.setBounds(100, 71, 116, 21);
        contentPane.add(textPhone);
        
        textCity = new JTextField();
        textCity.setBounds(100, 109, 116, 21);
        contentPane.add(textCity);
        textCity.setColumns(10);
        
        JLabel lblCity = new JLabel("지역/도시");
        lblCity.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        lblCity.setHorizontalAlignment(SwingConstants.CENTER);
        lblCity.setBounds(19, 109, 70, 20);
        contentPane.add(lblCity);
        
        JLabel lblBirthday = new JLabel("생년월일");
        lblBirthday.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        lblBirthday.setHorizontalAlignment(SwingConstants.CENTER);
        lblBirthday.setBounds(19, 150, 70, 20);
        contentPane.add(lblBirthday);
        
        textBirthday = new JTextField();
        textBirthday.setColumns(10);
        textBirthday.setBounds(100, 149, 116, 21);
        contentPane.add(textBirthday);
        
        lblNewLabel = new JLabel("메모");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        lblNewLabel.setBounds(303, 10, 57, 15);
        contentPane.add(lblNewLabel);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(239, 31, 183, 138);
        contentPane.add(scrollPane);
        
        textQ = new JTextArea();
        scrollPane.setViewportView(textQ);
        
        btnInsert = new JButton("등록");
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            memberInsert();
            }
        });
        btnInsert.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        btnInsert.setBounds(239, 186, 80, 23);
        contentPane.add(btnInsert);
        
        JButton btnNewButton = new JButton("취소");
        btnNewButton.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        btnNewButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	dispose();
        	}
        });
        btnNewButton.setBounds(342, 186, 80, 23);
        contentPane.add(btnNewButton);
    }


    private void memberInsert() {
       
    try {
        String memBirthDay = textBirthday.getText();
        String memName = textName.getText();
        String memPhone = telNumber(textPhone.getText());
        String memCity = textCity.getText();
        String memQ = textQ.getText();
        
        if(memName.equals("") || memPhone.equals("") || memCity.equals("")
                || memBirthDay.equals("") || memQ.equals("")){
            
            JOptionPane.showMessageDialog(
                    MemberSignFrame.this,
                    "빈 항목이 없도록 작성 해주세요.", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return; 
     }
    
     Member member = new Member(null, null, memBirthDay, memName, memPhone, memCity, memQ);
        int result = dao.insert(member);
        if (result == 1) { 
            JOptionPane.showMessageDialog(this, "멤버 등록 성공");
            dispose(); 
            
            listener.MemberSign();
        }else {
            JOptionPane.showMessageDialog(this, 
                    "멤버 등록 실패", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
      }catch (Exception e) {
    	  
    	  JOptionPane.showMessageDialog(this, 
                  "YYYY-MM-DD 로 입력해주세요.",
                  "Error",
                  JOptionPane.ERROR_MESSAGE);
          
          return;
      }

    }
    
    public static String telNumber(String number) {

        String regEx = "(\\d{2,3})(\\d{3,4})(\\d{4})"; 

          if(!Pattern.matches(regEx, number)){ 
            System.out.println("에러 1 : 형식 오류 ====> "+number.toString());
            return null; 
          } 

     
          if(number.substring(0,2).contains("02") && number.length() == 9){ 
            return number.replaceAll(regEx, "$1-$2-$3");
          } 

          else if(number.length() == 9){
            System.out.println("에러 2 : 자릿수 입력 오류 ====> "+number.toString()); 
            return null;
          }
        return number.replaceAll(regEx, "$1-$2-$3");
      } 
}