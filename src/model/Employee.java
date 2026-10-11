public abstract class Employee {
    private int employeeId;
    private String name;
    private String department;
    private String email;
    private String phone;
    private double basicSalary;
    //1.constructors
    public Employee(String name,String email,String phone,String department,double basicSalary) {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.department=department;
        this.basicSalary=basicSalary;
    }
    public Employee(int employeeId,String name,String email,String phone,String department,double basicSalary) {
        this.employeeId=employeeId;
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.department=department;
        this.basicSalary=basicSalary;
    }
    //2.getters and setters
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email=email;
    }
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone) {
        this.phone=phone;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department=department;
    }
    public double getBasicSalary() {
        return basicSalary;
    }
    public void setBasicSalary(double basicSalary) {
        this.basicSalary=basicSalary;
    }
    //3.abstract calculate salary
    public abstract double calculateSalary();
    //4.display info
    public void displayEmployee() {
        System.out.println("ID: " + employeeId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Department: " + department);
        System.out.println("Basic Salary: " + basicSalary);
    }
}