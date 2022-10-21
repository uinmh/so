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

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MemberNoticeUpdateFrame extends JFrame {

    public interface MemberNoticeListener {
        void memberNoticeView();
    }
    
    private JPanel contentPane;
    private MemberNoticeListener listener;
    private MemberDaoImpl dao;
    private Component parent;
    private Integer noticeNo;
    private JTextField textLocalDate;
    private JTextField textTitle;
    private JLabel lblModifieDate;
    private JTextField textModifieDate;
    private JTextArea textContent;
    private JTextField textNameNotice;
    /**
     * Launch the application.
     */
    public static void newNoticeFrame(Component parent, Integer noticeNo, MemberNoticeListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberNoticeUpdateFrame frame = new MemberNoticeUpdateFrame(parent, noticeNo, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public MemberNoticeUpdateFrame(Component parent, Integer noticeNo, MemberNoticeListener listener) {
        this.parent = parent;
        this.listener = listener;
        this.dao = MemberDaoImpl.getInstance();
        this.noticeNo = noticeNo;
        initialize();
        initializeNoticeInfo();
    }
    
    void initializeNoticeInfo() {
        Member notice = dao.readNotice(noticeNo);
        textTitle.setText(notice.getTitle());
        textContent.setText(notice.getContent());
        textNameNotice.setText(notice.getMemNameNotice());
        textLocalDate.setText(notice.getLocalDateTime().toString());
        textModifieDate.setText(notice.getModifieDateTime().toString());
    }
    /**
     * Create the frame.
     */
    public void initialize() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        int x = parent.getX();
        int y = parent.getY();
        
        setTitle("메모");
        setBounds(x + 120, y + 50, 450, 430);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblLocalDate = new JLabel("작성일");
        lblLocalDate.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblLocalDate.setBounds(25, 310, 45, 22);
        contentPane.add(lblLocalDate);
        
        textLocalDate = new JTextField();
        textLocalDate.setEnabled(false);
        textLocalDate.setBounds(82, 310, 116, 21);
        contentPane.add(textLocalDate);
        textLocalDate.setColumns(10);
        
        textTitle = new JTextField();
        textTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        textTitle.setBounds(25, 28, 381, 21);
        contentPane.add(textTitle);
        textTitle.setColumns(10);
        
        lblModifieDate = new JLabel("수정일");
        lblModifieDate.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblModifieDate.setBounds(25, 342, 45, 22);
        contentPane.add(lblModifieDate);
        
        textModifieDate = new JTextField();
        textModifieDate.setEnabled(false);
        textModifieDate.setColumns(10);
        textModifieDate.setBounds(82, 345, 116, 21);
        contentPane.add(textModifieDate);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 62, 381, 238);
        contentPane.add(scrollPane);
        
        textContent = new JTextArea();
        textContent.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        scrollPane.setViewportView(textContent);
        
        JButton btnUpdate = new JButton("수정");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentUpdate();
            }
        });
        btnUpdate.setBounds(210, 344, 97, 22);
        contentPane.add(btnUpdate);
        
        JButton btnClose = new JButton("취소");
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnClose.setBounds(309, 344, 97, 22);
        contentPane.add(btnClose);
        
        JLabel lblnameNotice = new JLabel("작성자");
        lblnameNotice.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        lblnameNotice.setBounds(262, 310, 45, 22);
        contentPane.add(lblnameNotice);
        
        textNameNotice = new JTextField();
        textNameNotice.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        textNameNotice.setColumns(10);
        textNameNotice.setBounds(309, 310, 97, 21);
        contentPane.add(textNameNotice);
    }
    private void contentUpdate() {
        String title = textTitle.getText();
        String content = textContent.getText();
        String name = textNameNotice.getText();
        Date localDate = Date.valueOf(textLocalDate.getText());
        Date modifiedDate = Date.valueOf(textModifieDate.getText());
        
        if (title.equals("") || content.equals("") || name.equals("")){
            
            JOptionPane.showMessageDialog(
                    MemberNoticeUpdateFrame.this,
                    "빈 항목이 없도록 작성 해주세요.", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;     
        }
        Member member = new Member(noticeNo, title, content, name, localDate, modifiedDate);
        
        int result = dao.updateNotice(member);
       
        if (result == 1) {
            
            dispose();

         listener.memberNoticeView();
         
         JOptionPane.showMessageDialog(parent, 
                 "업데이트 성공", 
                 "Success", 
                 JOptionPane.INFORMATION_MESSAGE);
     } else {
         JOptionPane.showMessageDialog(parent, 
                 "업데이트 실패", 
                 "Error", 
                 JOptionPane.ERROR_MESSAGE);
     }
    
    }
}
