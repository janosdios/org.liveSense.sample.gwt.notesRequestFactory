package org.liveSense.sample.gwt.notesRequestFactory.server.domain;

import java.util.List;

public interface NoteDao {
	public void createNote(NoteBean note);
	public void deleteNote(String path);
	public List<NoteBean> getNotes() throws Exception;
}
