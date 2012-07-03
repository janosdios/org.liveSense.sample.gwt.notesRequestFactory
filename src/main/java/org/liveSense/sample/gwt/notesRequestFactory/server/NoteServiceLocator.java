package org.liveSense.sample.gwt.notesRequestFactory.server;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;

public class NoteServiceLocator implements ServiceLocator {

	// Caching services
	Map<Class, Object> serviceCache = new HashMap<Class, Object>();

	public Object getInstance(Class<?> clazz) {
		if (clazz != null) {
			// If the service is not presented in cache
			Object service = serviceCache.get(clazz);
			if (service == null) {
				Bundle bundle = FrameworkUtil.getBundle(clazz);
				if (bundle != null) {
					serviceCache.put(clazz, bundle.getBundleContext().getService(bundle.getBundleContext().getServiceReference(clazz.getName())));
				}
			}
			return serviceCache.get(clazz);
		}
		return null;
	}
}
