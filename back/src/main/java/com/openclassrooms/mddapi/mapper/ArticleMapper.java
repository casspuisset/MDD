package com.openclassrooms.mddapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.mddapi.dto.Articles.ArticleDto;
import com.openclassrooms.mddapi.models.Article;
import com.openclassrooms.mddapi.models.Topic;
import com.openclassrooms.mddapi.models.User;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    @Mapping(target = "id", source = "article.id")
    @Mapping(target = "title", source = "article.title")
    @Mapping(target = "description", source = "article.description")
    @Mapping(target = "topicId", source = "article.topicId")
    @Mapping(target = "topicName", source = "topic.name")
    @Mapping(target = "createdAt", source = "article.createdAt")
    @Mapping(target = "updatedAt", source = "article.updatedAt")
    @Mapping(target = "creatorId", source = "user.id")
    @Mapping(target = "creatorUsername", source = "user.name")
    public ArticleDto mapToDto(Article article, User user, Topic topic);

}
