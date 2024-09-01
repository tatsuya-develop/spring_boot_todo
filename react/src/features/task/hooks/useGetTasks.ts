import useTaskContext from "@/features/task/hooks/useTaskContext";
import { isTaskCompleted } from "@/features/task/models/task";
import { useMemo } from "react";

const useGetTasks = () => {
  const { tasks } = useTaskContext();
  const incompleteTasks = useMemo(
    () => tasks.filter((task) => !isTaskCompleted(task)),
    [tasks],
  );
  const completedTasks = useMemo(
    () => tasks.filter((task) => isTaskCompleted(task)),
    [tasks],
  );

  return { incompleteTasks, completedTasks };
};

export default useGetTasks;
