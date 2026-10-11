import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class PayrollHistoryForm extends JFrame{
    JTextField search=new JTextField();
    DefaultTableModel model=new DefaultTableModel(new String[]{"Payroll ID","Employee ID","Employee Name","Month","Year","Gross Salary","Deduction","Net Salary"},0){
        public boolean isCellEditable(int r,int c){
            return false;
        }
    };
    public PayrollHistoryForm(Frame owner){
        super("Payroll History");
        setSize(1000,540);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8,8));
        
        JPanel top=new JPanel(new BorderLayout(6,6));
        top.setBorder(BorderFactory.createTitledBorder("Search by employee ID, name, month, or year"));
        
        JButton find=new JButton("Search");
        JButton all=new JButton("Show all");
        
        JPanel b=new JPanel();
        b.add(find);
        b.add(all);
        top.add(search);
        top.add(b,BorderLayout.EAST);
        add(top,BorderLayout.NORTH);
        add(new JScrollPane(new JTable(model)),BorderLayout.CENTER);
        find.addActionListener(e->load(search.getText().trim()));
        all.addActionListener(e->{
            search.setText("");
            load("");
        });
        load("");
    }

    void load(String q){
        model.setRowCount(0);
        String sql="SELECT p.payroll_id,p.employee_id,e.name,p.month,p.year,p.gross_salary,p.total_deduction,p.net_salary FROM payroll p LEFT JOIN employee e ON e.employee_id=p.employee_id WHERE CAST(p.employee_id AS CHAR) LIKE ? OR COALESCE(e.name,'') LIKE ? OR CAST(p.month AS CHAR) LIKE ? OR CAST(p.year AS CHAR) LIKE ? ORDER BY p.year DESC,p.month DESC,p.payroll_id DESC";
        try(Connection c=DatabaseConnection.getConnection();
        PreparedStatement p=c.prepareStatement(sql)){
            String pat="%"+q+"%";
            for(int i=1;i<=4;i++)p.setString(i,pat);
            try(ResultSet r=p.executeQuery()){
                while(r.next())model.addRow(new Object[]{r.getObject(1),r.getObject(2),r.getString(3),r.getObject(4),r.getObject(5),r.getBigDecimal(6),r.getBigDecimal(7),r.getBigDecimal(8)});
            }
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
