public class PermanentEmployee extends Employee {

    private double bonus;
    private double allowance;

    public PermanentEmployee(int employeeId, String name, String email, String phone, String department, double basicSalary, double bonus, double allowance) {

        super(employeeId, name, email, phone, department, basicSalary);

        this.bonus = bonus;
        this.allowance = allowance;
    }

    public PermanentEmployee(String name, String email, String phone, String department, double basicSalary, double bonus, double allowance) {

        super(name, email, phone, department, basicSalary);

        this.bonus = bonus;
        this.allowance = allowance;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double calculateSalary() {
        return getBasicSalary() + getBonus() + getAllowance();
    }

    @Override
    public void displayEmployee() {

        super.displayEmployee();

        System.out.println("Employee Type: Permanent");
        System.out.println("Allowance: " + getAllowance());
        System.out.println("Bonus: " + getBonus());
        System.out.println("Gross Salary: " + calculateSalary());
    }
}