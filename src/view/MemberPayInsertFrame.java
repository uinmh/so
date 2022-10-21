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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class MemberPayInsertFrame extends JFrame {

    public interface MemberPayInsertNotify {
        void MemberPayInsertNotifyView();
    }
    private JPanel contentPane;
    private Component parent;
    private MemberPayInsertNotify listener;
    private MemberDaoImpl dao;
    private JTextField textDate;
    private JTextField textMoney;
    private JTextField textPlace;
    private JLabel lblName;
    private JTextField textName;
    private JButton btnInsert;
    private JButton btnCancel;
    

    /**
     * Launch the application.
     */
    public static void newMemberPayInsertFrame(Component parent, MemberPayInsertNotify listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberPayInsertFrame frame = new MemberPayInsertFrame(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public MemberPayInsertFrame(Component parent, MemberPayInsertNotify listener) {
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
        setTitle("지출 내역 등록");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x + 450, y , 275, 252);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblDate = new JLabel("지출 날짜");
        lblDate.setHorizontalAlignment(SwingConstants.CENTER);
        lblDate.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblDate.setBounds(12, 10, 76, 30);
        contentPane.add(lblDate);
        
        textDate = new JTextField();
        textDate.setBounds(100, 15, 116, 21);
        contentPane.add(textDate);
        textDate.setColumns(10);
        
        JLabel lblMoney = new JLabel("지출 금액");
        lblMoney.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblMoney.setHorizontalAlignment(SwingConstants.CENTER);
        lblMoney.setBounds(12, 50, 76, 30);
        contentPane.add(lblMoney);
        
        textMoney = new JTextField();
        textMoney.setColumns(10);
        textMoney.setBounds(100, 55, 116, 21);
        contentPane.add(textMoney);
        
        JLabel lblPlace = new JLabel("지출 장소");
        lblPlace.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblPlace.setHorizontalAlignment(SwingConstants.CENTER);
        lblPlace.setBounds(12, 90, 76, 30);
        contentPane.add(lblPlace);
        
        textPlace = new JTextField();
        textPlace.setColumns(10);
        textPlace.setBounds(100, 95, 116, 21);
        contentPane.add(textPlace);
        
        lblName = new JLabel("작성자");
        lblName.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setBounds(12, 130, 76, 30);
        contentPane.add(lblName);
        
        textName = new JTextField();
        textName.setColumns(10);
        textName.setBounds(100, 135, 116, 21);
        contentPane.add(textName);
        
        btnInsert = new JButton("등록");
        btnInsert.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            payInsert();
            }
        });
        btnInsert.setBounds(20, 175, 97, 23);
        contentPane.add(btnInsert);
        
        btnCancel = new JButton("취소");
        btnCancel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            }
        });
        btnCancel.setBounds(129, 175, 97, 23);
        contentPane.add(btnCancel);
    }


    @SuppressWarnings("unlikely-arg-type")
    private void payInsert() {
        
        try {
            String payDate = textDate.getText();
            String memName = textName.getText();
            String payPlace = textPlace.getText();
            Integer money = Integer.parseInt(textMoney.getText());
           
            if(memName.equals("") || payDate.equals("") || payPlace.equals("")
                    || (money.equals(""))){
                
                JOptionPane.showMessageDialog(
                        MemberPayInsertFrame.this,
                        "빈 항목이 없도록 작성 해주세요.", 
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return; 
         }
        
         Member member = new Member(null, memName, payDate, money, payPlace, null);
         
            int result = dao.insertPay(member);
            if (result == 1) { 
                JOptionPane.showMessageDialog(this, "등록 성공");
                dispose(); 
                
                listener.MemberPayInsertNotifyView();;
                
            }else {
                JOptionPane.showMessageDialog(this, 
                        "등록 실패", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
          }catch (Exception e) {
              e.printStackTrace();
              JOptionPane.showMessageDialog(this, 
                      "YYYY-MM-DD 로 입력해주세요.",
                      "Error",
                      JOptionPane.ERROR_MESSAGE);
              
              return;
          }
        
        
    }
}
