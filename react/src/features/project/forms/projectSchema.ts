import { z } from "zod";

const name = z
  .string({ required_error: "入力必須項目です" })
  .min(1, { message: "入力必須項目です" })
  .max(100, { message: "100文字以内で入力してください" });

const summary = z
  .string()
  .max(255, { message: "255文字以内で入力してください" });

const ProjectSchema = z.object({ name, summary });

export type ProjectSchemaType = z.infer<typeof ProjectSchema>;

export default ProjectSchema;
