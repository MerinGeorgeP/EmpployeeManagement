public class PayrollProcessor {

    public static double calculateGrossSalary(Employee employee) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        return employee.calculateSalary();
    }

    public static double calculateDeduction(Employee employee, double deductionPercentage) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (deductionPercentage < 0) {
            throw new IllegalArgumentException("Deduction percentage cannot be negative.");
        }

        double grossSalary = calculateGrossSalary(employee);
        return grossSalary * (deductionPercentage / 100.0);
    }

    public static double calculateNetSalary(Employee employee, double deductionPercentage) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }

        double grossSalary = calculateGrossSalary(employee);
        double deduction = calculateDeduction(employee, deductionPercentage);
        return grossSalary - deduction;
    }

    public static Payroll generatePayroll(Employee employee, int month, int year, double deductionPercentage) {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12.");
        }
        if (year < 2000 || year > 2100) {
            throw new IllegalArgumentException("Year must be between 2000 and 2100.");
        }

        double grossSalary = calculateGrossSalary(employee);
        double totalDeduction = calculateDeduction(employee, deductionPercentage);
        double netSalary = grossSalary - totalDeduction;

        return new Payroll(
                employee.getEmployeeId(),
                month,
                year,
                grossSalary,
                totalDeduction,
                netSalary
        );
    }

    public static Payroll generatePayroll(Employee employee, int month, int year) {
        return generatePayroll(employee, month, year, 10.0);
    }
}
