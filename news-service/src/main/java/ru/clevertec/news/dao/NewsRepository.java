package ru.clevertec.news.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.news.models.News;

/**
 * Repository for performing operation with news in DB
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
