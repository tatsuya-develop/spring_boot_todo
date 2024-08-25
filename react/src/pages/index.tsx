import HomePage from "@/components/pages/Home";
import { ProjectProvider } from "@/features/project/contexts";
import { TagProvider } from "@/features/tag/contexts";
import { TaskProvider } from "@/features/task/contexts";

const Home = () => {
  return (
    <ProjectProvider>
      <TagProvider>
        <TaskProvider>
          <HomePage />
        </TaskProvider>
      </TagProvider>
    </ProjectProvider>
  );
};

export default Home;
