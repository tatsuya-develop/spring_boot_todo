import type Project from "@/features/project/models/project";
import type Tag from "@/features/tag/models/tag";
import { toYmdHm } from "@/lib/date";
import { isBefore } from "date-fns";
import type { TaskPriorityType } from "./taskPriority";

interface Task {
  id: number;
  project?: Project;
  tags: Tag[];
  parentId?: number;
  name: string;
  priority: TaskPriorityType;
  memo: string;
  deadlineAt?: Date;
  completedAt?: Date;
  createdAt: Date;
  updatedAt: Date;
}

export const isTaskCompleted = (task: Task) => {
  return !!task.completedAt && isBefore(task.completedAt, new Date());
};

export const getDeadline = (task: Task): string => {
  if (!task.deadlineAt) {
    return "いつか";
  }

  return toYmdHm(task.deadlineAt);
};

export default Task;
