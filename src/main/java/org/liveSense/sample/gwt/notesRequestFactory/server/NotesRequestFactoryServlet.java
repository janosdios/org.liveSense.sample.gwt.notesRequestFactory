/*
 *  Copyright 2010 Robert Csakany <robson@semmi.se>.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

/**
 *
 * @author Robert Csakany (robson@semmi.se)
 * @created Feb 12, 2010
 */
package org.liveSense.sample.gwt.notesRequestFactory.server;

import javax.jcr.RepositoryException;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.liveSense.servlet.requestfactory.GWTRequestFactoryServlet;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.web.bindery.requestfactory.shared.ServerFailure;

/**
 * This class implements a servlet-based RPC remote service for handling RPC calls from the GWT client application.
 * <p/>
 * It registers as an OSGi service and component, under the <code>javax.servlet.Servlet</code> interface. It thus
 * acts as a servlet, registered under the path specified  by the <code>sling.servlet.paths</code> @scr.property.
 * The path under which the servlet is registered must correspond to the GWT module's base url.
 * <p/>
 * The NotesServiceImpl class handles the creation, retrieval and deletion of {@link Note}s, as POJOs and as
 * <code>javax.jcr.Node</code>s in the repository.
 * <p/>
 * The class is an implementation of the <code>SlingRemoteServiceServlet</code> and is as such able to handle
 * GWT RPC calls in a Sling environment. The servlet must be registered with the GWT client application in the
 * <code>Notes.gwt.xml</code> module configuration file.
 */

@SuppressWarnings("serial")
@Component(label = "%noterequestfactoryservice.label", metatype=false, immediate=true, inherit=true)
@Service(value = javax.servlet.Servlet.class)
@Properties(value = {
		@Property(label ="%servletPath" , name = "sling.servlet.paths", value = "/gwt/sample/noterequestfactoryservice", propertyPrivate=true )
})

public class NotesRequestFactoryServlet extends GWTRequestFactoryServlet {

    /**
     * The logging facility.
     */
    private static final Logger log = LoggerFactory.getLogger(NotesRequestFactoryServlet.class);
     
    /**
     * This is the OSGi component/service activation method. It initializes this service.
     *
     * @param context The OSGi context provided by the activator.
     */
	@Activate
    protected void activate(ComponentContext context) throws RepositoryException {
        log.info("activate: initialized and provided classloader {} to GWT.", this.getClass().getClassLoader());

        initOsgiProcessor();
        
    }


	@Override
	public void callInit() throws Throwable {
		log.info("callInit");
	}

	@Override
	public void callFinal() throws Throwable {
		log.info("callFinal");
	}

	@Override
	public ServerFailure failure(Throwable throwable) {
		return new ServerFailure(throwable.getMessage());
	}


}
