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

    @Query(value = "SELECT * FROM line l, vers v where l.rank=:rank AND v.versid=:versid AND s.versid=v.fk_vers", nativeQuery = true)
    Line findLineInVers(@Param("rank") int rank, @Param("versid") long versid);

    @Query(value = "SELECT * FROM line l, vers v where v.versid=:versid AND v.versid=l.fk_vers order by l.rank", nativeQuery = true)
    List<Vers> findAllByVersid(@Param("versid") long versid);
}
