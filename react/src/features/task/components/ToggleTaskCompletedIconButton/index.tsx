import ToggleIconButton from "@/components/common/ToggleIconButton";
import { useTaskContext } from "@/features/task/contexts";
import type Task from "@/features/task/models/task";
import { isTaskCompleted } from "@/features/task/models/task";
import { toggleTaskCompleted } from "@/features/task/services/taskService";

type Props = {
  task: Task;
};

const ToggleTaskCompletedIconButton = ({ task }: Props) => {
  const { tasks, setTasks } = useTaskContext();

  const toggleCompleted = async (targetTask: Task) => {
    const toggledTask = await toggleTaskCompleted(targetTask.id);
    const updatedTasks = tasks.map((t) => {
      if (t.id === toggledTask.id) {
        return toggledTask;
      }

      return t;
    });

    setTasks(updatedTasks);
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
