public class PayrollProcessorTest {
    public static void main(String[] args) {
        PermanentEmployee permanentEmployee = new PermanentEmployee(
                1001,
                "Rahul",
                "rahul@gmail.com",
                "9876543210",
                "IT",
                40000,
                5000,
                3000
        );

        Payroll payroll = PayrollProcessor.generatePayroll(permanentEmployee, 6, 2026, 10.0);

        if (payroll.getGrossSalary() != 48000.0) {
            throw new AssertionError("Expected gross salary 48000.0 but got " + payroll.getGrossSalary());
        }

        if (payroll.getTotalDeduction() != 4800.0) {
            throw new AssertionError("Expected deduction 4800.0 but got " + payroll.getTotalDeduction());
        }

        if (payroll.getNetSalary() != 43200.0) {
            throw new AssertionError("Expected net salary 43200.0 but got " + payroll.getNetSalary());
        }

        Intern intern = new Intern(
                1002,
                "Anu",
                "anu@gmail.com",
                "9876543211",
                "HR",
                15000
        );

        Payroll internPayroll = PayrollProcessor.generatePayroll(intern, 7, 2026, 5.0);

        if (internPayroll.getGrossSalary() != 15000.0) {
            throw new AssertionError("Expected intern gross salary 15000.0 but got " + internPayroll.getGrossSalary());
        }

        if (internPayroll.getTotalDeduction() != 750.0) {
            throw new AssertionError("Expected intern deduction 750.0 but got " + internPayroll.getTotalDeduction());
        }

        if (internPayroll.getNetSalary() != 14250.0) {
            throw new AssertionError("Expected intern net salary 14250.0 but got " + internPayroll.getNetSalary());
        }

        System.out.println("PayrollProcessorTest passed.");
    }
}
