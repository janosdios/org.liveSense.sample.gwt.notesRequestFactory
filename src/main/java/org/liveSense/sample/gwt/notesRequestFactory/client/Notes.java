package org.liveSense.sample.gwt.notesRequestFactory.client;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.liveSense.sample.gwt.notesRequestFactory.shared.proxies.NoteValueProxy;
import org.liveSense.sample.gwt.notesRequestFactory.shared.requestfactories.NoteRequestFactory;
import org.liveSense.sample.gwt.notesRequestFactory.shared.requestfactories.NoteRequestFactory.NoteRequestContext;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.web.bindery.requestfactory.gwt.client.DefaultRequestTransport;
import com.google.web.bindery.requestfactory.shared.Receiver;
import com.google.web.bindery.requestfactory.shared.ServerFailure;

public class Notes implements EntryPoint {

	final TextBox inputNoteTitle = new TextBox();
	final TextArea inputNoteText = new TextArea();
	final VerticalPanel notesPanel = new VerticalPanel();

	{
		inputNoteTitle.setStyleName("formField");
		inputNoteText.setStyleName("formField");
		inputNoteText.setVisibleLines(3);
	}

	NoteRequestFactory factory = GWT.create(NoteRequestFactory.class);

	public void onModuleLoad() {
		DefaultRequestTransport transport = new DefaultRequestTransport();
		transport.setRequestUrl("/gwt/sample/noterequestfactoryservice");
		factory.initialize(new SimpleEventBus(), transport);

		final HorizontalPanel mainpanel = new HorizontalPanel();

		final HTML displayTitle = new HTML("Existing Notes");
		displayTitle.setStyleName("displayTitle");
		final HTML entryTitle = new HTML("Create A Note");
		entryTitle.setStyleName("entryTitle");

		final VerticalPanel displayPanel = new VerticalPanel();
		displayPanel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		displayPanel.setStyleName("displayPanel");
		displayPanel.add(displayTitle);

		final VerticalPanel entryPanel = new VerticalPanel();
		entryPanel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		entryPanel.setStyleName("entryPanel");
		entryPanel.add(entryTitle);

		displayPanel.add(notesPanel);

		final VerticalPanel form = createForm();
		entryPanel.add(form);

		mainpanel.add(displayPanel);
		mainpanel.add(entryPanel);

		RootPanel.get("notes").add(mainpanel);


		getNotes();
	}

	private VerticalPanel createForm() {

		final VerticalPanel form = new VerticalPanel();
		form.setStyleName("formPanel");

		final HorizontalPanel titleLine = new HorizontalPanel();
		final HTML textNoteTitle = new HTML("Title: ");
		textNoteTitle.setWidth("50px");
		titleLine.add(textNoteTitle);
		titleLine.add(inputNoteTitle);

		final HorizontalPanel textLine = new HorizontalPanel();
		final HTML textNoteText = new HTML("Note: ");
		textNoteText.setWidth("50px");
		textLine.add(textNoteText);
		textLine.add(inputNoteText);

		form.add(titleLine);
		form.add(textLine);
		form.add(createButtons());

		return form;
	}

	private Panel createButtons() {

		final HorizontalPanel panel = new HorizontalPanel();

		Button save = new Button("Save");
		save.setStyleName("button");
		save.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				createNote(inputNoteTitle.getText(), inputNoteText.getText());
				resetForm();
			}
		});

		Button clear = new Button("Clear");
		save.setStyleName("button");
		clear.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				resetForm();
			}
		});
		panel.add(save);
		panel.add(clear);
		return panel;
	}


	private String getViolationsAsString(Set<ConstraintViolation<?>> violations) {
		String str = "";
		for (ConstraintViolation<?> violation : violations) {
			str += violation.getMessage()+" - "+violation.getInvalidValue()+"\n";
		}
		return str;
	}
	public void createNote(String title, String text) {
		NoteRequestContext context = factory.context();

		NoteValueProxy note = context.create(NoteValueProxy.class);
		note.setTitle(title);
		note.setText(text);

		context.createNote(note).fire( new Receiver<Void>() {
			public void onSuccess( Void arg0 ) {
				getNotes();
			}
			public void onFailure( ServerFailure error ) {
				Window.alert("Failed to created note: " + error);
			}
		});
	}	

	private void getNotes() {
		notesPanel.clear();

		NoteRequestContext context = factory.context();
		context.getNotes().fire(new Receiver<List<NoteValueProxy>>() {
			@Override
			public void onSuccess(List<NoteValueProxy> notesList) {
				for (int i = 0; i < notesList.size(); i++) {
					final NoteValueProxy note = notesList.get(i);

					final HorizontalPanel noteEntry = new HorizontalPanel();
					noteEntry.setStyleName("noteEntry");

					final HTML noteTitle = new HTML(note.getTitle());
					noteTitle.setStyleName("noteTitle");

					final HTML noteText = new HTML(note.getText());
					noteText.setStyleName("noteText");

					final Button delButton = new Button("Delete");
					delButton.setStyleName("noteControls");
					delButton.addClickHandler(new ClickHandler() {

						public void onClick(ClickEvent event) {
							NoteRequestContext context = factory.context();
							context.deleteNote(note.getPath()).fire(new Receiver<Void>() {

								@Override
								public void onSuccess(Void response) {
									getNotes();
								}

								@Override
								public void onFailure(ServerFailure error) {
									Window.alert("Could not delete note: " + note.getPath()+" "+error.getMessage());
								};

								@Override
								public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
									Window.alert("Could not delete note (contraint violation): " + note.getPath()+" "+getViolationsAsString(violations));
								}

							});
						}
					});

					noteEntry.add(noteTitle);
					noteEntry.add(noteText);
					noteEntry.add(delButton);

					notesPanel.add(noteEntry);

				}
			}

			@Override
			public void onFailure(ServerFailure error) {
				notesPanel.add(new HTML("No notes stored so far."));
				Window.alert("Could not retrieve notes: " + error);
			}

			@Override
			public void onConstraintViolation(Set<ConstraintViolation<?>> violations) {
				Window.alert("Could retreive notes (contraint violation): "+getViolationsAsString(violations));
			}

		});
	}	

	private void resetForm() {
		inputNoteTitle.setText("");
		inputNoteText.setText("");
	}
}