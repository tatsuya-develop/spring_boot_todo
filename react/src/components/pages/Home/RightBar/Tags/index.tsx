import SelectTags from "@/features/tag/components/SelectTags";
import type Tag from "@/features/tag/models/tag";
import {
  type UpdateTaskSchemaType,
  generateUpdateTaskSchema,
} from "@/features/task/forms/updateTaskSchema";
import useGetSelectedTask from "@/features/task/hooks/useGetSelectedTask";
import {
  Tag as ChakraTag,
  Flex,
  TagCloseButton,
  TagLabel,
} from "@chakra-ui/react";

type Props = {
  onChange: (task: UpdateTaskSchemaType) => void;
};

const RightBarTags = ({ onChange }: Props) => {
  const selectedTask = useGetSelectedTask();
  const onCloseTag = (tagId: number) => {
    const tags = selectedTask.tags.filter((tag) => tag.id !== tagId);
    onSelectTags(tags);
  };

  const onSelectTags = (tags: Tag[]) => {
    const task = generateUpdateTaskSchema(selectedTask);
    task.tagIds = tags.map((tag) => tag.id);

    onChange(task);
  };

  return (
    <Flex justifyItems="center" gap={2} p={3}>
      {selectedTask.tags.map((tag) => (
        <ChakraTag
          key={tag.id}
          borderRadius="full"
          variant="solid"
          colorScheme="pink"
        >
          <TagLabel>{tag.name}</TagLabel>
          <TagCloseButton onClick={() => onCloseTag(tag.id)} />
        </ChakraTag>
      ))}
      <SelectTags selectedTags={selectedTask.tags} onClick={onSelectTags} />
    </Flex>
  );
};

export default RightBarTags;
