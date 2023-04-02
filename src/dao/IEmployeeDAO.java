package dao;

import entity.Employee;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface IEmployeeDAO {

    List<Employee> getEmployees(String deptId, Date startDate, Date endDate) throws SQLException;

    String addEmployee(Employee employee) throws SQLException;

    String updateEmployeeDetails(int employeeId, String deptId, double increment) throws SQLException;

    Employee getEmployeeById(int employeeId) throws SQLException;

    String deleteEmployee(int employeeId) throws SQLException;

    Map<String, Double> getDepartmentByAverageSalary() throws SQLException;
    public void cleanUp() throws SQLException;
}
