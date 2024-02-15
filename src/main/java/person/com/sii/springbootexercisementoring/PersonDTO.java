package person.com.sii.springbootexercisementoring;


import address.com.sii.springbootexercisementoring.Address;
import phonenumber.com.sii.springbootexercisementoring.PhoneNumber;
import user.com.sii.springbootexercisementoring.User;

public class PersonDTO {
  private String id;
  private String firstName;
  private String lastName;
  private User user;
  private Address address;
  private PhoneNumber phoneNumber;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public PhoneNumber getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(PhoneNumber phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
