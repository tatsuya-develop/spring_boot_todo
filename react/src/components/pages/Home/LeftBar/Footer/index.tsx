import SaveProjectModal from "@/features/project/components/SaveProjectModal";
import SaveTagModal from "@/features/tag/components/SaveTagModal";
import {
  Box,
  Button,
  Flex,
  IconButton,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { faPlus, faTag } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const LeftBarFooter = () => {
  const addProjectDisclosure = useDisclosure();
  const addTagDisclosure = useDisclosure();

  return (
    <>
      <Flex justifyContent={"space-between"} py={1}>
        <Box>
          <Button
            leftIcon={<FontAwesomeIcon icon={faPlus} />}
            variant="transparent"
            onClick={addProjectDisclosure.onOpen}
          >
            <Text mt={0.5}>プロジェクトの追加</Text>
          </Button>
        </Box>
        <Box>
          <IconButton
            icon={<FontAwesomeIcon icon={faTag} />}
            aria-label="Add tag"
            onClick={addTagDisclosure.onOpen}
            bg="transparent"
          />
        </Box>
      </Flex>
      <SaveProjectModal
        isOpen={addProjectDisclosure.isOpen}
        onClose={addProjectDisclosure.onClose}
      />
      <SaveTagModal
        isOpen={addTagDisclosure.isOpen}
        onClose={addTagDisclosure.onClose}
      />
    </>
  );
};

export default LeftBarFooter;
