import useProjectContext from "@/features/project/hooks/useProjectContext";
import { getProjectName } from "@/features/project/models/project";
import { Button, List, ListItem } from "@chakra-ui/react";

const LeftBarList = () => {
  const { projects, selectedProject, selectProject, resetProject } =
    useProjectContext();
  const viewProjectList = [undefined, ...projects];

  return (
    <List spacing={3}>
      {viewProjectList.map((project) => (
        <ListItem key={project?.id ?? "non-selected"}>
          <Button
            variant="ghost"
            width="100%"
            justifyContent="flex-start"
            _hover={{ bg: "blue.100" }}
            onClick={() => (project ? selectProject(project) : resetProject())}
            bg={
              selectedProject?.id === project?.id ? "blue.500" : "transparent"
            }
            color={selectedProject?.id === project?.id ? "white" : "black"}
          >
            {getProjectName(project)}
          </Button>
        </ListItem>
      ))}
    </List>
  );
};

export default LeftBarList;
