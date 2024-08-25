import type Project from "@/features/project/models/project";
import AddTaskInput from "@/features/task/components/AddTaskInput";
import TaskListAccordion from "@/features/task/components/TaskListAccordion";
import useGetTasks from "@/features/task/hooks/useGetTasks";
import { Box, Flex, Heading, Text } from "@chakra-ui/react";

type MainSelectedProjectProps = {
  project: Project;
};

const MainSelectedProject = (props: MainSelectedProjectProps) => {
  const { incompleteTasks, completedTasks } = useGetTasks();

  return (
    <Flex direction="column" gap={4}>
      <Heading as="h2" size="md" my={4}>
        {props.project.name}
      </Heading>
      {/* サマリ */}
      <Box bg="white" p={2} my={2}>
        <Flex width="inherit" justifyContent="space-around">
          <Flex direction="column" justifyContent="center" alignItems="center">
            <Text fontSize="3xl">{incompleteTasks.length}</Text>
            <Text>未完了のタスク</Text>
          </Flex>
          <Flex direction="column" justifyContent="center" alignItems="center">
            <Text fontSize="3xl" color="green.400">
              {completedTasks.length}
            </Text>
            <Text>完了済のタスク</Text>
          </Flex>
        </Flex>
      </Box>
      {/* タスク追加 */}
      <Box bg="white" p={2} my={2}>
        <AddTaskInput />
      </Box>
      {/* タスク一覧 */}
      <Flex direction="column" gap={8} my={2}>
        <TaskListAccordion title="未完了のタスク" tasks={incompleteTasks} />
        <TaskListAccordion title="完了済のタスク" tasks={completedTasks} />
      </Flex>
    </Flex>
  );
};

export default MainSelectedProject;
