package org.orcsun.sunspace.dao;

import java.util.List;
import org.orcsun.sunspace.object.Todo;

public abstract interface TodoDAO
{
  public abstract int addTodo(Todo paramTodo);

  public abstract int delTodo(long paramLong);

  public abstract int updateTodo(Todo paramTodo);

  public abstract Todo getTodo(long tid);

  public abstract List<Todo> findTodayTodos(long uid);

  public abstract int doneTodo(long tid);
}
