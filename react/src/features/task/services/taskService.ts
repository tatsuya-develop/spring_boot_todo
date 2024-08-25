import apiClient from "@/adapters/apiClient";
import type { CreateTaskSchemaType } from "@/features/task/forms/createTaskSchema";
import type Task from "@/features/task/models/task";
import type TaskQueryModel from "@/features/task/models/taskQueryModel";
import { convertDate } from "@/lib/date";
import type { UpdateTaskSchemaType } from "../forms/updateTaskSchema";
import TaskPriority from "../models/taskPriority";

type ResponseTaskType = Task & {
  deadlineAt?: string;
  completedAt?: string;
  createdAt: string;
  updatedAt: string;
};

export const searchTasks = async (
  params: TaskQueryModel = {},
): Promise<Task[]> => {
  const response = await apiClient.get<ResponseTaskType[]>("/tasks", {
    params,
  });
  return convertResponseToTasks(response.data);
};

export const createTask = async (
  params: CreateTaskSchemaType,
): Promise<Task> => {
  const response = await apiClient.post<ResponseTaskType>("/tasks", params);
  return convertResponseToTask(response.data);
};

export const updateTask = async (
  params: UpdateTaskSchemaType,
): Promise<Task> => {
  const response = await apiClient.put<ResponseTaskType>("/tasks", params);
  return convertResponseToTask(response.data);
};

export const deleteTask = async (id: number): Promise<void> => {
  await apiClient.delete(`/tasks/${id}`);
};

export const toggleTaskCompleted = async (id: number): Promise<Task> => {
  const response = await apiClient.put<ResponseTaskType>(`/tasks/${id}/toggle`);
  return convertResponseToTask(response.data);
};

const convertResponseToTasks = (response: ResponseTaskType[]): Task[] => {
  return response.map((task) => convertResponseToTask(task));
};

const convertResponseToTask = (response: ResponseTaskType): Task => {
  return {
    ...response,
    priority: TaskPriority[response.priority.name],
    deadlineAt: convertDate(response.deadlineAt),
    completedAt: convertDate(response.completedAt),
  };
};
