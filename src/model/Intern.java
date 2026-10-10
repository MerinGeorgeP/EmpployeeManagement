public class Intern extends Employee {
    public Intern(int employeeId, String name, String email, String phone, String department, double basicSalary) {
        super(employeeId, name, email, phone, department, basicSalary);
    }
    public Intern (String name,String email,String phone,String department, double basicSalary) {
        super (name, email, phone, department, basicSalary); 
    }
    @Override
    public double calculateSalary() {
        return getBasicSalary();
    }
    @Override
    public void displayEmployee() {
        super.displayEmployee();
        System.out.println("Employee Type:Intern");
        System.out.println("Stipend:"+calculateSalary());
    }
}