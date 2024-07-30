import { FastifyInstance } from "fastify";
import { redirectHandler } from "./link.controller";
import { $ref } from "./link.schema";

async function linkRoutes(server: FastifyInstance) {
  server.get(
    ":link",
    { schema: { params: { link: $ref("linkSchema") } } },
    redirectHandler
  );
}

export default linkRoutes;
