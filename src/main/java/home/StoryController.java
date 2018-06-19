package home;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/stories")
public class StoryController {

    private long counter = 1;
    private Map<Long, Story> stories = new HashMap<>();

    @GetMapping
    public ResponseEntity<List<Story>> list() {
        return new ResponseEntity<>(new ArrayList<>(stories.values()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Story> read(@PathVariable Long id) {
        Story story = stories.get(id);
        if (story != null) {
            return new ResponseEntity<>(story, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Story> createStory(@RequestBody Story story){
        story.setId(counter);
        stories.put(counter, story);
        counter++;
        return new ResponseEntity<>(story, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Story> updateStory(@PathVariable Long id, @RequestBody Story story){
        Story localStory = stories.get(id);
        localStory.setProjectId(story.getProjectId());
        localStory.setName(story.getName());
        localStory.setInfo(story.getInfo());
        stories.put(id, localStory);
        return new ResponseEntity<>(localStory, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Story> delete(@PathVariable Long id) {
        stories.remove(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/reset")
    public ResponseEntity<Story> reset(){
        stories.clear();
        counter = 1;
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/clear")
    public ResponseEntity<Story> clear(){
        stories.clear();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
