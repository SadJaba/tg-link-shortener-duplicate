import { PrismaClient } from "@prisma/client";
import { randomBytes } from "crypto";

function generateSecureRandomString(length: number): string {
  const characters =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  let result = "";
  const bytes = randomBytes(length);
  for (let i = 0; i < length; i++) {
    result += characters.charAt(bytes[i] % characters.length);
  }
  return result;
}

async function createUniqueShortenURL(attempt = 0): Promise<string> {
  const maxAttempts = 10;
  if (attempt >= maxAttempts) {
    throw new Error(
      "Превышено количество попыток генерации уникальной короткой ссылки."
    );
  }

  const shorten_url = `${generateSecureRandomString(7)}`;

  const existingLink = await prisma.link.findUnique({
    where: { shorten_url },
  });

  if (existingLink) {
    return createUniqueShortenURL(attempt + 1);
  } else {
    return shorten_url;
  }
}

const prisma = new PrismaClient();
async function main() {
  for (let i = 1; i <= 10; i++) {
    const user = await prisma.user.upsert({
      where: { telegram_id: i },
      update: {},
      create: {
        telegram_id: i,
      },
    });

    for (let j = 1; j <= 3; j++) {
      const shortenURL = await createUniqueShortenURL();
      await prisma.link.upsert({
        where: { shorten_url: `${shortenURL}` },
        update: {},
        create: {
          source_url: `https://example${i}${j}.com`,
          shorten_url: `${shortenURL}`,
          author_id: user.telegram_id,
        },
      });
    }
  }
}

main()
  .then(async () => {
    await prisma.$disconnect();
  })
  .catch(async (e) => {
    console.error(e);
    await prisma.$disconnect();
    process.exit(1);
  });
