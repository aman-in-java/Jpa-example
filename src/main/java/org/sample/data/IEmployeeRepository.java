package org.sample.data;

import org.sample.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Data access operations for {@link Employee}.
 */
public interface IEmployeeRepository extends JpaRepository<Employee, Long>
{
}
