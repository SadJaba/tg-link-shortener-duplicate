import { PrismaClient } from "@prisma/client";
import fp from "fastify-plugin";

async function initDatabaseConnection(): Promise<PrismaClient> {
  const db = new PrismaClient();
  await db.$connect();
  return db;
}

declare module "fastify" {
  interface FastifyInstance {
    prisma: PrismaClient;
  }
}

const prismaPlugin = fp(async (server) => {
  const prisma = await initDatabaseConnection();

  server.decorate("prisma", prisma);
  server.addHook("onClose", async () => {
    await server.prisma.$disconnect();
  });
});

export default prismaPlugin;
