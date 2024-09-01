import useTaskContext from "@/features/task/hooks/useTaskContext";
import type Task from "../models/task";

const useGetSelectedTask = (): Task => {
  const { selectedTask } = useTaskContext();

  if (!selectedTask) {
    throw new Error("useGetSelectedTask must be used within a TaskProvider");
  }

  return selectedTask;
};

export default useGetSelectedTask;
