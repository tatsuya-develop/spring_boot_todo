import { useContext } from "react";
import { ProjectContext, type ProjectContextType } from "../contexts";

const useProjectContext = (): ProjectContextType => {
  const context = useContext(ProjectContext);
  if (!context) {
    throw new Error("useProjects must be used within a ProjectProvider");
  }
  return context;
};

export default useProjectContext;
