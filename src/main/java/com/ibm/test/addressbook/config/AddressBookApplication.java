package com.ibm.test.addressbook.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * JAX-RS Application configuration class.
 * Defines the base path for all REST endpoints.
 */
@ApplicationPath("/api")
public class AddressBookApplication extends Application {
    // No additional configuration needed for basic setup
    // All @Path annotated classes will be automatically discovered
}