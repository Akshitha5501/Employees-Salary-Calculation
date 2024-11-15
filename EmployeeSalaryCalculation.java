import java.util.Scanner;
public class EmployeeSalaryCalculation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter employee name: ");
            String name = scanner.nextLine();

            System.out.print("Enter employee designation: ");
            String designation = scanner.nextLine();

            System.out.print("Is the employee Full-Time? (yes/no): ");
            String type = scanner.nextLine();

            boolean isFullTime = type.equalsIgnoreCase("yes");
            Employee employee;

            if (isFullTime) {
                System.out.print("Enter basic salary for full-time employee: ");
                double basicSalary = scanner.nextDouble();
                employee = new FullTimeEmployee(name, designation, basicSalary);
            } else {
                System.out.print("Enter hourly rate for part-time employee: ");
                double hourlyRate = scanner.nextDouble();
                System.out.print("Enter regular working hours: ");
                double regularHours = scanner.nextDouble();
                System.out.print("Enter overtime rate per hour: ");
                double overtimeRate = scanner.nextDouble();
                employee = new PartTimeEmployee(name, designation, hourlyRate, regularHours, overtimeRate);

                System.out.print("Enter overtime hours worked: ");
                ((PartTimeEmployee) employee).setOvertimeHours(scanner.nextDouble());
            }

            System.out.print("Enter bonus amount: ");
            employee.setBonus(scanner.nextDouble());
            System.out.print("Enter tax deduction amount: ");
            employee.setTaxDeduction(scanner.nextDouble());
            System.out.print("Enter leave deduction amount: ");
            employee.setLeaveDeduction(scanner.nextDouble());

            employee.printSalaryDetails();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter valid data.");
        } finally {
            scanner.close();
        }
    }
}

abstract class Employee {
    protected String name;
    protected String designation;
    protected double hra;
    protected double bonus;
    protected double taxDeduction;
    protected double leaveDeduction;

    public Employee(String name, String designation) {
        this.name = name;
        this.designation = designation;
    }

    public abstract double calculateTotalSalary();

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setTaxDeduction(double taxDeduction) {
        this.taxDeduction = taxDeduction;
    }

    public void setLeaveDeduction(double leaveDeduction) {
        this.leaveDeduction = leaveDeduction;
    }

    public void printCommonDetails() {
        System.out.println("Employee Name: " + name);
        System.out.println("Designation: " + designation);
    }

    public abstract void printSalaryDetails();
}

// FullTimeEmployee
class FullTimeEmployee extends Employee {
    private double basicSalary;

    public FullTimeEmployee(String name, String designation, double basicSalary) {
        super(name, designation);
        this.basicSalary = basicSalary;
        this.hra = 0.20 * basicSalary; // 20% HRA
    }

    public double calculateTotalSalary() {
        double salaryBeforeTax = basicSalary + hra + bonus;
        return salaryBeforeTax - (taxDeduction + leaveDeduction);
    }

    public void printSalaryDetails() {
        printCommonDetails();
        System.out.println("Full-Time Salary Breakdown:");
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("HRA: " + hra);
        System.out.println("Bonus: " + bonus);
        System.out.println("Tax Deduction: " + taxDeduction);
        System.out.println("Leave Deduction: " + leaveDeduction);
        System.out.println("Total Salary: " + calculateTotalSalary());
    }
}

// PartTimeEmployee
class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private double regularHours;
    private double overtimeHours;
    private double overtimeRate;

    public PartTimeEmployee(String name, String designation, double hourlyRate, double regularHours, double overtimeRate) {
        super(name, designation);
        this.hourlyRate = hourlyRate;
        this.regularHours = regularHours;
        this.overtimeRate = overtimeRate;
        this.hra = 0.10 * hourlyRate * regularHours; // 10% HRA
    }

    public void setOvertimeHours(double overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public double calculateTotalSalary() {
        double regularPay = regularHours * hourlyRate;
        double overtimePay = overtimeHours * overtimeRate;
        double totalSalary = regularPay + overtimePay + bonus;
        return totalSalary - (taxDeduction + leaveDeduction);
    }

    public void printSalaryDetails() {
        printCommonDetails();
        System.out.println("Part-Time Salary Breakdown:");
        System.out.println("Regular Hours Worked: " + regularHours);
        System.out.println("Overtime Hours Worked: " + overtimeHours);
        System.out.println("Overtime Rate: " + overtimeRate);
        System.out.println("Regular Pay: " + (regularHours * hourlyRate));
        System.out.println("Overtime Pay: " + (overtimeHours * overtimeRate));
        System.out.println("Bonus: " + bonus);
        System.out.println("Tax Deduction: " + taxDeduction);
        System.out.println("Leave Deduction: " + leaveDeduction);
        System.out.println("Total Salary: " + calculateTotalSalary());
    }
}

