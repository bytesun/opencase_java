package org.orcsun.sunspace.dao;

import java.util.List;
import org.orcsun.sunspace.object.Todo;

public abstract interface TodoDAO
{
  public abstract int addTodo(Todo paramTodo);

  public abstract int delTodo(long paramLong);

  public abstract int updateTodo(Todo paramTodo);

  public abstract Todo getTodo(long paramLong);

  public abstract List<Todo> findTodayTodos(long paramLong);

  public abstract int doneTodo(Object[] paramArrayOfObject);
}
