package com.group_project.telegramlkbot.handlers;

import com.group_project.telegramlkbot.data.LinkEntity;
import com.group_project.telegramlkbot.data.LinkRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LinkDAO {
    private final LinkRepository linkRepository;

    public LinkDAO(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public void addLink(LinkEntity link) {
        linkRepository.save(link);
    }

    public List<LinkEntity> findLink(String sourceUrl) {
        return linkRepository.findBySourceUrl(sourceUrl);
    }

    public List<LinkEntity> findShortenLink(String shortenUrl) {
        return linkRepository.findByShortenUrl(shortenUrl);
    }

    public List<LinkEntity> findTable(Long authorId) {
        return linkRepository.findByAuthorId(authorId);
    }

    public void deleteLink(LinkEntity link) {
        linkRepository.delete(link);
    }
}
