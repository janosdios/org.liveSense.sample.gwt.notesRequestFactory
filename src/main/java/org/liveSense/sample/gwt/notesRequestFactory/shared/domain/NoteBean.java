/*
 *  Copyright 2012 Janos Dios <janos.dios@allretailconsulting.com>.
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
 * @author Janos Dios (janos.dios@allretailconsulting.com)
 * @created Jun 22, 2012
 */
package org.liveSense.sample.gwt.notesRequestFactory.shared.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * This class represents a GWT entity. An entity is a domain class in this application that has concept of a persistent identity.
 * The <code>Note</code> class features simple getters and setters for its data.
 */
@Entity
public class NoteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The default public constructor.
	 */
	public NoteBean() {
	}

	/**
	 * The String representing the title of the note.
	 */
	@Size(min = 3, max = 30)
	private String title;

	/**
	 * The String representing the text of the note.
	 */
	@NotNull
	private String text;

	/**
	 * The String representing the path of the <code>javax.jcr.Node</code> that this entity is based on.
	 */
	private String path;

	/**
	 * The setter method for the <code>String</code> representing the title of the note.
	 * 
	 * @param title The <code>String</code> representing the title of the note.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The setter method for the <code>String</code> representing the text of the note.
	 * 
	 * @param text The <code>String</code> representing the text of the note.
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * The setter method for the <code>String</code> representing the path of the note.
	 * 
	 * @param path The <code>String</code> representing the path of the <code>javax.jcr.Node</code> corresponding
	 *             to this entity.
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * The getter method for the <code>String</code> representing the title of the note.
	 *
	 * @return The <code>String</code> representing the title of the note.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * The getter method for the <code>String</code> representing the text of the note.
	 *
	 * @return The <code>String</code> representing the text of the note.
	 */
	public String getText() {
		return text;
	}

	/**
	 * The getter method for the <code>String</code> representing the path of the <code>javax.jcr.Node</code>
	 * corresponding to this entity.
	 *
	 * @return The <code>String</code> representing the path of the note.
	 */
	public String getPath() {
		return path;
	}


}
