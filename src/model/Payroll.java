public class Payroll {
    private int payrollId;
    private int employeeId;
    private int month;
    private int year;
    private double grossSalary;
    private double totalDeduction;
    private double netSalary;

    public Payroll() {
    }

    public Payroll(int employeeId, int month, int year, double grossSalary, double totalDeduction, double netSalary) {
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
        this.grossSalary = grossSalary;
        this.totalDeduction = totalDeduction;
        this.netSalary = netSalary;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getTotalDeduction() {
        return totalDeduction;
    }

    public void setTotalDeduction(double totalDeduction) {
        this.totalDeduction = totalDeduction;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public void displayPayroll() {
        System.out.println("Payroll ID: " + payrollId);
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
        System.out.println("Gross Salary: " + grossSalary);
        System.out.println("Total Deduction: " + totalDeduction);
        System.out.println("Net Salary: " + netSalary);
    }
}
