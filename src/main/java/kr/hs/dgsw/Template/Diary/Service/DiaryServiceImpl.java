package kr.hs.dgsw.Template.Diary.Service;

import kr.hs.dgsw.Template.Diary.DTO.DiaryDTO;
import kr.hs.dgsw.Template.Diary.DTO.DiaryReq;
import kr.hs.dgsw.Template.Diary.Entity.DiaryEntity;
import kr.hs.dgsw.Template.Diary.Repository.DiaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    public Long register(DiaryReq req) {
        DiaryEntity _save = diaryRepository.save(DiaryReq.toEntity(req));
        return _save.getId();
    }

    @Override
    public List<DiaryDTO> getList() {
        List<DiaryEntity> _result = diaryRepository.findAll();
        return _result.stream().map( DiaryEntity::toDTO ).toList();
    }

    @Override
    public DiaryDTO get(Long id) {
       Optional<DiaryEntity> _result = diaryRepository.findById(id);
       return _result.map(DiaryEntity::toDTO).orElse(null);
    }

    @Override
    public void modify(Long id, DiaryReq req) {
        DiaryEntity _entity = DiaryEntity.builder()
                .id(id)
                .title(req.getTitle())
                .content(req.getContent())
                .writer(req.getWriter())
                .likes(req.getLikes())
                .weather(req.getWeather())
                .build();
        diaryRepository.save(_entity);
    }

    @Override
    public void remove(Long id) {
        diaryRepository.deleteById(id);
    }

    @Override
    public void like(Long id) {
        DiaryEntity _entity = diaryRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("문제 발생");
        });

       _entity.plusLikes();

       diaryRepository.save(_entity);




    }
}
