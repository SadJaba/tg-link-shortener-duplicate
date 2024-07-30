package com.group_project.telegramlkbot.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkEntity, String> {

    List<LinkEntity> findBySourceUrl(String sourceUrl);

    List<LinkEntity> findByShortenUrl(String shortenUrl);

    List<LinkEntity> findByAuthorId(Long authorId);

}
