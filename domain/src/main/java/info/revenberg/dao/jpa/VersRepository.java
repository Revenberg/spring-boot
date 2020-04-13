package info.revenberg.dao.jpa;

import info.revenberg.domain.Vers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VersRepository extends PagingAndSortingRepository<Vers, Long> {
    Vers findVersByName(String name);

    Page<Vers> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM vers v, song s where v.rank=:rank AND s.songid=:songid AND s.songid=v.fk_song", nativeQuery = true)
    Vers findVersInSong(@Param("rank") int rank, @Param("songid") long songid);

    @Query(value = "SELECT * FROM vers v, song s where s.songid=:songid AND s.songid=v.fk_song order by v.rank", nativeQuery = true)
    List<Vers> findAllByVersid(@Param("songid") long songid);

    @Query(value = "SELECT min(v.id) FROM vers v where v.id>:versid order by v.id", nativeQuery = true)
    long findNextId(@Param("versid") long versid);

    @Query(value = "SELECT COALESCE(max(bundleid), 0) FROM vers v ", nativeQuery = true)
    Long getMaxVersId();
}
