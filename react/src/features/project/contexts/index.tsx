import type Project from "@/features/project/models/project";
import { createContext, useState } from "react";

export interface ProjectContextType {
  projects: Project[];
  selectedProject?: Project;
  setProjects: (projects: Project[]) => void;
  selectProject: (project: Project) => void;
  resetProject: () => void;
}

export const ProjectContext = createContext<ProjectContextType | undefined>(
  undefined,
);

export const ProjectProvider = ({
  children,
}: { children: React.ReactNode }) => {
  const [projects, setProjects] = useState<Project[]>([]);
  const [selectedProject, setSelectedProject] = useState<Project | undefined>(
    undefined,
  );
  const selectProject = (project: Project) => setSelectedProject(project);
  const resetProject = () => setSelectedProject(undefined);

  return (
    <ProjectContext.Provider
      value={{
        projects,
        selectedProject,
        setProjects,
        selectProject,
        resetProject,
      }}
    >
      {children}
    </ProjectContext.Provider>
  );
};
