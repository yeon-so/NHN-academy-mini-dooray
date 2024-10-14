package com.nhnacademy.miniDooray.service;

import com.nhnacademy.miniDooray.entity.Project;
import com.nhnacademy.miniDooray.entity.Tag;
import com.nhnacademy.miniDooray.exception.ProjectNotFoundException;
import com.nhnacademy.miniDooray.exception.TagNotFoundException;
import com.nhnacademy.miniDooray.exception.TagNotFoundInProjectException;
import com.nhnacademy.miniDooray.repository.ProjectRepository;
import com.nhnacademy.miniDooray.repository.ProjectTagRepository;
import com.nhnacademy.miniDooray.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;
    private final ProjectTagRepository projectTagRepository;


    public void create(long projectId, String tagName) {
        // 프로젝트를 찾아서 존재하는지 확인
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException());

        // 새 태그 객체 생성 및 속성 설정
        Tag tag = new Tag();
        tag.setProject(project); // 태그에 프로젝트 설정
        tag.setTagName(tagName); // 태그 이름 설정

        // 태그를 데이터베이스에 저장
        tagRepository.save(tag);
    }

    public void update(long tagId, long projectId, String tagName) {
        Tag tag = tagRepository.findById(tagId)
                        .orElseThrow(() -> new TagNotFoundException());
        if (!tag.getProject().getId().equals(projectId)) {
            throw new TagNotFoundInProjectException();
        }
        tag.setTagName(tagName);
        tagRepository.save(tag);
    }

    public void delete(long projectId, long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new TagNotFoundException();
        }

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new TagNotFoundException());
        if (!tag.getProject().getId().equals(projectId)) {
            throw new TagNotFoundInProjectException();
        }

        projectTagRepository.deleteAllByTagId(tagId);

        tagRepository.deleteById(tagId);
    }
}
