package org.liveSense.sample.gwt.notesRequestFactory.shared;

import java.util.List;

import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.Request;

import org.liveSense.sample.gwt.notesRequestFactory.server.NoteServiceLocator;
import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteDao;

public interface NoteRequestFactory extends RequestFactory {

	@Service(value = NoteDao.class, locator = NoteServiceLocator.class)
	public interface NoteRequestContext extends RequestContext {
		Request<List<NoteEntityProxy>> getNotes();
	    Request<Void> createNote(NoteEntityProxy note);
	}
	NoteRequestContext context();
}
