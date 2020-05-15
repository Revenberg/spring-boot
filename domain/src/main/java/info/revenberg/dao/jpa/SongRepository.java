package info.revenberg.dao.jpa;

import info.revenberg.domain.Bundle;
import info.revenberg.domain.Song;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//, PagingAndSortingRepository<Song, Long> 
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findSongByName(String name);

    Song findSongByBundle(Bundle bundle);

    Page<Song> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM song s, bundle b where s.name=:name AND b.id=:id AND b.id=s.fk_bundle ", nativeQuery = true)
    Song findSongByNameInBundle(@Param("name") String name, @Param("id") long id);

    @Query(value = "SELECT COALESCE(max(id), 0) FROM song s ", nativeQuery = true)
    long getId();

    @Query(value = "SELECT * FROM song s, bundle b where b.id=:id AND b.id=s.fk_bundle ", nativeQuery = true)
    Page<Song> findAllOfBundle(Pageable pageable, @Param("id") long id);

    @Query(value = "SELECT * FROM song s, bundle b where b.id=:id AND b.id=s.fk_bundle ", nativeQuery = true)
    List<Song> findAllById(@Param("id") long id);

    @Query(value = "SELECT COALESCE(max(id), 0) FROM song s ", nativeQuery = true)
    Long getMaxId();
}
