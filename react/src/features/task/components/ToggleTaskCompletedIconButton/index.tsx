import ToggleIconButton from "@/components/common/ToggleIconButton";
import useTaskContext from "@/features/task/hooks/useTaskContext";
import type Task from "@/features/task/models/task";
import { isTaskCompleted } from "@/features/task/models/task";
import { toggleTaskCompleted } from "@/features/task/services/taskService";

type Props = {
  task: Task;
};

const ToggleTaskCompletedIconButton = ({ task }: Props) => {
  const { upsertTasks, selectedTask, selectTask } = useTaskContext();

  const toggleCompleted = async (targetTask: Task) => {
    const toggledTask = await toggleTaskCompleted(targetTask.id);

    if (selectedTask && selectedTask.id === toggledTask.id) {
      selectTask(toggledTask);
    }

    upsertTasks(toggledTask);
  };

  return (
    <ToggleIconButton
      item={task}
      onClick={toggleCompleted}
      isChecked={isTaskCompleted}
    />
  );
};

export default ToggleTaskCompletedIconButton;
