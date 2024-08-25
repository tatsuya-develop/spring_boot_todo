import type Tag from "@/features/tag/models/tag";
import { createContext, useContext, useState } from "react";

interface TagContextType {
  tags: Tag[];
  setTags: (tags: Tag[]) => void;
}

const TagContext = createContext<TagContextType | undefined>(undefined);

export const TagProvider = ({ children }: { children: React.ReactNode }) => {
  const [tags, setTags] = useState<Tag[]>([]);

  return (
    <TagContext.Provider value={{ tags, setTags }}>
      {children}
    </TagContext.Provider>
  );
};

export const useTagContext = (): TagContextType => {
  const context = useContext(TagContext);
  if (!context) {
    throw new Error("useTags must be used within a TagProvider");
  }
  return context;
};
