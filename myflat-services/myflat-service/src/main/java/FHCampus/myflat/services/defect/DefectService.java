package fhcampus.myflat.services.defect;

import fhcampus.myflat.dtos.DefectDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DefectService {

    void reportDefect(DefectDto defectDto, MultipartFile image) throws IOException;

    List<DefectDto> getAllDefects();

    List<DefectDto> getDefectsByUserId(Long userId);
}
