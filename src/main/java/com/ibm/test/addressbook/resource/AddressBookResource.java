package com.ibm.test.addressbook.resource;

import com.ibm.test.addressbook.entity.AddressBook;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

import java.util.List;

/**
 * JAX-RS resource providing CRUD operations for the AddressBook entity.
 * Provides REST endpoints for managing address book contacts.
 */
@ApplicationScoped
@Path("/addressbook")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressBookResource {

    @PersistenceContext(unitName = "addressbook-pu")
    private EntityManager entityManager;

    /**
     * Get all address book entries.
     * 
     * @return List of all AddressBook entries
     */
    @GET
    public Response getAllContacts() {
        try {
            TypedQuery<AddressBook> query = entityManager.createNamedQuery("AddressBook.findAll", AddressBook.class);
            List<AddressBook> contacts = query.getResultList();
            return Response.ok(contacts).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving contacts: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Get a specific address book entry by ID.
     * 
     * @param id The ID of the contact to retrieve
     * @return The AddressBook entry or 404 if not found
     */
    @GET
    @Path("/{id}")
    public Response getContact(@PathParam("id") Long id) {
        try {
            AddressBook contact = entityManager.find(AddressBook.class, id);
            if (contact == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Contact with ID " + id + " not found")
                        .build();
            }
            return Response.ok(contact).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving contact: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Create a new address book entry.
     * 
     * @param contact The AddressBook entry to create
     * @return The created contact with generated ID
     */
    @POST
    @Transactional
    public Response createContact(@Valid AddressBook contact) {
        try {
            // Ensure ID is null for new entities
            contact.setId(null);
            entityManager.persist(contact);
            entityManager.flush(); // Force ID generation
            return Response.status(Response.Status.CREATED)
                    .entity(contact)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error creating contact: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Update an existing address book entry.
     * 
     * @param id The ID of the contact to update
     * @param updatedContact The updated contact information
     * @return The updated contact or appropriate error response
     */
    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateContact(@PathParam("id") Long id, @Valid AddressBook updatedContact) {
        try {
            AddressBook existingContact = entityManager.find(AddressBook.class, id);
            if (existingContact == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Contact with ID " + id + " not found")
                        .build();
            }

            // Update fields
            existingContact.setName(updatedContact.getName());
            existingContact.setPhoneNumber(updatedContact.getPhoneNumber());
            existingContact.setAddress(updatedContact.getAddress());

            entityManager.merge(existingContact);
            return Response.ok(existingContact).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error updating contact: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Delete an address book entry.
     * 
     * @param id The ID of the contact to delete
     * @return Success message or appropriate error response
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteContact(@PathParam("id") Long id) {
        try {
            AddressBook contact = entityManager.find(AddressBook.class, id);
            if (contact == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Contact with ID " + id + " not found")
                        .build();
            }

            entityManager.remove(contact);
            return Response.ok()
                    .entity("Contact with ID " + id + " deleted successfully")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting contact: " + e.getMessage())
                    .build();
        }
    }

    /**
     * Search contacts by name (partial match).
     * 
     * @param name The name to search for
     * @return List of matching contacts
     */
    @GET
    @Path("/search")
    public Response searchContactsByName(@QueryParam("name") String name) {
        try {
            if (name == null || name.trim().isEmpty()) {
                return getAllContacts();
            }

            TypedQuery<AddressBook> query = entityManager.createNamedQuery("AddressBook.findByName", AddressBook.class);
            query.setParameter("name", "%" + name.trim() + "%");
            List<AddressBook> contacts = query.getResultList();
            
            return Response.ok(contacts).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error searching contacts: " + e.getMessage())
                    .build();
        }
    }
}