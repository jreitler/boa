package com.reitler.boa.app.gui;

import java.util.HashSet;
import java.util.Set;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public abstract class AbstractTableModel implements TableModel {

	private final Set<TableModelListener> listeners = new HashSet<>();

	@Override
	public void addTableModelListener(final TableModelListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeTableModelListener(final TableModelListener l) {
		this.listeners.remove(l);
	}

	protected void update() {
		for (TableModelListener l : this.listeners) {
			l.tableChanged(new TableModelEvent(this));
		}
	}

}
