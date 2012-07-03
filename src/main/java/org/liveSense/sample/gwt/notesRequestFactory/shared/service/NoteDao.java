package org.liveSense.sample.gwt.notesRequestFactory.shared.service;

import java.util.List;

import org.liveSense.sample.gwt.notesRequestFactory.shared.domain.NoteBean;

public interface NoteDao {
	public void createNote(NoteBean note);
	public void deleteNote(String path);
	public List<NoteBean> getNotes() throws Exception;
}
