package model;

public class Attendance {

    // Fields (private = encapsulation)
    private int attendanceId;
    private int employeeId;
    private int month;
    private int year;
    private int workingDays;
    private int presentDays;
    private int leaveDays;

    // Constructors
    public Attendance() {
    }

    public Attendance(int employeeId, int month, int year,
                      int workingDays, int presentDays, int leaveDays) {
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
        this.workingDays = workingDays;
        this.presentDays = presentDays;
        this.leaveDays = leaveDays;
    }

    // Getters and setters
    public int getAttendanceId() { 
        return attendanceId; }
    public void setAttendanceId(int attendanceId) { 
        this.attendanceId = attendanceId; }

    public int getEmployeeId() { 
        return employeeId; }
    public void setEmployeeId(int employeeId) { 
        this.employeeId = employeeId; }

    public int getMonth() {
         return month; }
    public void setMonth(int month) { 
        this.month = month; }

    public int getYear() { 
        return year; }
    public void setYear(int year) { 
        this.year = year; }

    public int getWorkingDays() { 
        return workingDays; }
    public void setWorkingDays(int workingDays) { 
        this.workingDays = workingDays; }

    public int getPresentDays() {
         return presentDays; }
    public void setPresentDays(int presentDays) { 
        this.presentDays = presentDays; }

    public int getLeaveDays() { 
        return leaveDays; }
    public void setLeaveDays(int leaveDays) { 
        this.leaveDays = leaveDays; }

    // Add attendance: sets all values, then checks them
    public void addAttendance(int employeeId, int month, int year,
                              int workingDays, int presentDays, int leaveDays) throws Exception {
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
        this.workingDays = workingDays;
        this.presentDays = presentDays;
        this.leaveDays = leaveDays;
        validate();
    }

    // Update attendance: changes the days, then checks them
    public void updateAttendance(int workingDays, int presentDays, int leaveDays) throws Exception {
        this.workingDays = workingDays;
        this.presentDays = presentDays;
        this.leaveDays = leaveDays;
        validate();
    }

    // View attendance: returns the details as text for the GUI
    public String viewAttendance() {
        return "Employee ID: " + employeeId
             + "\nMonth/Year: " + month + "/" + year
             + "\nWorking Days: " + workingDays
             + "\nPresent Days: " + presentDays
             + "\nLeave Days: " + leaveDays
             + "\nAttendance: " + calculateAttendancePercentage() + "%";
    }

    // Connect with employees: does this record belong to this employee?
    public boolean belongsToEmployee(int id) {
        return employeeId == id;
    }

    // Calculate attendance percentage = (present / working) * 100
    public double calculateAttendancePercentage() {
        if (workingDays == 0) {
            return 0;
        }
        return (presentDays * 100.0) / workingDays;
    }

    // Validate attendance input
    public void validate() throws Exception {
        if (employeeId <= 0) {
            throw new Exception("Employee ID must be greater than 0.");
        }
        if (month < 1 || month > 12) {
            throw new Exception("Month must be between 1 and 12.");
        }
        if (year < 2000 || year > 2100) {
            throw new Exception("Year must be between 2000 and 2100.");
        }
        if (workingDays < 1 || workingDays > 31) {
            throw new Exception("Working days must be between 1 and 31.");
        }
        if (presentDays < 0 || leaveDays < 0) {
            throw new Exception("Present and leave days cannot be negative.");
        }
        if (presentDays + leaveDays > workingDays) {
            throw new Exception("Present + leave days cannot be more than working days.");
        }
    }