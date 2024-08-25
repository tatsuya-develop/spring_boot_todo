import { Box, Flex } from "@chakra-ui/react";
import LeftBarFooter from "./Footer";
import LeftBarList from "./List";

const LeftBar = () => {
  return (
    <Box height="100%" width="100%" p={1}>
      <Flex direction="column" height="inherit" justifyContent="space-between">
        <LeftBarList />
        <LeftBarFooter />
      </Flex>
    </Box>
  );
};

export default LeftBar;
