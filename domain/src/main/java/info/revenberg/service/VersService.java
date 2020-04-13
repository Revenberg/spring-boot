package info.revenberg.service;

import info.revenberg.domain.Vers;
import info.revenberg.dao.jpa.VersRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class VersService {
    @Autowired
    private VersRepository versRepository;

    public VersService() {
    }

    public Vers createVers(Vers vers) {        
        Vers newVers = versRepository.save(vers);
        if (newVers.getVersid() == 0) {
            newVers.setVersid( newVers.getId() );
            newVers = versRepository.save(vers);
        }
        return newVers;
    }

    public Optional<Vers> getVers(long id) {
        return versRepository.findById(id);
    }

    public Long getNextVersId(long id) {
        return versRepository.findNextId(id);
    }    

    public void updateVers(Vers vers) {
        versRepository.save(vers);
    }

    public void deleteVers(Long id) {
        versRepository.deleteById(id);
    }
    
    public Page<Vers> getAllVerses(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<Vers> pageOfVerses = versRepository.findAll(pageable);
        return pageOfVerses;
    }

	public Vers findVersInSong(int rank, long songid) {
		return versRepository.findVersInSong(rank, songid);
	}
}
