package com.ibm.test.addressbook.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.json.bind.annotation.JsonbProperty;

/**
 * AddressBook entity representing a contact in the address book.
 * Contains id, name, phone number, and address fields.
 */
@Entity
@Table(name = "address_book")
@NamedQueries({
    @NamedQuery(name = "AddressBook.findAll", 
                query = "SELECT a FROM AddressBook a ORDER BY a.name"),
    @NamedQuery(name = "AddressBook.findByName", 
                query = "SELECT a FROM AddressBook a WHERE a.name LIKE :name")
})
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonbProperty("id")
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "name", nullable = false, length = 100)
    @JsonbProperty("name")
    private String name;

    @Column(name = "phone_number", length = 20)
    @JsonbProperty("phoneNumber")
    private String phoneNumber;

    @Column(name = "address", length = 500)
    @JsonbProperty("address")
    private String address;

    // Default constructor (required by JPA)
    public AddressBook() {
    }

    // Constructor with all fields except ID
    public AddressBook(String name, String phoneNumber, String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString, equals, and hashCode methods
    @Override
    public String toString() {
        return "AddressBook{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AddressBook that = (AddressBook) obj;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}