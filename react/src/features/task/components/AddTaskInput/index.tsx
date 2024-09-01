import useProjectContext from "@/features/project/hooks/useProjectContext";
import TaskSchema, {
  type CreateTaskSchemaType,
  createTaskSchemaDefaultValues,
} from "@/features/task/forms/createTaskSchema";
import useTaskContext from "@/features/task/hooks/useTaskContext";
import type { TaskPriorityType } from "@/features/task/models/taskPriority";
import { createTask } from "@/features/task/services/taskService";
import {
  Box,
  Flex,
  FormControl,
  FormErrorMessage,
  Input,
} from "@chakra-ui/react";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { zodResolver } from "@hookform/resolvers/zod";
import { Controller, useForm } from "react-hook-form";
import PrioritySelectBox from "../PrioritySelectBox";

const AddTaskInput = () => {
  const {
    register,
    handleSubmit,
    control,
    formState: { errors },
    reset,
  } = useForm<CreateTaskSchemaType>({
    resolver: zodResolver(TaskSchema),
    defaultValues: createTaskSchemaDefaultValues,
  });
  const { selectedProject } = useProjectContext();
  const { tasks, setTasks } = useTaskContext();

  const onSubmit = async (data: CreateTaskSchemaType) => {
    const task = await createTask({ ...data, projectId: selectedProject?.id });
    setTasks([...tasks, task]);
    reset();
  };

  return (
    <form id="AddTaskInputForm" onSubmit={handleSubmit(onSubmit)}>
      <Flex width="inherit" justifyContent="space-between">
        <Flex justifyContent="flex-start" alignItems="center" width="100%">
          <Box px={2}>
            <FontAwesomeIcon icon={faPlus} />
          </Box>
          <FormControl
            id="name"
            isInvalid={!!errors.name}
            isRequired
            width="inherit"
          >
            <Input
              placeholder="追加したいタスク名を入力してください。Enterを押すと追加されます。"
              {...register("name")}
              border="none"
              width="100%"
              _focus={{ border: "none", boxShadow: "none" }}
              _invalid={{ border: "none", boxShadow: "none" }}
            />
          </FormControl>
        </Flex>
        <Box>
          <Controller
            name="priority"
            control={control}
            render={({ field }) => (
              <PrioritySelectBox
                value={field.value}
                onChange={(priority) => field.onChange(priority.value)}
              />
            )}
          />
        </Box>
      </Flex>
      <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
    </form>
  );
};

export default AddTaskInput;
