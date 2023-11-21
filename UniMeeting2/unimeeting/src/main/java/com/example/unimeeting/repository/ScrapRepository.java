package com.example.unimeeting.repository;

import com.example.unimeeting.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScrapRepository extends JpaRepository<Scrap, Integer> {
        public boolean existsByMeetingIdxAndUserIdx(Integer meeting_idx, Integer user_idx);
        public void deleteByMeetingIdxAndUserIdx(Integer meeting_idx, Integer user_idx);

}
