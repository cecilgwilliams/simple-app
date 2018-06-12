package home;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class StoryRepository {

    private final JdbcTemplate jdbcTemplate;

    public StoryRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("create table if not exists backlog "
                + "( id BIGINT(20) NOT NULL AUTO_INCREMENT, project_id BIGINT(20), name varchar(100), info varchar(200), PRIMARY KEY (id) );");
    }

    public Story create(Story Story) {
        KeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO backlog (project_id, name, info) VALUES (?, ?, ?)",
                    RETURN_GENERATED_KEYS
            );

            statement.setLong(1, Story.getProjectId());
            statement.setString(2, Story.getName());
            statement.setString(3, Story.getInfo());

            return statement;
        }, generatedKeyHolder);

        return find(generatedKeyHolder.getKey().longValue());
    }

    public Story find(Long id) {
        return jdbcTemplate.query(
                "SELECT id, project_id, name, info FROM backlog WHERE id = ?",
                new Object[]{id},
                extractor);
    }

    public List<Story> list() {
        return jdbcTemplate.query("SELECT id, project_id, name, info FROM backlog", mapper);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM backlog WHERE id = ?", id);
    }

    private final RowMapper<Story> mapper = (rs, rowNum) -> new Story(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getString("name"),
            rs.getString("info")
    );

    private final ResultSetExtractor<Story> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
