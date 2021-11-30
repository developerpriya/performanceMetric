package com.metric.performance.repository;

import com.metric.performance.model.Webpage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebpageRepository extends JpaRepository<Webpage,Long> {
}
