package net.iubris.ulysses.ui.tasks._base;

import java.util.EnumMap;

public abstract class AbstractTasksMap<T extends PopulateTask> {

	protected final EnumMap<SearchType, T> tasksMap = new EnumMap<SearchType,T>(SearchType.class);
	
	public void putTask(SearchType action, T task) {
		tasksMap.put(action, task);
	}
	
	public T getTask(SearchType searchAction) {
		return tasksMap.get(searchAction);
	}

}
