import ToggleTaskCompletedIconButton from "@/features/task/components/ToggleTaskCompletedIconButton";
import useTaskContext from "@/features/task/hooks/useTaskContext";
import type Task from "@/features/task/models/task";
import { getDeadline, isTaskCompleted } from "@/features/task/models/task";
import {
  Accordion,
  AccordionButton,
  AccordionIcon,
  AccordionItem,
  AccordionPanel,
  Box,
  Button,
  Flex,
  Heading,
  Icon,
  Text,
} from "@chakra-ui/react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

interface TaskListAccordionProps {
  title: string;
  tasks: Task[];
}

const TaskListAccordion = (props: TaskListAccordionProps) => {
  const { selectedTask, selectTask, resetSelectedTask } = useTaskContext();

  const switchSelectedTask = (task: Task) => {
    task.id === selectedTask?.id ? resetSelectedTask() : selectTask(task);
  };

  const displayRightArea = (task: Task) => {
    return isTaskCompleted(task) ? (
      <Text>{getDeadline(task)}</Text>
    ) : (
      <Icon
        as={FontAwesomeIcon}
        icon={task.priority.icon}
        color={task.priority.color}
      />
    );
  };

  return (
    <Accordion allowToggle bg="white">
      <AccordionItem>
        <Heading as="h3">
          <AccordionButton py={4}>
            <Box flex="1" textAlign="left">
              <Text fontSize="lg" fontWeight="bold">
                {props.title}
              </Text>
            </Box>
            <AccordionIcon />
          </AccordionButton>
        </Heading>
        <AccordionPanel>
          {props.tasks.map((task) => (
            <Flex
              key={task.id}
              justifyContent="space-between"
              alignItems="center"
              borderBottom="1px"
              borderColor="gray.100"
              py={1}
            >
              <Flex width="full">
                <ToggleTaskCompletedIconButton task={task} />
                <Button
                  variant="link"
                  color="inherit"
                  _hover={{ textDecoration: "none" }}
                  onClick={() => switchSelectedTask(task)}
                  flex={1}
                  justifyContent="flex-start"
                  fontWeight="normal"
                >
                  {task.name}
                </Button>
              </Flex>
              <Flex flex="0 0 auto">{displayRightArea(task)}</Flex>
            </Flex>
          ))}
        </AccordionPanel>
      </AccordionItem>
    </Accordion>
  );
};

export default TaskListAccordion;
