import {
  type UpdateTaskSchemaType,
  generateUpdateTaskSchema,
} from "@/features/task/forms/updateTaskSchema";
import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import { Textarea } from "@chakra-ui/react";
import { useEffect, useState } from "react";

type Props = {
  onBlur: (task: UpdateTaskSchemaType) => void;
};

const RightBarInputMemo = ({ onBlur }: Props) => {
  const selectedTask = useGetSelectedTask();
  const [memo, setMemo] = useState<string>(selectedTask.memo);
  useEffect(() => setMemo(selectedTask.memo), [selectedTask]);

  const onChangeMemo = (e: React.ChangeEvent<HTMLTextAreaElement>) =>
    setMemo(e.target.value);
  const onBlurMemo = async (e: React.FocusEvent<HTMLTextAreaElement>) => {
    const task = generateUpdateTaskSchema(selectedTask);
    task.memo = e.target.value;

    onBlur(task);
  };

  return (
    <Textarea
      flex={1}
      value={memo}
      onChange={onChangeMemo}
      onBlur={onBlurMemo}
      placeholder="必要であればメモを自由に入力してください"
      resize="none"
    />
  );
};

export default RightBarInputMemo;
