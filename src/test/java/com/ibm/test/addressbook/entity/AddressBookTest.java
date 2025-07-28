package com.ibm.test.addressbook.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AddressBook entity class.
 */
public class AddressBookTest {

    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        addressBook = new AddressBook();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(addressBook);
        assertNull(addressBook.getId());
        assertNull(addressBook.getName());
        assertNull(addressBook.getPhoneNumber());
        assertNull(addressBook.getAddress());
    }

    @Test
    void testParameterizedConstructor() {
        String name = "John Doe";
        String phoneNumber = "+1-555-0123";
        String address = "123 Main St, Anytown, USA";
        
        AddressBook contact = new AddressBook(name, phoneNumber, address);
        
        assertNull(contact.getId()); // ID should be null until persisted
        assertEquals(name, contact.getName());
        assertEquals(phoneNumber, contact.getPhoneNumber());
        assertEquals(address, contact.getAddress());
    }

    @Test
    void testSettersAndGetters() {
        Long id = 1L;
        String name = "Jane Smith";
        String phoneNumber = "+1-555-0456";
        String address = "456 Oak Ave, Somewhere, USA";

        addressBook.setId(id);
        addressBook.setName(name);
        addressBook.setPhoneNumber(phoneNumber);
        addressBook.setAddress(address);

        assertEquals(id, addressBook.getId());
        assertEquals(name, addressBook.getName());
        assertEquals(phoneNumber, addressBook.getPhoneNumber());
        assertEquals(address, addressBook.getAddress());
    }

    @Test
    void testEquals() {
        AddressBook contact1 = new AddressBook("John Doe", "+1-555-0123", "123 Main St");
        AddressBook contact2 = new AddressBook("John Doe", "+1-555-0123", "123 Main St");
        
        // Without ID (both null), they should be equal per the equals implementation
        assertEquals(contact1, contact2);
        
        // With same ID, they should be equal
        contact1.setId(1L);
        contact2.setId(1L);
        assertEquals(contact1, contact2);
        
        // With different IDs, they should not be equal
        contact2.setId(2L);
        assertNotEquals(contact1, contact2);
        
        // One with ID, one without ID - should not be equal
        contact1.setId(1L);
        contact2.setId(null);
        assertNotEquals(contact1, contact2);
    }

    @Test
    void testHashCode() {
        AddressBook contact1 = new AddressBook("John Doe", "+1-555-0123", "123 Main St");
        AddressBook contact2 = new AddressBook("Jane Smith", "+1-555-0456", "456 Oak Ave");
        
        // Without ID, hashCode should be 0
        assertEquals(0, contact1.hashCode());
        assertEquals(0, contact2.hashCode());
        
        // With ID, hashCode should be based on ID
        contact1.setId(1L);
        contact2.setId(2L);
        assertEquals(1L, contact1.hashCode());
        assertEquals(2L, contact2.hashCode());
    }

    @Test
    void testToString() {
        addressBook.setId(1L);
        addressBook.setName("John Doe");
        addressBook.setPhoneNumber("+1-555-0123");
        addressBook.setAddress("123 Main St");
        
        String expectedString = "AddressBook{id=1, name='John Doe', phoneNumber='+1-555-0123', address='123 Main St'}";
        assertEquals(expectedString, addressBook.toString());
    }
}