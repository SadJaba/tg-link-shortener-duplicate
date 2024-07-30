import Fastify, { FastifyServerOptions } from "fastify";
import prismaPlugin from "./prisma.plugin";
import linkRoutes from "./modules/link/link.route";
import { linkSchemas } from "./modules/link/link.schema";

export type AppOptions = Partial<FastifyServerOptions>;

async function buildApp(options: AppOptions = {}) {
  const fastify = Fastify(options);
  fastify.register(prismaPlugin);
  for (const schema of [...linkSchemas]) {
    fastify.addSchema(schema);
  }
  fastify.register(linkRoutes, { prefix: "r/" });
  return fastify;
}

export { buildApp };
