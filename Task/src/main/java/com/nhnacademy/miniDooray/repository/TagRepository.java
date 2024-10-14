package com.nhnacademy.miniDooray.repository;

import com.nhnacademy.miniDooray.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findTagByProjectIdAndTagName(long projectId, String tagName);
}
