import type Task from "@/features/task/models/task";
import { createContext, useContext, useState } from "react";

interface TaskContextType {
  tasks: Task[];
  selectedTask?: Task;
  setTasks: (tasks: Task[]) => void;
  selectTask: (task?: Task) => void;
  resetSelectedTask: () => void;
}

const TaskContext = createContext<TaskContextType | undefined>(undefined);

export const TaskProvider = ({ children }: { children: React.ReactNode }) => {
  const [tasks, setTasks] = useState<Task[]>([]);
  const [selectedTask, setSelectedTask] = useState<Task | undefined>(undefined);
  const selectTask = (task?: Task) => setSelectedTask(task);
  const resetSelectedTask = () => setSelectedTask(undefined);

  return (
    <TaskContext.Provider
      value={{ tasks, selectedTask, setTasks, selectTask, resetSelectedTask }}
    >
      {children}
    </TaskContext.Provider>
  );
};

export const useTaskContext = (): TaskContextType => {
  const context = useContext(TaskContext);
  if (!context) {
    throw new Error("useTasks must be used within a TaskProvider");
  }
  return context;
};
