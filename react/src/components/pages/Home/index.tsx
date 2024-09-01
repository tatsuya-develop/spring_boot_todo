import LeftBar from "@/components/pages/Home/LeftBar";
import Main from "@/components/pages/Home/Main";
import RightBar from "@/components/pages/Home/RightBar";
import useProjectContext from "@/features/project/hooks/useProjectContext";
import { searchProjects } from "@/features/project/services/projectService";
import { useTagContext } from "@/features/tag/contexts";
import { searchTags } from "@/features/tag/services/tagService";
import useTaskContext from "@/features/task/hooks/useTaskContext";
import { searchTasks } from "@/features/task/services/taskService";
import { Grid, GridItem } from "@chakra-ui/react";
import { useEffect } from "react";

const HomePage = () => {
  const { setProjects, selectedProject } = useProjectContext();
  const { setTags } = useTagContext();
  const { setTasks, selectedTask } = useTaskContext();

  useEffect(() => {
    searchProjects().then((data) => setProjects(data));
    searchTags().then((data) => setTags(data));
  }, [setProjects, setTags]);

  useEffect(() => {
    searchTasks({ projectId: selectedProject?.id }).then((data) =>
      setTasks(data),
    );
  }, [selectedProject, setTasks]);

  return (
    <>
      <Grid
        templateColumns={selectedTask ? "300px 1fr 480px" : "300px 1fr 0px"}
        height="inherit"
        transition="all 0.3s ease-in-out"
      >
        <GridItem borderRight="1px" borderColor="gray.200">
          <LeftBar />
        </GridItem>
        <GridItem>
          <Main />
        </GridItem>
        <GridItem
          width={selectedTask ? "480px" : "0px"}
          overflow="hidden"
          transition="width 0.3s ease-in-out"
        >
          {!!selectedTask && <RightBar />}
        </GridItem>
      </Grid>
    </>
  );
};

export default HomePage;
