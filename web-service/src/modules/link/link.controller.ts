import { FastifyReply, FastifyRequest, FastifyInstance } from "fastify";
import { findLink, updateRedirects } from "./link.service";

export async function redirectHandler(
  this: FastifyInstance,
  request: FastifyRequest<{ Params: { link: string } }>,
  reply: FastifyReply
) {
  const link = await findLink(this.prisma, request.params.link);
  if (link === null) return reply.status(404).send("Not Found");
  reply.redirect(link.source_url);
  updateRedirects(this.prisma, link);
}
