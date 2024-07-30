FROM node:21-alpine AS builder

WORKDIR /app

COPY package.json yarn.lock ./

RUN yarn install

COPY . .

RUN yarn prisma generate

RUN yarn build

FROM node:21-alpine

WORKDIR /app

COPY package.json yarn.lock ./

COPY prisma ./

RUN yarn install --production

COPY --from=builder /app/node_modules/@prisma/client /app/node_modules/@prisma/client

COPY --from=builder /app/dist ./dist

CMD yarn prisma migrate deploy && node dist/src/main.js