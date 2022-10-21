package view;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import controller.MemberDaoImpl;
import model.Member;
import view.MemberPayInsertFrame.MemberPayInsertNotify;
import view.MemberPayUpdateFrame.MemberPayUpdateNotify;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class MemberPayFrame extends JFrame 
                                implements MemberPayInsertNotify,
                                           MemberPayUpdateNotify{
        

    private static final String[] COLUMN_NAMES_PAY = {
         "지출 번호", "지출 일자", "지출 금액", "지출 장소"
          };
    
    public interface MemberPayNotify{
        void MemberPayNotifyView();
    }
    
    private JPanel contentPane;
    private JTable table;
    private Component parent;
    private MemberDaoImpl dao;
    private DefaultTableModel model;
    private JTextField textUsing;
    private MemberPayNotify listener;
    private JTextField textKeyword;
    private JComboBox<String> comboBox;
    /**
     * Launch the application.
     */
    public static void newMemberPayFrame(Component parent, MemberPayNotify listener) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MemberPayFrame frame = new MemberPayFrame(parent, listener);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MemberPayFrame(Component parent, MemberPayNotify listener) {
    	setResizable(false);
        this.parent = parent;
        this.dao = MemberDaoImpl.getInstance();
        this.listener = listener;
        initialize();
        initializePayTable();
    }
    
private void initializePayTable() {
        
        model = new DefaultTableModel(null,COLUMN_NAMES_PAY);
        
        table.setModel(model);
        
        List<Member> list = dao.readPay();
        
        for (Member m : list) {
            Object[] row = {
                    m.getPayNo(),m.getPayDate(), String.format("%,d",m.getDepositPay()),m.getMemPlace()
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
        tcm.getColumn(2).setCellRenderer(dtcr);
        tcm.getColumn(3).setCellRenderer(dtcrc);
    }
    /**
     * Create the frame.
     */
    public void initialize() {
        setTitle("회비 지출 관리");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        int x = parent.getX();
        int y = parent.getY();
        setBounds(x + 130, y + 50, 450, 435);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 51, 410, 254);
        contentPane.add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        
        JButton btnInsert = new JButton("등록");
        btnInsert.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MemberPayInsertFrame.newMemberPayInsertFrame(MemberPayFrame.this, MemberPayFrame.this);;
            }
        });
        btnInsert.setBounds(45, 315, 97, 23);
        contentPane.add(btnInsert);
        
        JButton btnDelete = new JButton("삭제");
        btnDelete.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	delete();
            }
        });
        btnDelete.setBounds(287, 315, 97, 23);
        contentPane.add(btnDelete);
        
        JButton btnViewUpdate = new JButton("자세히/수정");
        btnViewUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPayUpdate();
            }
            
        });
        btnViewUpdate.setFont(new Font("맑은 고딕", Font.BOLD, 12));
        btnViewUpdate.setBounds(154, 315, 121, 23);
        contentPane.add(btnViewUpdate);
        
        JLabel lblNewLabel = new JLabel("사용된 회비 총액 : ");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        lblNewLabel.setBounds(45, 10, 140, 27);
        contentPane.add(lblNewLabel);
        
        textUsing = new JTextField();
        textUsing.setBackground(SystemColor.control);
        textUsing.setForeground(SystemColor.control);
        textUsing.setDisabledTextColor(new Color(0, 153, 255));
        textUsing.setSelectionColor(SystemColor.control);
        textUsing.setHorizontalAlignment(SwingConstants.CENTER);
        textUsing.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        textUsing.setEnabled(false);
        textUsing.setColumns(10);
        textUsing.setBounds(174, 10, 93, 27);
        textUsing.setBorder(BorderFactory.createEmptyBorder());
        int money = Integer.parseInt(dao.usingDepositMoney());
        textUsing.setText(String.format("%,d", money));
        contentPane.add(textUsing);
        
        JLabel lblNewLabel_1 = new JLabel("원");
        lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(255, 12, 39, 23);
        contentPane.add(lblNewLabel_1);
        
        comboBox = new JComboBox<String>();
        String[] comboBoxItems =  {"지출 장소", "지출 년/월"};
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(comboBoxItems);
        comboBox.setModel(comboBoxModel);
        comboBox.setSelectedIndex(0);
        comboBox.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        comboBox.setBounds(42, 354, 93, 27);
        contentPane.add(comboBox);
        
        textKeyword = new JTextField();
        textKeyword.setToolTipText("입력하세요.");
        textKeyword.setColumns(15);
        textKeyword.setBounds(139, 354, 178, 27);
        contentPane.add(textKeyword);
        
        JButton btnSearch = new JButton("검색");
        btnSearch.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	searchKeyword();
        	}
        });
        btnSearch.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnSearch.setBounds(324, 353, 63, 27);
        contentPane.add(btnSearch);
        
        JButton btnNewButton = new JButton("새로고침");
        btnNewButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        	initializePayTable();
        	}
        });
        btnNewButton.setFont(new Font("맑은 고딕", Font.BOLD, 13));
        btnNewButton.setBounds(324, 10, 98, 30);
        contentPane.add(btnNewButton);
    }

    private void searchKeyword() {
		
        String keyword = textKeyword.getText();
        
        if (keyword.equals("")) { 
            JOptionPane.showMessageDialog(MemberPayFrame.this,    
                    "검색어를 입력하세요.", 
                    "Warning", 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int type = comboBox.getSelectedIndex();
        
        List<Member> list = dao.readPay(type, keyword);
        
        model = new DefaultTableModel(null, COLUMN_NAMES_PAY);
        table.setModel(model);
                
        for (Member m : list) {
            Object[] row = {
                    m.getPayNo(),m.getPayDate(), String.format("%,d",m.getDepositPay()),m.getMemPlace()
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
        tcm.getColumn(2).setCellRenderer(dtcr);
        tcm.getColumn(3).setCellRenderer(dtcrc);
	}

	private void delete() {
		
    	  int row = table.getSelectedRow();
          if (row == -1) { 
              JOptionPane.showMessageDialog(MemberPayFrame.this, 
                      "삭제할 행을 먼저 선택하세요",
                      "Warning",
                      JOptionPane.WARNING_MESSAGE);
              return;
          }
          
      
          Integer payNo = (Integer) model.getValueAt(row, 0);
          int confirm = JOptionPane.showConfirmDialog(MemberPayFrame.this, 
                  "선택한 정보를 삭제 하시겠습니까?",
                  "삭제 확인",
                  JOptionPane.YES_NO_OPTION);
          if (confirm == JOptionPane.YES_OPTION) {
           try {
              int result = dao.deletePay(payNo);
              if (result == 1) {

            	  initializePayTable();

            	  int money = Integer.parseInt(dao.usingDepositMoney());
                  textUsing.setText(String.format("%,d", money));
                  
                  listener.MemberPayNotifyView();
             
                  JOptionPane.showMessageDialog(MemberPayFrame.this, "삭제 성공");
              } else {
                  JOptionPane.showMessageDialog(MemberPayFrame.this, "삭제 실패");
              }
           }catch (Exception e) {
        	   e.printStackTrace();
           }
          }
		
	}

	private void showPayUpdate() {

        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(MemberPayFrame.this, 
                    "리스트 값을 선택하세요.",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
 
        Integer payNo = (Integer) model.getValueAt(row, 0);
 
        MemberPayUpdateFrame.newMemberPayUpdateFrame (MemberPayFrame.this, payNo, MemberPayFrame.this);

    }

    @Override
    public void MemberPayInsertNotifyView() {
        
        initializePayTable();
        int money = Integer.parseInt(dao.usingDepositMoney());
        textUsing.setText(String.format("%,d", money));
        listener.MemberPayNotifyView();
    }

    @Override
    public void MemberUpdatetNotifyView() {
        initializePayTable();     
        int money = Integer.parseInt(dao.usingDepositMoney());
        textUsing.setText(String.format("%,d", money));
        listener.MemberPayNotifyView();
    }
}
