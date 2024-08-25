import apiClient from "@/adapters/apiClient";
import type { ProjectSchemaType } from "@/features/project/forms/projectSchema";
import type Project from "@/features/project/models/project";

export const searchProjects = async (): Promise<Project[]> => {
  const response = await apiClient.get<Project[]>("/projects");
  return response.data;
};

export const createProject = async (
  params: ProjectSchemaType,
): Promise<Project> => {
  const response = await apiClient.post<Project>("/projects", params);
  return response.data;
};
