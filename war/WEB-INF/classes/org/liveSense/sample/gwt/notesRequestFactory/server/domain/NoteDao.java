package org.liveSense.sample.gwt.notesRequestFactory.server.domain;

import java.util.List;

import org.liveSense.sample.gwt.notesRequestFactory.server.domain.NoteBean;

public interface NoteDao {
	public void createNote(NoteBean note);
	public List<NoteBean> getNotes() throws Exception;
}
