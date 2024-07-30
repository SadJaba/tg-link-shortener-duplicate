import { buildApp, AppOptions } from "./app";
import dotenv from "dotenv";

dotenv.config();

const options: AppOptions = {
  logger: true,
};

const start = async () => {
  const app = await buildApp(options);

  try {
    await app.listen({
      port: process.env.PORT ? parseInt(process.env.PORT) : 3000,
      host: process.env.HOST ? process.env.HOST : "localhost",
    });
  } catch (err) {
    app.log.error(err);
    process.exit(1);
  }
};

start();
