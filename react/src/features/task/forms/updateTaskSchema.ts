import { z } from "zod";
import type Task from "../models/task";
import BaseTaskSchema, { baseTaskSchemaDefaultValues } from "./baseTaskSchema";

const defaultValues = {
  tagIds: [],
};

// deadlineAt, completedAt
const UpdateTaskSchema = BaseTaskSchema.extend({
  id: z.number().positive("IDは正の整数である必要があります"),
  tagIds: z.array(z.number().positive()).transform((tagIds) => {
    return Array.from(new Set(tagIds));
  }),
  deadlineAt: z.date().optional(),
  completedAt: z.date().optional(),
});

export type UpdateTaskSchemaType = z.infer<typeof UpdateTaskSchema>;

export const updateTaskSchemaDefaultValues: Omit<UpdateTaskSchemaType, "id"> = {
  ...baseTaskSchemaDefaultValues,
  ...defaultValues,
};

export const generateUpdateTaskSchema = (model: Task): UpdateTaskSchemaType => {
  return {
    id: model.id,
    projectId: model.project?.id,
    tagIds: model.tags.map((tag) => tag.id),
    name: model.name,
    priority: model.priority.value,
    memo: model.memo,
    deadlineAt: model.deadlineAt,
    completedAt: model.completedAt,
  };
};

export default UpdateTaskSchema;
