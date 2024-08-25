import apiClient from "@/adapters/apiClient";
import type { TagSchemaType } from "@/features/tag/forms/tagSchema";
import type Tag from "@/features/tag/models/tag";

export const searchTags = async (): Promise<Tag[]> => {
  const response = await apiClient.get<Tag[]>("/tags");
  return response.data;
};

export const createTag = async (params: TagSchemaType): Promise<Tag> => {
  const response = await apiClient.post<Tag>("/tags", params);
  return response.data;
};
