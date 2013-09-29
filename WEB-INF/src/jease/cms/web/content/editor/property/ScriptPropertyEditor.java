/*
    Copyright (C) 2013 maik.jablonski@jease.org

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jease.cms.web.content.editor.property;

import jease.cms.domain.property.ScriptProperty;
import jfix.util.I18N;
import jfix.zk.ActionListener;
import jfix.zk.Button;
import jfix.zk.Codearea;
import jfix.zk.Div;
import jfix.zk.Images;
import jfix.zk.Modal;
import jfix.zk.Textfield;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zk.ui.event.Event;

public class ScriptPropertyEditor extends Div implements
		PropertyEditor<ScriptProperty> {

	private Textfield classarea = new Textfield();
	private Codearea codearea = new Codearea();
	private ScriptProperty property;

	public ScriptPropertyEditor() {
		classarea.setWidth("100%");
		codearea.setHeight("300px");
		codearea.setSyntax("java");
		Button execute = new Button(I18N.get("Execute"),
				Images.MediaPlaybackStart, new ActionListener() {
					public void actionPerformed(Event event) {
						executePerformed();
					}
				});
		appendChild(new Div(codearea, classarea));
		appendChild(execute);
	}

	public ScriptProperty getProperty() {
		if (codearea.isVisible()) {
			property.setCode(codearea.getValue());
		} else {
			property.setCode(classarea.getValue());
		}
		return property;
	}

	public void setProperty(ScriptProperty property) {
		boolean clazzMode = StringUtils.isNotBlank(property.getCode())
				&& !property.getCode().contains("\n");
		this.property = property;
		this.codearea.setValue(property.getCode());
		this.codearea.setVisible(!clazzMode);
		this.classarea.setValue(property.getCode());
		this.classarea.setVisible(clazzMode);
	}

	private void executePerformed() {
		ScriptProperty clone = property.copy();
		clone.setCode(codearea.getText());
		Modal.info(clone.toString());
	}

}