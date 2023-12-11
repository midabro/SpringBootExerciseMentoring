package main.java.com.example.springbootexercisementoring.phonenumber;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PhoneNumber {

  @Id
  @Column(name="phone_number_id")
  private String id;

  @Column
  private String number;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNumber() {
    return number;
  }

  public void setNumber(String number) {
    this.number = number;
  }
}
