import type { ModalSlimProps } from "@/types/modal";
import {
  Button,
  Flex,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
} from "@chakra-ui/react";
import { zodResolver } from "@hookform/resolvers/zod";
import { useForm } from "react-hook-form";
import { useTagContext } from "../../contexts";
import TagSchema, { type TagSchemaType } from "../../forms/tagSchema";
import { createTag } from "../../services/tagService";

const SaveTagModal = (props: ModalSlimProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<TagSchemaType>({ resolver: zodResolver(TagSchema) });
  const { tags, setTags } = useTagContext();

  const onSubmit = async (data: TagSchemaType) => {
    const tag = await createTag(data);
    setTags([...tags, tag]);
    onModalClose();
    reset();
  };

  const onModalClose = () => {
    props.onClose();
    reset();
  };

  return (
    <Modal isOpen={props.isOpen} onClose={onModalClose} isCentered>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>タグの追加</ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <form id="SaveTagModalForm" onSubmit={handleSubmit(onSubmit)}>
            <Flex direction="column" gap={4}>
              <FormControl id="name" isInvalid={!!errors.name} isRequired>
                <FormLabel>タグ名</FormLabel>
                <Input
                  placeholder="タグ名を入力してください"
                  {...register("name")}
                />
                <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
              </FormControl>
              <FormControl id="summary">
                <FormLabel>概要</FormLabel>
                <Input
                  placeholder="概要を入力してください"
                  {...register("summary")}
                />
                <FormErrorMessage>{errors.summary?.message}</FormErrorMessage>
              </FormControl>
            </Flex>
          </form>
        </ModalBody>
        <ModalFooter>
          <Flex justifyContent={"space-between"} width="100%">
            <Button colorScheme="gray" mr={3} onClick={onModalClose}>
              閉じる
            </Button>
            <Button type="submit" colorScheme="blue" form="SaveTagModalForm">
              追加
            </Button>
          </Flex>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

export default SaveTagModal;
