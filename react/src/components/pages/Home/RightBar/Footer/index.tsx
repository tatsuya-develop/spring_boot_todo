import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import useTaskContext from "@/features/task/hooks/useTaskContext";
import { deleteTask } from "@/features/task/services/taskService";
import { toYmdHm } from "@/lib/date";
import { Flex, IconButton, Text } from "@chakra-ui/react";
import { faAngleRight, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const RightBarFooter = () => {
  const selectedTask = useGetSelectedTask();
  const { deleteTasks, resetSelectedTask } = useTaskContext();

  const onClickDeleteTask = async () => {
    await deleteTask(selectedTask.id);
    deleteTasks(selectedTask);
    resetSelectedTask();
  };

  return (
    <Flex direction="column" gap={4}>
      <Flex justifyContent="space-between" alignItems="center">
        <IconButton
          icon={<FontAwesomeIcon icon={faAngleRight} />}
          aria-label="CloseRightBar"
          onClick={() => resetSelectedTask()}
          bg="transparent"
          _hover={{ bg: "transparent" }}
        />
        <Text>{toYmdHm(selectedTask.createdAt)} 作成</Text>
        <IconButton
          icon={<FontAwesomeIcon icon={faTrash} />}
          aria-label="DeleteTask"
          onClick={onClickDeleteTask}
          bg="transparent"
          _hover={{ bg: "transparent" }}
        />
      </Flex>
    </Flex>
  );
};

export default RightBarFooter;
