import ToggleIconButton from "@/components/common/ToggleIconButton";
import { useTagContext } from "@/features/tag/contexts";
import type Tag from "@/features/tag/models/tag";
import {
  Button,
  ButtonGroup,
  Flex,
  Icon,
  List,
  ListItem,
  Popover,
  PopoverArrow,
  PopoverBody,
  PopoverContent,
  PopoverFooter,
  PopoverTrigger,
  useDisclosure,
} from "@chakra-ui/react";
import { faPlus, faTag } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";

type Props = {
  selectedTags: Tag[];
  onClick: (tags: Tag[]) => void;
};

const SelectTags = ({ selectedTags, onClick }: Props) => {
  const { tags } = useTagContext();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [selectingTags, setSelectingTags] = useState<Tag[]>(selectedTags);
  const isSelecting = (tag: Tag) => selectingTags.some((t) => t.id === tag.id);
  const addTag = (tag: Tag) => setSelectingTags([...selectingTags, tag]);
  const removeTag = (tag: Tag) => {
    const filteredTags = selectingTags.filter((t) => t.id !== tag.id);
    setSelectingTags(filteredTags);
  };
  const toggleTag = (tag: Tag) =>
    isSelecting(tag) ? removeTag(tag) : addTag(tag);
  const handleUpdate = () => {
    onClick(selectingTags);
    onClose(); // Popoverを閉じる
  };
  const handleClose = () => {
    setSelectingTags(selectedTags);
    onClose(); // Popoverを閉じる
  };

  useEffect(() => {
    setSelectingTags(selectedTags);
  }, [selectedTags]);

  return (
    <Popover isOpen={isOpen} onOpen={onOpen} onClose={onClose}>
      <PopoverTrigger>
        {tags.length ? (
          <Button
            leftIcon={<FontAwesomeIcon icon={faPlus} />}
            colorScheme="gray"
            variant="outline"
            size="sm"
            borderRadius="full"
          >
            タグ
          </Button>
        ) : (
          <></>
        )}
      </PopoverTrigger>
      <PopoverContent>
        <PopoverArrow />
        <PopoverBody>
          <List spacing={3}>
            {tags.map((tag) => (
              <ListItem
                as={Flex}
                key={tag.id}
                cursor="pointer"
                onClick={() => toggleTag(tag)}
                justifyContent="space-between"
                alignItems="center"
                gap={2}
              >
                <Flex alignItems="center" gap={2}>
                  <Icon as={FontAwesomeIcon} icon={faTag} />
                  {tag.name}
                </Flex>
                <ToggleIconButton
                  item={tag}
                  onClick={toggleTag}
                  isChecked={isSelecting}
                />
              </ListItem>
            ))}
          </List>
        </PopoverBody>
        <PopoverFooter
          as={ButtonGroup}
          borderTop="none"
          justifyContent="center"
        >
          <Button colorScheme="gray" size="sm" onClick={handleClose}>
            キャンセル
          </Button>
          <Button colorScheme="teal" size="sm" onClick={handleUpdate}>
            更新
          </Button>
        </PopoverFooter>
      </PopoverContent>
    </Popover>
  );
};

export default SelectTags;
