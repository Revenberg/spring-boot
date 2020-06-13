package info.revenberg.service;

import info.revenberg.domain.Line;
import info.revenberg.dao.jpa.LineRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Service
public class LineService {
    @Autowired
    private LineRepository lineRepository;

    public LineService() {
    }

    public Line createLine(Line line) {
        
        line = lineRepository.save(line);
        return line;
    }

    public Optional<Line> getLine(long id) {
        return lineRepository.findById(id);
    }

    public void updateLine(Line line) {
        lineRepository.save(line);
    }

    public void deleteLine(Long id) {
        lineRepository.deleteById(id);
    }
    
    public Page<Line> getAllLines(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));

        Page<Line> pageOflines = lineRepository.findAll(pageable);
        return pageOflines;
    }

	public Line findLineInVers(int rank, long lineid) {
		return lineRepository.findLineInVers(rank, lineid);
	}

	public void deleteAll() {
        lineRepository.deleteAll();
	}
}
