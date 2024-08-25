import { useProjectContext } from "@/features/project/contexts";
import { Box } from "@chakra-ui/react";
import MainNotSelectedProject from "./NotSelectedProject";
import MainSelectedProject from "./SelectedProject";

const Main = () => {
  const { selectedProject } = useProjectContext();

  return (
    <Box height="100%" p={2} bg="gray.50">
      {selectedProject ? (
        <MainSelectedProject project={selectedProject} />
      ) : (
        <MainNotSelectedProject />
      )}
    </Box>
  );
};

export default Main;
