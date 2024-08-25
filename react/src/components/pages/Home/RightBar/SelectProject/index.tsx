import { useProjectContext } from "@/features/project/contexts";
import type Project from "@/features/project/models/project";
import {
  type UpdateTaskSchemaType,
  generateUpdateTaskSchema,
} from "@/features/task/forms/updateTaskSchema";
import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import { Flex, Icon, Select, Text } from "@chakra-ui/react";
import { faList } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type Props = {
  onChange: (task: UpdateTaskSchemaType) => void;
};

const RightBarSelectProject = ({ onChange }: Props) => {
  const selectedTask = useGetSelectedTask();
  const { projects } = useProjectContext();

  const onClickProject = async (e: React.ChangeEvent<HTMLSelectElement>) => {
    const task = generateUpdateTaskSchema(selectedTask);
    task.projectId = Number(e.target.value);

    onChange(task);
  };

  return (
    <Flex justifyContent="space-between" alignItems="center">
      <Flex alignItems="center" gap={5} flex="0 0 auto">
        <Icon as={FontAwesomeIcon} icon={faList} />
        <Text>プロジェクト</Text>
      </Flex>
      <Select
        value={selectedTask.project?.id}
        onChange={onClickProject}
        variant="unstyled"
        textAlign="right"
        // biome-ignore lint/complexity/noUselessFragments: <explanation>
        icon={<></>}
        sx={{ paddingInlineEnd: 0 }}
        cursor="pointer"
      >
        {projects.map((project: Project) => (
          <option key={project.id} value={project.id}>
            {project.name}
          </option>
        ))}
      </Select>
    </Flex>
  );
};

export default RightBarSelectProject;
