import { IconButton } from "@chakra-ui/react";
import { faCircle } from "@fortawesome/free-regular-svg-icons";
import { faCircleCheck } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

type Props<T> = {
  item: T;
  onClick: (item: T) => void;
  isChecked: (item: T) => boolean;
};

const ToggleIconButton = <T,>({ item, onClick, isChecked }: Props<T>) => {
  const getIcon = (item: T) => (isChecked(item) ? faCircleCheck : faCircle);

  return (
    <IconButton
      icon={<FontAwesomeIcon icon={getIcon(item)} />}
      aria-label="Toggle task completed"
      onClick={() => onClick(item)}
      bg="transparent"
      _hover={{ bg: "transparent" }}
    />
  );
};

export default ToggleIconButton;
