import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class EmployeeForm extends JFrame {
    JTextField id=new JTextField();
    JTextField name=new JTextField();
    JTextField email=new JTextField();
    JTextField phone=new JTextField();
    JTextField dept=new JTextField();
    JTextField basic=new JTextField();
    JTextField search=new JTextField();
    
    JComboBox<String> type=new JComboBox<>(new String[]{"PERMANENT","INTERN"});
    DefaultTableModel model=new DefaultTableModel(new String[]{"ID","Name","Email","Phone","Department","Type","Basic Salary"},0){
        public boolean isCellEditable(int r,int c){return false;}};
        JTable table=new JTable(model);
        public EmployeeForm(Frame owner){super("Employee Management");
        setSize(1000,620);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8,8));
        
        JPanel f=new JPanel(new GridLayout(0,4,7,7));
        f.setBorder(BorderFactory.createTitledBorder("Employee details"));
        field(f,"Employee ID (update/delete)",id);
        field(f,"Name",name);
        field(f,"Email",email);
        field(f,"Phone",phone);
        field(f,"Department",dept);
        field(f,"Employee type",type);
        field(f,"Basic salary",basic);
 
        JButton add=new JButton("Add");
        JButton update=new JButton("Update");
        JButton del=new JButton("Delete");
        JButton clear=new JButton("Clear");
        JButton view=new JButton("View all");
        JButton find=new JButton("Search");
        

        JPanel actions=new JPanel();
        for(JButton b:new JButton[]{add,update,del,clear,view})actions.add(b);
        
        JPanel top=new JPanel(new BorderLayout());
        top.add(f,BorderLayout.CENTER);
        top.add(actions,BorderLayout.SOUTH);
        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        JPanel bottom=new JPanel(new BorderLayout(5,5));
        bottom.setBorder(BorderFactory.createTitledBorder("Search by ID, name, email, or department"));
        bottom.add(search);
        bottom.add(find,BorderLayout.EAST);
        add(bottom,BorderLayout.SOUTH);
        
        add.addActionListener(e->save(false));
        update.addActionListener(e->save(true));
        del.addActionListener(e->delete());clear.addActionListener(e->clear());
        view.addActionListener(e->load(""));
        find.addActionListener(e->load(search.getText().trim()));

        table.getSelectionModel().addListSelectionListener(e->{if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
            int r=table.convertRowIndexToModel(table.getSelectedRow());
            id.setText(v(r,0));
            name.setText(v(r,1));
            email.setText(v(r,2));
            phone.setText(v(r,3));
            dept.setText(v(r,4));
            type.setSelectedItem(v(r,5));
            basic.setText(v(r,6));}});
            load(""); 
        }
        
        void field(JPanel p,String l,JComponent c){
            p.add(new JLabel(l));
            p.add(c);
        } 
        
        String v(int r,int c){
            Object x=model.getValueAt(r,c);
            return x==null?"":x.toString();
        } 
        
        String nullable(String s){
            return s.trim().isEmpty()?null:s.trim();
        }
        
        void save(boolean update){
            try(Connection c=DatabaseConnection.getConnection()){
                if(name.getText().trim().isEmpty())throw new Exception("Name is required.");
                String sql=update?"UPDATE employee SET name=?,email=?,phone=?,department=?,employee_type=?,basic_salary=? WHERE employee_id=?":"INSERT INTO employee(name,email,phone,department,employee_type,basic_salary) VALUES(?,?,?,?,?,?)";
                try(PreparedStatement p=c.prepareStatement(sql)){
                    p.setString(1,name.getText().trim());
                    p.setString(2,nullable(email.getText()));
                    p.setString(3,nullable(phone.getText()));
                    p.setString(4,nullable(dept.getText()));
                    p.setString(5,(String)type.getSelectedItem());
                    p.setBigDecimal(6,new java.math.BigDecimal(basic.getText().trim()));
                    if(update)p.setInt(7,Integer.parseInt(id.getText().trim()));
                    int n=p.executeUpdate();msg(n==0?"No matching employee found.":update?"Employee updated.":"Employee added.");
                }
                load("");
            }
            
            catch(Exception ex){
                error(ex);
            }
        }
        
        void delete(){
            if(id.getText().trim().isEmpty()){
                msg("Select an employee first.");
                return;
            }
            if(JOptionPane.showConfirmDialog(this,"Delete selected employee?","Confirm",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION){
                return;
            }
            
            try(Connection c=DatabaseConnection.getConnection();
            PreparedStatement p=c.prepareStatement("DELETE FROM employee WHERE employee_id=?")){
                p.setInt(1,Integer.parseInt(id.getText().trim()));
                p.executeUpdate();load("");clear();msg("Delete request completed.");
            }
            catch(Exception ex){
                error(ex);
            }
        }
        void load(String q){
            model.setRowCount(0);
            String sql="SELECT employee_id,name,email,phone,department,employee_type,basic_salary FROM employee WHERE CAST(employee_id AS CHAR) LIKE ? OR name LIKE ? OR COALESCE(email,'') LIKE ? OR COALESCE(department,'') LIKE ? ORDER BY employee_id";
            try(Connection c=DatabaseConnection.getConnection();
            PreparedStatement p=c.prepareStatement(sql)){
                String s="%"+q+"%";
                for(int i=1;i<=4;i++)p.setString(i,s);
                try(ResultSet r=p.executeQuery()){
                    while(r.next())model.addRow(new Object[]{r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getString(5),r.getString(6),r.getBigDecimal(7)});
                }
            }
            catch(Exception ex){
                error(ex);
            }
        }
                    
        void clear(){
            id.setText("");
            name.setText("");
            email.setText("");
            phone.setText("");
            dept.setText("");
            basic.setText("");
            type.setSelectedIndex(0);
            table.clearSelection();
        } 
        
        void msg(String s){
            JOptionPane.showMessageDialog(this,s);
        }
        
        void error(Exception e){
            JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
}
