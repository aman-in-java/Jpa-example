package org.sample.domain;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * An employee.
 */
@Entity
@Table(name = "employee")
public class Employee implements Serializable
{
  @Column(name = "id")
  @Generated(GenerationTime.INSERT)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  // Peers are all employees having same title, except this employee.
  // Note: This attribute is temporary made transient to avoid
  // 'org.hibernate.MappingException'
  // What annotation should I put here to make it a `many to many` association.
  private transient Set<Employee> peers;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "version")
  @SuppressWarnings("unused")
  @Version
  private Long version;

  /**
   * Deliberately hidden to prevent direct instantiation.
   */
  Employee()
  {
    //
  }

  /**
   * Sets the name and title for the employee.
   *
   * @param name  The name for the employee.
   * @param title The title for the employee.
   */
  public Employee(final String name, final String title)
  {
    this();

    this.name = name;
    this.title = title;
  }

  /**
   * Gets the unique identifier for the employee.
   *
   * @return The unique identifier for the employee.
   */
  public Long getId()
  {
    return id;
  }

  /**
   * Gets the name of the employee.
   *
   * @return The name of the employee.
   */
  public String getName()
  {
    return name;
  }

  /**
   * Gets all peers for the employee, that is all employee those have sample
   * title.
   *
   * @return A {@link Set} of {@link Employee}s.
   */
  public Set<Employee> getPeers()
  {
    return peers == null ? Collections.emptySet() : peers;
  }

  /**
   * Gets the title for the employee.
   *
   * @return The title for the employee.
   */
  public String getTitle()
  {
    return title;
  }

  /**
   * Gets the version of the employee.
   *
   * @return The version of the employee.
   */
  public Long getVersion()
  {
    return version;
  }
}
