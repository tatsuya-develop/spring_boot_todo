import TaskPriority, {
  TaskPriorityMap,
} from "@/features/task/models/taskPriority";
import { z } from "zod";

const defaultValues = {
  name: "",
  priority: TaskPriority.MEDIUM.value,
  memo: "",
};

const projectId = z.number().positive().optional();
const name = z
  .string({ required_error: "入力必須項目です" })
  .min(1, { message: "入力必須項目です" })
  .max(100, { message: "100文字以内で入力してください" })
  .default(defaultValues.name);

const priority = z.nativeEnum(TaskPriorityMap).default(defaultValues.priority);
const memo = z.string().default(defaultValues.memo);

const BaseTaskSchema = z.object({ projectId, name, priority, memo });

export type BaseTaskSchemaType = z.infer<typeof BaseTaskSchema>;

export const baseTaskSchemaDefaultValues: BaseTaskSchemaType = defaultValues;

export default BaseTaskSchema;
