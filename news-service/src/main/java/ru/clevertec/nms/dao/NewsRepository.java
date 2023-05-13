package ru.clevertec.nms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.nms.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
