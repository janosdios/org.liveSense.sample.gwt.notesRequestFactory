package org.liveSense.sample.gwt.notesRequestFactory.server;

import javax.jcr.RepositoryException;

import com.google.web.bindery.requestfactory.shared.ServiceLocator;
import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteDao;
import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteDaoImpl;

public class NoteServiceLocator implements ServiceLocator {

	public Object getInstance(Class<?> clazz) {
		try {
//			return clazz.newInstance();
			return new NoteDaoImpl();
//		} catch( InstantiationException e ) {
//	      throw new RuntimeException( e );
		} catch( RepositoryException e ) {
		      throw new RuntimeException( e );
//	    } catch( IllegalAccessException e ) {
//	      throw new RuntimeException( e );
	    }
	}
}
