import { z } from "zod";
import { buildJsonSchemas } from "fastify-zod";

const linkSchema = z.string().length(7);

export const { schemas: linkSchemas, $ref } = buildJsonSchemas({
  linkSchema,
});
