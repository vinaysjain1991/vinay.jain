package com.hazelcast.examples;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "EMPLOYEES")
@Proxy(lazy=false)
public class Employee implements Serializable{
   @Id @GeneratedValue
   @Column(name = "EMPLOYEE_ID")
   private int id;

   @Column(name = "first_name")
   private String firstName;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "salary")
   private String salary;  

   public Employee() {}
   public int getId() {
      return id;
   }
   public void setId( int id ) {
      this.id = id;
   }
   public String getFirstName() {
      return firstName;
   }
   public void setFirstName( String first_name ) {
      this.firstName = first_name;
   }
   public String getLastName() {
      return lastName;
   }
   public void setLastName( String last_name ) {
      this.lastName = last_name;
   }
   public String getSalary() {
      return salary;
   }
   public void setSalary( String salary ) {
      this.salary = salary;
   }
@Override
public String toString() {
	return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", salary=" + salary + "]";
}
   
}