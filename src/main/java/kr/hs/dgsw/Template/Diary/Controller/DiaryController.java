package kr.hs.dgsw.Template.Diary.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.dgsw.Template.Diary.DTO.DiaryDTO;
import kr.hs.dgsw.Template.Diary.DTO.DiaryReq;
import kr.hs.dgsw.Template.Diary.DTO.DiaryResponse;
import kr.hs.dgsw.Template.Diary.Service.DiaryService;
import kr.hs.dgsw.Template.Diary.Service.Query.DiaryQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
@Tag(name = "일기")
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;
    private final DiaryQueryService queryService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody DiaryReq _dto) {
        Long _register = diaryService.register( _dto );

        return ResponseEntity.status(HttpStatus.CREATED).body( _register.toString() );
    }

    @GetMapping("/all")
    public ResponseEntity<List<DiaryResponse>> findAll(){
        return ResponseEntity.ok(queryService.findDiaryList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryResponse> get(@PathVariable Long id) {
        DiaryResponse _dto = queryService.findDiaryById(id);
        if (_dto == null) {
            return ResponseEntity.notFound().build();
        }
        else{
            return ResponseEntity.ok(_dto);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> modify(@PathVariable Long id,@RequestBody DiaryReq req) {
        diaryService.modify( id , req );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<String> remove(Long id) {
        diaryService.remove(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/like")
    public ResponseEntity<String> like(Long id) {
        diaryService.like(id);
        return ResponseEntity.ok().build();
    }
}
