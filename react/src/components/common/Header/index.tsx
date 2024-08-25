import { Box, Heading } from "@chakra-ui/react";

const Header = () => {
  return (
    <Box as="header" p={2} borderBottom="1px" borderColor="gray.200">
      <Heading as="h1" size="lg">
        ToDo Learning App
      </Heading>
    </Box>
  );
};

export default Header;
