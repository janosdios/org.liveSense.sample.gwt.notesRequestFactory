package org.liveSense.sample.gwt.notesRequestFactory.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.sling.jcr.api.SlingRepository;
import org.apache.felix.scr.annotations.Reference;

public class NoteDaoImpl implements NoteDao {

	Logger log = LoggerFactory.getLogger(NoteDaoImpl.class);

    private String pn_notetitle = "noteTitle";
    private String pn_notetext = "noteText";
    private String path_democontent = "/samples/notesrequestfactory/notes";
	
	private Session session = null;
	private Node root = null;

	@Reference
	private SlingRepository dao_repository;
	
	public NoteDaoImpl() throws RepositoryException {
//        try {
            // retrieve a session from the repository
            session = dao_repository.loginAdministrative(dao_repository.getDefaultWorkspace());
//        } catch (RepositoryException e) {
//            log.error("NoteDaoImpl create: default repository unavailable: ", e);
//        }

//        try {
            root = (Node) session.getItem(path_democontent);
//        } catch (RepositoryException e) {
//        	log.error("NoteDaoImpl create: error while getting demo content path " + path_democontent + ": "
//                    + session.getWorkspace().getName() + ": ", e);
//        }
	}
	
	public NoteDaoImpl(Session session) {
		this.session = session;
	}

	public NoteDaoImpl(Session session, String pn_notetitle, String pn_notetext, String path_democontent) {
		this.session = session;
		this.pn_notetitle = pn_notetitle;
		this.pn_notetext = pn_notetext;
		this.path_democontent = path_democontent;
		
        try {
            root = (Node) session.getItem(path_democontent);
        } catch (RepositoryException e) {
        	log.error("activate: error while getting demo content path " + path_democontent + ": "
                    + session.getWorkspace().getName() + ": ", e);
        }
	}
	
    public void createNote(NoteBean note) {
        log.info("createNote: creating note with title {}...", note.getTitle());
        if (root != null) {
            try {

                // add a node to the root of the demo content and set the properties via the POJO's getters
                final Node node = root.addNode("note");
                node.setProperty(pn_notetitle, note.getTitle());
                node.setProperty(pn_notetext, note.getText());
                session.save();
                log.info("createNote: successfully saved note {} to repository.", node.getPath());

            } catch (RepositoryException e) {

                log.error("createNote: error while creating note in repository: ", e);
                try {
                    // if the node creation failed, try to roll-back the repository changes accumulated
                    if (session.hasPendingChanges()) {
                        session.refresh(false);
                    }
                } catch (RepositoryException e1) {
                    log.error("createNote: error while reverting changes after trying to save note to repository: ", e1);
                }
            }

        } else {
            log.error("createNote: cannot create note, demo content path {} unavailable!", path_democontent);
        }
    }

    public List<NoteBean> getNotes() throws Exception {

        final List<NoteBean> notes = new ArrayList<NoteBean>();

        if (root != null) {
            try {

                // get all child nodes of the demo content root node
                final NodeIterator nodes = root.getNodes();
                while (nodes.hasNext()) {

                    Node node = nodes.nextNode();
                    // for every child node, that has the correct properties set, create a POJO and add it to the list
                    if (node.hasProperty(pn_notetitle) && node.hasProperty(pn_notetext)) {

                        final NoteBean note = new NoteBean();
                        note.setTitle(node.getProperty(pn_notetitle).getString());
                        note.setText(node.getProperty(pn_notetext).getString());
                        // set the node's path, so that the GWT client app can use it as the parameter for the
                        // deleteNote(String path) method.
                        note.setPath(node.getPath());

                        notes.add(note);
                        log.info("getNotes: found note {}, adding to list...", node.getPath());
                    }
                }

            } catch (RepositoryException e) {
                log.error("getNotes: error while getting list of notes from " + path_democontent + ": ", e);
            }

        } else {
        	throw new Exception("getNotes: error while getting notes, demo content path {} unavailable! " + path_democontent + ", root: " + root.getPath());
//            log.error("getNotes: error while getting notes, demo content path {} unavailable!", path_democontent);
        }

        return notes;
    }
	
    public void deleteNote(String path) {
        try {
            session.getItem(path).remove();
            session.save();
        } catch (RepositoryException e) {
            log.error("deleteNote: error while deleting note {}: ", e);
        }
    }

    
}
