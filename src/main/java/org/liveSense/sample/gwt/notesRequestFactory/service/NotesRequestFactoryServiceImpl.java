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
package org.liveSense.sample.gwt.notesRequestFactory.service;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteDao;
import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteDaoImpl;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.felix.scr.annotations.Reference;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.ArrayList;

import org.apache.felix.scr.annotations.Activate;

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

@Component(label = "%noterequestfactoryservice.label", metatype=true, immediate=true)
@Service
public class NotesRequestFactoryServiceImpl implements NotesRequestFactoryService {

    /**
     * The logging facility.
     */
    private static final Logger log = LoggerFactory.getLogger(NotesRequestFactoryServiceImpl.class);

    /**
     * The <code>String</code> constant representing the name of the <code>javax.jcr.Property</code> in which the
     * title of a note is stored.
     */
    private static final String PN_NOTETITLE = "noteTitle";

    /**
     * The <code>String</code> constant representing the name of the <code>javax.jcr.Property</code> in which the
     * text of a note is stored.
     */
    private static final String PN_NOTETEXT = "noteText";

    /**
     * The <code>String</code> constant representing the name of the path in the repository under which the notes are
     * stored.
     */
    private static final String PATH_DEMOCONTENT = "/samples/notesrequestfactory/notes";

    /**
     * This is the <code>javax.jcr.Session</code> used for repository operations. It is retrieved from the repository
     * through an administrative login.
     */
    private Session session;

    @Reference
    private SlingRepository repository;
 
    /**
     * This is the OSGi component/service activation method. It initializes this service.
     *
     * @param context The OSGi context provided by the activator.
     */
	@Activate
    protected void activate(ComponentContext context) throws RepositoryException {
        log.info("activate: initialized and provided classloader {} to GWT.", this.getClass().getClassLoader());

        try {
            // retrieve a session from the repository
            session = repository.loginAdministrative(repository.getDefaultWorkspace());
        } catch (RepositoryException e) {
            log.error("activate: repository unavailable: " + context + ": ", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public NoteDao getNoteDaoInstance(Session session) {
    	return new NoteDaoImpl(session);
    }


}
