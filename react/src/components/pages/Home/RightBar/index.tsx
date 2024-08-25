import type { UpdateTaskSchemaType } from "@/features/task/forms/updateTaskSchema";
import useUpdateTask from "@/features/task/hooks/useUpdateTask";
import { Box, Flex } from "@chakra-ui/react";
import RightBarFooter from "./Footer";
import RightBarHeader from "./Header";
import RightBarInputMemo from "./InputMemo";
import RightBarSelectDeadlineAt from "./SelectDeadlineAt";
import RightBarSelectProject from "./SelectProject";

const RightBar = () => {
  const updateTask = useUpdateTask();
  const handleUpdateTask = async (task: UpdateTaskSchemaType) =>
    await updateTask(task);

  return (
    <Box bg="gray.50" height="100%" px={2} py={4}>
      <Flex
        bg="white"
        height="inherit"
        direction="column"
        justifyContent="space-between"
        gap={2}
        px={2}
        pt={2}
      >
        <Flex direction="column" gap={4} flex={1}>
          <RightBarHeader onChange={handleUpdateTask} />
          <Flex direction="column" gap={4} p={2} flex={1}>
            <Flex direction="column" gap={8} px={1} flex={1}>
              <RightBarSelectDeadlineAt onChange={handleUpdateTask} />
              <RightBarSelectProject onChange={handleUpdateTask} />
              <RightBarInputMemo onBlur={handleUpdateTask} />
            </Flex>
          </Flex>
        </Flex>
        <RightBarFooter />
      </Flex>
    </Box>
  );
};

export default RightBar;
