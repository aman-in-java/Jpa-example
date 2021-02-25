package org.sample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sample.data.IEmployeeRepository;
import org.sample.domain.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit test cases for {@link org.sample.domain.Employee}.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeTest
{
  private static final Set<Employee>                       employees = new HashSet<>();
  private static final Map<Employee, Collection<Employee>> peers     = new HashMap<>();

  @Autowired
  private IEmployeeRepository employeeRepository;

  /**
   * Set up a few employees for unit tests.
   */
  @Before
  public void setup()
  {
    final Employee employee1 = new Employee("John Doe", "Manager");
    final Employee peer11 = new Employee("Tim Hook", "Manager");
    final Employee peer12 = new Employee("Bill Ford ", "Manager");
    final Employee peer13 = new Employee("Dwayne Johnson ", "Manager");

    final Collection<Employee> employee1Peers = new HashSet<>();
    employee1Peers.add(peer11);
    employee1Peers.add(peer12);
    employee1Peers.add(peer13);

    peers.put(employee1, employee1Peers);

    final Employee employee2 = new Employee("Tom Curran", "Associate");
    final Employee peer21 = new Employee("Robin Singh", "Associate");
    final Employee peer22 = new Employee("Jack", "Associate");

    final Collection<Employee> employee2Peers = new HashSet<>();
    employee2Peers.add(peer21);
    employee2Peers.add(peer22);

    peers.put(employee2, employee2Peers);

    employees.add(employee1);
    employees.add(peer11);
    employees.add(peer12);
    employees.add(peer13);

    employees.add(employee2);
    employees.add(peer21);
    employees.add(peer22);

    employeeRepository.saveAll(employees);
  }

  /**
   * Test employee peers.
   */
  @Test
  public void testEmployeePeers()
  {
    for (final Employee employee : peers.keySet())
    {
      final Collection<Employee> peers = EmployeeTest.peers.get(employee);
      assertEquals(employee.getPeers().size(), peers.size());

      peers.forEach(peer -> assertTrue(employee.getPeers().contains(peer)));
    }
  }

  /**
   * Tests that all employees where saved successfully.
   */
  @Test
  public void testEmployeePersistence()
  {
    assertEquals(employeeRepository.findAll().size(), employees.size());
  }
}
