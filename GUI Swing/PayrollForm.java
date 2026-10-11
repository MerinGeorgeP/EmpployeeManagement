import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.math.BigDecimal;

public class PayrollForm extends JFrame{
    JComboBox<Emp> employee=new JComboBox<>();
    JTextField month=new JTextField("10");
    JTextField year=new JTextField("2026");
    
    JLabel basic=new JLabel("—");
    JLabel allowance=new JLabel("—");
    JLabel bonus=new JLabel("—");
    JLabel deduction=new JLabel("—");
    JLabel gross=new JLabel("—");
    JLabel net=new JLabel("—");
    public PayrollForm(Frame owner){
        super("Generate Payroll");
        setSize(600,460);
        setLocationRelativeTo(owner);
        
        JPanel f=new JPanel(new GridLayout(0,2,10,10));
        f.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));
        f.setBorder(BorderFactory.createTitledBorder("Payroll details"));
        row(f,"Employee",employee);
        row(f,"Month (1-12)",month);
        row(f,"Year",year);
        row(f,"Basic salary",basic);
        row(f,"Allowance",allowance);
        row(f,"Bonus",bonus);
        row(f,"Deduction",deduction);
        row(f,"Gross salary",gross);
        row(f,"Net salary",net);
        
        JButton gen=new JButton("Generate Payroll");
        gen.addActionListener(e->generate());
        setLayout(new BorderLayout());
        add(f);
        add(gen,BorderLayout.SOUTH);
        loadEmployees();
        employee.addActionListener(e->salaryDetails());
        salaryDetails();
    }
    
    void row(JPanel p,String s,Component c){
        p.add(new JLabel(s));
        p.add(c);
    }
    
    void loadEmployees(){
        try(Connection c=DatabaseConnection.getConnection();
            Statement s=c.createStatement();
            ResultSet r=s.executeQuery("SELECT employee_id,name FROM employee ORDER BY name")){
                while(r.next())employee.addItem(new Emp(r.getInt(1),r.getString(2)));
            }
        catch(Exception e){
            err(e);
        }
    }
 
    void salaryDetails(){
        Emp e=(Emp)employee.getSelectedItem();
        if(e==null)return;
        try(Connection c=DatabaseConnection.getConnection();
        PreparedStatement p=c.prepareStatement("SELECT e.basic_salary,COALESCE(s.allowance,0),COALESCE(s.bonus,0),COALESCE(s.deduction,0) FROM employee e LEFT JOIN salary s ON s.employee_id=e.employee_id WHERE e.employee_id=? ORDER BY s.salary_id DESC LIMIT 1")){
            p.setInt(1,e.id);
            try(ResultSet r=p.executeQuery()){
                if(r.next()){
                    basic.setText(z(r.getBigDecimal(1)).toPlainString());
                    allowance.setText(z(r.getBigDecimal(2)).toPlainString());
                    bonus.setText(z(r.getBigDecimal(3)).toPlainString());
                    deduction.setText(z(r.getBigDecimal(4)).toPlainString());
                }
            }
        }
        catch(Exception ex){
            err(ex);
        }
    }
 
    void generate(){
        Emp e=(Emp)employee.getSelectedItem();
        if(e==null){
            err(new Exception("Add an employee first."));
            return;
        }
        try(Connection c=DatabaseConnection.getConnection()){
            int m=Integer.parseInt(month.getText());
            int y=Integer.parseInt(year.getText());
            if(m<1||m>12)throw new Exception("Month must be 1-12.");
            BigDecimal b=new BigDecimal(basic.getText());
            BigDecimal a=new BigDecimal(allowance.getText());
            BigDecimal bo=new BigDecimal(bonus.getText());
            BigDecimal d=new BigDecimal(deduction.getText());
            BigDecimal g=b.add(a).add(bo);
            BigDecimal n=g.subtract(d);
            if(n.signum()<0)throw new Exception("Deductions exceed gross salary.");
            try(PreparedStatement chk=c.prepareStatement("SELECT payroll_id FROM payroll WHERE employee_id=? AND month=? AND year=?")){
                chk.setInt(1,e.id);
                chk.setInt(2,m);
                chk.setInt(3,y);
                try(ResultSet r=chk.executeQuery()){
                    if(r.next())throw new Exception("Payroll already exists for this employee and period.");
                }
            }
            try(PreparedStatement p=c.prepareStatement("INSERT INTO payroll(employee_id,month,year,gross_salary,total_deduction,net_salary) VALUES(?,?,?,?,?,?)")){
                p.setInt(1,e.id);
                p.setInt(2,m);
                p.setInt(3,y);
                p.setBigDecimal(4,g);
                p.setBigDecimal(5,d);
                p.setBigDecimal(6,n);
                p.executeUpdate();
            }
            gross.setText(g.toPlainString());
            net.setText(n.toPlainString());
            JOptionPane.showMessageDialog(this,"Payroll generated and saved.");
        }
        catch(Exception ex){
            err(ex);
        }
    }

    BigDecimal z(BigDecimal x){
        return x==null?BigDecimal.ZERO:x;
    }
    void err(Exception e){
        JOptionPane.showMessageDialog(this,e.getMessage(),"Payroll Error",JOptionPane.ERROR_MESSAGE);
    }
    static class Emp{
        int id;
        String name;
        Emp(int i,String n){
            id=i;
            name=n;
        }
        public String toString(){
            return id+" - "+name;
        }
    }
}
