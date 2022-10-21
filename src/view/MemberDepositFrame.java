package view;


import java.awt.Component;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.MemberDaoImpl;
import model.Member;
import view.MemberDepositInsertFrame.MemberDepositInsert;
import view.MemberDepositUpdateFrame.MemberDepositUpdate;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.SystemColor;
import java.awt.Color;

@SuppressWarnings("serial")
public class MemberDepositFrame extends JFrame 
                        implements MemberDepositInsert,
                                   MemberDepositUpdate{

    private static final String[] COLUMN_NAMES_DEPOSIT= {
            "입금 번호", "입금 일자", "이름", "금액"           
          };
    
    public interface MemberDepositListener {
        void MemberDepositView();
    }
    
    
    private Component parent;
    private MemberDepositListener listener;
    private MemberDaoImpl dao;
    private JPanel contentPane;
    private JTable table;
    DefaultTableModel model;
    private JTextField textKeyword;
    private JComboBox<String> comboBox;
    private JTextField textTotal;

    
    /**
     * Launch the application.
     */
    public static void newMemberDepositFrame(Component parent, MemberDepositListener listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberDepositFrame frame = new MemberDepositFrame(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    public MemberDepositFrame(Component parent, MemberDepositListener listener) {
    	setResizable(false);
        this.parent = parent;
        this.listener = listener;
        this.dao = MemberDaoImpl.getInstance();
        initialize();
        initializeDepositTable();
    }
    
private void initializeDepositTable() {
        
        model = new DefaultTableModel(null,COLUMN_NAMES_DEPOSIT);
        
        table.setModel(model);
        
        List<Member> list = dao.readDeposit();
        
        for (Member m : list) {
            Object[] row = {
                    m.getDepositNo(),m.getDepositDate(), m.getDepositName(),String.format("%,d",m.getDeposit())
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
        tcm.getColumn(3).setCellRenderer(dtcr);

    }
    
    /**
     * Create the frame.
     */
    public void initialize() {
        setTitle("회비 입금 관리 화면");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
                
        setBounds(x + 150, y + 50, 450, 463);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 58, 410, 263);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnInsert = new JButton("등록");
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            MemberDepositInsertFrame.newMemberDepositInsertFrame(MemberDepositFrame.this, MemberDepositFrame.this);
            
            }
        });
        btnInsert.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnInsert.setBounds(12, 331, 97, 23);
        contentPane.add(btnInsert);
        
        JButton btnUpdate = new JButton("수정");
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDepositUpdateFrame();
            }
        });
        btnUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnUpdate.setBounds(167, 331, 97, 23);
        contentPane.add(btnUpdate);
        
        JButton btnDelete = new JButton("삭제");
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositDelete();
            
            }
        });
        
        btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnDelete.setBounds(325, 331, 97, 23);
        contentPane.add(btnDelete);
        
        comboBox = new JComboBox<>();
        String[] comboBoxItems = { "이름", "월", "년도"};
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(comboBoxItems);
        comboBox.setModel(comboBoxModel);
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        comboBox.setBounds(40, 381, 93, 27);
        contentPane.add(comboBox);
        
        textKeyword = new JTextField();
        textKeyword.setToolTipText("입력하세요.");
        textKeyword.setHorizontalAlignment(SwingConstants.LEFT);
        textKeyword.setColumns(15);
        textKeyword.setBounds(137, 381, 178, 27);
        contentPane.add(textKeyword);
        
        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            searchKeyword();
            }
        });
        btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnSearch.setBounds(322, 380, 63, 27);
        contentPane.add(btnSearch);
        
        JLabel lblNewLabel = new JLabel("입금된 회비 총액 : ");
        lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(45, 14, 140, 27);
        contentPane.add(lblNewLabel);
        
        textTotal = new JTextField();
        textTotal.setDisabledTextColor(new Color(0, 153, 255));
        textTotal.setBackground(SystemColor.control);
        textTotal.setSelectionColor(new Color(0, 204, 102));
        textTotal.setEnabled(false);
        textTotal.setHorizontalAlignment(SwingConstants.CENTER);
        textTotal.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        textTotal.setBounds(197, 14, 79, 27);
        contentPane.add(textTotal);
        textTotal.setColumns(10);
        textTotal.setBorder(BorderFactory.createEmptyBorder());
        int money = Integer.parseInt(dao.totalDepositMoney());
        textTotal.setText(String.format("%,d",money));
        
        JLabel lblNewLabel_1 = new JLabel("원");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(273, 16, 39, 23);
        contentPane.add(lblNewLabel_1);
        
        JButton btnNewButton = new JButton("새로고침");
        btnNewButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	initializeDepositTable();
        	}
        });
        btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnNewButton.setBounds(335, 10, 87, 35);
        contentPane.add(btnNewButton);
    }


    private void searchKeyword() {
        String keyword = textKeyword.getText();
        if (keyword.equals("")) { 
            JOptionPane.showMessageDialog(MemberDepositFrame.this,    
                    "검색어를 입력하세요.", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int type = comboBox.getSelectedIndex();
        
        List<Member> list = dao.readDeposit(type, keyword);
        
        model = new DefaultTableModel(null, COLUMN_NAMES_DEPOSIT);
        table.setModel(model);
        
        for (Member m : list) {
            Object[] row = {
                    m.getDepositNo(),m.getDepositDate(), m.getDepositName(),String.format("%,d",m.getDeposit())
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
        tcm.getColumn(3).setCellRenderer(dtcr);
    }


    private void depositDelete() {
      
        int row = table.getSelectedRow();
        if (row == -1) { 
            JOptionPane.showMessageDialog(MemberDepositFrame.this, 
                    "삭제할 행을 먼저 선택하세요",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
    
        Integer depositNo = (Integer) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(MemberDepositFrame.this, 
                "선택한 정보를 삭제 하시겠습니까?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
         
            int result = dao.deleteDeposit(depositNo);
            if (result == 1) {

                initializeDepositTable();
                
                listener.MemberDepositView();
                int money = Integer.parseInt(dao.totalDepositMoney());
                textTotal.setText(String.format("%,d",money));
                
                JOptionPane.showMessageDialog(MemberDepositFrame.this, "삭제 성공");
            } else {
                JOptionPane.showMessageDialog(MemberDepositFrame.this, "삭제 실패");
            }
        }
    }


    private void showDepositUpdateFrame() {
     
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(MemberDepositFrame.this, 
                    "리스트 값을 선택하세요.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        Integer depositNo = (Integer) model.getValueAt(row, 0);
 
        MemberDepositUpdateFrame.newMemberDepositUpdateFrame(MemberDepositFrame.this, depositNo, this);
    }


    public void MemberDepositInsertView() {
    	
    	int money = Integer.parseInt(dao.totalDepositMoney());
        textTotal.setText(String.format("%,d",money));
    	
        initializeDepositTable();        
        listener.MemberDepositView();
        
    }


    @Override
    public void MemberDepositUpdateView() {
    	int money = Integer.parseInt(dao.totalDepositMoney());
        textTotal.setText(String.format("%,d", money));
        
        initializeDepositTable();       
        listener.MemberDepositView();        
    }
}
