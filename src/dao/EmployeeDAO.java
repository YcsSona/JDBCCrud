package dao;

import entity.Employee;
import utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDAO implements IEmployeeDAO {

    private final Connection connection;

    private final PreparedStatement statement1, statement2, statement3, statement4, statement5, statement6;

    public EmployeeDAO() throws SQLException {
        System.out.println("Initializing the resources...");
        connection = DBUtils.getConnection();
        statement1 = connection.prepareStatement("select empid, name, salary, join_date from my_emp where " +
                "deptid = ? and join_date between ? and ?");
        statement2 = connection.prepareStatement("insert into my_emp(name, addr, salary, deptid, join_date) values(?,?,?,?,?)");
        statement3 = connection.prepareStatement("update my_emp set deptid = ?, salary = salary + ? where empid = ?");
        statement4 = connection.prepareStatement("select * from my_emp where empid = ?");
        statement5 = connection.prepareStatement("delete from my_emp where empid = ?");
        statement6 = connection.prepareStatement("select deptid, avg(salary) from my_emp group by deptid");
    }

    @Override
    public List<Employee> getEmployees(String deptId, Date startDate, Date endDate) throws SQLException {

        List<Employee> employees = new ArrayList<>();

//        setting IN parameters
        statement1.setString(1, deptId);
        statement1.setDate(2, startDate);
        statement1.setDate(3, endDate);

        try (ResultSet resultSet = statement1.executeQuery()) {
            while (resultSet.next()) {
                employees.add(new Employee(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getDate(4)));
            }
        }
        return employees;
    }

    @Override
    public String addEmployee(Employee employee) throws SQLException {
//        setting IN parameters
        statement2.setString(1, employee.getName());
        statement2.setString(2, employee.getAddress());
        statement2.setDouble(3, employee.getSalary());
        statement2.setString(4, employee.getDeptId());
        statement2.setDate(5, employee.getJoinDate());

        int result = statement2.executeUpdate();

        return result == 1 ? result + " record inserted successfully" : "Record insertion failure";
    }

    @Override
    public String updateEmployeeDetails(int employeeId, String deptId, double increment) throws SQLException {
//        setting IN parameters
        statement3.setString(1, deptId);
        statement3.setDouble(2, increment);
        statement3.setInt(3, employeeId);

        int result = statement3.executeUpdate();

        return result == 1 ? result + " record updated successfully" : "Record update failure";
    }

    @Override
    public Employee getEmployeeById(int employeeId) throws SQLException {
        Employee employee = null;
//        setting IN parameter
        statement4.setInt(1, employeeId);

        try (ResultSet resultSet = statement4.executeQuery()) {
            if (resultSet.next())
                employee = new Employee(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDouble(4), resultSet.getString(5), resultSet.getDate(6));
        }
        return employee;
    }

    @Override
    public String deleteEmployee(int employeeId) throws SQLException {
//        setting IN parameter
        statement5.setInt(1, employeeId);

        int result = statement5.executeUpdate();

        return result == 1 ? result + " record deleted successfully" : "Record deletion failure";
    }

    @Override
    public Map<String, Double> getDepartmentByAverageSalary() throws SQLException {
        Map<String, Double> map = new HashMap<>();

        try (ResultSet resultSet = statement6.executeQuery()) {
            while (resultSet.next()) {
                map.put(resultSet.getString(1), resultSet.getDouble(2));
            }
        }

        return map;
    }

    public void cleanUp() throws SQLException {
        if (statement1 != null)
            statement1.close();
        if (statement2 != null)
            statement2.close();
        if (statement3 != null)
            statement3.close();
        if (statement4 != null)
            statement4.close();
        if (statement5 != null)
            statement5.close();
        if (statement6 != null)
            statement6.close();
        if (connection != null)
            connection.close();
        System.out.println("Cleaning up the resources...");
    }
}
