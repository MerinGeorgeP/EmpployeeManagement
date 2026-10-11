import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class AttendanceForm extends JFrame{
    JTextField aid=new JTextField();
    JTextField emp=new JTextField();
    JTextField month=new JTextField();
    JTextField year=new JTextField();
    JTextField working=new JTextField();
    JTextField present=new JTextField();
    JTextField leave=new JTextField();
    
    DefaultTableModel model=new DefaultTableModel(new String[]{"Attendance ID","Employee ID","Month","Year","Working Days","Present Days","Leave Days"},0){
        public boolean isCellEditable(int r,int c){
            return false;
        }
    };
    JTable table=new JTable(model);

    public AttendanceForm(Frame owner){
        super("Attendance");
        setSize(900,550);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(8,8));
        JPanel f=new JPanel(new GridLayout(0,4,6,6));
        f.setBorder(BorderFactory.createTitledBorder("Attendance details"));
        field(f,"Attendance ID (update)",aid);
        field(f,"Employee ID",emp);
        field(f,"Month (1-12)",month);
        field(f,"Year",year);
        field(f,"Working days",working);
        field(f,"Present days",present);
        field(f,"Leave days",leave);
        JButton save=new JButton("Save");
        JButton update=new JButton("Update selected");
        JButton view=new JButton("View all");
        
        JPanel a=new JPanel();
        a.add(save);
        a.add(update);
        a.add(view);
        
        JPanel top=new JPanel(new BorderLayout());
        top.add(f);
        top.add(a,BorderLayout.SOUTH);
        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        save.addActionListener(e->persist(false));
        update.addActionListener(e->persist(true));
        view.addActionListener(e->load());
        table.getSelectionModel().addListSelectionListener(e->{
            if(!e.getValueIsAdjusting()&&table.getSelectedRow()>=0){
                int r=table.convertRowIndexToModel(table.getSelectedRow());
                aid.setText(model.getValueAt(r,0).toString());
                emp.setText(model.getValueAt(r,1).toString());
                 month.setText(model.getValueAt(r,2).toString());
                year.setText(model.getValueAt(r,3).toString());
                working.setText(model.getValueAt(r,4).toString());
                present.setText(model.getValueAt(r,5).toString());
                leave.setText(model.getValueAt(r,6).toString());
            }
        });
        load();
    }

    void field(JPanel p,String s,JComponent c){
        p.add(new JLabel(s));
        p.add(c);
    }
    void persist(boolean upd){
        String sql=upd?"UPDATE attendance SET employee_id=?,month=?,year=?,working_days=?,present_days=?,leave_days=? WHERE attendance_id=?":"INSERT INTO attendance(employee_id,month,year,working_days,present_days,leave_days) VALUES(?,?,?,?,?,?)";
        try(Connection c=DatabaseConnection.getConnection();
        PreparedStatement p=c.prepareStatement(sql)){
            int m=Integer.parseInt(month.getText());
            int w=Integer.parseInt(working.getText());
            int pr=Integer.parseInt(present.getText());
            int l=Integer.parseInt(leave.getText());
            if(m<1||m>12||w<0||pr<0||l<0||pr>w||l>w)throw new Exception("Check month and day values.");
            p.setInt(1,Integer.parseInt(emp.getText()));
            p.setInt(2,m);
            p.setInt(3,Integer.parseInt(year.getText()));
            p.setInt(4,w);
            p.setInt(5,pr);
            p.setInt(6,l);
            if(upd)p.setInt(7,Integer.parseInt(aid.getText()));
            p.executeUpdate();
            JOptionPane.showMessageDialog(this,"Attendance saved.");
            load();
        }
        catch(Exception ex){
            err(ex);
        }
    }

    void load(){
        model.setRowCount(0);
        try(Connection c=DatabaseConnection.getConnection();Statement s=c.createStatement();ResultSet r=s.executeQuery("SELECT attendance_id,employee_id,month,year,working_days,present_days,leave_days FROM attendance ORDER BY year DESC,month DESC")){
            while(r.next())model.addRow(new Object[]{r.getObject(1),r.getObject(2),r.getObject(3),r.getObject(4),r.getObject(5),r.getObject(6),r.getObject(7)});
        }
        catch(Exception e){
            err(e);
        }
    }
        
    void err(Exception e){JOptionPane.showMessageDialog(this,e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);}
}
