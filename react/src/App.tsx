import Header from "@/components/common/Header";
import Home from "@/pages";
import { Flex } from "@chakra-ui/react";

const App = () => {
  return (
    <Flex direction="column" height="100vh">
      <Header />
      <Home />
    </Flex>
  );
};

export default App;
