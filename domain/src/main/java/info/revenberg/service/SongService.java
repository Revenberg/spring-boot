package info.revenberg.service;

import info.revenberg.domain.Song;
import info.revenberg.dao.jpa.SongRepository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class SongService {
    // private static final Logger logger =
    // Logger.getLogger(FileService.class.getName());

    @Autowired
    private SongRepository songRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public SongService() {
    }

    public Song createSong(Song song) {

        return songRepository.save(song);
    }

    public Optional<Song> getSong(long id) {
        return songRepository.findById(id);
    }

    public void updateSong(Song song) {
        songRepository.save(song);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public Page<Song> getAllSongs(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<Song> pageOfSongs = songRepository.findAll(pageable);
        return pageOfSongs;
    }

    public Song findSongByNameInBundle(String name, long id) {
        return songRepository.findSongByNameInBundle(name, id);
    }

    public Page<Song> getAllSongsOfBundle(Integer page, Integer size, Long id) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));

        Page<Song> pageOfSongs = songRepository.findAllOfBundle(pageable, id);
        return pageOfSongs;
    }

    public Long getMaxId() {
        return songRepository.getMaxId();
    }
}
