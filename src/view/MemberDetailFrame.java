package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.MemberDaoImpl;
import model.Member;
import view.MemberSignFrame.MemberSignListener;
import view.MemberUpdateFrame.MemberUpdateListener;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import java.awt.Font;


import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MemberDetailFrame extends JFrame 
            implements MemberUpdateListener, MemberSignListener{
    
  private static final String[] COLUMN_NAMES = {
    "회원 번호","이름","휴대 전화","생년월일","거주지"
  };
    
    public interface MemberViewListener {
        void MemberViewFrame();
    }

    private JPanel contentPane;
    private Component parent;
    private JComboBox<String> comboBox;
    private DefaultTableModel model;
    private MemberDaoImpl dao;
    private JTable table;
    private JTextField textKeyword;
    
    /**
     * Launch the application.
     */
    public static void newMemberDetailFrame(Component parent, MemberViewListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberDetailFrame frame = new MemberDetailFrame(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public MemberDetailFrame(Component parent, MemberViewListener listener) {
    	setResizable(false);
        this.parent = parent;
        dao = MemberDaoImpl.getInstance();
        initialize(); 
        initializeTable();
    }

    private void initializeTable() {
        
        model = new DefaultTableModel(null,COLUMN_NAMES);
        
        table.setModel(model);
        
        List<Member> list = dao.read();
        
        for (Member m : list) {
            Object[] row = {
                m.getMemberId(),m.getMemName(),m.getMemPhone(),m.getMemBirthDay(),m.getMemCity()
            };
            model.addRow(row);
        }
        // JTable 셀 정렬 (우측 중앙)
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer dtcrc = new DefaultTableCellRenderer();
        
        dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
        dtcrc.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        TableColumnModel tcm = table.getColumnModel();
        
        tcm.getColumn(0).setCellRenderer(dtcr);
        tcm.getColumn(1).setCellRenderer(dtcrc);
        tcm.getColumn(2).setCellRenderer(dtcrc);
        tcm.getColumn(3).setCellRenderer(dtcrc);
        tcm.getColumn(4).setCellRenderer(dtcrc);
    }
    /**
     * Create the frame.
     */
	public void initialize() {
        setTitle("회원 관리 정보창");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x + 100, y, 510, 500);
        contentPane = new JPanel();
        contentPane.setAlignmentX(Component.RIGHT_ALIGNMENT);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JButton btnUpdateMem = new JButton("자세히/수정");
        btnUpdateMem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            showUpdateFrame();
            }
        });
        btnUpdateMem.setBounds(183, 402, 110, 31);
        contentPane.add(btnUpdateMem);
        btnUpdateMem.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        
        comboBox = new JComboBox<>();
        comboBox.setBounds(66, 365, 93, 27);
        comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        
        String[] comboBoxItems = { "이름", "지역", "전화번호", "가입월", "생년월일"};
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(comboBoxItems);
        comboBox.setModel(comboBoxModel);
        comboBox.setSelectedIndex(0);
        contentPane.add(comboBox);
        
        textKeyword = new JTextField();
        textKeyword.setBounds(163, 365, 178, 27);
        contentPane.add(textKeyword);
        textKeyword.setToolTipText("입력하세요.");
        textKeyword.setHorizontalAlignment(SwingConstants.LEFT);
        textKeyword.setColumns(15);
        
        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMemberByKeyword();
            }
        });
        btnSearch.setBounds(348, 365, 63, 29);
        contentPane.add(btnSearch);
        btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 42, 470, 310);
        contentPane.add(scrollPane,BorderLayout.CENTER);
        
        table = new JTable();
        table.setAlignmentY(0.0f);
        table.setAlignmentX(0.0f);
        scrollPane.setViewportView(table);
        JLabel lblNewLabel = new JLabel("회원 명단");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        lblNewLabel.setBounds(132, 11, 198, 21);
        contentPane.add(lblNewLabel);
        
        JButton btnNewButton = new JButton("새로고침");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            initializeTable();
            }
        });
        btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnNewButton.setBounds(389, 8, 93, 29);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("신규 생성");
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               MemberSignFrame.newMemberSignFrame(MemberDetailFrame.this,MemberDetailFrame.this);
            }
        });
        btnNewButton_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnNewButton_1.setBounds(76, 402, 95, 31);
        contentPane.add(btnNewButton_1);
        
        JButton btnDelete = new JButton("삭제");
        btnDelete.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		memberDelete();
        	}
        });
        btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnDelete.setBounds(305, 402, 95, 31);
        contentPane.add(btnDelete);
    }

    private void memberDelete() {
		
        int row = table.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(MemberDetailFrame.this, 
                    "삭제할 행을 먼저 선택하세요",
                    "Warning", // 타이틀 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
    
        Integer memNo = (Integer) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(MemberDetailFrame.this, 
                "선택한 정보를 삭제 하시겠습니까?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
         
            int result = dao.delete(memNo);
            if (result == 1) {
                initializeTable();
                JOptionPane.showMessageDialog(MemberDetailFrame.this, "삭제 성공");
            } else {
                JOptionPane.showMessageDialog(MemberDetailFrame.this, "삭제 실패");
            }
        }
		
	}

	private void searchMemberByKeyword() {
        String keyword = textKeyword.getText();
        if (keyword.equals("")) { 
            JOptionPane.showMessageDialog(MemberDetailFrame.this,    
                    "검색어를 입력하세요.", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int type = comboBox.getSelectedIndex();
        
        List<Member> list = dao.read(type, keyword);
        
        model = new DefaultTableModel(null, COLUMN_NAMES);
        table.setModel(model);
        
        for (Member m : list) {
            Object[] row = {
                m.getMemberId(),m.getMemName(),m.getMemPhone(),m.getMemBirthDay(),m.getMemCity()
            };
            model.addRow(row);
        }
        
        // JTable 셀 정렬 (우측 중앙)
        DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
        DefaultTableCellRenderer dtcrc = new DefaultTableCellRenderer();
        
        dtcr.setHorizontalAlignment(SwingConstants.RIGHT);
        dtcrc.setHorizontalAlignment(SwingConstants.CENTER);
        
        
        TableColumnModel tcm = table.getColumnModel();
        
        tcm.getColumn(0).setCellRenderer(dtcr);
        tcm.getColumn(1).setCellRenderer(dtcrc);
        tcm.getColumn(2).setCellRenderer(dtcrc);
        tcm.getColumn(3).setCellRenderer(dtcrc);
        tcm.getColumn(4).setCellRenderer(dtcrc);
    }

    private void showUpdateFrame() {
       
    	int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(MemberDetailFrame.this, 
                    "리스트 값을 선택하세요.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        Integer memNo = (Integer) model.getValueAt(row, 0);
 
        MemberUpdateFrame.newMemberUpdateFrame(MemberDetailFrame.this, memNo, this);
    }


    
	@Override
    public void MemberUpdate() {
       
        initializeTable();
    }

    @Override
    public void MemberSign() {
        
        initializeTable();
        
    }
}