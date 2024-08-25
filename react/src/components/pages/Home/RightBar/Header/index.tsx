import PrioritySelectBox from "@/features/task/components/PrioritySelectBox";
import ToggleTaskCompletedIconButton from "@/features/task/components/ToggleTaskCompletedIconButton";
import {
  type UpdateTaskSchemaType,
  generateUpdateTaskSchema,
} from "@/features/task/forms/updateTaskSchema";
import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import type { TaskPriorityType } from "@/features/task/models/taskPriority";
import { Flex, Text } from "@chakra-ui/react";
import RightBarTags from "../Tags";

type Props = {
  onChange: (task: UpdateTaskSchemaType) => void;
};
const RightBarHeader = ({ onChange }: Props) => {
  const selectedTask = useGetSelectedTask();

  const onChangePrioritySelectBox = async (priority: TaskPriorityType) => {
    const task = generateUpdateTaskSchema(selectedTask);
    task.priority = priority.value;

    onChange(task);
  };

  return (
    <Flex direction="column" borderBottom="2px" borderColor="gray.50" py={2}>
      <Flex justifyContent="space-between" alignItems="center">
        <Flex gap={2} alignItems="center">
          <ToggleTaskCompletedIconButton task={selectedTask} />
          <Text>{selectedTask.name}</Text>
        </Flex>
        <PrioritySelectBox
          value={selectedTask.priority.value}
          onChange={onChangePrioritySelectBox}
        />
      </Flex>
      <RightBarTags onChange={onChange} />
    </Flex>
  );
};

export default RightBarHeader;
