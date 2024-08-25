import type { z } from "zod";
import BaseTaskSchema, { baseTaskSchemaDefaultValues } from "./baseTaskSchema";

const CreateTaskSchema = BaseTaskSchema.extend({});

export type CreateTaskSchemaType = z.infer<typeof CreateTaskSchema>;

export const createTaskSchemaDefaultValues: CreateTaskSchemaType =
  baseTaskSchemaDefaultValues;

export default CreateTaskSchema;
