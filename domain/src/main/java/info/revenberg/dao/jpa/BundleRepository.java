package info.revenberg.dao.jpa;

import info.revenberg.domain.Bundle;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.PagingAndSortingRepository;

// , PagingAndSortingRepository<Bundle, Long>
public interface BundleRepository extends JpaRepository<Bundle, Long> {
    Page<Bundle> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM bundle b order by name", nativeQuery = true)
    List<Bundle> findAll();

    Bundle findBundleByName(String name);

    Bundle findBundleByMnemonic(String mnemonic);

    List<Bundle> findBundleAllByName(String name);

    List<Bundle> findBundleAllByMnemonic(String mnemonic);
    
    List<Bundle> findAllBundleByMnemonic(String mnemonic);

    @Query(value = "SELECT COALESCE(max(bundleid), 0) FROM bundle b ", nativeQuery = true)
    Long getMaxBundleId();

}
