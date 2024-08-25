import CalendarSelect from "@/components/common/CalendarSelect";
import {
  type UpdateTaskSchemaType,
  generateUpdateTaskSchema,
} from "@/features/task/forms/updateTaskSchema";
import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import { Box, Flex, Icon, Text } from "@chakra-ui/react";
import { faCalendar } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type Props = {
  onChange: (task: UpdateTaskSchemaType) => void;
};

const RightBarSelectDeadlineAt = ({ onChange }: Props) => {
  const selectedTask = useGetSelectedTask();

  const onChangeDeadline = async (date: Date | null) => {
    const taskSchema = generateUpdateTaskSchema(selectedTask);
    taskSchema.deadlineAt = date ?? undefined;

    onChange(taskSchema);
  };

  return (
    <Flex justifyContent="space-between" alignItems="center">
      <Flex alignItems="center" gap={5} flex="0 0 auto">
        <Icon as={FontAwesomeIcon} icon={faCalendar} />
        <Text>期限</Text>
      </Flex>
      <Box>
        <CalendarSelect
          onDateChange={onChangeDeadline}
          placeholder="未設定"
          selectedDate={selectedTask.deadlineAt}
        />
      </Box>
    </Flex>
  );
};

export default RightBarSelectDeadlineAt;
