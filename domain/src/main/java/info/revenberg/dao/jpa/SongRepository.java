package info.revenberg.dao.jpa;

import info.revenberg.domain.Bundle;
import info.revenberg.domain.Song;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Long>, PagingAndSortingRepository<Song, Long> {
    Song findSongByName(String name);

    Song findSongByBundle(Bundle bundle);

    Page<Song> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM song s, bundle b where s.name=:name AND b.bundleid=:bundleid AND b.bundleid=s.fk_bundle ", nativeQuery = true)
    Song findSongByNameInBundle(@Param("name") String name, @Param("bundleid") long bundleid);

    @Query(value = "SELECT COALESCE(max(songid), 0) FROM song s ", nativeQuery = true)
    long getSongId();

	@Query(value = "SELECT * FROM song s, bundle b where b.bundleid=:bundleid AND b.bundleid=s.fk_bundle ", nativeQuery = true)
    Page<Song> findAllOfBundle(Pageable pageable, @Param("bundleid") long bundleid);

	@Query(value = "SELECT * FROM song s, bundle b where b.id=:bundleid AND b.bundleid=s.fk_bundle ", nativeQuery = true)
    List<Song> findAllByBundleid(@Param("bundleid") long bundleid);
}
