import {
  TaskPriorityArray,
  type TaskPriorityType,
  getTaskPriority,
} from "@/features/task/models/taskPriority";
import {
  Button,
  Flex,
  Icon,
  Menu,
  MenuButton,
  MenuItem,
  MenuList,
  Text,
} from "@chakra-ui/react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type PrioritySelectBoxProps = {
  value: TaskPriorityType["value"];
  onChange: (value: TaskPriorityType) => void;
};

const PrioritySelectBox = (props: PrioritySelectBoxProps) => {
  const propsTaskPriority = getTaskPriority(props.value);

  return (
    <Menu>
      <MenuButton as={Button} bg="transparent">
        <Icon
          as={FontAwesomeIcon}
          icon={propsTaskPriority.icon}
          color={propsTaskPriority.color}
        />
      </MenuButton>
      <MenuList>
        {TaskPriorityArray.map((priority: TaskPriorityType) => (
          <MenuItem
            key={priority.value}
            onClick={() => props.onChange(priority)}
          >
            <Flex align="center">
              <Icon
                as={FontAwesomeIcon}
                icon={priority.icon}
                color={priority.color}
              />
              <Text as="span" ml={2}>
                {priority.label}
              </Text>
            </Flex>
          </MenuItem>
        ))}
      </MenuList>
    </Menu>
  );
};

export default PrioritySelectBox;
