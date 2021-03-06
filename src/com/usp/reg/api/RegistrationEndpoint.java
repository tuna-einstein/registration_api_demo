package com.usp.reg.api;

import com.usp.reg.api.PMF;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.datanucleus.query.JDOCursorHelper;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

@Api(name = "registrationendpoint", namespace = @ApiNamespace(ownerDomain = "usp.com", ownerName = "usp.com", packagePath = "reg.api"))
public class RegistrationEndpoint {

	/**
	 * This method lists all the entities inserted in datastore.
	 * It uses HTTP GET method and paging support.
	 *
	 * @return A CollectionResponse class containing the list of all entities
	 * persisted and a cursor to the next page.
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	@ApiMethod(name = "listRegistration")
	public CollectionResponse<Registration> listRegistration(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("limit") Integer limit) {

		PersistenceManager mgr = null;
		Cursor cursor = null;
		List<Registration> execute = null;

		try {
			mgr = getPersistenceManager();
			Query query = mgr.newQuery(Registration.class);
			if (cursorString != null && cursorString != "") {
				cursor = Cursor.fromWebSafeString(cursorString);
				HashMap<String, Object> extensionMap = new HashMap<String, Object>();
				extensionMap.put(JDOCursorHelper.CURSOR_EXTENSION, cursor);
				query.setExtensions(extensionMap);
			}

			if (limit != null) {
				query.setRange(0, limit);
			}

			execute = (List<Registration>) query.execute();
			cursor = JDOCursorHelper.getCursor(execute);
			if (cursor != null)
				cursorString = cursor.toWebSafeString();

			// Tight loop for fetching all entities from datastore and accomodate
			// for lazy fetch.
			for (Registration obj : execute)
				;
		} finally {
			mgr.close();
		}

		return CollectionResponse.<Registration> builder().setItems(execute)
				.setNextPageToken(cursorString).build();
	}

	/**
	 * This method gets the entity having primary key id. It uses HTTP GET method.
	 *
	 * @param id the primary key of the java bean.
	 * @return The entity with primary key id.
	 */
	@ApiMethod(name = "getRegistration")
	public Registration getRegistration(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		Registration registration = null;
		try {
			registration = mgr.getObjectById(Registration.class, id);
		} finally {
			mgr.close();
		}
		return registration;
	}

	/**
	 * This inserts a new entity into App Engine datastore. If the entity already
	 * exists in the datastore, an exception is thrown.
	 * It uses HTTP POST method.
	 *
	 * @param registration the entity to be inserted.
	 * @return The inserted entity.
	 */
	@ApiMethod(name = "insertRegistration")
	public Registration insertRegistration(Registration registration) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (containsRegistration(registration)) {
				throw new EntityExistsException("Object already exists");
			}
			mgr.makePersistent(registration);
		} finally {
			mgr.close();
		}
		return registration;
	}

	/**
	 * This method is used for updating an existing entity. If the entity does not
	 * exist in the datastore, an exception is thrown.
	 * It uses HTTP PUT method.
	 *
	 * @param registration the entity to be updated.
	 * @return The updated entity.
	 */
	@ApiMethod(name = "updateRegistration")
	public Registration updateRegistration(Registration registration) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			if (!containsRegistration(registration)) {
				throw new EntityNotFoundException("Object does not exist");
			}
			mgr.makePersistent(registration);
		} finally {
			mgr.close();
		}
		return registration;
	}

	/**
	 * This method removes the entity with primary key id.
	 * It uses HTTP DELETE method.
	 *
	 * @param id the primary key of the entity to be deleted.
	 */
	@ApiMethod(name = "removeRegistration")
	public void removeRegistration(@Named("id") Long id) {
		PersistenceManager mgr = getPersistenceManager();
		try {
			Registration registration = mgr.getObjectById(Registration.class,
					id);
			mgr.deletePersistent(registration);
		} finally {
			mgr.close();
		}
	}

	private boolean containsRegistration(Registration registration) {
		PersistenceManager mgr = getPersistenceManager();
		if (registration.getId() == null) {
			return false;
		}
		boolean contains = true;
		try {
			mgr.getObjectById(Registration.class, registration.getId());
		} catch (javax.jdo.JDOObjectNotFoundException ex) {
			contains = false;
		} finally {
			mgr.close();
		}
		return contains;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
