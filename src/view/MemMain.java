package view;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.MemberDaoImpl;
import model.Member;
import view.MemberDepositFrame.MemberDepositListener;
import view.MemberDepositInsertFrame.MemberDepositInsert;
import view.MemberDetailFrame.MemberViewListener;
import view.MemberNoticeUpdateFrame.MemberNoticeListener;
import view.MemberNoticeInsertFrame.MemberNoticeInsert;
import view.MemberPayFrame.MemberPayNotify;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class MemMain 
                implements MemberViewListener,
                           MemberNoticeListener,
                           MemberNoticeInsert,
                           MemberDepositListener,
                           MemberPayNotify,
                           MemberDepositInsert
                           {
  
    private static final String[] COLUMN_NAMES_NOTICE = {
            "글 번호", "작성 일자", "제목", "작성자"          
          };
    private static final String[] COLUMN_NAMES_DEPOSIT= {
            "납부 번호", "납부 일자", "이름", "금액"          
          };
    private JFrame frame;
    private JButton btnDetailMem;
    private JTable pay_Table;
    private JTable notice_Table;
    private MemberDaoImpl dao;
    private DefaultTableModel notice_model;
    private DefaultTableModel deposit_model;
    private JTextField textMonth;
    java.util.Date now = new java.util.Date();
    
    SimpleDateFormat month = new SimpleDateFormat("MM");
    private String nowMonth = month.format(now);
    private JTextField textFinalMoney;
    
    
    
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemMain window = new MemMain();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    /**
     * Create the application.
     */
    public MemMain() {
        this.dao = MemberDaoImpl.getInstance();
        
        initialize();
        initializeNoticeTable();
        initializeDepositTable();
    }

    private void initializeNoticeTable() {
        
        notice_model = new DefaultTableModel(null,COLUMN_NAMES_NOTICE);
        
        notice_Table.setModel(notice_model);
        
        List<Member> list = dao.readNotice();
        
        for (Member m : list) {
            Object[] row = {
                    m.getNoticeNo(),m.getModifieDateTime(), m.getTitle(),m.getMemNameNotice()
            };
            notice_model.addRow(row);
        }
        // JTable 셀 정렬 (우측 중앙)
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer dtcrc = new DefaultTableCellRenderer();
        
        dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
        dtcrc.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        TableColumnModel tcm = notice_Table.getColumnModel();
        
        tcm.getColumn(0).setCellRenderer(dtcr);
        tcm.getColumn(1).setCellRenderer(dtcrc);
        tcm.getColumn(2).setCellRenderer(dtcrc);
        tcm.getColumn(3).setCellRenderer(dtcrc);
     
        //===========================
    }
    
private void initializeDepositTable() {
        
        deposit_model = new DefaultTableModel(null,COLUMN_NAMES_DEPOSIT);
        
        pay_Table.setModel(deposit_model);
        
        List<Member> list = dao.readDeposit();
        
        for (Member m : list) {
            Object[] row = {
                    m.getDepositNo(),m.getDepositDate(), m.getDepositName(), String.format("%,d",m.getDeposit())
            };
            deposit_model.addRow(row);
        }
    
        //==========================
        
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer dtcrc = new DefaultTableCellRenderer();
        
        dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
        dtcrc.setHorizontalAlignment(SwingConstants.CENTER);
        
        dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
        TableColumnModel tcm = pay_Table.getColumnModel();
        tcm.getColumn(0).setCellRenderer(dtcr);
        tcm.getColumn(1).setCellRenderer(dtcrc);
        tcm.getColumn(2).setCellRenderer(dtcrc);
        tcm.getColumn(3).setCellRenderer(dtcr);
     
        //===========================
}
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.getContentPane().setBackground(SystemColor.control);
        frame.setTitle("모임 회비 관리 ver 0.1");
        frame.setBounds(600, 300, 730, 504);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        btnDetailMem = new JButton("회원 목록");
        btnDetailMem.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnDetailMem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            MemberDetailFrame.newMemberDetailFrame(frame, MemMain.this);
            }
        });
        btnDetailMem.setBounds(41, 10, 106, 116);
        frame.getContentPane().add(btnDetailMem);
        
        JButton btnDetailPay = new JButton("회비 입금");
        btnDetailPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            MemberDepositFrame.newMemberDepositFrame(frame, MemMain.this);
            }
        });
        btnDetailPay.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnDetailPay.setBounds(171, 76, 120, 50);
        frame.getContentPane().add(btnDetailPay);
        
        JButton btnPayInfo = new JButton("지출 내역");
        btnPayInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberPayFrame.newMemberPayFrame(frame, MemMain.this);
            }
        });
        btnPayInfo.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        btnPayInfo.setBounds(171, 10, 120, 50);
        frame.getContentPane().add(btnPayInfo);
        
        JScrollPane scrollPane_Pay = new JScrollPane();
        scrollPane_Pay.setBounds(12, 176, 333, 275);
        frame.getContentPane().add(scrollPane_Pay);
        
        pay_Table = new JTable();
        pay_Table.setSelectionBackground(SystemColor.control);
        scrollPane_Pay.setViewportView(pay_Table);
      
        JScrollPane scrollPane_notice = new JScrollPane();
        scrollPane_notice.setBounds(357, 176, 345, 258);
        frame.getContentPane().add(scrollPane_notice);
        
        notice_Table = new JTable();
        scrollPane_notice.setViewportView(notice_Table);
        
        JButton btnNoticeDetailButton = new JButton("보기");
        btnNoticeDetailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            showNoiceFrame();
            }
        });
        btnNoticeDetailButton.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        btnNoticeDetailButton.setBounds(580, 435, 60, 23);
        frame.getContentPane().add(btnNoticeDetailButton);
        
        JButton btnNoticeDeleteButton = new JButton("삭제");
        btnNoticeDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            contentDelete();
            }
        });
        btnNoticeDeleteButton.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        btnNoticeDeleteButton.setBounds(642, 435, 60, 23);
        frame.getContentPane().add(btnNoticeDeleteButton);
        
        JLabel lblNotice = new JLabel("메모");
        lblNotice.setHorizontalAlignment(SwingConstants.CENTER);
        lblNotice.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblNotice.setBounds(446, 142, 150, 30);
        frame.getContentPane().add(lblNotice);
        
        JLabel lblPayment = new JLabel("입금 현황");
        lblPayment.setHorizontalAlignment(SwingConstants.CENTER);
        lblPayment.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        lblPayment.setBounds(96, 141, 150, 30);
        frame.getContentPane().add(lblPayment);
        
        JButton btnNoticeInsert = new JButton("작성");
        btnNoticeInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberNoticeInsertFrame.newMemberNoticeInsertFrame(frame, MemMain.this);
            }
        });
        btnNoticeInsert.setFont(new Font("맑은 고딕", Font.BOLD, 10));
        btnNoticeInsert.setBounds(516, 435, 60, 23);
        frame.getContentPane().add(btnNoticeInsert);
        int depositMoney = Integer.parseInt(dao.totalDepositMoney());
        int usingMoney = Integer.parseInt(dao.usingDepositMoney());
        
        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBounds(357, 10, 345, 128);
        frame.getContentPane().add(panel);
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("월 회비 잔액 현황");
        lblNewLabel.setBounds(105, 21, 188, 30);
        panel.add(lblNewLabel);
        lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        
        textMonth = new JTextField();
        textMonth.setBounds(52, 20, 50, 30);
        panel.add(textMonth);
        textMonth.setDisabledTextColor(new Color(255, 51, 102));
        textMonth.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        textMonth.setEnabled(false);
        textMonth.setHorizontalAlignment(SwingConstants.CENTER);
        textMonth.setBackground(SystemColor.control);
        textMonth.setColumns(10);
        textMonth.setBorder(BorderFactory.createEmptyBorder());
        textMonth.setText(nowMonth);
        
        textFinalMoney = new JTextField();
        textFinalMoney.setBounds(31, 62, 180, 50);
        panel.add(textFinalMoney);
        textFinalMoney.setDisabledTextColor(new Color(0, 153, 255));
        textFinalMoney.setSelectedTextColor(new Color(0, 153, 255));
        textFinalMoney.setForeground(SystemColor.infoText);
        textFinalMoney.setBackground(SystemColor.control);
        textFinalMoney.setSelectionColor(SystemColor.control);
        textFinalMoney.setEnabled(false);
        textFinalMoney.setHorizontalAlignment(SwingConstants.RIGHT);
        textFinalMoney.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        textFinalMoney.setBorder(BorderFactory.createEmptyBorder());
        textFinalMoney.setColumns(10);
        textFinalMoney.setText(String.format("%,d", (depositMoney - usingMoney)));
        
        JLabel lblNewLabel_1 = new JLabel("원");
        lblNewLabel_1.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        lblNewLabel_1.setBounds(224, 65, 37, 42);
        panel.add(lblNewLabel_1);
        
    }

    private void contentDelete() {
        int row = notice_Table.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(frame, 
                    "삭제할 행을 먼저 선택하세요",
                    "Warning", // 타이틀 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer noticeNo = (Integer) notice_model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, 
                "선택한 정보를 삭제 하시겠습니까?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
         
            int result = dao.deleteNotice(noticeNo);
            if (result == 1) {
                initializeNoticeTable();
                JOptionPane.showMessageDialog(frame, "삭제 성공");
            } else {
                JOptionPane.showMessageDialog(frame, "삭제 실패");
            }
        }
        
    }

    private void showNoiceFrame() {

            int row = notice_Table.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(frame, 
                        "리스트 값을 선택하세요.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
     
            Integer noticeNo = (Integer) notice_model.getValueAt(row, 0);

            MemberNoticeUpdateFrame.newNoticeFrame(frame, noticeNo, MemMain.this);
    }

    @Override 
    public void MemberViewFrame() { // 디테일 뷰 프레임
        initializeNoticeTable();
    }

    @Override
    public void memberNoticeView() {
        initializeNoticeTable();
    }

    @Override
    public void MemberNoticeInsertIn() {
        initializeNoticeTable();
    }

    @Override
    public void MemberDepositView() {
        initializeDepositTable();
        totalMoney();
        
    }

    @Override
    public void MemberPayNotifyView() {
    	totalMoney();
    }

    @Override
    public void MemberDepositInsertView() {
        initializeDepositTable();
        totalMoney();
    }    
    public void totalMoney() {
    	 int depositMoney = Integer.parseInt(dao.totalDepositMoney());
         int usingMoney = Integer.parseInt(dao.usingDepositMoney());
         textFinalMoney.setText(String.format("%,d", (depositMoney - usingMoney)));
    }
}