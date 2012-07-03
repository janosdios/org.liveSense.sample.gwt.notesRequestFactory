package org.liveSense.sample.gwt.notesRequestFactory.shared;

//import org.liveSense.sample.gwt.notesRequestFactory.server.NoteLocator;
import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteBean;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

/**
 * This interface is the note's entity-proxy. 
 * An entity proxy is a client-side representation of a server-side entity. The proxy interfaces are implemented by RequestFactory. * 
 */
@ProxyFor(value = NoteBean.class /*, locator = NoteLocator.class */)
public interface NoteEntityProxy extends EntityProxy {

    public void setTitle(String title);
	
    public void setText(String text);
    
    public void setPath(String path);
    
    public String getTitle();
    
    public String getText();
    
    public String getPath();    
}
