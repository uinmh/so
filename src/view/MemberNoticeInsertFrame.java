package view;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.MemberDaoImpl;
import model.Member;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MemberNoticeInsertFrame extends JFrame {
    
    public interface MemberNoticeInsert {
        void MemberNoticeInsertIn();
    }
    
        
    private JPanel contentPane;
    private Component parent;
    private MemberNoticeInsert listener;
    private MemberDaoImpl dao;
    private JTextField textTitle;
    private JTextArea textContent;
    private JTextField textName;
    private Integer noticeNo;
    /**
     * Launch the application.
     */
    public static void newMemberNoticeInsertFrame(Component parent, MemberNoticeInsert listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberNoticeInsertFrame frame = new MemberNoticeInsertFrame(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MemberNoticeInsertFrame(Component parent, MemberNoticeInsert listener) {
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
        setTitle("메모 작성");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        
        setBounds(x + 150, y + 50, 450, 416);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        
        textTitle = new JTextField();
        textTitle.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        textTitle.setBounds(40, 31, 366, 21);
        contentPane.add(textTitle);
        textTitle.setColumns(10);
        
                
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(40, 62, 366, 238);
        contentPane.add(scrollPane);
        
        textContent = new JTextArea();
        textContent.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        scrollPane.setViewportView(textContent);
        
        JButton btnInsert = new JButton("등록");
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noticeContentInsert();
            }
        });
        btnInsert.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        btnInsert.setBounds(200, 347, 97, 23);
        contentPane.add(btnInsert);
        
        JButton btnCamcel = new JButton("취소");
        btnCamcel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            dispose();
            }
        });
        btnCamcel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        btnCamcel.setBounds(309, 347, 97, 23);
        contentPane.add(btnCamcel);
        
        JLabel lblTitle = new JLabel("제목");
        lblTitle.setBounds(7, 32, 34, 18);
        contentPane.add(lblTitle);
        
        JLabel lblContent = new JLabel("내용");
        lblContent.setBounds(7, 170, 34, 15);
        contentPane.add(lblContent);
        
        JLabel lblName = new JLabel("작성자");
        lblName.setBounds(264, 315, 57, 15);
        contentPane.add(lblName);
        
        textName = new JTextField();
        textName.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        textName.setColumns(10);
        textName.setBounds(321, 310, 85, 21);
        contentPane.add(textName);
    }

    private void noticeContentInsert() {
        
        String title = textTitle.getText();
        String content = textContent.getText();
        String name = textName.getText();
        
        if (title.equals("") || content.equals("") || name.equals("")) {
            
            JOptionPane.showMessageDialog(
                    MemberNoticeInsertFrame.this,
                    "빈 항목이 없도록 작성 해주세요.", 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;     
        }
        
        Member member = new Member(noticeNo, title, content, name, null, null);
        
        int result = dao.insertNotice(member);
       
        if (result == 1) { 
            JOptionPane.showMessageDialog(this, "게시글 등록 성공");
            dispose(); 
            
            listener.MemberNoticeInsertIn();
            
        }else {
            JOptionPane.showMessageDialog(this, 
                    "게시글 등록 실패", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
        
                        
    }
}
