package org.liveSense.sample.gwt.notesRequestFactory.shared.proxies;

//import org.liveSense.sample.gwt.notesRequestFactory.server.NoteLocator;
import org.liveSense.sample.gwt.notesRequestFactory.shared.domain.NoteBean;

import com.google.web.bindery.requestfactory.shared.ProxyFor;
import com.google.web.bindery.requestfactory.shared.ValueProxy;

/**
 * This interface is the note's entity-proxy. 
 * An entity proxy is a client-side representation of a server-side entity. The proxy interfaces are implemented by RequestFactory. * 
 */
@ProxyFor(value = NoteBean.class)
public interface NoteValueProxy extends ValueProxy {

    public void setTitle(String title);
	
    public void setText(String text);
    
    public void setPath(String path);
    
    public String getTitle();
    
    public String getText();
    
    public String getPath();    
}
