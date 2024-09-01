import type Task from "@/features/task/models/task";
import { createContext, useState } from "react";

export interface TaskContextType {
  tasks: Task[];
  selectedTask?: Task;
  setTasks: (tasks: Task[]) => void;
  upsertTasks: (task: Task) => void;
  deleteTasks: (task: Task) => void;
  selectTask: (task: Task) => void;
  resetSelectedTask: () => void;
}

export const TaskContext = createContext<TaskContextType | undefined>(
  undefined,
);

export const TaskProvider = ({ children }: { children: React.ReactNode }) => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const upsertTasks = (task: Task) => {
    if (tasks.some((t) => t.id === task.id)) {
      // 更新
      setTasks(tasks.map((t) => (t.id === task.id ? task : t)));
    } else {
      // 追加
      setTasks([...tasks, task]);
    }
  };
  const deleteTasks = (task: Task) =>
    setTasks(tasks.filter((t) => t.id !== task.id));
  const [selectedTask, setSelectedTask] = useState<Task | undefined>(undefined);
  const selectTask = (task?: Task) => setSelectedTask(task);
  const resetSelectedTask = () => setSelectedTask(undefined);

  return (
    <TaskContext.Provider
      value={{
        tasks,
        selectedTask,
        setTasks,
        upsertTasks,
        deleteTasks,
        selectTask,
        resetSelectedTask,
      }}
    >
      {children}
    </TaskContext.Provider>
  );
};
