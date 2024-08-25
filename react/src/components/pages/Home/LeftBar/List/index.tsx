import { useProjectContext } from "@/features/project/contexts";
import { Button, List, ListItem } from "@chakra-ui/react";

const LeftBarList = () => {
  const { projects, selectedProject, selectProject } = useProjectContext();

  return (
    <List spacing={3}>
      {projects.map((project) => (
        <ListItem key={project.id}>
          <Button
            variant="ghost"
            onClick={() => selectProject(project)}
            width="100%"
            justifyContent="flex-start"
            bg={selectedProject?.id === project.id ? "blue.500" : "transparent"}
            color={selectedProject?.id === project.id ? "white" : "black"}
            _hover={{ bg: "blue.100" }}
          >
            {project.name}
          </Button>
        </ListItem>
      ))}
    </List>
  );
};

export default LeftBarList;
