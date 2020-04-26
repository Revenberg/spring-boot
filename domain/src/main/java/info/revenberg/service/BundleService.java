package info.revenberg.service;

import info.revenberg.domain.Bundle;
import info.revenberg.dao.jpa.BundleRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class BundleService {
    @Autowired
    private BundleRepository bundleRepository;
    
    public BundleService() {    
    }

    public Bundle createBundle(Bundle bundle) {
        if (bundle.getBundleid() == 0) {
            bundle.setBundleid(this.getMaxBundleId() + 1);            
        }
        return bundleRepository.save(bundle);        
    }

    public Optional<Bundle> getBundle(long id) {
        return bundleRepository.findById(id);
    }

    public void updateBundle(Bundle bundle) {
        bundleRepository.save(bundle);
    }

    public void deleteBundle(Long id) {
        bundleRepository.deleteById(id);
    }

    public Page<Bundle> getAllBundles(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<Bundle> pageOfBundles = bundleRepository.findAll(pageable);
        return pageOfBundles;
    }

    public List<Bundle> getAllBundlesByName(String name) {
        return bundleRepository.findBundleAllByName(name);
    }

    public List<Bundle> getAllBundlesByMnemonic(String mnemonic) {

        return bundleRepository.findAllBundleByMnemonic(mnemonic);
    }

    public Bundle getBundleByName(String bundle) {
        return bundleRepository.findBundleByName(bundle);
    }

    public Bundle getBundleByMnemonic(String mnemonic) {

        return bundleRepository.findBundleByMnemonic(mnemonic);
    }

	public Long getMaxBundleId() {
		return bundleRepository.getMaxBundleId();
	}

}
