import { isBefore } from "date-fns";

interface Project {
  id: number;
  name: string;
  summary: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
}

export const isProjectDeleted = (project: Project) => {
  return !!project.deletedAt && isBefore(project.deletedAt, new Date());
};

export default Project;
