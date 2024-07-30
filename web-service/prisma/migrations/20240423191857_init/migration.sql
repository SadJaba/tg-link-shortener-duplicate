-- CreateTable
CREATE TABLE "links" (
    "id" TEXT NOT NULL,
    "source_url" VARCHAR(255) NOT NULL,
    "shorten_url" VARCHAR(255) NOT NULL,
    "redirect_count" INTEGER NOT NULL DEFAULT 0,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,
    "author_id" BIGINT NOT NULL,

    CONSTRAINT "links_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "users" (
    "telegram_id" BIGINT NOT NULL,

    CONSTRAINT "users_pkey" PRIMARY KEY ("telegram_id")
);

-- CreateIndex
CREATE UNIQUE INDEX "links_shorten_url_key" ON "links"("shorten_url");

-- AddForeignKey
ALTER TABLE "links" ADD CONSTRAINT "links_author_id_fkey" FOREIGN KEY ("author_id") REFERENCES "users"("telegram_id") ON DELETE RESTRICT ON UPDATE CASCADE;
