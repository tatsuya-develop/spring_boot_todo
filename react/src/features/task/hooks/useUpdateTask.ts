import { useProjectContext } from "@/features/project/contexts";
import { useTaskContext } from "@/features/task/contexts";
import UpdateTaskSchema, {
  type UpdateTaskSchemaType,
} from "@/features/task/forms/updateTaskSchema";
import { updateTask } from "@/features/task/services/taskService";

const useUpdateTask = () => {
  const { selectTask, selectedTask } = useTaskContext();
  const { selectProject } = useProjectContext();

  const updateAction = async (taskSchema: UpdateTaskSchemaType) => {
    const updatedTask = await updateTask(UpdateTaskSchema.parse(taskSchema));

    if (selectedTask?.project?.id !== updatedTask.project?.id) {
      selectProject(updatedTask?.project);
    }

    selectTask(updatedTask);
  };

  return updateAction;
};

export default useUpdateTask;
