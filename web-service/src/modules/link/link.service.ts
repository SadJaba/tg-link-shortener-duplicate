import { Link, PrismaClient } from "@prisma/client";

export async function findLink(
  prisma: PrismaClient,
  shorten_url: string
): Promise<Link | null> {
  const link = await prisma.link.findUnique({
    where: {
      shorten_url,
    },
  });
  return link;
}

export async function updateRedirects(prisma: PrismaClient, link: Link) {
  await prisma.link.update({
    where: {
      id: link.id,
    },
    data: {
      redirect_count: {
        increment: 1,
      },
    },
  });
}
