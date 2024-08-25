import {
  faAngleDoubleDown,
  faAngleDoubleUp,
  faBars,
} from "@fortawesome/free-solid-svg-icons";

const TaskPriority = {
  LOW: {
    name: "LOW",
    value: 0,
    label: "低",
    icon: faAngleDoubleDown,
    color: "blue.400",
  },
  MEDIUM: {
    name: "MEDIUM",
    value: 1,
    label: "中",
    icon: faBars,
    color: "yellow.400",
  },
  HIGH: {
    name: "HIGH",
    value: 2,
    label: "高",
    icon: faAngleDoubleUp,
    color: "red.400",
  },
} as const;

export type TaskPriorityType = (typeof TaskPriority)[keyof typeof TaskPriority];

export const TaskPriorityArray: TaskPriorityType[] =
  Object.values(TaskPriority);

export const pluckTaskPriorityArray = <K extends keyof TaskPriorityType>(
  field: K,
) => {
  return TaskPriorityArray.map((priority) => priority[field]);
};

type TaskPriorityMapType = {
  [K in TaskPriorityType["name"]]: Extract<
    TaskPriorityType,
    { name: K }
  >["value"];
};

export const TaskPriorityMap: TaskPriorityMapType = {
  LOW: TaskPriority.LOW.value,
  MEDIUM: TaskPriority.MEDIUM.value,
  HIGH: TaskPriority.HIGH.value,
};

export const getTaskPriority = (value: TaskPriorityType["value"]) => {
  const priority = TaskPriorityArray.find(
    (priority) => priority.value === value,
  );
  // 引数: value がTaskPriorityに確実に存在する値しか受け付けないため、例外は基本起きることはない
  if (!priority) {
    throw new Error(`TaskPriority not found: ${value}`);
  }

  return priority;
};

// interface TaskPriorityDisplayProps {
//   icon: IconDefinition;
//   label: string;
//   color: string;
// }

// type TaskPriority = 0 | 1 | 2;

// export const TaskPriorityArray: TaskPriority[] = [0, 1, 2];

// export const isTaskPriority = (
//   priority: number | string | TaskPriority,
// ): priority is TaskPriority => {
//   return TaskPriorityArray.includes(priority as TaskPriority);
// };

// export const TaskPriorityValues = {
//   LOW: 0 as TaskPriority,
//   MEDIUM: 1 as TaskPriority,
//   HIGH: 2 as TaskPriority,
// };

// export const TaskPriorityDisplay: {
//   [key in TaskPriority]: TaskPriorityDisplayProps;
// } = {
//   0: { icon: faAngleDoubleDown, label: "低", color: "blue.400" },
//   1: { icon: faBars, label: "中", color: "yellow.400" },
//   2: { icon: faAngleDoubleUp, label: "高", color: "red.400" },
// };

export default TaskPriority;
