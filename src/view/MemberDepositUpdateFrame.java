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

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MemberDepositUpdateFrame extends JFrame {

    public interface MemberDepositUpdate{
        void MemberDepositUpdateView();
    }

    
    private Component parent;
    private MemberDepositUpdate listener;
    private MemberDaoImpl dao;
        
    private JPanel contentPane;
    private JTextField textDeposit_Date;
    private JTextField textName;
    private JLabel lblDeposit_Name;
    private JLabel lblDeposit_Date;
    private JTextField textDeposit;
    private JButton btnInsert;
    private JButton btnCancel;
    private Integer depositNo;
    

    /**
     * Launch the application.
     */
    public static void newMemberDepositUpdateFrame(Component parent, Integer depositNo, MemberDepositUpdate listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberDepositUpdateFrame frame = new MemberDepositUpdateFrame(parent, depositNo, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    
    
    
    public MemberDepositUpdateFrame(Component parent, Integer depositNo, MemberDepositUpdate listener){
    	setResizable(false);
        this.parent = parent;
        this.listener = listener;
        this.dao = MemberDaoImpl.getInstance();
        this.depositNo = depositNo;
        initialize();
        initializeMemberDepositInfo();
    }
    
    void initializeMemberDepositInfo() {
        Member member = dao.readDeposit(depositNo);
        textName.setText(member.getDepositName());
        textDeposit.setText(String.valueOf(member.getDeposit()));
        textDeposit_Date.setText(member.getDepositDate().toString());
    }
    
    /**
     * Create the frame.
     */
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("수정");
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x + 450, y, 283, 204);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        lblDeposit_Date = new JLabel("입금일");
        lblDeposit_Date.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblDeposit_Date.setBounds(39, 11, 43, 15);
        contentPane.add(lblDeposit_Date);
        
        textDeposit_Date = new JTextField();
        textDeposit_Date.setBounds(94, 10, 116, 21);
        contentPane.add(textDeposit_Date);
        textDeposit_Date.setColumns(10);
        
        lblDeposit_Name = new JLabel("이름");
        lblDeposit_Name.setHorizontalAlignment(SwingConstants.CENTER);
        lblDeposit_Name.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblDeposit_Name.setBounds(39, 50, 43, 15);
        contentPane.add(lblDeposit_Name);
        
        textName = new JTextField();
        textName.setColumns(10);
        textName.setBounds(94, 49, 116, 21);
        contentPane.add(textName);
        
        JLabel lblDeposit = new JLabel("금액");
        lblDeposit.setHorizontalAlignment(SwingConstants.CENTER);
        lblDeposit.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblDeposit.setBounds(39, 86, 43, 15);
        contentPane.add(lblDeposit);
        
        textDeposit = new JTextField();
        textDeposit.setColumns(10);
        textDeposit.setBounds(94, 85, 116, 21);
        contentPane.add(textDeposit);
        
        btnInsert = new JButton("수정");
        btnInsert.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            
                UpdateDeposit();
            
            }
        });
        btnInsert.setBounds(32, 132, 97, 23);
        contentPane.add(btnInsert);
        
        btnCancel = new JButton("취소");
        btnCancel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            }
        });
        btnCancel.setBounds(141, 132, 97, 23);
        contentPane.add(btnCancel);
    }

    @SuppressWarnings("unlikely-arg-type")
    private void UpdateDeposit() {

        try {
            String depositDate = textDeposit_Date.getText();
            String depositName = textName.getText();
            Integer deposit = Integer.parseInt(textDeposit.getText());
                    
            if (depositDate.equals("") || depositName.equals("")|| deposit.equals("") ){
                JOptionPane.showMessageDialog(this, 
                        "빈 항목이 존재 합니다.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                
                return;
                
                
            }

            Member member = new Member(depositNo, depositName, deposit, depositDate);
            
           int result = dao.updateDposit(member);
           
          
         
           if (result == 1) {
                
               listener.MemberDepositUpdateView();
                
                JOptionPane.showMessageDialog(parent, 
                        "수정 성공", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            
            } else {
                JOptionPane.showMessageDialog(parent, 
                        "수정 실패", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
            } catch (Exception e) {
//              e.printStackTrace();
                   JOptionPane.showMessageDialog(this, 
                            "YYYY-MM-DD 로 입력해주세요.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    
                    return;
            }
        
    }
}
