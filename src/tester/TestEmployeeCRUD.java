package tester;

import dao.EmployeeDAO;
import dao.IEmployeeDAO;
import entity.Employee;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class TestEmployeeCRUD {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            IEmployeeDAO employeeDAO = new EmployeeDAO();

            boolean exit = false;

            while (!exit) {
                System.out.println("1. Get employee details in the mentioned period.");
                System.out.println("2. Insert an employee.");
                System.out.println("3. Update an employee.");
                System.out.println("4. Get employee details by id.");
                System.out.println("5. Delete an employee.");
                System.out.println("6. Get department wise average salary.");
                System.out.println("100. Exit");

                try {
                    switch (scanner.nextInt()) {
                        case 1:
                            System.out.println("Enter dept id, start date and end date: ");
                            List<Employee> employees =
                                    employeeDAO.getEmployees(scanner.next(), Date.valueOf(scanner.next()), Date.valueOf(scanner.next()));
                            employees.forEach(System.out::println);
                            break;

                        case 2:
                            System.out.println("Enter name, address, salary, dept id, join date: ");
//                            String name, String address, double salary, String deptId, Date joinDate
                            System.out.println(employeeDAO.addEmployee(
                                    new Employee(scanner.next(),scanner.next(),scanner.nextDouble(),scanner.next(),Date.valueOf(scanner.next()))));

                            break;

                        case 3:
                            System.out.println("Enter employee id, new department id and increment: ");
                            System.out.println(employeeDAO.updateEmployeeDetails(scanner.nextInt(), scanner.next(), scanner.nextDouble()));
                            break;

                        case 4:
                            System.out.println("Enter the employee id: ");
                            System.out.println(employeeDAO.getEmployeeById(scanner.nextInt()));
                            break;

                        case 5:
                            System.out.println("Enter the employee id: ");
                            System.out.println(employeeDAO.deleteEmployee(scanner.nextInt()));
                            break;

                        case 6:
                            employeeDAO.getDepartmentByAverageSalary().forEach((key,value) -> System.out.println(key + " : " + value));
                            break;

                        case 100:
                            exit = true;
                            employeeDAO.cleanUp();
                            break;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
