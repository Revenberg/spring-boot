package info.revenberg.dao.jpa;

import info.revenberg.domain.Vers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// extends PagingAndSortingRepository<Vers, Long>
public interface VersRepository extends JpaRepository<Vers, Long> {
    Vers findVersByName(String name);

    Page<Vers> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM vers v, song s where v.rank=:rank AND s.id=:id AND s.id=v.fk_song", nativeQuery = true)
    Vers findVersInSong(@Param("rank") int rank, @Param("id") long id);

    @Query(value = "SELECT * FROM vers v, song s where s.id=:id AND s.id=v.fk_song order by v.rank", nativeQuery = true)
    List<Vers> findAllById(@Param("id") long id);

    @Query(value = "SELECT COALESCE(min(v.id), 0) FROM vers v where v.id>:id group by v.id", nativeQuery = true)
    long findNextId(@Param("id") long id);

    @Query(value = "SELECT COALESCE(max(id), 0) FROM vers v ", nativeQuery = true)
    Long getMaxId();
}
