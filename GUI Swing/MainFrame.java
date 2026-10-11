import java.awt.*;
import javax.swing.*;
public class MainFrame extends JFrame {
    public MainFrame(){
        super("Employee Payroll Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850,520);
        setLocationRelativeTo(null);
        JPanel root=new JPanel(new BorderLayout(12,12));
        root.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        root.setBackground(new Color(240,246,253));

        JLabel title=new JLabel("Employee Payroll Management System",SwingConstants.CENTER);
        title.setFont(new Font("SansSerif",Font.BOLD,25));
        title.setForeground(new Color(30,70,120)); root.add(title,BorderLayout.NORTH);

        JPanel menu=new JPanel(new GridLayout(2,2,14,14));
        menu.setBorder(BorderFactory.createEmptyBorder(35,60,35,60));
        menu.setBackground(root.getBackground());
        
        String[] labels={"Employee Management","Attendance","Generate Payroll","Payroll History"};
        for(String label:labels){
            JButton b=new JButton(label);
            b.setFont(new Font("SansSerif",Font.BOLD,17));
            b.setBackground(new Color(40,110,190));
            b.setForeground(Color.BLUE); menu.add(b);
            b.addActionListener(e->{ 
                switch(label){
                    case "Employee Management" -> new EmployeeForm(this).setVisible(true);
                    case "Attendance" -> new AttendanceForm(this).setVisible(true);
                    case "Generate Payroll" -> new PayrollForm(this).setVisible(true);
                    default -> new PayrollHistoryForm(this).setVisible(true);
                }
            });
        }

        root.add(menu,BorderLayout.CENTER);
        root.add(new JLabel("MySQL database: payroll_db",SwingConstants.CENTER),BorderLayout.SOUTH);
        setContentPane(root); 
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(()->new MainFrame().setVisible(true)); 
    }
}
