package info.revenberg.dao.jpa;

import info.revenberg.domain.Line;
import info.revenberg.domain.Vers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LineRepository extends JpaRepository<Line, Long> {

    Page<Line> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM line l, vers v where l.rank=:rank AND v.id=:id AND s.id=v.fk_vers", nativeQuery = true)
    Line findLineInVers(@Param("rank") int rank, @Param("id") long id);

    @Query(value = "SELECT * FROM line l, vers v where v.id=:id AND v.id=l.fk_vers order by l.rank", nativeQuery = true)
    List<Vers> findAllById(@Param("id") long id);
}
