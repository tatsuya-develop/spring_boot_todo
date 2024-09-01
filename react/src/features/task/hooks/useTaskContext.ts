import { useContext } from "react";
import { TaskContext, type TaskContextType } from "../contexts";

const useTaskContext = (): TaskContextType => {
  const context = useContext(TaskContext);
  if (!context) {
    throw new Error("useTasks must be used within a TaskProvider");
  }
  return context;
};

export default useTaskContext;
