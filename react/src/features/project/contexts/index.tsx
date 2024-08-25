import type Project from "@/features/project/models/project";
import { createContext, useContext, useState } from "react";

interface ProjectContextType {
  projects: Project[];
  selectedProject?: Project;
  setProjects: (projects: Project[]) => void;
  selectProject: (project?: Project) => void;
}

const ProjectContext = createContext<ProjectContextType | undefined>(undefined);

export const ProjectProvider = ({
  children,
}: { children: React.ReactNode }) => {
  const [projects, setProjects] = useState<Project[]>([]);
  const [selectedProject, setSelectedProject] = useState<Project | undefined>(
    undefined,
  );
  const selectProject = (project?: Project) => setSelectedProject(project);

  return (
    <ProjectContext.Provider
      value={{ projects, selectedProject, setProjects, selectProject }}
    >
      {children}
    </ProjectContext.Provider>
  );
};

export const useProjectContext = (): ProjectContextType => {
  const context = useContext(ProjectContext);
  if (!context) {
    throw new Error("useProjects must be used within a ProjectProvider");
  }
  return context;
};
