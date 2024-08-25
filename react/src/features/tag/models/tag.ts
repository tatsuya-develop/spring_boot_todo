import { isBefore } from "date-fns";

interface Tag {
  id: number;
  name: string;
  summary: string;
  createdAt: Date;
  updatedAt: Date;
  deletedAt: Date;
}

export const isTagDeleted = (tag: Tag) => {
  return !!tag.deletedAt && isBefore(tag.deletedAt, new Date());
};

export default Tag;
