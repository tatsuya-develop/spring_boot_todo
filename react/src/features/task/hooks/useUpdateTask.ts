import useProjectContext from "@/features/project/hooks/useProjectContext";
import UpdateTaskSchema, {
  type UpdateTaskSchemaType,
} from "@/features/task/forms/updateTaskSchema";
import useTaskContext from "@/features/task/hooks/useTaskContext";
import { updateTask } from "@/features/task/services/taskService";

const useUpdateTask = () => {
  const { selectTask, upsertTasks, selectedTask } = useTaskContext();
  const { selectProject, resetProject } = useProjectContext();

  const updateAction = async (taskSchema: UpdateTaskSchemaType) => {
    const updatedTask = await updateTask(UpdateTaskSchema.parse(taskSchema));

    if (selectedTask?.project?.id !== updatedTask.project?.id) {
      updatedTask.project ? selectProject(updatedTask.project) : resetProject();
    }

    selectTask(updatedTask);
    upsertTasks(updatedTask);
  };

  return updateAction;
};

export default useUpdateTask;
