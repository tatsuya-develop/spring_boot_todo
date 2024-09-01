import useProjectContext from "@/features/project/hooks/useProjectContext";
import type Project from "@/features/project/models/project";
import { getProjectName } from "@/features/project/models/project";
import {
  type UpdateTaskSchemaType,
  generateUpdateTaskSchema,
} from "@/features/task/forms/updateTaskSchema";
import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import {
  Button,
  Flex,
  Icon,
  List,
  ListItem,
  Popover,
  PopoverArrow,
  PopoverBody,
  PopoverContent,
  PopoverTrigger,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { faCheck, faList } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type Props = {
  onChange: (task: UpdateTaskSchemaType) => void;
};

const RightBarSelectProject = ({ onChange }: Props) => {
  const selectedTask = useGetSelectedTask();
  const { projects } = useProjectContext();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const showSelectingCheck = (project?: Project) => {
    return selectedTask.project?.id === project?.id ? (
      <Icon as={FontAwesomeIcon} icon={faCheck} />
    ) : (
      <></>
    );
  };
  const projectList = [undefined, ...projects];
  const onClickProject = async (project?: Project) => {
    const task = generateUpdateTaskSchema(selectedTask);
    task.projectId = project?.id;

    onChange(task);
    onClose();
  };

  return (
    <Flex justifyContent="space-between" alignItems="center">
      <Flex alignItems="center" gap={5} flex="0 0 auto">
        <Icon as={FontAwesomeIcon} icon={faList} />
        <Text>プロジェクト</Text>
      </Flex>
      <Popover
        isOpen={isOpen}
        onOpen={onOpen}
        onClose={onClose}
        closeOnBlur={true}
      >
        <PopoverTrigger>
          <Button variant="transparent" px={0}>
            <Text fontWeight="normal">
              {getProjectName(selectedTask.project)}
            </Text>
          </Button>
        </PopoverTrigger>
        <PopoverContent>
          <PopoverArrow />
          <PopoverBody>
            <List spacing={3}>
              {projectList.map((project) => (
                <ListItem
                  as={Flex}
                  key={project?.id ?? "UNSELECTED PROJECT"}
                  cursor="pointer"
                  onClick={() => onClickProject(project)}
                  justifyContent="space-between"
                  alignItems="center"
                  gap={2}
                >
                  <Text>{getProjectName(project)}</Text>
                  {showSelectingCheck(project)}
                </ListItem>
              ))}
            </List>
          </PopoverBody>
        </PopoverContent>
      </Popover>
    </Flex>
  );
};

export default RightBarSelectProject;
