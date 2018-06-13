package home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private StoryRepository storyRepo;

    public StoryController(StoryRepository storyRepo) {
        this.storyRepo = storyRepo;
    }

    @GetMapping
    public ResponseEntity<List<Story>> list() {
        return new ResponseEntity<>(storyRepo.list(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Story> read(@PathVariable Long id) {
        Story story = storyRepo.find(id);
        if (story != null) {
            return new ResponseEntity<>(story, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Story> update(@PathVariable Long id, @RequestBody Story story) {
        Story updatedStory = storyRepo.update(id, story);
        if (updatedStory != null) {
            return new ResponseEntity<>(updatedStory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story){
        Story createdStory = storyRepo.create(story);
        return new ResponseEntity<>(createdStory, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Story> delete(@PathVariable Long id) {
        storyRepo.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
