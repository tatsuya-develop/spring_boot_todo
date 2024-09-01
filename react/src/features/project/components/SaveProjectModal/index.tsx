import useProjectContext from "@/features/project/hooks/useProjectContext";
import ProjectSchema, {
  type ProjectSchemaType,
} from "@/features/project/forms/projectSchema";
import { createProject } from "@/features/project/services/projectService";
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

const SaveProjectModal = (props: ModalSlimProps) => {
  const {
    register,
    handleSubmit,
    formState: { errors },
    reset,
  } = useForm<ProjectSchemaType>({ resolver: zodResolver(ProjectSchema) });
  const { projects, setProjects } = useProjectContext();

  const onSubmit = async (data: ProjectSchemaType) => {
    const project = await createProject(data);
    setProjects([...projects, project]);
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
        <ModalHeader>プロジェクトの追加</ModalHeader>
        <ModalCloseButton />
        <ModalBody>
          <form id="SaveProjectModalForm" onSubmit={handleSubmit(onSubmit)}>
            <Flex direction="column" gap={4}>
              <FormControl id="name" isInvalid={!!errors.name} isRequired>
                <FormLabel>プロジェクト名</FormLabel>
                <Input
                  placeholder="プロジェクト名を入力してください"
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
            <Button
              type="submit"
              colorScheme="blue"
              form="SaveProjectModalForm"
            >
              追加
            </Button>
          </Flex>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};

export default SaveProjectModal;
